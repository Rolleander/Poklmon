package com.broll.pokleditor.debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.broll.poklmon.main.StartInformation;

public class GameDebugger {

	public static File debugPath;

	public static void setDebugPath(File debugPath) {
		GameDebugger.debugPath = debugPath;
	}

	public static void debugGame(StartInformation info) {

			System.out.println("Debug in Path: " + debugPath.getAbsolutePath());
			String start = StartInformation.parseToConsole(info);
			System.out.println("Debug-Command: " + start);
			
			try {
				String[] cmds=getGdxCommand(info, start);
				Process p = new ProcessBuilder().inheritIO().command(cmds).start();
				p.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}

	}
	
	private static String[] getGdxCommand(StartInformation info, String startCommands){
		String path=debugPath.getAbsolutePath()+"\\";
		String cp="\""+path+"PoklGdxLauncher.jar;"+path+"PoklGdxCore.jar;"+path+"libs/*\"";			
		String mainclass="com.broll.poklmon.desktop.DesktopLauncher";
		return new String[] { "java", "-cp",cp,  mainclass, startCommands };
	}
	
	private static String[] getSlickCommand(StartInformation info, String startCommands){
		String path=debugPath.getAbsolutePath()+"\\";
		String cp="\""+path+"Poklmon.jar;"+path+"libs/*\"";			
		String libPath = "-Djava.library.path=" + debugPath.getAbsolutePath() + "/natives";
		String mainclass="com.broll.poklmon.main.PoklmonGameMain";
		return new String[] { "java", "-cp",cp, libPath, mainclass, startCommands };
	}

	/*
	 * Runtime rt = Runtime.getRuntime(); try { Process proc =
	 * rt.exec(cmds); StreamGobbler errorGobbler = new
	 * StreamGobbler(proc.getErrorStream(), "ERROR"); StreamGobbler
	 * outputGobbler = new StreamGobbler(proc.getInputStream(),
	 * "OUTPUT");
	 * 
	 * errorGobbler.start(); outputGobbler.start();
	 * 
	 * proc.waitFor(); } catch (IOException e) { e.printStackTrace(); }
	 * catch (InterruptedException e) {
	 * 
	 * e.printStackTrace(); }
	 */
	private static class StreamGobbler extends Thread {
		InputStream is;
		String type;

		StreamGobbler(InputStream is, String type) {
			this.is = is;
			this.type = type;
		}

		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null)
					System.out.println(type + ">" + line);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

}
