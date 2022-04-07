package com.broll.poklmon.battle;

import com.broll.pokllib.poklmon.TypeComperator;
import com.broll.poklmon.battle.enemy.EnemyMoveSelection;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.player.PlayerMoveSelection;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.process.CustomScriptCall;
import com.broll.poklmon.battle.render.BattleRender;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.battle.util.BattleRandom;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.main.SystemClock;
import com.broll.poklmon.network.NetworkEndpoint;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.script.ProcessingUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleManager {

    private BattleEndListener endListener;
    private PlayerMoveSelection playerMoveSelection;
    private EnemyMoveSelection enemyMoveSelection;
    private BattleRender battleRender;
    private BattleParticipants participants;
    private DataContainer data;
    private FieldEffects fieldEffects;
    private Thread battleThread;
    private BattleProcessCore core;
    private GameManager game;
    private Player player;
    private Multimap<Object, CustomScriptCall> scriptCalls;
    private boolean networkBattle = false;

    public BattleManager(DataContainer data, GameManager game) {
        TypeComperator.init(data.getMisc().getTypeTable());
        this.data = data;
        this.game = game;
        this.player = game.getPlayer();
        battleRender = new BattleRender(this);
        playerMoveSelection = new PlayerMoveSelection(this);
        enemyMoveSelection = new EnemyMoveSelection(this);
    }

    public void debugInit(BattleParticipants participants) {
        fieldEffects = new FieldEffects(participants);
        this.participants = participants;
        battleRender.init();
    }

    private void initBattle(BattleParticipants participants, BattleEndListener end) {
        this.participants = participants;
        scriptCalls = ArrayListMultimap.create();
        fieldEffects = new FieldEffects(participants);
        battleRender.getPoklmonRender().setEnemyPoklmonVisible(false);
        battleRender.getPoklmonRender().setPlayerPoklmonVisible(false);
        playerMoveSelection.reset();
        // check player poklmons defeated
        if (participants.getPlayer().isFainted()) {
            // find next poklmon in list, that isnt fainted
            for (int i = 1; i < participants.getPlayerTeam().size(); i++) {
                if (!participants.getPlayerTeam().get(i).isFainted()) {
                    participants.changePlayerPoklmon(participants.getPlayerTeam().get(i));
                    break;
                }
            }
        }
    }

    private void startCore(BattleProcessCore core, BattleEndListener end) {
        this.core = core;
        core.init(this);
        // init ki
        enemyMoveSelection.initKI(core, participants);
        this.endListener = end;
        battleRender.init();
        battleThread = new Thread(core);
        battleThread.setName("BattleProcessCore");
        battleThread.start();
    }

    public void startNetworkBattle(NetworkEndpoint networkEndpoint, long seed, BattleParticipants participants,
                                   BattleEndListener end) {
        networkBattle = true;
        BattleRandom.init(seed);
        initBattle(participants, end);
        BattleProcessCore core = new BattleProcessCore();
        core.initNetwork(networkEndpoint);
        startCore(core, end);
    }

    public void startBattle(BattleParticipants participants, BattleEndListener end) {
        networkBattle = false;
        BattleRandom.init();
        initBattle(participants, end);
        BattleProcessCore core = new BattleProcessCore();
        startCore(core, end);
    }

    public void startCustom(BattleParticipants participants, BattleProcessCore core, BattleEndListener end) {
        networkBattle = false;
        BattleRandom.init();
        initBattle(participants, end);
        startCore(core, end);
    }

    public void update(float delta) {
        if (SystemClock.isTick()) {
            player.getData().getGameVariables().setPlayTime(player.getData().getGameVariables().getPlayTime() + 1);
        }
        battleRender.update(delta);
        playerMoveSelection.update(delta);
    }

    public void render(Graphics g) {
        battleRender.render(g);
        playerMoveSelection.render(g);
    }

    public EnemyMoveSelection getEnemyMoveSelection() {
        return enemyMoveSelection;
    }

    public PlayerMoveSelection getPlayerMoveSelection() {
        return playerMoveSelection;
    }

    public BattleRender getBattleRender() {
        return battleRender;
    }

    public BattleEndListener getEndListener() {
        return endListener;
    }

    public BattleParticipants getParticipants() {
        return participants;
    }

    public DataContainer getData() {
        return data;
    }

    public FieldEffects getFieldEffects() {
        return fieldEffects;
    }

    public Player getPlayer() {
        return player;
    }

    public void addScriptCall(Object owner, CustomScriptCall call) {
        synchronized (this.scriptCalls) {
            this.scriptCalls.put(owner, call);
        }
    }

    public <T extends CustomScriptCall> List<T> getScriptCalls(Class<T> type) {
        List<T> calls = new ArrayList<>();
        for (CustomScriptCall call : scriptCalls.values()) {
            if (type.isInstance(call)) {
                calls.add((T) call);
            }
        }
        return calls;
    }

    public void removeScriptCalls(Object owner) {
        synchronized (this.scriptCalls) {
            this.scriptCalls.removeAll(owner);
        }
    }

    public void clearScriptCalls(){
        this.scriptCalls.clear();
    }

    public boolean isNetworkBattle() {
        return networkBattle;
    }

    public GameManager getGame() {
        return game;
    }

    public void dispose(){
        if(core!=null){
            ProcessingUtils.cancel(battleThread, core);
        }
    }
}
