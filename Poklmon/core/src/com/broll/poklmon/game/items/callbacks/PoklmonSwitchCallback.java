package com.broll.poklmon.game.items.callbacks;

import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.CustomScriptCall;

public interface PoklmonSwitchCallback extends CustomScriptCall{

	public void call(FightPoklmon poklmonLeave, FightPoklmon poklmonNext);
	
}
