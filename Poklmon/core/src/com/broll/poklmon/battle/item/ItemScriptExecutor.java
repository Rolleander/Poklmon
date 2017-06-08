package com.broll.poklmon.battle.item;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.item.Item;
import com.broll.pokllib.jscript.PackageImporter;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.calc.FightPoklmonParameterCalc;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.process.CustomScriptCall;
import com.broll.poklmon.game.items.callbacks.XpReceiverCalculationCallback;
import com.broll.poklmon.game.items.execute.AttackItemRunner;
import com.broll.poklmon.game.items.execute.BasisItemRunner;
import com.broll.poklmon.game.items.execute.MenuMedicineItemRunner;
import com.broll.poklmon.game.items.execute.OtherItemRunner;
import com.broll.poklmon.model.CharacterWorldState;

public class ItemScriptExecutor {

	private ScriptEngine engine;

	public ItemScriptExecutor() {
	}

	private void buildScriptEngine() {
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("rhino");
	}

	private void runItemScript(Item item) {
		PackageImporter importer = new PackageImporter();
		importer.addPackage(PoklmonWesen.class.getPackage());
		importer.addPackage(ObjectDirection.class.getPackage());
		importer.addPackage(UseAttack.class.getPackage());
		importer.addPackage(TeamEffect.class.getPackage());
		importer.addPackage(AttackType.class.getPackage());
		importer.addPackage(FightPoklmon.class.getPackage());
		importer.addPackage(ObjectDirection.class.getPackage());
		importer.addPackage(FightPoklmonParameterCalc.class.getPackage());
		importer.addPackage(XpReceiverCalculationCallback.class.getPackage());
		importer.addPackage(CustomScriptCall.class.getPackage());
		importer.addPackage(MainFightStatus.class.getPackage());
		importer.addPackage(CharacterWorldState.class.getPackage());

		String script = importer.buildScript("function f(){\n"+item.getEffect()+"}");
		try {
			engine.eval(script);
			Invocable invocable = (Invocable) engine;
			invocable.invokeFunction("f");
		} catch (javax.script.ScriptException e) {
			System.err.println("Error in ItemScript [" + item.getId() + "] " + item.getName() + ": \n" + script);
			e.printStackTrace();
			// BugSplashDialog.showError("Error parsing Attack
			// \""+attack.getAttack().getName()+"\"!");
		} catch (NoSuchMethodException e) {
			System.err.println("Error in ItemScript [" + item.getId() + "] " + item.getName() + ": \n" + script);
			e.printStackTrace();
		}
	}

	public PoklballItemRunner buildPoklball(Item item, BattleManager manager, BattleProcessCore handler) {
		PoklballItemRunner runner = new PoklballItemRunner(manager, handler);
		buildScriptEngine();
		engine.put("poklball", runner);
		runItemScript(item);
		return runner;
	}

	public MedicineItemRunner executeMedicineScript(FightPoklmon target, Item item, BattleManager manager,
			BattleProcessCore handler) {
		MedicineItemRunner runner = new MedicineItemRunner(manager, handler);
		runner.init(target);
		buildScriptEngine();
		engine.put("medicine", runner);
		runItemScript(item);
		return runner;
	}

	public void buildWearable(Item item, FightPoklmon carrier, boolean isPlayerPoklmon, BattleManager manager,
			BattleProcessCore handler) {
		WearableItemRunner runner = new WearableItemRunner(manager, handler);
		runner.init(carrier, isPlayerPoklmon);
		buildScriptEngine();
		engine.put("wearable", runner);
		runItemScript(item);
	}

	public void executeMedicineScript(MenuMedicineItemRunner runner, Item item) {
		buildScriptEngine();
		engine.put("medicine", runner);
		runItemScript(item);
	}

	public void executeOtherItemScript(OtherItemRunner runner, Item item) {
		buildScriptEngine();
		engine.put("other", runner);
		runItemScript(item);
	}

	public void executeBasisItemScript(BasisItemRunner runner, Item item) {
		buildScriptEngine();
		engine.put("basis", runner);
		runItemScript(item);
	}

	public void executeAttackItemScript(AttackItemRunner runner, Item item) {
		buildScriptEngine();
		engine.put("attack", runner);
		runItemScript(item);
	}
}
