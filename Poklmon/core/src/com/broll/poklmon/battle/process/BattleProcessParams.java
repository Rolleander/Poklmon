package com.broll.poklmon.battle.process;

import java.util.ArrayList;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.AttackAttributePlus;
import com.broll.poklmon.battle.attack.AttributeChange;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.poklmon.FightAttributesCalc;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.render.BattleSequenceRender;
import com.broll.poklmon.battle.render.BattleSequences;
import com.broll.poklmon.battle.render.sequence.PoklmonAttributeChangeAnimation;
import com.broll.poklmon.battle.util.message.BattleMessages;

public class BattleProcessParams extends BattleProcessControl {

	public BattleProcessParams(BattleManager manager, BattleProcessCore handler) {
		super(manager, handler);
	}

	public synchronized void doParamChanges(UseAttack attack, FightPoklmon user, FightPoklmon target) {
		// param changes on target
		doParamChanges(target, attack.getAttributeChangeTarget());
		// param changes user
		doParamChanges(user, attack.getAttributeChangeUser());
	}

	public void doAttributeChange(FightPoklmon poklmon, AttributeChange change) {
		AttackAttributePlus atr = change.getAttribute();
		int strength = change.getStrength();
		// do change
		FightAttributesCalc.doAttributeChange(poklmon, change);
		boolean wasEffective = FightAttributesCalc.isEffectiveChange();
		String atrName = atr.getDisplayName();
		String poklName = poklmon.getName();
		String changeName = null;

		if (wasEffective) {
			changeName = BattleMessages.changeAttributePositive;
			if (strength < 0) {
				changeName = BattleMessages.changeAttributeNegative;
			}

			if (strength < 0) {
				strength *= -1;
			}
			String changeStrengthText = BattleMessages.attributeChangeStrength[strength - 1];
			String text = BattleMessages.putName(BattleMessages.changeAttribute, poklName, atrName) + changeName
					+ changeStrengthText;
			// show change animation
			BattleSequenceRender render = manager.getBattleRender().getSequenceRender();
			PoklmonAttributeChangeAnimation animation = (PoklmonAttributeChangeAnimation) render
					.getSequenceRender(BattleSequences.POKLMON_ATTRIBUTE_CHANGE);
			animation.init(poklmon, change, text);
			render.showAnimation(BattleSequences.POKLMON_ATTRIBUTE_CHANGE, processThreadHandler);
			waitForResume();

		} else {
			// change failed
			changeName = BattleMessages.changeAttributePositiveFailed;
			if (strength < 0) {
				changeName = BattleMessages.changeAttributeNegativeFailed;
			}

			// no animation!, just text
			String text = BattleMessages.putName(BattleMessages.changeAttribute, poklName, atrName) + changeName;
			showText(text);
		}
	}

	private synchronized void doParamChanges(FightPoklmon poklmon, ArrayList<AttributeChange> changes) {
		if (poklmon.isFainted()) {
			return;
		}
		for (AttributeChange change : changes) {
			doAttributeChange(poklmon, change);
		}
	}
}
