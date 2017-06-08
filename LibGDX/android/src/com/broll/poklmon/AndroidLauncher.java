package com.broll.poklmon;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.broll.poklmon.main.StartInformation;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer=false;
		config.useCompass=false;
		config.useGyroscope=false;
		initialize(new PoklmonGame(new StartInformation(null)), config);
	}
}
