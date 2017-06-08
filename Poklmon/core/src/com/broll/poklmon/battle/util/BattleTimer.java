package com.broll.poklmon.battle.util;

import java.util.Timer;
import java.util.TimerTask;

import com.broll.poklmon.gui.dialog.TimerListener;

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
