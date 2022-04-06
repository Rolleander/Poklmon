package com.broll.poklmon.battle.attack;

import com.badlogic.gdx.Gdx;
import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.jscript.PackageImporter;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.script.ScriptAttackActions;
import com.broll.poklmon.battle.calc.FightPoklmonParameterCalc;
import com.broll.poklmon.battle.process.callbacks.SkipAttackCallback;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.script.ProcessingUtils;
import com.broll.poklmon.script.ScriptEngineFactory;

import javax.script.ScriptEngine;

public class AttackBuilder {

	private ScriptAttackActions scriptActions;

	public AttackBuilder(BattleManager battle, BattleProcessCore core) {
		scriptActions = new ScriptAttackActions(battle, core);
	}

	public UseAttack useAttack(FightAttack attack, FightPoklmon user, FightPoklmon target) {
		UseAttack atk = new UseAttack();
		Attack adata = attack.getAttack();
		atk.setDamage(adata.getDamage().getDamage());
		atk.setElement(adata.getElementType());
		atk.setHitchance(adata.getDamage().getHitchance());
		atk.setType(adata.getAttackType());
		
		ScriptEngine engine =ScriptEngineFactory.createScriptEngine();
		scriptActions.init(attack, atk, user, target);
		engine.put("atk", atk);
		engine.put("user", user);
		engine.put("target", target);
		engine.put("util", scriptActions);
		PackageImporter importer = new PackageImporter();
		importer.addPackage(PoklmonWesen.class.getPackage());
		importer.addPackage(ObjectDirection.class.getPackage());
		importer.addPackage(UseAttack.class.getPackage());
		importer.addPackage(TeamEffect.class.getPackage());
		importer.addPackage(FightPoklmon.class.getPackage());
		importer.addPackage(ObjectDirection.class.getPackage());
		importer.addPackage(FightPoklmonParameterCalc.class.getPackage());
		importer.addPackage(SkipAttackCallback.class.getPackage());
		importer.addPackage(AttackType.class.getPackage());
		String effect = attack.getAttack().getEffectCode();
		if (effect != null && !effect.isEmpty()) {
			ProcessingUtils.runScript(engine, importer.buildScript(effect));
		}
		return atk;
	}

}
