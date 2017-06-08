package com.broll.poklmon.game.items.callbacks;

import java.util.List;

import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.process.CustomScriptCall;

public interface XpReceiverCalculationCallback extends CustomScriptCall{

	public void call(List<PlayerPoklmon> expPoklmon);
	
}
