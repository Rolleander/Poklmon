package com.broll.pokleditor.resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class SoundLoader {

	
	private static String soundPath;
	
	public static void initPath(File file)
	{
		soundPath=file.getPath()+"/sounds/";
	}
	
	public static String getBattleSoundsPath(){
		
		return soundPath+"/battle";
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
	
	public static ArrayList<String> listBattleSounds()
	{
		File file=new File(getBattleSoundsPath());
		ArrayList<String> names=new ArrayList<String>();
		
		for(File f:file.listFiles())
		{
			names.add(f.getName());
		}	
		names.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int c1=Integer.parseInt(o1.split("_")[0]);
				int c2=Integer.parseInt(o2.split("_")[0]);			
				return Integer.compare(c1, c2);
			}
		});
		return names;
	}
}
