package com.broll.poklmon.battle.process.callbacks;

import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.process.CustomScriptCall;

import java.util.List;

public interface XpReceiverCalculationCallback extends CustomScriptCall{

	public void call(List<PlayerPoklmon> expPoklmon);
	
}
