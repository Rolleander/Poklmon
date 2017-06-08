package com.broll.pokleditor.resource;

import java.io.File;
import java.util.ArrayList;

public class SoundLoader {

	
	private static String soundPath;
	
	public static void initPath(File file)
	{
		soundPath=file.getPath()+"/sounds/";
	}
	
	public static ArrayList<String> listSounds()
	{
		File file=new File(soundPath);
		ArrayList<String> names=new ArrayList<String>();
		
		for(File f:file.listFiles())
		{
			names.add(f.getName());
		}	
		return names;
	}
	
	
}
