package com.broll.poklmon.save.manage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.broll.poklmon.player.Player;
import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;
import com.broll.poklmon.save.GameData;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SaveFileManager {

	private static String saveFolder = "save/";
	private final static String metaFile = "savefolder";
	private static SaveFolderInfo saveFolderInfo;
	private static SaveFileInfo loadedGameFile;

	public static void readSaves() {
		//check if save folder exists
		File file=new File(saveFolder);
		if(!file.exists()){
			file.mkdir(); //create save folder
		}
		file = new File(saveFolder + metaFile + ".dat");	
		if (file.exists()) {
			saveFolderInfo = (SaveFolderInfo) loadFile(metaFile, SaveFolderInfo.class);
		} else {
			saveFolderInfo = new SaveFolderInfo();
		}
	}

	public static boolean hasGameFile() {
		return saveFolderInfo.getInfos().size() > 0;
	}

	public static List<SaveFileInfo> getSaveFiles() {
		return saveFolderInfo.getInfos();
	}

	public static SaveFileInfo createNewSaveFile(GameData data) {
		SaveFileInfo info = new SaveFileInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		info.setFileName("save-" + sdf.format(new Date()));
		info.setCreated(CaughtPoklmonMeasurement.getCaughtDayInfo());
		loadedGameFile = info;
		return info;
	}

	public static GameData loadGame(SaveFileInfo info) {
		loadedGameFile = info;
		return (GameData) loadFile(info.getFileName(), GameData.class);
	}

	public static void saveGame(GameData data) {
		if (Player.SAVING_ALLOWED) {
			loadedGameFile.setPlayerName(data.getPlayerData().getName());
			loadedGameFile.setPlayTime(data.getVariables().getPlayTime());
			if (!saveFolderInfo.getInfos().contains(loadedGameFile)) {
				saveFolderInfo.getInfos().add(loadedGameFile);
			}
			saveFile(metaFile, saveFolderInfo);
			saveFile(loadedGameFile.getFileName(), data);
		}
	}

	private static void saveFile(String file, Object object) {
		Kryo kryo = new Kryo();
		Output output = null;
		try {
			output = new Output(new FileOutputStream(saveFolder + file + ".dat"));
			kryo.writeObject(output, object);
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} finally {
			if (output != null)
				output.close();
		}
	}

	private static Object loadFile(String file, Class clazz) {
		Kryo kryo = new Kryo();
		Input input = null;
		Object data = null;
		try {
			input = new Input(new FileInputStream(saveFolder + file + ".dat"));
			data = kryo.readObject(input, clazz);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (input != null)
				input.close();
		}
		if (data == null) {
			System.err.println("Could not load: "+file);
		}
		return data;
	}

}
