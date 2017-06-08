package com.broll.pokllib.animation;

public class AnimationSprite {

	private int x,y;
	private int spriteID;
	private float transparency=1;
	private float size;
	private float angle;
	
	public float getAngle()
    {
        return angle;
    }
	
	public void setAngle(float angle)
    {
        this.angle = angle;
    }
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpriteID() {
		return spriteID;
	}
	public void setSpriteID(int spriteID) {
		this.spriteID = spriteID;
	}
	public float getTransparency() {
		return transparency;
	}
	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	
	
	
	
}
