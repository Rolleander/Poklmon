package com.broll.poklmon.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.broll.pokllib.game.StartInformation;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.network.NetworkServer;
import com.broll.poklmon.save.manage.SaveFileManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowSizeLimits(PoklmonGame.WIDTH, PoklmonGame.HEIGHT, PoklmonGame.WIDTH, PoklmonGame.HEIGHT);
        config.setResizable(false);
        config.setTitle("Poklmon");
        config.setForegroundFPS(PoklmonGame.FPS);
        config.setWindowIcon("data/resource/graphics/icon.png");
        StartInformation startInformation;
        if (arg.length > 0) {
            startInformation = StartInformation.parseFromConsole(arg[0]);
        } else {
            startInformation = new StartInformation();
        }

        //read properties
        try {
            Properties prop = new Properties();
            InputStream input = new FileInputStream("game.properties");
            prop.load(input);
            startInformation.setTouchControling(isProperty(prop, "appmode"));
           if(isProperty(prop, "fullscreen")){
               Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
               config.setWindowedMode(displayMode.width, displayMode.height);
               config.setDecorated(false);
           }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //	startInformation.debugMap(10,27,35);
        //	startInformation.debugScene(BattleDebugState.class);

        SaveFileManager.initSaveInterface(new DesktopSaveFolder());
        NetworkServer.setAddressProvider(new DesktopIpProvider());
        new Lwjgl3Application(new PoklmonGame(startInformation), config);
    }

    private static boolean isProperty(Properties prop, String property) {
        String value = prop.getProperty(property, "false").toLowerCase();
        return value.equals("true");
    }


}
