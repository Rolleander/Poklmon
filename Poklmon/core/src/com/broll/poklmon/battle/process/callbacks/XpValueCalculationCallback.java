package com.broll.poklmon.battle.process.callbacks;

import com.broll.poklmon.battle.process.CustomScriptCall;

public interface XpValueCalculationCallback extends CustomScriptCall{

	public int call(int exp);
	
}
