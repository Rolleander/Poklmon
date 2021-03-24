package com.broll.poklmon.battle.util;

import com.broll.poklmon.gui.dialog.TimerListener;

import java.util.Timer;
import java.util.TimerTask;

public class BattleTimer {

	private Timer timer;
	
	public BattleTimer()
	{
		
	}

	public void wait(float seconds, final TimerListener listener)
	{
		int milli=(int) (seconds*1000);
		timer=new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				listener.timerStopped();
			}
		},milli);
	}
	
	
}
