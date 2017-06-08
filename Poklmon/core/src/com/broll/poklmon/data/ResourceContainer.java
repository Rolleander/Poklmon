package com.broll.poklmon.data;

import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.resource.ResourceUtils;

public abstract class ResourceContainer {

	private String path="";
	private final static String sourcePath="resource/graphics/";
	
	protected void setPath(String path) {
		this.path = path;
	}

	protected SpriteSheet loadSprites(String file, int w, int h) throws DataException {
		return DataLoader.loadSprites(ResourceUtils.DATA_PATH+sourcePath+path + file, w, h);
	}

	protected Image loadImage(String file) throws DataException {
		return DataLoader.loadImage(ResourceUtils.DATA_PATH+sourcePath+path + file);
	}

}
