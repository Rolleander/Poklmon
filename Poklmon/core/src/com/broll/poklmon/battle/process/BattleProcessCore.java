package com.broll.poklmon.battle.process;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.calc.AttackHitCalculator;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.util.BattleMove;
import com.broll.poklmon.battle.util.BattleMoveType;
import com.broll.poklmon.battle.util.PoklmonTeamCheck;
import com.broll.poklmon.battle.util.ProcessThreadHandler;
import com.broll.poklmon.battle.util.flags.BattleEventFlags;
import com.broll.poklmon.battle.process.callbacks.PoklmonEnterCallback;
import com.broll.poklmon.battle.process.callbacks.RoundEndCallback;
import com.broll.poklmon.battle.process.callbacks.RoundStartCallback;
import com.broll.poklmon.network.NetworkEndpoint;
import com.broll.poklmon.network.NetworkException;
import com.broll.poklmon.save.PoklmonStatistic;
import com.broll.poklmon.script.ScriptProcessingRunnable;

public class BattleProcessCore extends ScriptProcessingRunnable {

    protected BattleManager manager;
    protected ProcessThreadHandler processThreadHandler;
    private boolean battleOver = false;

    private NetworkEndpoint networkEndpoint;
    private BattleProcessAttack attackProcess;
    private BattleProcessEffects effectProcess;
    private BattleProcessParams paramProcess;
    private BattleProcessEXP expProcess;
    private BattleProcessPlayerMove playerMoveProcess;
    private BattleProcessEnemyMove enemyMoveProcess;
    private BattleProcessItems itemProcess;
    private BattleEventFlags eventFlags;
    private int battleRound;

    public void init(BattleManager manager) {
        this.manager = manager;
        this.battleRound = 0;
        processThreadHandler = new ProcessThreadHandler(this);
        attackProcess = new BattleProcessAttack(manager, this);
        effectProcess = new BattleProcessEffects(manager, this);
        paramProcess = new BattleProcessParams(manager, this);
        expProcess = new BattleProcessEXP(manager, this);
        playerMoveProcess = new BattleProcessPlayerMove(manager, this);
        enemyMoveProcess = new BattleProcessEnemyMove(manager, this);
        itemProcess = new BattleProcessItems(manager, this);
        eventFlags = new BattleEventFlags(manager);
    }

    public boolean isNetworkBattle() {
        return networkEndpoint != null;
    }

    public void initNetwork(NetworkEndpoint networkEndpoint) {
        this.networkEndpoint = networkEndpoint;
    }

    public ProcessThreadHandler getProcessThreadHandler() {
        return processThreadHandler;
    }

    @Override
    public void runProcess() {
        // init wearable items
        itemProcess.initWearables();

        // trigger intro animations
        enemyMoveProcess.enemyPoklmonIntro();
        playerMoveProcess.playerPoklmonIntro();
        manager.getParticipants().getPlayer().setFighting(true);
        manager.getParticipants().getEnemy().setFighting(true);

        // add enemy to players exp source
        ((PlayerPoklmon) manager.getParticipants().getPlayer()).addEXPSource(manager.getParticipants().getEnemy());
        ((PlayerPoklmon) manager.getParticipants().getPlayer()).setUsedInBattle(true);
        // battle enter callbacks
        for (PoklmonEnterCallback script : manager.getScriptCalls(PoklmonEnterCallback.class)) {
            script.call(manager.getParticipants().getPlayer());
        }
        for (PoklmonEnterCallback script : manager.getScriptCalls(PoklmonEnterCallback.class)) {
            script.call(manager.getParticipants().getEnemy());
        }

        // start fight
        try {
            processPlayerInput();
        } catch (NetworkException e) {
            // network error => stop battle
            e.printStackTrace();
            playerMoveProcess.connectionLost(e.getMessage());
            escapeBattle();
        }
    }

