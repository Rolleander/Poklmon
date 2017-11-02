package com.broll.poklmon.save.manage;

import com.broll.poklmon.player.Player;
import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;
import com.broll.poklmon.save.GameData;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SaveFileManager {

	private final static String metaFile = "savefolder";
	private static SaveFolderInfo saveFolderInfo;
	private static SaveFileInfo loadedGameFile;
	private static DeviceSaveOperations saveOperations;

	public static void initSaveInterface(DeviceSaveOperations op){
		SaveFileManager.saveOperations=op;
	}

	public static void readSaves() {
		saveFolderInfo= (SaveFolderInfo)loadFile(metaFile,SaveFolderInfo.class);
		if(saveFolderInfo==null){
			saveFolderInfo=new SaveFolderInfo();
			saveFile(metaFile,saveFolderInfo);
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
			output = new Output(saveOperations.writeFile(file + ".dat"));
			kryo.writeObject(output, object);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static Object loadFile(String file, Class clazz) {
		Kryo kryo = new Kryo();
		Input input = null;
		Object data = null;
		try {
			input = new Input(saveOperations.readFile(file + ".dat"));
			data = kryo.readObject(input, clazz);
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


		if (data == null) {
			System.err.println("Could not load: "+file);
		}
		return data;
	}

}
