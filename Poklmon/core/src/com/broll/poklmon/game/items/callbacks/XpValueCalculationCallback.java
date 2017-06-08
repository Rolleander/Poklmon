package com.broll.poklmon.game.items.callbacks;

import com.broll.poklmon.battle.process.CustomScriptCall;

public interface XpValueCalculationCallback extends CustomScriptCall{

	public int call(int exp);
	
}
