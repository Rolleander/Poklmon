package com.broll.pokleditor.attackdex;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackDamage;
import com.broll.pokllib.attack.AttackPriority;
import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.poklmon.ElementType;

public class AttackGenerator {

	
	public static Attack newAtack()
	{
		Attack atk=new Attack();
		atk.setAnimationID(0);
		atk.setAttackType(AttackType.PHYSICAL);
		atk.setEffectCode("");
		atk.setElementType(ElementType.NORMAL);
		atk.setName("New Attack");
		AttackDamage damage=new AttackDamage();
		damage.setAp(30);
		damage.setDamage(50);
		damage.setHitchance(1);
		damage.setPriority(AttackPriority.STANDARD);
		atk.setDamage(damage);
		return atk;
	}
	
}
