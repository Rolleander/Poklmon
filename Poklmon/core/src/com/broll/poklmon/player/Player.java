package com.broll.poklmon.player;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.data.player.PlayerGraphics;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.player.control.impl.InventarControl;
import com.broll.poklmon.player.control.impl.PlayerControl;
import com.broll.poklmon.player.control.impl.PokldexControl;
import com.broll.poklmon.player.control.impl.PoklmonControl;
import com.broll.poklmon.player.control.impl.VariableControl;
import com.broll.poklmon.poklmon.PoklmonAttributeCalculator;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.GameData;
import com.broll.poklmon.save.PoklmonData;
import com.broll.poklmon.save.manage.SaveFileManager;

import java.util.HashMap;

public class Player {

	private OverworldPlayer overworld;
	private PlayerGameData data;
	private InventarControl inventarControl;
	private PokldexControl pokldexControl;
	private PlayerControl playerControl;
	private VariableControl variableControl;
	private PoklmonControl poklmonControl;
	private GameManager game;
	private PlayerGraphics playerGraphics;
	public static boolean SAVING_ALLOWED = true;
	public final static String SCHUTZ_ID = "$PLAYER.SCHUTZ";
	public final static String STEP_ID = "$PLAYER.STEPS";
	public final static String SHORTCUT = "#itemshortcut";



	public Player(GameManager gameManager) {
		this.game = gameManager;
		overworld = new OverworldPlayer(gameManager);
	}

	// for debug
	public Player() {
	}

	public void saveGame(){
		playerControl.saveCurrentLocation(game.getMap().getMapId());
		SaveFileManager.saveGame(data.getSaveFile());
	}

	public void doneStep() {
		// reduce schutz variable
		int count = variableControl.getInt(SCHUTZ_ID);
		if (count > 0) {
			count--;
			variableControl.setInt(SCHUTZ_ID, count);
			if (count == 0) {
				game.getMessageGuiControl().showCustomDialog("Dein Schutz wirkt nicht mehr!");
			}
		}
		// inc step variable
		variableControl.setInt(STEP_ID, variableControl.getInt(STEP_ID) + 1);
	}

	public void healTeam() {
		HashMap<Integer, PoklmonData> team = game.getPlayer().getPoklmonControl().getPoklmonsInTeam();
		for (PoklmonData poklmon : team.values()) {
			int kp = PoklmonAttributeCalculator.getKP(game.getData(), poklmon);
			// heal
			poklmon.setKp(kp);
			// heal status
			poklmon.setStatus(null);
			// fresh ap
			for (int i = 0; i < 4; i++) {
				AttackData atk = poklmon.getAttacks()[i];
				int id = atk.getAttack();
				if (id != -1) {
					atk.setAp((byte) game.getData().getAttacks().getAttack(id).getDamage().getAp());
				}
			}
		}
	}

	public TeleportDestination getRecoveryLocation() {
		int map = data.getPlayerData().getRecoverMap();
		int x = data.getPlayerData().getRecoverX();
		int y = data.getPlayerData().getRecoverY();
		ObjectDirection dir = ObjectDirection.values()[data.getPlayerData().getRecoverView()];
		return new TeleportDestination(map, x, y, dir);
	}

	public void saveRecoveryPosition() {
		data.getPlayerData().setRecoverMap(game.getMap().getMapId());
		data.getPlayerData().setRecoverX((int) overworld.getXpos());
		data.getPlayerData().setRecoverY((int) overworld.getYpos());
		data.getPlayerData().setRecoverView(overworld.getDirection().ordinal());
	}

	public void init(GameData file) {
		data = new PlayerGameData(file);
		// init controllers
		inventarControl = new InventarControl(game.getData(),data);
		pokldexControl = new PokldexControl(data);
		playerControl = new PlayerControl(overworld, data.getPlayerData());
		variableControl = new VariableControl(data);
		poklmonControl = new PoklmonControl(game.getData(),this);
		int playerNr=data.getPlayerData().getCharacter();
		this.playerGraphics=game.getData().getGraphics().getPlayer().get(playerNr);
		overworld.initGraphic(playerGraphics);
	}

	public void addTeamToFight(BattleParticipants battleParticipants) {
		for (int i = 0; i < 6; i++) {
			PoklmonData pokl = poklmonControl.getPoklmonsInTeam().get(i);
			if (pokl != null) {
				FightPoklmon player = FightPokemonBuilder.createPlayerPoklmon(game.getData(), pokl);
				battleParticipants.addPlayerPoklmon(player);
			}
		}
	}

	public void update(float delta) {
		playerControl.update(delta);
		overworld.update(delta);
	}

	public OverworldPlayer getOverworld() {
		return overworld;
	}

	public PlayerGameData getData() {
		return data;
	}

	public InventarControl getInventarControl() {
		return inventarControl;
	}

	public PokldexControl getPokldexControl() {
		return pokldexControl;
	}

	public PlayerControl getPlayerControl() {
		return playerControl;
	}

	public VariableControl getVariableControl() {
		return variableControl;
	}

	public PoklmonControl getPoklmonControl() {
		return poklmonControl;
	}

	public PlayerGraphics getPlayerGraphics() {
		return playerGraphics;
	}
}
