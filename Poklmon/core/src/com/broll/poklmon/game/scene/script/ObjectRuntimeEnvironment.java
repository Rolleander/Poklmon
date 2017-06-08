package com.broll.poklmon.game.scene.script;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.item.Item;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.enemy.EnemyKIType;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.script.commands.BattleCommands;
import com.broll.poklmon.game.scene.script.commands.DialogCommands;
import com.broll.poklmon.game.scene.script.commands.MenuCommands;
import com.broll.poklmon.game.scene.script.commands.NetworkCommands;
import com.broll.poklmon.game.scene.script.commands.ObjectCommands;
import com.broll.poklmon.game.scene.script.commands.PathingCommands;
import com.broll.poklmon.game.scene.script.commands.PlayerCommands;
import com.broll.poklmon.game.scene.script.commands.SystemCommands;
import com.broll.poklmon.game.scene.script.commands.VariableCommands;
import com.broll.poklmon.map.MapContainer;
import com.broll.poklmon.map.areas.AreaType;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.model.CharacterWorldState;
import com.broll.poklmon.network.transfer.PoklmonTransfer;
import com.broll.poklmon.save.PoklmonData;

public class ObjectRuntimeEnvironment extends ScriptingEnvironment {

	private PlayerCommands playerCommands;
	private BattleCommands battleCommands;
	private DialogCommands dialogCommands;
	private MenuCommands menuCommands;
	private ObjectCommands objectCommands;
	private SystemCommands systemCommands;
	private VariableCommands variableCommands;
	private PathingCommands pathingCommands;
	private NetworkCommands networkCommands;

	public ObjectRuntimeEnvironment() {
		super();
	}

	@Override
	protected void addControllers(GameManager game, MapObject caller) {
		addController(caller, "self");

		playerCommands = new PlayerCommands(game);
		battleCommands = new BattleCommands(game);
		dialogCommands = new DialogCommands(game);
		menuCommands = new MenuCommands(game, dialogCommands, playerCommands);
		objectCommands = new ObjectCommands(game);
		systemCommands = new SystemCommands(game);
		variableCommands = new VariableCommands(game);
		pathingCommands = new PathingCommands(game);
		networkCommands = new NetworkCommands(game);
		addController(playerCommands, "player");
		addController(battleCommands, "battle");
		addController(dialogCommands, "dialog");
		addController(menuCommands, "menu");
		addController(objectCommands, "object");
		addController(systemCommands, "system");
		addController(variableCommands, "game");
		addController(pathingCommands, "path");
		addController(networkCommands, "network");

	}

	@Override
	protected void addImports() {
		addPackage(EnemyKIType.class);
		addPackage(PoklmonData.class);
		addPackage(Poklmon.class);
		addPackage(ObjectDirection.class);
		addPackage(Item.class);
		addPackage(Attack.class);
		addPackage(MapContainer.class);
		addPackage(AreaType.class);
		addPackage(PoklmonTransfer.class);
		addPackage(CharacterWorldState.class);

	}

	public PathingCommands getPathingCommands() {
		return pathingCommands;
	}

	public PlayerCommands getPlayerCommands() {
		return playerCommands;
	}

	public BattleCommands getBattleCommands() {
		return battleCommands;
	}

	public DialogCommands getDialogCommands() {
		return dialogCommands;
	}

	public MenuCommands getMenuCommands() {
		return menuCommands;
	}

	public ObjectCommands getObjectCommands() {
		return objectCommands;
	}

	public SystemCommands getSystemCommands() {
		return systemCommands;
	}

	public VariableCommands getVariableCommands() {
		return variableCommands;
	}

	public NetworkCommands getNetworkCommands() {
		return networkCommands;
	}

}
