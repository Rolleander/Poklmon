package com.broll.poklmon.model;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.map.MapDisplay;

public class CharacterGraphic {

	private SpriteSheet graphic;
	private Image fixedSprite;
	private int currentFrameX, currentFrameY;
	private float animationSpeed = 0.35f;
	private float animationWait;
	private boolean fix = false;

	public CharacterGraphic() {
		currentFrameX = 0;
		currentFrameY = 0;
	}

	public void setFixed(int x, int y) {
		fix = true;
		currentFrameX = x;
		currentFrameY = y;
	}

	public void setSpriteSheet(SpriteSheet sprite) {
		fix = false;
		this.graphic = sprite;
		this.fixedSprite = null;
	}

	public void setFixSprite(Image image) {
		fix = true;
		this.fixedSprite = image;
	}

	public void setAnimationSpeed(int animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	public void setMovementAnimationStanding(ObjectDirection direction) {
		if (!fix) {
			currentFrameY = getViewY(direction);
			currentFrameX = 0;
		}
	}

	public void updateMovementAnimation(ObjectDirection direction, float delta) {
		if (!fix) {
			currentFrameY = getViewY(direction);
			animationWait += delta;
			if (animationWait >= animationSpeed) {
				// update x frame
				currentFrameX = (currentFrameX + 1) % 4;
				animationWait = 0;
			}
		}
	}

	public void viewDirection(ObjectDirection direction) {
		if (!fix) {
			// stand image
			// currentFrameX = 0;
			currentFrameY = getViewY(direction);
			animationWait = animationSpeed;
		}
	}

	private int getViewY(ObjectDirection direction) {
		switch (direction) {
		case DOWN:
			return 0;
		case UP:
			return 1;
		case LEFT:
			return 2;
		case RIGHT:
			return 3;
		}
		return 0;
	}

	public void render(Graphics g, float x, float y) {
		if (fixedSprite != null) {
			fixedSprite.draw(x - MapDisplay.TILE_SIZE / 2,  (y - MapDisplay.TILE_SIZE / 2), 2);
		} else if (graphic != null) {
			Image image = graphic.getSprite(currentFrameX, currentFrameY);
			image.draw(x - MapDisplay.TILE_SIZE, (float)(y - MapDisplay.TILE_SIZE * 1.5), 2);
		}

	}

}
