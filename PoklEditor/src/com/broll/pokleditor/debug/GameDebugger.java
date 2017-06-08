package com.broll.pokleditor.debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.broll.pokleditor.main.PoklEditorMain;
import com.broll.poklmon.data.DataLoader;
import com.broll.poklmon.main.PoklmonGameMain;
import com.broll.poklmon.main.StartInformation;
import com.broll.poklmon.player.Player;

public class GameDebugger {

	public static File debugPath;

	public static void setDebugPath(File debugPath) {
		GameDebugger.debugPath = debugPath;
	}

	public static void debugGame(StartInformation info) {

		if (debugPath == null) {
			PoklmonGameMain game = new PoklmonGameMain();
			Player.SAVING_ALLOWED = false;
			DataLoader.SKIP_SOUNDS = true;
			game.startGame(info);
			// restore
			PoklEditorMain.cleanupDebug();
		} else {
			System.out.println("Debug in Path: " + debugPath.getAbsolutePath());

			try {
				String start = StartInformation.parseToConsole(info);
				System.out.println("Debug-Command: " + start);
				String libPath="-Djava.library.path="+debugPath.getAbsolutePath()+"/natives";
				Process ps = Runtime.getRuntime().exec(
						new String[] { "java",libPath,"-jar", debugPath.getAbsolutePath() + "/Poklmon.jar", start });
				ps.waitFor();
				try {
					String line;
					BufferedReader error = new BufferedReader(new InputStreamReader(ps.getErrorStream()));
					while ((line = error.readLine()) != null) {
						System.err.println("=DEBUG-GAME-ERROR=>" + line);
					}
				} catch (final IOException e) {
				}
			} catch (IOException e) {

				e.printStackTrace();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
	}

}
