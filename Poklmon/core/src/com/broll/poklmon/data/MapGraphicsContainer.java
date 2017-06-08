package com.broll.poklmon.data;

import com.broll.poklmon.data.basics.SpriteSheet;

public class MapGraphicsContainer extends ResourceContainer {

	private final static String mapGraphicPath = "map/";
	private SpriteSheet grass;
	private SpriteSheet action;

	public MapGraphicsContainer() {
		setPath(mapGraphicPath);
	}

	public void load() throws DataException {

		grass = loadSprites("grass.png",16,16);
		action = loadSprites("action.png",16,16);

	}
	

	public SpriteSheet getAction() {
		return action;
	}

	public SpriteSheet getGrass() {
		return grass;
	}

}
