package com.broll.poklmon.data.player;

import com.broll.poklmon.data.DataException;
import com.broll.poklmon.data.DataLoader;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.resource.ResourceUtils;

public class PlayerGraphics {

	private SpriteSheet throwAnimation;
	private SpriteSheet mapChar;
	private Image photo;

	public PlayerGraphics(int nr) throws DataException {
		throwAnimation = new SpriteSheet(DataLoader.loadImage(ResourceUtils.DATA_PATH
				+ "resource/graphics/player/playerThrow" + nr + ".png"), 80, 80);
		mapChar = new SpriteSheet(DataLoader.loadImage(ResourceUtils.DATA_PATH + "resource/graphics/player/playerChar"
				+ nr + ".png"), 32, 32);
	}

	public SpriteSheet getThrowAnimation() {
		return throwAnimation;
	}

	public SpriteSheet getMapChar() {
		return mapChar;
	}

	public Image getPhoto() {
		return photo;
	}
}
