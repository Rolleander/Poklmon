package com.broll.poklmon.script;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.item.Item;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.enemy.EnemyKIType;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.script.commands.DialogCommands;
import com.broll.poklmon.script.commands.MenuCommands;
import com.broll.poklmon.script.commands.NetworkCommands;
import com.broll.poklmon.script.commands.ObjectCommands;
import com.broll.poklmon.script.commands.PathingCommands;
import com.broll.poklmon.script.commands.PlayerCommands;
import com.broll.poklmon.script.commands.VariableCommands;
import com.broll.poklmon.map.MapContainer;
import com.broll.poklmon.map.areas.AreaType;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.model.CharacterWorldState;
import com.broll.poklmon.network.transfer.PoklmonTransfer;
import com.broll.poklmon.save.PoklmonData;
import com.broll.poklmon.script.commands.BattleCommands;
import com.broll.poklmon.script.commands.SystemCommands;

public class ObjectRuntimeEnvironment extends ScriptingEnvironment {

	private PlayerCommands playerCommands;
	private com.broll.poklmon.script.commands.BattleCommands battleCommands;
	private DialogCommands dialogCommands;
	private MenuCommands menuCommands;
	private ObjectCommands objectCommands;
	private com.broll.poklmon.script.commands.SystemCommands systemCommands;
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
		battleCommands = new com.broll.poklmon.script.commands.BattleCommands(game);
		dialogCommands = new DialogCommands(game);
		menuCommands = new MenuCommands(game, dialogCommands, playerCommands);
		objectCommands = new ObjectCommands(game);
		systemCommands = new com.broll.poklmon.script.commands.SystemCommands(game);
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
