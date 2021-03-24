package com.broll.poklmon.main.states;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.input.CharReceiver;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.network.NetworkEndpoint;
import com.broll.poklmon.player.Player;

public class BattleState extends GameState {

	public static int STATE_ID = 5;
	private DataContainer data;
	private BattleManager battle;

	public BattleState(DataContainer data) {
		this.data = data;

	}
	
	private void playBattleMusic(BattleParticipants participants) {
		String customMusic=participants.getCustomMusic();
		if(customMusic!=null) {
			data.getMusics().playMusic(customMusic+".ogg", true);											
		}
		else if(participants.isTrainerFight()) {
			data.getMusics().playMusic("battle_trainer.ogg", true);								
		}
		else {
			data.getMusics().playMusic("battle.ogg", true);					
		}
	}

	public void startBattle(BattleParticipants participants, Player player, final BattleEndListener endListener) {
		if (battle == null) {
			// init
			battle = new BattleManager(data, player);
		}
		playBattleMusic(participants);

		battle.startBattle(participants, new BattleEndListener() {
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

	public void startNetworkBattle(NetworkEndpoint endpoint, long seed, BattleParticipants participants, Player player,
			final BattleEndListener endListener) {
		if (battle == null) {
			// init
			battle = new BattleManager(data, player);
		}
		playBattleMusic(participants);

		battle.startNetworkBattle(endpoint, seed, participants, new BattleEndListener() {
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
