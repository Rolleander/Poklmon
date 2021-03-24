package com.broll.poklmon.battle.attack.script;

import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.poklmon.FightPoklmon;

public class AttackScriptScope  {

	private ScriptAttackActions actions;
	private UseAttack attack;
	private FightPoklmon user, target;

	public AttackScriptScope(FieldEffects fieldEffects) {
		actions = new ScriptAttackActions(fieldEffects);
	}

	public void init(UseAttack attack, FightPoklmon user, FightPoklmon target) {
		this.attack = attack;
		this.user = user;
		this.target = target;
		actions.init(attack, user, target);
	}
/*
	@Override
	public void command(DoCommand cmd) {
		String c = cmd.getCommandName();
		switch (c) {
		case "changetargetattributes":
			String param = (String) cmd.getParameter().get(0).getValue();
			int value = (int) getValue(cmd.getParameter().get(1).getValue());
			actions.addAttributeChange(true, param, value);
			break;
		case "changeuserattributes":
			String param2 = (String) cmd.getParameter().get(0).getValue();
			int value2 = (int) getValue(cmd.getParameter().get(1).getValue());
			actions.addAttributeChange(false, param2, value2);
			break;
		case "addtargeteffect":
			String effect = (String) cmd.getParameter().get(0).getValue();
			double chance = getValue(cmd.getParameter().get(1).getValue());
			actions.addEffectChance(effect, chance, true);
			break;
		case "addusereffect":
			String effect2 = (String) cmd.getParameter().get(0).getValue();
			double chance2 = getValue(cmd.getParameter().get(1).getValue());
			actions.addEffectChance(effect2, chance2, false);
			break;
		case "multihit":
			int min = (int) getValue(cmd.getParameter().get(0).getValue());
			int max = (int) getValue(cmd.getParameter().get(1).getValue());
			actions.addMultihit(min, max);
			break;
		case "addglobaleffect":
			String global = (String) cmd.getParameter().get(0).getValue();
			actions.addGlobalEffect(global);
			break;
		case "addweathereffect":
			String weather = (String) cmd.getParameter().get(0).getValue();
			actions.addWeatherEffect(weather);
			break;
		case "adduserteameffect":
			String teameffect1 = (String) cmd.getParameter().get(0).getValue();
			actions.addTeamEffect(teameffect1, true);
			break;
		case "addtargetteameffect":
			String teameffect2 = (String) cmd.getParameter().get(0).getValue();
			actions.addTeamEffect(teameffect2, false);
			break;
		}
	}

	@Override
	public void set(SetCommand cmd) {
		String v = cmd.getVariableName();
		int var = 0;
		double dvar = 0;
		switch (v) {
		case "volltrefferchance":
			var = (int) cmd.getParameter().getValue();
			attack.setVolltrefferChance(var);
			break;
		case "hitalways":
			attack.setHitsAlways(true);
			break;
		case "hitchance":
			dvar = (double) cmd.getParameter().getValue();
			attack.setHitchance((float) dvar);
			break;
		case "damage":
			var = (int) cmd.getParameter().getValue();
			attack.setDamage(var);
			break;
		case "absorbedamage":
			dvar = (double) cmd.getParameter().getValue();
			attack.setAbsorbeDamagePercent(dvar);
			break;
		case "selfdamage":
			dvar = (double) cmd.getParameter().getValue();
			attack.setSelfDamagePercent(dvar);
			break;
		case "lifelose":
			dvar = (double) cmd.getParameter().getValue();
			attack.setLifeLose(dvar);
			break;
		case "selfheal":
			dvar = (double) cmd.getParameter().getValue();
			attack.setKpHealPercent(dvar);
			break;
		case "specialscript":
			String scriptName = (String) cmd.getParameter().getValue();
			actions.setSpecialEffect(scriptName);
			break;
		case "cancelattack":
			attack.setHasNoEffect(true);
			break;
		case "ignoreconfusion":
			attack.setIgnoreConfusion(true);
			break;
		case "ignoreice":
			attack.setIgnoreIce(true);
			break;
		case "ignoresleep":
			attack.setIgnoreSleep(true);
			break;
		case "ignoreparalyze":
			attack.setIgnoreParalyze(true);
			break;
		case "targetkppercentdamage":
			dvar = (double) cmd.getParameter().getValue();
			attack.setPercentCurrentDamage(dvar);
			break;
		case "targetmaxkppercentdamage":
			dvar = (double) cmd.getParameter().getValue();
			attack.setPercentDamage(dvar);
			break;
		case "animation":
			var = (int) cmd.getParameter().getValue();
			attack.setCustomAnimation(var);
			break;
		case "skipdamage":
			attack.setSkipDamage(true);
			break;
		case "fixdamage":
			var = (int) cmd.getParameter().getValue();
			attack.setFixDamage(var);
			break;
		}
	}

	@Override
	public boolean isCommand(IsCommand cmd) {
		String v = cmd.getVariableName();
		try {
			switch (v) {
			case "random":
				return ScriptCompare.is(cmd, BattleRandom.random());
			case "targetstate":
				String param = (String) cmd.getParameter().getValue();
				return actions.hasState(target, param);
			case "userstate":
				param = (String) cmd.getParameter().getValue();
				return actions.hasState(user, param);
			case "userpoklmontype":
				param = (String) cmd.getParameter().getValue();
				return actions.isPoklmonType(user, param);
			case "targetpoklmontype":
				param = (String) cmd.getParameter().getValue();
				return actions.isPoklmonType(target, param);
			case "globaleffect":
				param = (String) cmd.getParameter().getValue();
				return actions.isGlobalEffect(param);
			case "weathereffect":
				param = (String) cmd.getParameter().getValue();
				return actions.isWeatherEffect(param);
			case "userteameffect":
				param = (String) cmd.getParameter().getValue();
				return actions.isTeamEffect(param, true);
			case "targetteameffect":
				param = (String) cmd.getParameter().getValue();
				return actions.isTeamEffect(param, false);
			case "hasattackadvantage":
				boolean comp = (boolean) cmd.getParameter().getValue();
				return comp == actions.hasMoreAttackThanEnemyDef(false);
			case "hasspecialattackadvantage":
				comp = (boolean) cmd.getParameter().getValue();
				return comp == actions.hasMoreAttackThanEnemyDef(true);
			case "userkp":
			 	return ScriptCompare.is(cmd, user.getAttributes().getHealthPercent());			
			case "targetkp":
				return ScriptCompare.is(cmd, target.getAttributes().getHealthPercent());
			}
		}

		catch (SyntaxError e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void noCommand() {

	}

	public double getValue(Object value) {
		if (value instanceof Integer) {
			return (int) value;
		} else if (value instanceof Double) {
			return (double) value;
		}
		return 0;
	}
	*/
}
