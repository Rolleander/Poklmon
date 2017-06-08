package com.broll.poklmon.data.basics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.broll.poklmon.data.DataException;

public class Image {

	private Graphics g;
	private Texture texture;
	private Sprite sprite;

	public Image(Graphics g, Texture texture) throws DataException {
		if(texture==null){
			throw new DataException("Texture is null!");
		}
		this.texture = texture;
		this.g = g;
		this.sprite = new Sprite(texture);
		sprite.setFlip(false, true);
	}
	
	public Image(Texture texture,Graphics g, Sprite sprite) {
		this.sprite = sprite;
		this.texture = texture;
		this.g = g;
	}

	public Image(Texture texture,Graphics g, TextureRegion textureRegion) {
		this.g = g;
		this.texture = texture;
		this.sprite = new Sprite(textureRegion);
		setCenterOfRotation();
		sprite.setFlip(false, true);
	}


	public void rotate(float i) {
		sprite.rotate(i);
	}

	public void setRotation(float angle2) {
		sprite.setRotation(angle2);
	}

	public void draw() {
		draw(0, 0);
	}

	public void draw(float x, float y) {
		sprite.setPosition(x, y);
		sprite.setScale(1);
		sprite.draw(g.sb);
	}

	public void draw(float x, float y, float scale) {
		draw(x, y, scale * getWidth(), scale * getHeight());
	}

	public void draw(float x, float y, Color c) {
		draw(x, y);
	}

	public void draw(float x, float y, float w, float h) {
		sprite.setScale(w/getWidth(),h/getHeight());
		sprite.setPosition(x, y);
		sprite.draw(g.sb);
	}

	public void draw(float x, float y, float scale, Color filter) {
		draw(x, y, scale);
	}

	public void drawCentered(float x, float y) {
		draw(x - getWidth() / 2, y - getHeight() / 2);
	}

	public void drawCentered(float x, float y, float w, float h) {
		sprite.setScale(w/getWidth(),h/getHeight());
		drawCentered(x,y);
	}

	public Image getScaledCopy(float w, float h) {
		Sprite s = new Sprite(sprite);
		s.setSize(w, h);
		s.setOrigin(0, 0);
		return new Image(texture,g, s);
	}
	

	public Image getSubImage(int x, int y, int w, int h) {
		if(texture==null){
			System.err.println("TEXTURE NULL!");
		}
		Sprite s = new Sprite(texture, x, y, w, h);
		s.setRegion(x, y, w, h);
		s.setOrigin(0, 0);
		s.setFlip(false, true);
		return new Image(texture,g, s);
	}

	public Image getFlippedCopy(boolean horizontal, boolean vertical) {
		Sprite s = new Sprite(sprite);
		boolean flipx=false;
		if(horizontal){
			flipx=!s.isFlipX();
		}
		s.setFlip(flipx, !vertical);
		s.setOrigin(0, 0);
		return new Image(texture,g, s);
	}

	public Image getScaledCopy(float scale) {
		return getScaledCopy(scale * getWidth(), scale * getHeight());
	}

	public float getWidth() {
		return sprite.getWidth();
	}

	public float getHeight() {
		return sprite.getHeight();
	}

	public void setCenterOfRotation(){
		setCenterOfRotation(getWidth() / 2, getHeight() / 2);
	}
	
	public void setCenterOfRotation(float x, float y) {
		sprite.setOrigin(x, y);
	}

	public float getRotation() {
		return sprite.getRotation();
	}

	public Texture getTexture() {
		return texture;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public Graphics getGraphics() {
		return g;
	}


}
