package com.broll.poklmon.battle.util;

import com.broll.poklmon.battle.process.BattleProcessCore;

public class ProcessThreadHandler {

	private BattleProcessCore core;
	
	public ProcessThreadHandler(BattleProcessCore core)
	{
		this.core=core;
	}
	
	public synchronized void resume()
	{
		core.resume();
	}
	
}
