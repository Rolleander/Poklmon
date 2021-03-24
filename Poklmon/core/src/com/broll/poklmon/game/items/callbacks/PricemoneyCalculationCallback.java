package com.broll.poklmon.game.items.callbacks;

import com.broll.poklmon.battle.process.CustomScriptCall;

public interface PricemoneyCalculationCallback extends CustomScriptCall{

	public int call(int money);
	
}
