package com.broll.poklmon.desktop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.main.StartInformation;
import com.broll.poklmon.main.states.BattleDebugState;
import com.broll.poklmon.main.states.BattleState;
import com.broll.poklmon.main.states.MenuDebugState;
import com.broll.poklmon.network.NetworkServer;
import com.broll.poklmon.save.manage.SaveFileManager;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PoklmonGame.WIDTH;
		config.height = PoklmonGame.HEIGHT;
		config.resizable = false;
		config.title = "Poklmon";
		config.vSyncEnabled=true;
		config.foregroundFPS = PoklmonGame.FPS;
		StartInformation startInformation;
		if(arg.length>0) {
			startInformation=StartInformation.parseFromConsole(arg[0]);
		}
		else {
			startInformation=new StartInformation(null);
		}
		
		//read properties
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream("game.properties");
			prop.load(input);
			startInformation.setTouchControling(isProperty(prop, "appmode"));
			config.fullscreen=isProperty(prop, "fullscreen");	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	//	startInformation.debugMap(10,27,35);
	//	startInformation.debugScene(BattleDebugState.class);

		SaveFileManager.initSaveInterface(new DesktopSaveFolder());
		NetworkServer.setAddressProvider(new DesktopIpProvider());
		new LwjglApplication(new PoklmonGame(startInformation), config);
	}
	
	private static boolean isProperty(Properties prop, String property) {
		String value=prop.getProperty(property, "false").toLowerCase();
		return value.equals("true");
	}
	
	
}
