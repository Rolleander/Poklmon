package com.broll.poklmon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.main.StartInformation;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PoklmonGame.WIDTH;
		config.height = PoklmonGame.HEIGHT;
		config.resizable = false;
		config.title = "Poklmon";
		config.vSyncEnabled=true;
		config.foregroundFPS = PoklmonGame.FPS;
		StartInformation startInformation = new StartInformation(null);
	//	startInformation.debugScene(TitleMenuState.class);
		startInformation.debugMap(0, 10, 10);
	//	startInformation.debugAttack(0);
	//	startInformation.debugScene(AnimationDebugState.class);
		new LwjglApplication(new PoklmonGame(startInformation), config);
	}
}
