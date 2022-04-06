package com.broll.poklmon.main.states;

import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.debug.DebugRender;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.TouchIconsRender;
import com.broll.poklmon.input.CharReceiver;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.network.NetworkEndpoint;
import com.broll.poklmon.save.GameData;
import com.broll.poklmon.transition.ScreenTransition;

public class MapState extends GameState {

    public static int STATE_ID = 4;
    private DataContainer data;
    private GameManager gameInstance;
    private DebugRender debugRender;
    private BattleEndListener battleEndListener;

    public MapState(DataContainer data) {
        this.data = data;

    }

    public GameManager getGameInstance() {
        return gameInstance;
    }

    public void openGame(GameData gameData) {
        gameInstance = new GameManager(data, states, this);
        gameInstance.startGame(gameData);
        debugRender = new DebugRender(gameInstance, game.getInput());
    }

    public void startPoklmonBattle(BattleParticipants participants, ScreenTransition transition,
                                   BattleEndListener endListener) {
        BattleState battle = (BattleState) states.getState(BattleState.class);
        battle.startBattle(participants, gameInstance, endListener);
        states.transition(BattleState.class, transition);
    }

    public void startNetworkPoklmonBattle(NetworkEndpoint endpoint, long seed, BattleParticipants participants,
                                          ScreenTransition transition, BattleEndListener endListener) {
        BattleState battle = (BattleState) states.getState(BattleState.class);
        battle.startNetworkBattle(endpoint, seed, participants, gameInstance, endListener);
        states.transition(BattleState.class, transition);
    }

    public void startCustomBattle(BattleParticipants participants, BattleProcessCore core, ScreenTransition transition,
                                  BattleEndListener endListener) {
        CustomBattleState battle = (CustomBattleState) states.getState(CustomBattleState.class);
        battle.startBattle(participants, gameInstance, core, endListener);
        states.transition(CustomBattleState.class, transition);
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onEnter() {
        gameInstance.checkFastItemUse();
        game.getInput().setCharReceiver(new CharReceiver() {
            @Override
            public void typed(int keycode, char typedChar) {
                gameInstance.keyPressed(keycode, typedChar);
            }
        });
        data.getMusics().playMusic("village.ogg", false);
        if (this.gameInstance.isGameOver()) {
            this.gameInstance.recoverPlayer();
        }
    }

    @Override
    public void onExit() {
        TouchIconsRender.hideButton(2, true);
        TouchIconsRender.hideButton(3, true);
    }

    @Override
    public void update(float delta) {
        long longDelta = (long) (delta * 1000);
        this.gameInstance.updateGame(longDelta, delta);
        if (PoklmonGame.DEBUG_MODE) {
            debugRender.update();
        }
        GUIUpdate.consume();
    }

    @Override
    public void render(Graphics g) {
        this.gameInstance.renderGame(g);
        this.debugRender.render(g);
    }

    @Override
    public void dispose() {
        if (gameInstance != null) {
            gameInstance.getSceneProcessManager().cancelScript();
        }
    }
}
