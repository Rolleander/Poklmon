package com.broll.poklmon.game.items.execute;

import com.broll.pokllib.item.Item;
import com.broll.poklmon.battle.item.ItemScriptExecutor;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.ScriptInstance;
import com.broll.poklmon.save.PoklmonData;

public class MenuItemExecutor {

	private ItemScriptExecutor scriptExecutor;
	private GameManager game;

	public MenuItemExecutor(GameManager game) {
		this.scriptExecutor = new ItemScriptExecutor();
		this.game = game;
	}

	public void useMedicine(final PoklmonData target, final Item item) {

		game.getSceneProcessManager().runScript(new ScriptInstance(new Runnable() {
			@Override
			public void run() {
				MenuMedicineItemRunner runner = new MenuMedicineItemRunner(game);
				runner.init(target);
				scriptExecutor.executeMedicineScript(runner, item);

				if (runner.isCanceled()) {

					game.getMessageGuiControl().showText("Das Item hatte keine Wirkung!");
					game.getSceneProcessManager().waitForResume();
				}
				game.getMessageGuiControl().hideGui();
			}
		}));
	}

	public void useBasisItem(final Item item) {
		game.getSceneProcessManager().runScript(new ScriptInstance(new Runnable() {
			@Override
			public void run() {
				BasisItemRunner runner = new BasisItemRunner(game);
				runner.init();
				scriptExecutor.executeBasisItemScript(runner, item);
				game.getMessageGuiControl().hideGui();
			}
		}));
	}

	public void useOtherItem(final Item item, final ItemExecuteCallback callback) {

		game.getSceneProcessManager().runScript(new ScriptInstance(new Runnable() {
			@Override
			public void run() {
				OtherItemRunner runner = new OtherItemRunner(game);
				runner.init();
				scriptExecutor.executeOtherItemScript(runner, item);
				game.getMessageGuiControl().hideGui();
				callback.itemExecuted(item, runner.isUseItem());
			}
		}));
	}
	
	public void useAttackItem(final Item item, final ItemExecuteCallback callback) {

		game.getSceneProcessManager().runScript(new ScriptInstance(new Runnable() {
			@Override
			public void run() {
				AttackItemRunner runner = new AttackItemRunner(game);
				runner.init();
				scriptExecutor.executeAttackItemScript(runner, item);
				game.getMessageGuiControl().hideGui();
				callback.itemExecuted(item, !runner.isReclaimItem());
			}
		}));
	}


}
