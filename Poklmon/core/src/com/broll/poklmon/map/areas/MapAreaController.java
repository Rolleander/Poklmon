package com.broll.poklmon.map.areas;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.poklmon.WildPoklmon;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.map.MapAnimation;
import com.broll.poklmon.map.areas.util.RandomBattle;
import com.broll.poklmon.map.areas.util.WildPoklmonEntry;
import com.broll.poklmon.map.areas.util.WildPoklmonOccurCalc;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.transition.WildBattleTransition;
import com.esotericsoftware.minlog.Log;

import java.util.List;

public class MapAreaController {

	private GameManager game;
	private MapAreaContainer areas;
	private WildPoklmonOccurCalc wildPoklmonOccurCalc;

	public MapAreaController(GameManager game) {
		this.game = game;
		wildPoklmonOccurCalc = new WildPoklmonOccurCalc();
	}

	public void setAreas(MapAreaContainer areas) {
		this.areas = areas;
	}

	public void areaTrigger(int areaId, int x, int y) {
		MapArea mapArea = areas.getArea(areaId);
		if (mapArea.getWildPoklmons().size()>0) {
			if (mapArea.getType() == AreaType.GRASS) {
				// show grass animation
				MapAnimation animation = new MapAnimation(game.getData().getGraphics().getMapGraphicsContainer()
						.getGrass(), x, y, 150);
				animation.setLooping(false);
				game.getMap().showAnimation(animation);
			}
		}
		if (game.getPlayer().getVariableControl().getInt(Player.SCHUTZ_ID) <= 0) {
			checkForWildBattle(mapArea.getWildPoklmons(), mapArea.getType());
		}
	}

	public RandomBattle calcRandomBattle(List<WildPoklmonEntry> list) {
		return wildPoklmonOccurCalc.calc(list);
	}

	private void checkForWildBattle(List<WildPoklmonEntry> list, AreaType area) {
		RandomBattle rb = calcRandomBattle(list);
		if (rb != null) {
			int id = rb.getPoklmon();
			int level = rb.getLevel();
			Poklmon poklmon = game.getData().getPoklmons().getPoklmon(id);
			WildPoklmon wild = FightPokemonBuilder.createWildPoklmon(game.getData(), poklmon, level);
			BattleParticipants battleParticipants = new BattleParticipants(false);
			battleParticipants.addEnemyPoklmon(wild);
			battleParticipants.setAreaType(area);
			game.startBattle(battleParticipants, new WildBattleTransition(), new BattleEndListener() {
				@Override
				public void battleWon() {
					// nothing happens
					game.getPlayer().getOverworld().setMovementAllowed(true);
				}

				@Override
				public void battleLost() {
					game.getPlayer().getOverworld().setMovementAllowed(true);
					// game over
					game.gameOver();
				}

				@Override
				public void battleEnd() {
					game.getPlayer().getOverworld().setMovementAllowed(true);
				}
			});
		}
	}
}
