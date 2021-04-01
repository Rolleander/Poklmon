package com.broll.poklmon.game.items.execute;

import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.items.BasisItemScript;
import com.broll.poklmon.game.scene.SceneProcessManager;
import com.broll.poklmon.script.ScriptProcessInteraction;
import com.broll.poklmon.script.commands.BattleCommands;
import com.broll.poklmon.script.commands.DialogCommands;
import com.broll.poklmon.script.commands.MenuCommands;
import com.broll.poklmon.script.commands.PlayerCommands;
import com.broll.poklmon.script.commands.SystemCommands;
import com.broll.poklmon.script.commands.VariableCommands;
import com.broll.poklmon.map.object.MapObject;

public class BasisItemRunner extends MenuItemRunner implements BasisItemScript {

	private PlayerCommands player;
	private SystemCommands system;
	private DialogCommands dialog;
	private VariableCommands variable;
	private MenuCommands menu;
	private BattleCommands battle;

	public BasisItemRunner(GameManager game) {
		super(game);

	}

	public void init() {
		ScriptInteraction interaction = new ScriptInteraction(process);
		player = new PlayerCommands(game);
		system = new SystemCommands(game);
		dialog = new DialogCommands(game);
		variable = new VariableCommands(game);
		battle = new BattleCommands(game);
		menu = new MenuCommands(game, dialog, player);
		MapObject object = null;
		player.init(object, interaction);
		system.init(object, interaction);
		dialog.init(object, interaction);
		variable.init(object, interaction);
		menu.init(object, interaction);
		battle.init(object, interaction);
	}

	private class ScriptInteraction implements ScriptProcessInteraction {
		private SceneProcessManager process;

		public ScriptInteraction(SceneProcessManager process) {
			this.process = process;
		}

		@Override
		public void resume() {
			process.resume();
		}

		@Override
		public void waitForResume() {
			process.waitForResume();
		}
	}

	@Override
	public PlayerCommands getPlayerCmd() {

		return player;
	}

	@Override
	public SystemCommands getSystemCmd() {

		return system;
	}

	@Override
	public DialogCommands getDialogCmd() {

		return dialog;
	}

	@Override
	public VariableCommands getVariableCmd() {

		return variable;
	}

	@Override
	public MenuCommands getMenuCmd() {

		return menu;
	}

	@Override
	public BattleCommands getBattleCmd() {

		return battle;
	}

}
