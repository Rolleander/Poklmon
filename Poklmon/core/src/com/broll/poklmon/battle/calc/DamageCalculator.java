package com.broll.poklmon.battle.calc;

import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.poklmon.ElementType;
import com.broll.pokllib.poklmon.TypeCompare;
import com.broll.pokllib.poklmon.TypeComperator;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.poklmon.FightAttributes;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.BattleRandom;

public class DamageCalculator {

	private static float typeBonus;

	public static int getDamage(FightPoklmon user, FightPoklmon target, UseAttack attack, boolean volltreffer) {
		int level = user.getLevel();

		int atk = getAttack(user, attack.getType(), volltreffer);
		int defence = getDefence(target, attack.getType(), volltreffer);
		float value = (int) ((float) level * 2 / 5f);
		value += 2;
		value *= attack.getDamage() * atk;
		value = (int) (value / 50);
		value = (int) (value / defence);
		value += 2;

		if (volltreffer) {
			value = (int) (value * 2);
		}
		value = (int) (value * getRandom());
		value = (int) (value * getSTABBonus(user, attack.getElement()));
		typeBonus = getTypeBonus(attack.getElement(), target);
		value = (int) (value * typeBonus);
		value = AttackDamageBonus.getAttackBonus(attack, user, target, value);
		int damage = (int) value;
		if (damage < 1) {
			damage = 1;
			if (typeBonus == 0) {
				damage = 0;
			}
		}
		return damage;
	}

	public static int getSpeicalDamage(FightPoklmon user, FightPoklmon target, UseAttack attack) {
		int kp = target.getAttributes().getHealth();
		int maxkp = target.getAttributes().getMaxhealth();

		int atk = 0;

		// calc current kp damage
		atk = (int) (kp * attack.getPercentCurrentDamage());
		// calc max kp damage
		atk += (int) (maxkp * attack.getPercentDamage());

		// calc satic damage
		int fix = attack.getFixDamage();
		atk += fix;

		// dont do damage on resisting elements
		typeBonus = getTypeBonus(attack.getElement(), target);
		if (typeBonus == 0) {
			return 0;
		} else {
			// set typebonus to standard for text output
			typeBonus = 1;
		}

		if (atk < 1) {
			atk = 1;
		}

		return atk;
	}

	public static TypeCompare getTypeBonus() {
		if (typeBonus == 0) {
			return TypeCompare.NOEFFECT;
		} else if (typeBonus == 1) {
			return TypeCompare.STANDARD;
		} else if (typeBonus < 1) {
			return TypeCompare.NOTEFFECTIVE;
		} else if (typeBonus > 1) {
			return TypeCompare.EFFECTIVE;
		}
		return null;
	}

	private static float getRandom() {
		return (float) ((85 + BattleRandom.random() * 16) / 100);
	}

	private static float getTypeBonus(ElementType attack, FightPoklmon target) {
		float bonus = 1;
		if (attack == null) {
			return 1;
		}
		ElementType type1 = target.getPoklmon().getBaseType();
		ElementType type2 = target.getPoklmon().getSecondaryType();

		bonus = TypeComperator.getTypeCompare(attack, type1).getMultiplicator();
		if (type2 != null) {
			float multi2 = TypeComperator.getTypeCompare(attack, type2).getMultiplicator();
			bonus *= multi2;
		}
		return bonus;
	}

	private static float getSTABBonus(FightPoklmon user, ElementType attack) {
		if (user.getPoklmon().getBaseType() == attack || user.getPoklmon().getSecondaryType() == attack) {
			return 1.5f;
		} else {
			return 1;
		}
	}

	private static int getAttack(FightPoklmon poklmon, AttackType type, boolean volltreffer) {
		FightAttributes att = poklmon.getAttributes();
		switch (type) {
		case PHYSICAL:
			if (volltreffer && att.getAttackPlus() < 0) {
				return FightPoklmonParameterCalc.getAttackBase(poklmon);
			}
			return FightPoklmonParameterCalc.getAttack(poklmon);
		case SPECIAL:
			if (volltreffer && att.getSpecial_attackPlus() < 0) {
				return FightPoklmonParameterCalc.getSpecialAttackBase(poklmon);
			}
			return FightPoklmonParameterCalc.getSpecialAttack(poklmon);
		case STATUS:
			return 1;
		}
		return 0;
	}

	private static int getDefence(FightPoklmon poklmon, AttackType type, boolean volltreffer) {
		FightAttributes att = poklmon.getAttributes();
		switch (type) {
		case PHYSICAL:
			if (volltreffer && att.getDefencePlus() > 0) {
				return FightPoklmonParameterCalc.getDefenceBase(poklmon);
			}
			return FightPoklmonParameterCalc.getDefence(poklmon);
		case SPECIAL:
			if (volltreffer && att.getSpecial_defencePlus() > 0) {
				return FightPoklmonParameterCalc.getSpecialDefenceBase(poklmon);
			}
			return FightPoklmonParameterCalc.getSpecialDefence(poklmon);
		case STATUS:
			return 1;
		}
		return 0;
	}

}
