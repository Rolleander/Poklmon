package com.broll.poklmon.battle.process;

import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.item.TrainerItem;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.process.effects.EffectProcessInflict;
import com.broll.poklmon.battle.render.BattleSequences;
import com.broll.poklmon.battle.util.BattleMove;
import com.broll.poklmon.battle.util.PoklmonTeamCheck;
import com.broll.poklmon.data.TextContainer;
import com.broll.poklmon.battle.process.callbacks.PoklmonEnterCallback;
import com.broll.poklmon.battle.process.callbacks.PoklmonLeaveCallback;
import com.broll.poklmon.battle.process.callbacks.PoklmonSwitchCallback;
import com.broll.poklmon.battle.process.callbacks.PricemoneyCalculationCallback;
import com.broll.poklmon.network.NetworkException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class BattleProcessEnemyMove extends BattleProcessControl {

    private boolean trainerIntro = true;

    public BattleProcessEnemyMove(BattleManager manager, BattleProcessCore handler) {
        super(manager, handler);
    }

    public void doSpecialMove(BattleMove move) {
        switch (move.getMoveType()) {
            case ATTACK: // cant occur, attack is no special move
                System.err.println("Undefined Battle Process: Attack is no special Move!");
                break;
            case CHANGE_POKLMON:
                switchPoklmon(move.getSwitchTo());
                break;

            case TRY_ESCAPE:

                break;

            case USE_ITEMS:
                int item = move.getItemId();
                useTrainerItem(item);
                break;
        }
    }

    private void switchPoklmon(FightPoklmon to) {

        FightPoklmon oldPoklmon = manager.getParticipants().getEnemy();
        String oldName = oldPoklmon.getName();
        String text = TextContainer.get("trainerSwitch", manager.getParticipants().getEnemyName(), oldName);
        showText(text);
        // TODO show outro animation
        sendNextPoklmon(to);
    }

    private void useTrainerItem(int item) {
        // reduce itemcount from itemslist and remove item when 0
        Iterator<TrainerItem> items = manager.getParticipants().getTrainerItems().iterator();
        while (items.hasNext()) {
            TrainerItem it = items.next();
            if (it.getId() == item) {
                if (it.use()) {
                    items.remove();
                }
                break;
            }
        }
        // trainer always use item on active poklmon
        FightPoklmon itemTarget = manager.getParticipants().getEnemy();
        core.getItemProcess().useItem(item, manager.getParticipants().getEnemyName(), itemTarget);
    }

    public void trainerLost() {
        if (core.isNetworkBattle()) {
            String enemy = manager.getParticipants().getEnemyName();
            showText(TextContainer.get("networkWon", enemy));
        } else {
            String trainer = manager.getParticipants().getEnemyName();
            showText(TextContainer.get("trainerDefeated", trainer));
            String outro = manager.getParticipants().getOutroText();
            showText(outro);
            int money = manager.getParticipants().getWinMoney();
            for (PricemoneyCalculationCallback script : manager.getScriptCalls(PricemoneyCalculationCallback.class)) {
                money = script.call(money);
            }
            showText(TextContainer.get("trainerMoney", manager.getParticipants().getPlayerName(), money));
            manager.getPlayer().getInventarControl().addMoney(money);
        }
    }

    public void nextPoklmon() throws NetworkException {
        FightPoklmon next = manager.getEnemyMoveSelection().sendNextPoklmon();
        if (!core.isNetworkBattle()) { // fast switching not in network fights!
            int okPoklNum = PoklmonTeamCheck.getNumberOfUnfaintedPoklmons(manager.getParticipants().getPlayerTeam());
            if (okPoklNum > 1) {
                int sel = showCancelableSelection(TextContainer.get("trainerNextPoklmonQuestion", manager.getParticipants().getEnemyName(), next.getName()), new String[]{"Ja", "Nein"});
                if (sel == 0) {
                    // switch poklmon
                    core.getPlayerMoveProcess().showPoklmonChangeDialog(false);
                }
            }
        }
        sendNextPoklmon(next);
    }

    private void sendNextPoklmon(FightPoklmon next) {
        FightPoklmon oldPoklmon = manager.getParticipants().getEnemy();
        oldPoklmon.setFighting(false);
        for (PoklmonLeaveCallback script : manager.getScriptCalls(PoklmonLeaveCallback.class)) {
            script.call(oldPoklmon);
        }
        // repalce positions in array
        ArrayList<FightPoklmon> team = manager.getParticipants().getEnemyTeam();
        int oldPos = team.indexOf(oldPoklmon);
        int newPos = team.indexOf(next);
        // swawp
        Collections.swap(team, oldPos, newPos);
        manager.getParticipants().changeEnemyPoklmon(next);
        // add enemy to player poklmons exp list
        ((PlayerPoklmon) manager.getParticipants().getPlayer()).addEXPSource(manager.getParticipants().getEnemy());
        enemyPoklmonIntro();
        // enter callback
        next.setFighting(true);
        for (PoklmonSwitchCallback script : manager.getScriptCalls(PoklmonSwitchCallback.class)) {
            script.call(oldPoklmon, next);
        }
        for (PoklmonEnterCallback script : manager.getScriptCalls(PoklmonEnterCallback.class)) {
            script.call(next);
        }
    }

    public synchronized void enemyPoklmonIntro() {

        // update pokldex
        Poklmon pokl = manager.getParticipants().getEnemy().getPoklmon();
        manager.getPlayer().getPokldexControl().foundNewPoklmon(pokl.getId());

        manager.getBattleRender().getHudRender().setShowText(false); // hide
        // textbox
        manager.getBattleRender().getBackgroundRender().setMoving(false);
        manager.getBattleRender().getHudRender().setShowHud(false);
        if (manager.getParticipants().isTrainerFight()) { // if trainer show
            // trainer intro

            if (trainerIntro) {
                String trainer = manager.getParticipants().getEnemyName();
                showText(TextContainer.get("trainerIntro", trainer));
                manager.getBattleRender().getSequenceRender()
                        .showAnimation(BattleSequences.TRAINER_INTRO, processThreadHandler);
                waitForResume();
                trainerIntro = false;
            }

            manager.getBattleRender().getSequenceRender()
                    .showAnimation(BattleSequences.ENEMY_INTRO, processThreadHandler);
            waitForResume();
        } else {
            // WILD
            manager.getBattleRender().getSequenceRender()
                    .showAnimation(BattleSequences.WILD_POKLMON_INTRO, processThreadHandler);
            waitForResume();
        }

        // check for rare poklmon intro
        if (manager.getParticipants().getEnemy().isHasPowerfulGenes()) {
            Animation showAnimation = manager.getData().getAnimations()
                    .getAnimation(EffectProcessInflict.STRONG_GENES_ANIMATION_ID);
            core.getAttackProcess().showOverlayAnimation(showAnimation, true);
        }
        manager.getBattleRender().getBackgroundRender().setMoving(true);
        manager.getBattleRender().getHudRender().setShowHud(true);
    }
}
