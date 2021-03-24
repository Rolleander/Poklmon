package com.broll.poklmon.battle.poklmon;

import com.broll.poklmon.battle.attack.AttributeChange;

public class FightAttributesCalc {

	private static boolean effectiveChange;

	public static void doAttributeChange(FightPoklmon poklmon, AttributeChange change) {
		FightAttributes atr = poklmon.getAttributes();
		effectiveChange = true;
		int v = change.getStrength();
		switch (change.getAttribute()) {
		case ATTACK:
			atr.setAttackPlus(getNewValue(atr.getAttackPlus(), v));
			break;
		case DEFENCE:
			atr.setDefencePlus(getNewValue(atr.getDefencePlus(), v));
			break;
		case FLUCHTWERT:
			atr.setFluchtwertPlus(getNewValue(atr.getFluchtwertPlus(), v));
			break;
		case GENAUIGKEIT:
			atr.setGenauigkeitsPlus(getNewValue(atr.getGenauigkeitsPlus(), v));
			break;
		case INITIATIVE:
			atr.setInitPlus(getNewValue(atr.getInitPlus(), v));
			break;
		case SPECIALATTACK:
			atr.setSpecial_attackPlus(getNewValue(atr.getSpecial_attackPlus(), v));
			break;
		case SPECIALDEFENCE:
			atr.setSpecial_defencePlus(getNewValue(atr.getSpecial_defencePlus(), v));
			break;
		}
	}

	private static int getNewValue(int old, int change) {
		int newv = old + change;
		if (newv > 6) {
			if (old == 6) {
				effectiveChange = false;
			}
			newv = 6;
		}
		if (newv < -6) {
			if (old == -6) {
				effectiveChange = false;
			}
			newv = -6;
		}
		return newv;
	}

	public static boolean isEffectiveChange() {
		return effectiveChange;
	}
}
