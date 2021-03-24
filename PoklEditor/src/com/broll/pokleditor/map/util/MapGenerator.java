package com.broll.pokleditor.map.util;

import java.util.ArrayList;

import com.broll.pokllib.map.MapData;
import com.broll.pokllib.map.MapFile;
import com.broll.pokllib.object.MapObject;

public class MapGenerator {

	
	public static MapData generateNewMap()
	{
		int width=20;
		int height=20;
		MapData map=new MapData(width, height);
		MapFile file=new MapFile();
		file.setWidth(width);
		file.setHeight(height);
		file.setAreaScripts(new ArrayList<String>());
		file.setMapTiles(map.getTileData());
		file.setName("New Map");
		file.setObjects(new ArrayList<MapObject>());
		map.setFile(file);		
		return map;
	}
	
}