    private synchronized void processPlayerInput() throws NetworkException {
        // process player input
        manager.getBattleRender().getHudRender().setShowText(false);
        FightPoklmon playerPoklmon = manager.getParticipants().getPlayer();
        boolean hasNoRoundAttack = effectProcess.getRoundBasedEffectAttacks().canDoNormalAttack(playerPoklmon);
        boolean canSelectAttack = effectProcess.getHandicapProcess().canSelectMove(playerPoklmon);
        FightAttack overwriteAttack = effectProcess.getRoundBasedEffectAttacks().useSpecialAttack(playerPoklmon);
        BattleMove playerMove;

        if (canSelectAttack && hasNoRoundAttack) {
            manager.getPlayerMoveSelection().processInput(processThreadHandler, overwriteAttack);
            waitForResume();
            playerMove = manager.getPlayerMoveSelection().getNextMove();
            if (isNetworkBattle()) {
                networkEndpoint.send(NetworkUtil.convert(manager.getParticipants(), playerMove));
            }
        } else {
            // load dummy attack
            FightAttack dummy = new FightAttack(manager.getData().getAttacks().getAttack(0));
            playerMove = new BattleMove(dummy);
        }

        // check for round based player attacks
        if (!hasNoRoundAttack && playerMove.getMoveType() == BattleMoveType.ATTACK) {
            // try use round attack
            if (overwriteAttack != null) {
                playerMove = new BattleMove(overwriteAttack);
            }
        }

        BattleMove enemyMove = processEnemyInput();
        startBattleRound(playerMove, enemyMove);
    }

    private synchronized BattleMove processEnemyInput() throws NetworkException {
        FightPoklmon enemyPoklmon = manager.getParticipants().getEnemy();

        boolean hasNoRoundAttack = effectProcess.getRoundBasedEffectAttacks().canDoNormalAttack(enemyPoklmon);
        boolean canSelectAttack = effectProcess.getHandicapProcess().canSelectMove(enemyPoklmon);
        FightAttack overwriteAttack = effectProcess.getRoundBasedEffectAttacks().useSpecialAttack(enemyPoklmon);

        BattleMove enemyMove;
        if (canSelectAttack && hasNoRoundAttack) {
            playerMoveProcess.connectionWaiting();
            manager.getEnemyMoveSelection().processKI();
            enemyMove = manager.getEnemyMoveSelection().getNextMove();
        } else {
            // set dummy move
            FightAttack dummy = new FightAttack(manager.getData().getAttacks().getAttack(0));
            enemyMove = new BattleMove(dummy);
        }

        // check for round based enemy attacks
        if (enemyMove.getMoveType() == BattleMoveType.ATTACK) {
            if (overwriteAttack != null) {
                enemyMove = new BattleMove(overwriteAttack);
            }
        }
        return enemyMove;
    }

    private synchronized void startBattleRound(BattleMove playerMove, BattleMove enemyMove) throws NetworkException {
        // callbacks
        for (RoundStartCallback script : manager.getScriptCalls(RoundStartCallback.class)) {
            script.call();
        }

        // check who attacks first
        manager.getBattleRender().getBackgroundRender().setMoving(false);

        // process first round
        FightPoklmon player = manager.getParticipants().getPlayer();
        FightPoklmon enemy = manager.getParticipants().getEnemy();
        boolean playerAttacksFirst = AttackHitCalculator.attacksPlayerFirst(playerMove, enemyMove, player, enemy);
        playerMoveProcess.init(playerMove);
        FightPoklmon first = player;
        FightPoklmon second = enemy;
        BattleMove firstMove = playerMove;
        BattleMove secondMove = enemyMove;
        if (!playerAttacksFirst) {
            first = enemy;
            second = player;
            firstMove = enemyMove;
            secondMove = playerMove;
        }
        // pre round effects
        effectProcess.preAttackRound();
        if (firstMove.getMoveType() == BattleMoveType.ATTACK) {
            // first attack
            FightAttack attack = firstMove.getAttack();
            attackProcess.processAttack(attack, first, second);
        } else {
            // do special move
            first = doSpecialMove(playerAttacksFirst, firstMove);
            if (playerMoveProcess.isCatchedPoklmon()) {
                escapeBattle();
            }
        }

        if (first.isFainted() || second.isFainted()) {
            attackProcess.doPokmlonDefeated();
        }

        // second move
        if (!battleOver) {
            if (secondMove.getMoveType() == BattleMoveType.ATTACK) {
                // check if poklmon got swapped meanwhile
                boolean skip = false;
                if (playerAttacksFirst) {
                    skip = second != manager.getParticipants().getEnemy();
                } else {
                    skip = second != manager.getParticipants().getPlayer();
                }
                if (!skip) {
                    FightAttack attack = secondMove.getAttack();
                    attackProcess.processAttack(attack, second, first);
                }
            } else {
                // do special move
                second = doSpecialMove(!playerAttacksFirst, secondMove);
                if (playerMoveProcess.isCatchedPoklmon()) {
                    escapeBattle();
                }
            }
            if (first.isFainted() || second.isFainted()) {
                attackProcess.doPokmlonDefeated();
            }
        }

        // post round effects
        if (!battleOver) {
            // callbacks
            for (RoundEndCallback script : manager.getScriptCalls(RoundEndCallback.class)) {
                script.call();
            }
            effectProcess.postAttackRound();
            // start next round
            if (!battleOver) {
                manager.getBattleRender().getBackgroundRender().setMoving(true);
                battleRound++;
                // start next round
                processPlayerInput();
            }
        }
    }

