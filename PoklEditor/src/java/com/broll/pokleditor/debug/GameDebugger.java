package com.broll.pokleditor.debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.broll.poklmon.main.StartInformation;

public class GameDebugger {

	public static File debugPath;

	public static void debugGame(StartInformation info) {
			System.out.println("Debug in Path: " + debugPath.getAbsolutePath());
			String start = StartInformation.parseToConsole(info);
			System.out.println("Debug-Command: " + start);
			try {
				String[] cmds=getCommand( start);
				Process p = new ProcessBuilder().inheritIO().command(cmds).start();
				p.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	private static String[] getCommand( String startCommands){
		String path= debugPath.getAbsolutePath();
	//	String cp="\""+path+"PoklGdxLauncher.jar;"+path+"PoklGdxCore.jar;"+path+"libs/*\"";
	//	String mainclass="com.broll.poklmon.desktop.DesktopLauncher";
		return new String[] { "java", "-jar",path, startCommands };
	}

}
