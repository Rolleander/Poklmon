package com.broll.poklmon;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.broll.pokllib.game.StartInformation;
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
		StartInformation start=new StartInformation();
	//	start.debugScene(TitleMenuState.class);
		start.setTouchControling(true);
		SaveFileManager.initSaveInterface(new AndroidSaveFolder());
		NetworkServer.setAddressProvider(new AndroidIpProvider(getContext()));
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initialize(new PoklmonGame(start), config);

	}
}