    private synchronized FightPoklmon doSpecialMove(boolean forPlayer, BattleMove move) throws NetworkException {
        if (forPlayer) {
            playerMoveProcess.doSpecialMove(move);
            return manager.getParticipants().getPlayer();
        } else {
            enemyMoveProcess.doSpecialMove(move);
            return manager.getParticipants().getEnemy();
        }

    }

    public synchronized void playerPoklmonDefeated() throws NetworkException {
        // change statistics
        PlayerPoklmon pokl = (PlayerPoklmon) manager.getParticipants().getPlayer();
        PoklmonStatistic statistic = pokl.getPoklmonData().getStatistic();
        statistic.fainted();
        // check for next poklmon
        if (!PoklmonTeamCheck.isTeamDefeated(manager.getParticipants().getPlayerTeam())) {
            // next poklmon
            playerMoveProcess.nextPoklmon();
        } else {
            // player defeated
            playerMoveProcess.playerLost();
            endBattle(false);
        }
    }

    public synchronized void enemyPoklmonDefeated() throws NetworkException {
        // change statistics
        PlayerPoklmon pokl = (PlayerPoklmon) manager.getParticipants().getPlayer();
        PoklmonStatistic statistic = pokl.getPoklmonData().getStatistic();
        statistic.killedPoklmon();
        // give exp
        FightPoklmon enemy = manager.getParticipants().getEnemy();
        if (!isNetworkBattle()) {
            expProcess.enemyReleaseEXP(enemy);
        }
        if (manager.getParticipants().isTrainerFight()) {
            // check next poklmon or end battle
            if (!PoklmonTeamCheck.isTeamDefeated(manager.getParticipants().getEnemyTeam())) {
                // get next poklmon
                playerMoveProcess.connectionWaiting();
                enemyMoveProcess.nextPoklmon();
            } else {
                // trainer lost
                enemyMoveProcess.trainerLost();
                endBattle(true);
            }
        } else {
            endBattle(true);
        }
    }

    private void updateBattleStatistic() {
        // update statistics
        for (FightPoklmon pokl : manager.getParticipants().getPlayerTeam()) {
            if (((PlayerPoklmon) pokl).isUsedInBattle()) {
                if (manager.getParticipants().isTrainerFight()) {
                    ((PlayerPoklmon) pokl).getPoklmonData().getStatistic().foughtInTrainerBattle();
                } else {
                    ((PlayerPoklmon) pokl).getPoklmonData().getStatistic().foughtInBattle();
                }
            }
        }
    }

    public void endBattle(boolean playerWon) {
        manager.clearScriptCalls();
        battleOver = true;
        updateBattleStatistic();
        if (playerWon) {
            manager.getEndListener().battleWon();
        } else {
            manager.getEndListener().battleLost();
        }
    }

    public void escapeBattle() {
        battleOver = true;
        updateBattleStatistic();
        manager.getEndListener().battleEnd();
    }

    public BattleProcessEffects getEffectProcess() {
        return effectProcess;
    }

    public BattleProcessAttack getAttackProcess() {
        return attackProcess;
    }

    public boolean isBattleOver() {
        return battleOver;
    }

    public boolean isBattleRunning() {
        return !battleOver;
    }

    public BattleProcessParams getParamProcess() {
        return paramProcess;
    }

    public BattleEventFlags getEventFlags() {
        return eventFlags;
    }

    public BattleProcessItems getItemProcess() {
        return itemProcess;
    }

    public BattleProcessPlayerMove getPlayerMoveProcess() {
        return playerMoveProcess;
    }

    public BattleProcessEnemyMove getEnemyMoveProcess() {
        return enemyMoveProcess;
    }

    public NetworkEndpoint getNetworkEndpoint() {
        return networkEndpoint;
    }

    public BattleProcessEXP getExpProcess() {
        return expProcess;
    }

    public int getBattleRound() {
        return battleRound;
    }
}
