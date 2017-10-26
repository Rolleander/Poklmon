package com.broll.poklmon;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.broll.poklmon.main.StartInformation;
import com.broll.poklmon.main.states.TitleMenuState;
import com.broll.poklmon.network.NetworkServer;
import com.broll.poklmon.save.manage.SaveFileManager;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer=false;
		config.useCompass=false;
		config.useGyroscope=false;
		config.useImmersiveMode=true;
		StartInformation start=new StartInformation(null);
		start.debugMap(8,36,9);
	//	start.debugScene(TitleMenuState.class);
		start.setTouchControling(true);
		SaveFileManager.initSaveInterface(new AndroidSaveFolder());
		NetworkServer.setAddressProvider(new AndroidIpProvider(getContext()));
		initialize(new PoklmonGame(start), config);

	}
}
