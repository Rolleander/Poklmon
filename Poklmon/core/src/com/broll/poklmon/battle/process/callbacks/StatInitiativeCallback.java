package com.broll.poklmon.battle.process.callbacks;

import com.broll.poklmon.battle.process.CustomScriptCall;

public interface StatInitiativeCallback extends CustomScriptCall{

	public int call(int value);
	
}
