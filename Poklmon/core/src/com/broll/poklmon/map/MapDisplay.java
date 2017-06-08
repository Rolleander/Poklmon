package com.broll.poklmon.map;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;

public class MapDisplay {
	public final static int TILE_SIZE = 32;
	private CurrentMap map;
	private SpriteSheet tileSet;
	
	public MapDisplay(DataContainer data) {
		tileSet = data.getGraphics().getTileSet();
	}

	public void setMap(CurrentMap map) {
		this.map = map;
	}

	public void renderMap(Graphics g, float x, float y) {
		for (int i = 0; i < map.getWidth(); i++) {
			for (int h = 0; h < map.getHeight(); h++) {
				float tx = x + i * TILE_SIZE;
				float ty = y + h * TILE_SIZE;
				MapTile tile = map.getTile(i, h);
				int tileID = tile.getTileID() - 1;
				if (tileID >= 0) {
					Image image = getTile(tileID);
					image.draw(tx, ty, TILE_SIZE, TILE_SIZE);
				}
			}
		}
	
	}

	private Image getTile(int tiled) {
		int x = tiled % 10;
		int y = tiled / 10;
		return tileSet.getSprite(x, y);
	}

}
