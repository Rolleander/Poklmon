package com.broll.poklmon.map;

import com.broll.pokllib.map.MapData;
import com.broll.poklmon.map.areas.MapArea;
import com.broll.poklmon.map.areas.MapAreaContainer;

public class CurrentMap {
	private MapTile[][] tiles;
	private int width, height;
	private MapAreaContainer areas;

	public CurrentMap(MapData data) {
		width = data.getWidth();
		height = data.getHeight();
		tiles = new MapTile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = new MapTile(data.getTiles()[x][y], data.getAreas()[x][y]);
			}
		}
		areas = new MapAreaContainer(data.getFile());
	}

	public MapAreaContainer getAreas() {
		return areas;
	}

	public MapTile getTile(int x, int y) {
		if (x > -1 && y > -1 && x < tiles.length && y < tiles[0].length) {
			return tiles[x][y];
		}
		return null;
	}

	public MapArea getArea(int x, int y) {
		MapTile tile = getTile(x, y);
		if (tile != null) {
			int script = tile.getScriptID();
			if (script > 1) {
				return areas.getArea(script - 2);
			}
		}
		return null;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}
