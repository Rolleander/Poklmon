package com.broll.pokleditor.main;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.debug.GameDebugger;
import com.broll.pokleditor.map.MapTileEditor;
import com.broll.pokleditor.panel.EditorPanel;
import com.broll.pokleditor.resource.ImageLoader;
import com.broll.pokleditor.resource.SoundLoader;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokleditor.window.LoadingWindow;
import com.broll.pokllib.main.KryoDataControl;
import com.broll.pokllib.main.PoklLib;
import com.broll.poklmon.main.BugSplashDialog;
import com.broll.poklmon.main.PoklmonGameMain;

public class PoklEditorMain {

	private static KryoDataControl dataControl;
	public static File POKL_PATH;

	public static void main(String[] args) {

		if (args != null && args.length == 1) {
			try {
				String path = PoklmonGameMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				String decodedPath = URLDecoder.decode(path, "UTF-8");
				POKL_PATH = new File(decodedPath + "/data/");
				GameDebugger.debugPath = new File(decodedPath + "/");
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}

		} else {
			try {
				String path = PoklmonGameMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				String decodedPath = URLDecoder.decode(path, "UTF-8");
				decodedPath.substring(0,decodedPath.length()-"Poklmon/bin".length());
				POKL_PATH = new File(decodedPath + "../data/");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("POKL_PATH: " + POKL_PATH.getAbsolutePath());
		ImageLoader.initPath(POKL_PATH);
		SoundLoader.initPath(POKL_PATH);

		LoadingWindow.open();
		System.out.println("Load Tiles...");
		MapTileEditor.tiles = ImageLoader.loadTileset();
		System.out.println("Load DbControl...");
		dataControl = new KryoDataControl(POKL_PATH.getPath() + "/poklmon.data");
		try {
			System.out.println("Load Data...");
			dataControl.read();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("Init PoklLib..");
		PoklLib.init(dataControl);
		// PoklLib.init(new XmlDataControl());
		System.out.println("Init Window...");
		EditorWindow window = new EditorWindow();
		try {
			System.out.println("Load Indexes...");
			PoklData.loadIndexes();
		} catch (Exception e) {
			EditorWindow.showErrorMessage("Couldnt load Indexes!");
			e.printStackTrace();
			return;
		}
		System.out.println("Show Window...");
		EditorPanel panel = new EditorPanel();
		window.open(panel);
	}

	public static void forceSave() {
		try {
			System.out.println("Saving...");
			// dataControl.saveData("data/poklmon.data");
			dataControl.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BugSplashDialog.showError("Failed to save data: " + e.getMessage());
		}
	}

	public static void cleanupDebug() {
		PoklLib.init(dataControl);
	}

	/*
	 * public static void forceDebugSave() { try {
	 * System.out.println("Saving Test...");
	 * dataControl.saveData("data/poklmon_test.data"); } catch (Exception e) {
	 * e.printStackTrace(); BugSplashDialog.showError("Failed to save data: " +
	 * e.getMessage()); } }
	 */
}
