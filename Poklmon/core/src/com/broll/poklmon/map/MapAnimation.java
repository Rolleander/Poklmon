package com.broll.poklmon.map;

import com.broll.poklmon.data.basics.Animation;
import com.broll.poklmon.data.basics.SpriteSheet;

public class MapAnimation extends Animation {

	private int mapx, mapy;
	private float x,y;
	private boolean absolute=false;
	
	public MapAnimation(SpriteSheet sprite, int x, int y, int duration) {
		super(sprite, duration);
		this.mapx = x;
		this.mapy = y;
	}
	
	public MapAnimation(SpriteSheet sprite, float x, float y, int duration) {
		super(sprite, duration);
		this.x = x;
		this.y = y;
		absolute=true;
	}
	
	@Override
	public void draw(float vx, float vy) {
		float w = getWidth() * 2;
		float h = getHeight() * 2;
		if(absolute)
		{
			super.draw(vx+x-w/2,vy+y-h/2,w,h);
		}
		else
		{
		
		float x = vx+(mapx * MapDisplay.TILE_SIZE + MapDisplay.TILE_SIZE / 2);
		float y = vy+(mapy * MapDisplay.TILE_SIZE + MapDisplay.TILE_SIZE / 2);
		super.draw(x - w / 2, y - h / 2, w, h);
		}
	}

	public void update(float delta) {
		super.update(delta);
	}

}
