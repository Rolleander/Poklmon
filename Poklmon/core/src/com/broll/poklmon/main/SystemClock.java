package com.broll.poklmon.main;

import com.badlogic.gdx.Gdx;

import java.util.TimerTask;

public class SystemClock {


	private static float time;
	
	public static boolean isTick() {
		float expired = Gdx.graphics.getDeltaTime();
		time+=expired;
		if(time >= 1){
			time-=1;
			return true;
		}
		return false;
	}

}
