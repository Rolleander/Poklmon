package com.broll.poklmon.battle.attack.script;

import com.broll.pokllib.poklmon.ElementType;
import com.broll.poklmon.battle.attack.AttackAttributePlus;
import com.broll.poklmon.battle.attack.AttributeChange;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.field.GlobalEffect;
import com.broll.poklmon.battle.field.PoklmonTeamEffect;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.field.WeatherEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;
import com.broll.poklmon.battle.util.BattleRandom;

public class ScriptAttackActions {

	private FightPoklmon user, target;
	private UseAttack attack;
	private FieldEffects fieldEffects;

	public ScriptAttackActions(FieldEffects fieldEffects) {
		this.fieldEffects = fieldEffects;
	}

	public void init(UseAttack attack, FightPoklmon user, FightPoklmon target) {
		this.attack = attack;
		this.target = target;
		this.user = user;
	}
	
	public double random(){
		return BattleRandom.random();
	}

	public boolean addUserEffect(String effect) {
		return addEffectChance(effect, 1, false);
	}

	public boolean addTargetEffect(String effect) {
		return addEffectChance(effect, 1, true);
	}

	public boolean addUserEffectChance(String effect, double chance) {
		return addEffectChance(effect, chance, false);
	}

	public boolean addTargetEffectChance(String effect, double chance) {
		return addEffectChance(effect, chance, true);
	}

	private boolean addEffectChance(String effect, double chance, boolean target) {
		// check main status changes
		try {
			MainFightStatus status = MainFightStatus.valueOf(effect.toUpperCase());
			return addMainStatusChange(status, chance, target);

		} catch (IllegalArgumentException e) {

		}

		try {
			EffectStatus status = EffectStatus.valueOf(effect.toUpperCase());
			return addEffectStatusChange(status, chance, target);
		} catch (IllegalArgumentException e) {

		}
		throw new RuntimeException("No Effect could be matched to: " + effect);
	}

	private boolean addMainStatusChange(MainFightStatus status, double chance, boolean target) {
		if (target) {
			if (!this.target.getStatusChanges().hasMainStatusChange(status)) {
				if (BattleRandom.random() <= chance) {
					attack.setChangeStatusTarget(status);
				}
				return false;
			}
		} else {
			if (!this.user.getStatusChanges().hasMainStatusChange(status)) {
				if (BattleRandom.random() <= chance) {
					attack.setChangeStatusUser(status);
				}
				return false;
			}
		}
		return true;
	}

	private boolean addEffectStatusChange(EffectStatus status, double chance, boolean target) {
		if (target) {
			if (!this.target.getStatusChanges().hasEffectChange(status)) {
				if (BattleRandom.random() <= chance) {
					attack.setEffectStatusTarget(status);
				}
				return false;
			}
		} else {
			if (!this.user.getStatusChanges().hasEffectChange(status)) {
				if (BattleRandom.random() <= chance) {
					attack.setEffectStatusUser(status);
				}
				return false;
			}
		}
		return true;
	}

	public void addUserAttributeChange(String attribute, int value, double chance) {
		addAttributeChange(false, attribute, value, chance);
	}

	public void addTargetAttributeChange(String attribute, int value, double chance) {
		addAttributeChange(true, attribute, value, chance);
	}

	private void addAttributeChange(boolean target, String attribute, int value, double chance) {
		AttackAttributePlus type = AttackAttributePlus.valueOf(attribute.toUpperCase());
		if (type != null) {
			if (BattleRandom.random() <= chance) {
				if (target) {
					attack.getAttributeChangeTarget().add(new AttributeChange(type, value));
				} else {
					attack.getAttributeChangeUser().add(new AttributeChange(type, value));
				}
			}
		} else {
			throw new RuntimeException("Attribute \"" + attribute + "\" could not be resolved!");
		}
	}

	public void addMultihit(int min, int max) {
		int multihit = min + (int) (((max + 1) - min) * BattleRandom.random());
		attack.setMultihitCount(multihit);
	}

