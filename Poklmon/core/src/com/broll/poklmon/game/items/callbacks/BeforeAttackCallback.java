package com.broll.poklmon.game.items.callbacks;

import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.CustomScriptCall;

public interface BeforeAttackCallback extends CustomScriptCall{

	public void call(FightPoklmon attacker, UseAttack attack);
	
}
