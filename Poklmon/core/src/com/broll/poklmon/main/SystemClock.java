package com.broll.poklmon.main;

import java.util.TimerTask;

public class SystemClock extends TimerTask{

	
	public static boolean isTick;
	
	
	public static boolean isTick() {
		if(isTick)
		{
			isTick=false;
			return true;
		}
		return false;
	}


	@Override
	public void run() {
		isTick=true;
	}
}
