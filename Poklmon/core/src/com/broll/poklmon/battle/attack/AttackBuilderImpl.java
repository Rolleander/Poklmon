package com.broll.poklmon.battle.attack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.jscript.PackageImporter;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.battle.attack.script.ScriptAttackActions;
import com.broll.poklmon.battle.calc.FightPoklmonParameterCalc;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.data.DataContainer;

public class AttackBuilderImpl extends AttackBuilder {

	private ScriptAttackActions scriptActions;

	public AttackBuilderImpl(DataContainer data, FieldEffects fieldEffects) {
		super(data, fieldEffects);
		scriptActions = new ScriptAttackActions(fieldEffects);
	}

	@Override
	public UseAttack useAttack(FightAttack attack, FightPoklmon user, FightPoklmon target) {
		UseAttack atk = new UseAttack();
		Attack adata = attack.getAttack();
		atk.setDamage(adata.getDamage().getDamage());
		atk.setElement(adata.getElementType());
		atk.setHitchance(adata.getDamage().getHitchance());
		atk.setType(adata.getAttackType());
		
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("rhino");
		scriptActions.init(atk, user, target);
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

		String effect = attack.getAttack().getEffectCode();
		if (effect != null && !effect.isEmpty()) {
			try {
				engine.eval(importer.buildScript(effect));
			} catch (javax.script.ScriptException e) {
				System.err.println("Error in AttackScript ["+attack.getAttack().getId()+"] " + attack.getAttack().getName() + ": \n" + effect);
				e.printStackTrace();
			//	BugSplashDialog.showError("Error parsing Attack \""+attack.getAttack().getName()+"\"!");
			}
		}

		return atk;
	}

}
