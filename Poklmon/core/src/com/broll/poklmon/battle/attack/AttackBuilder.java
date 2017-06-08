package com.broll.poklmon.battle.attack;

import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.data.DataContainer;

public abstract class AttackBuilder {


	public AttackBuilder(DataContainer data, FieldEffects fieldEffects){
		
	}
	
	public abstract UseAttack useAttack(FightAttack attack, FightPoklmon user, FightPoklmon target);
	
}
