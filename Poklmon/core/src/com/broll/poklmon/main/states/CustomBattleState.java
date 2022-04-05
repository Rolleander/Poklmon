package com.broll.poklmon.main.states;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.input.CharReceiver;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.network.NetworkEndpoint;
import com.broll.poklmon.player.Player;

public class CustomBattleState extends GameState {

    private DataContainer data;
    private BattleManager battle;

    public CustomBattleState(DataContainer data) {
        this.data = data;
    }

    public void startBattle(BattleParticipants participants, GameManager game, BattleProcessCore core, final BattleEndListener endListener) {
        if (battle == null) {
            // init
            battle = new BattleManager(data, game);
        }

        battle.startCustom(participants, core, new BattleEndListener() {
            public void battleWon() {
                endBattle();
                endListener.battleWon();
            }

            public void battleLost() {
                endBattle();
                endListener.battleLost();
            }

            public void battleEnd() {
                endBattle();
                endListener.battleEnd();
            }
        });
    }

    private void endBattle() {
        states.transition(MapState.class);
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onEnter() {
        game.getInput().setCharReceiver(new CharReceiver() {
            @Override
            public void typed(int keycode, char typedChar) {
                battle.getBattleRender().getHudRender().keyPressed(keycode, typedChar);
            }
        });
    }

    @Override
    public void onExit() {

    }

    @Override
    public void update(float delta) {
        battle.update(delta);
        GUIUpdate.consume();
    }

    @Override
    public void render(Graphics g) {
        battle.render(g);
    }

}
