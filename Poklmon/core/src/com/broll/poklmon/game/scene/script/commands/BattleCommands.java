package com.broll.poklmon.game.scene.script.commands;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.script.syntax.VariableException;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.enemy.EnemyKIType;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.WildPoklmon;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.script.CommandControl;
import com.broll.poklmon.game.scene.script.Invoke;
import com.broll.poklmon.map.areas.AreaType;
import com.broll.poklmon.save.PoklmonData;
import com.broll.poklmon.save.PoklmonStatistic;
import com.broll.poklmon.transition.WildBattleTransition;

import java.util.HashMap;

public class BattleCommands extends CommandControl {
	private BattleParticipants battleParticipants;

	public BattleCommands(GameManager game) {
		super(game);
	}

	public void wonAgainstArenachamp() {
		HashMap<Integer, PoklmonData> team = game.getPlayer().getPoklmonControl().getPoklmonsInTeam();
		for (int i = 0; i < 6; i++) {
			PoklmonData pokl = team.get(i);
			if (pokl != null) {
				PoklmonStatistic stats = pokl.getStatistic();
				stats.setDefeatedArenas(stats.getDefeatedArenas() + 1);
			}
		}
	}

	public void wonAgainstLigachamp() {
		HashMap<Integer, PoklmonData> team = game.getPlayer().getPoklmonControl().getPoklmonsInTeam();
		for (int i = 0; i < 6; i++) {
			PoklmonData pokl = team.get(i);
			if (pokl != null) {
				PoklmonStatistic stats = pokl.getStatistic();
				stats.setLigaWins(stats.getLigaWins() + 1);
			}
		}
	}

	public void initBattle() {
		battleParticipants = new BattleParticipants();
		battleParticipants.setTrainerFight(false);
	}

	public void setBattleArea(AreaType area) {
		battleParticipants.setAreaType(area);
	}
	
	public void setBattleMusic(String musicName) {
		battleParticipants.setCustomMusic(musicName);
	}

	public void addWildPoklmon(int poklId, int level) {
		Poklmon poklmon = game.getData().getPoklmons().getPoklmon(poklId);
		WildPoklmon wild = FightPokemonBuilder.createWildPoklmon(game.getData(), poklmon, level);
		battleParticipants.addEnemyPoklmon(wild);
	}

	public void addTrainerPoklmon(int poklId, int level, int atk1, int atk2, int atk3, int atk4) {
		int[] atk = new int[4];
		Poklmon poklmon = game.getData().getPoklmons().getPoklmon(poklId);
		atk[0] = atk1;
		atk[1] = atk2;
		atk[2] = atk3;
		atk[3] = atk4;
		FightPoklmon trainer = FightPokemonBuilder.createTrainerPoklmon(game.getData(), poklmon, level, atk);
		battleParticipants.addEnemyPoklmon(trainer);
	}

	public void addTrainerItem(int id, int count) {
		battleParticipants.addTrainerItem(id, count);
	}

	public void addTrainerItem(int id) {
		battleParticipants.addTrainerItem(id);
	}

	public void setEnemeyKi(EnemyKIType enemyKIType) {
		battleParticipants.setEnemyKIType(enemyKIType);
	}

	

	public boolean startTrainerBattle(String name, String outroText, int winMoney) {
		battleParticipants.setIntroText(null);
		battleParticipants.setOutroText(outroText);
		battleParticipants.setWinMoney(winMoney);
		battleParticipants.setTrainerFight(true);
		battleParticipants.setEnemyKIType(EnemyKIType.TRAINER);
		battleParticipants.setEnemyName(name);
		if (startBattle() == 0) {
			// win
			return true;
		} else {
			// game over
			game.gameOver();
			return false;
		}
	}

	public int startBattle() {
		final Returner returner = new Returner();
		invoke(new Invoke() {

			@Override
			public void invoke() throws VariableException {
				game.startBattle(battleParticipants, new WildBattleTransition(), new BattleEndListener() {
					@Override
					public void battleWon() {
						returner.value = 0;
						resume();
					}

					@Override
					public void battleLost() {
						returner.value = 1;
						resume();
					}

					@Override
					public void battleEnd() {
						returner.value = 2;
						resume();
					}
				});
			}
		});
		return returner.value;
	}

	private class Returner {
		public int value;
	}

}