	public void setSpecialEffect(String effectName) {
		try {
			if (effectName != null && !effectName.isEmpty()) {
				SpecialScript func = SpecialScript.valueOf(effectName.toUpperCase());
				attack.setSpecialFunction(func);
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Cant create Special Script from Identifier: " + effectName);
		}
	}

	public boolean hasState(FightPoklmon poklmon, String stateName) {
		PoklmonStatusChanges status = poklmon.getStatusChanges();
		try {
			MainFightStatus effect = MainFightStatus.valueOf(stateName.toUpperCase());
			if (effect == MainFightStatus.POISON) {
				if (status.getMainStatus() == MainFightStatus.TOXIN) {
					return true;
				}
			}
			return status.getMainStatus() == effect;
		} catch (IllegalArgumentException e) {

		}

		try {
			EffectStatus effect = EffectStatus.valueOf(stateName.toUpperCase());
			return status.hasEffectChange(effect);
		} catch (IllegalArgumentException e) {

		}

		throw new RuntimeException("StateName " + stateName + " could not be resolved!");
	}

	public boolean isGlobalEffect(String name) {
		try {
			GlobalEffect effect = GlobalEffect.valueOf(name.toUpperCase());
			return fieldEffects.hasGlobalEffect(effect);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Cant map global effect name: " + name);
		}

	}

	public boolean isWeatherEffect(String name) {
		try {
			WeatherEffect effect = WeatherEffect.valueOf(name.toUpperCase());
			return fieldEffects.getWeatherEffect() == effect;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Cant map global effect name: " + name);
		}
	}

	public boolean isUserTeamEffect(String name) {
		return isTeamEffect(name, true);
	}

	public boolean isTargetTeamEffect(String name) {
		return isTeamEffect(name, false);
	}

	private boolean isTeamEffect(String name, boolean userTeam) {
		try {
			TeamEffect effect = TeamEffect.valueOf(name.toUpperCase());
			PoklmonTeamEffect teamEffect = null;
			if (userTeam) {
				teamEffect = fieldEffects.getTeamEffects(user);
			} else {
				teamEffect = fieldEffects.getTeamEffects(target);
			}
			return teamEffect.hasTeamEffect(effect);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Cant map global effect name: " + name);
		}

	}

	public boolean isPoklmonType(FightPoklmon poklmon, String param) {
		ElementType baseType = poklmon.getPoklmon().getBaseType();
		ElementType secondType = poklmon.getPoklmon().getSecondaryType();

		if (baseType.name().toLowerCase().equals(param)) {
			return true;
		}
		if (secondType != null) {
			if (secondType.name().toLowerCase().equals(param)) {
				return true;
			}
		}
		return false;
	}

	public boolean addGlobalEffect(String global, double chance) {
		try {
			GlobalEffect effect = GlobalEffect.valueOf(global.toUpperCase());
			if (!fieldEffects.hasGlobalEffect(effect)) {
				if (BattleRandom.random() <= chance) {
					attack.setGlobalEffect(effect);
				}
				return false;
			}
			return true;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Could not match global effect to: " + global);
		}
	}

	public boolean addWeatherEffect(String global, double chance) {
		try {
			WeatherEffect effect = WeatherEffect.valueOf(global.toUpperCase());
			if (!fieldEffects.isWeatherEffect(effect)) {
				if (BattleRandom.random() <= chance) {
					attack.setWeatherEffect(effect);
				}
			} else {
				// cant set
				return true;
			}

		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Could not match weather effect to: " + global);
		}
		return false;
	}

	public boolean addUserTeamEffect(String effect, double chance) {
		return addTeamEffect(effect, true, chance);
	}

	public boolean addTargetTeamEffect(String effect, double chance) {
		return addTeamEffect(effect, false, chance);
	}

	private boolean addTeamEffect(String global, boolean userTeam, double chance) {
		try {
			TeamEffect effect = TeamEffect.valueOf(global.toUpperCase());
			if (userTeam) {
				if (!fieldEffects.getTeamEffects(user).hasTeamEffect(effect)) {
					if (BattleRandom.random() <= chance) {
						attack.setTeamEffectUser(effect);
					}
					return false;
				}
			} else {

				if (!fieldEffects.getTeamEffects(target).hasTeamEffect(effect)) {
					if (BattleRandom.random() <= chance) {
						attack.setTeamEffectTarget(effect);
					}
					return false;
				}
			}
			return true;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Could not match team effect to: " + global);
		}
	}

}
