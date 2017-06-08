package com.broll.poklmon.resource;

import com.broll.poklmon.data.DataException;
import com.broll.poklmon.data.DataLoader;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;

public class MenuGraphics {

	public static Image background,logo,clouds;
	public static SpriteSheet touchIcons;
	
	public static void loadGraphics() throws DataException
	{
		background = DataLoader.loadImage(ResourceUtils.DATA_PATH +"resource/graphics/menu/intro.jpg");
		clouds = DataLoader.loadImage(ResourceUtils.DATA_PATH +"resource/graphics/menu/looping_cloud.png");	
		logo = DataLoader.loadImage(ResourceUtils.DATA_PATH +"resource/graphics/logo.png");
		touchIcons=DataLoader.loadSprites(ResourceUtils.DATA_PATH +"resource/graphics/touchicons.png",64,64);
	}
	
}
