package com.broll.poklmon.battle.process.callbacks;

import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.CustomScriptCall;

public interface AfterAttackCallback extends CustomScriptCall{

	void call(FightPoklmon attacker, UseAttack attack, boolean didHit);
	
}
