package com.broll.poklmon.main.states;

import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.debug.DebugPlayerFactory;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.GameData;
import com.broll.poklmon.save.PoklmonData;

public class BattleDebugState extends GameState {

	public static int STATE_ID = -1;

	private BattleManager battleManager;
	private DataContainer data;
	private int debugAttack = -1;

	public BattleDebugState(DataContainer data) {
		this.data = data;

	}

	public void debugAttack(int id) {
		debugAttack = id;
	}

	private void startBattle() {
		DebugPlayerFactory f = new DebugPlayerFactory(data);
		GameData dat = f.createDebugPlayer(0, 0, 0); // location irrelevant

		GameManager gameManager = new GameManager(data, null, null);
		gameManager.startGame(dat);
		gameManager.getPlayer().init(dat);
		battleManager = new BattleManager(data, gameManager);

		BattleParticipants participants = new BattleParticipants(false);
		participants.setPlayerName("Hans");
		int r = (int) (data.getPoklmons().getNumberOfPoklmons() * Math.random());
		r = (int) (Math.random() * 50);
		// r=0;

		int l = 30;
		// r=0;
		FightPoklmon enemy = FightPokemonBuilder.createWildPoklmon(data, data.getPoklmons().getPoklmon(r), l);
		if(debugAttack>1){
			enemy.getAttacks()[0] = new FightAttack(data.getAttacks().getAttack(debugAttack), 40);
		}
		participants.addEnemyPoklmon(enemy);

		for (int p = 0; p < 6; p++) {
			PoklmonData pokl = new PoklmonData();
			r = (int) (data.getPoklmons().getNumberOfPoklmons() * Math.random());
			r = p + 1;
			pokl.setPoklmon(r);
			pokl.setLevel(l);

			pokl.setKp(700);

			pokl.setWesen(PoklmonWesen.HART);
			pokl.setDv(new short[] { 2, 12, 30, 5, 11, 20 });
			pokl.setFp(new short[] { 20, 22, 23, 24, 25, 26 });
			pokl.setExp(50);

			AttackData[] atk = new AttackData[4];
			for (int i = 0; i < 4; i++)

			{
				r = (int) (data.getAttacks().getNumberOfAttacks() * Math.random());
				if (i == 0 && debugAttack > -1) {
					r = debugAttack;
				}
				atk[i] = new AttackData(r, (byte) 40);
			}
			pokl.setAttacks(atk);
			FightPoklmon player = FightPokemonBuilder.createPlayerPoklmon(data, pokl);
			participants.addPlayerPoklmon(player);
		}

		battleManager.startBattle(participants, new BattleEndListener() {
			public void battleWon() {
				startBattle();
			}

			public void battleLost() {
				startBattle();
			}

			public void battleEnd() {
				startBattle();
			}
		});
	}

	@Override
	public void onInit() {
	}

	@Override
	public void onEnter() {
		startBattle();
	}

	@Override
	public void onExit() {
	}

	@Override
	public void update(float delta) {
		battleManager.update(delta);
		GUIUpdate.consume();
	}

	@Override
	public void dispose() {
		if(battleManager!=null){
			battleManager.dispose();
		}
	}

	@Override
	public void render(Graphics g) {
		battleManager.render(g);
	}

}
