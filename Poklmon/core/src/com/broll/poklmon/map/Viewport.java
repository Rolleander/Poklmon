package com.broll.poklmon.map;

public class Viewport {

	public static int WIDTH = 800;
	public static int HEIGHT = 600;

	private float x, y;
	private int mapWidth, mapHeight;

	public Viewport() {

	}

	public void initMapSize(int w, int h) {
		this.mapWidth = w;
		this.mapHeight = h;
	}

	public void centerViewport(float xp, float yp) {
		this.x = xp;
		this.y = yp;
		// check for map bounds
		int mw = mapWidth * MapDisplay.TILE_SIZE - WIDTH / 2 - MapDisplay.TILE_SIZE / 2;
		int mh = mapHeight * MapDisplay.TILE_SIZE - HEIGHT / 2 - MapDisplay.TILE_SIZE / 2;

		if (x < WIDTH / 2 - MapDisplay.TILE_SIZE / 2) {
			this.x = WIDTH / 2 - MapDisplay.TILE_SIZE / 2;
		} else if (x > mw) {
			this.x = mw;
		}

		if (y < HEIGHT / 2 - MapDisplay.TILE_SIZE / 2) {
			this.y = HEIGHT / 2 - MapDisplay.TILE_SIZE / 2;
		} else if (y > mh) {
			this.y = mh;
		}

	}

	public float getScreenX(float mapX) {
		float rx = mapX * MapDisplay.TILE_SIZE;
		return rx - x + WIDTH / 2;
	}

	public float getScreenY(float mapY) {
		float ry = mapY * MapDisplay.TILE_SIZE;
		return ry - y + HEIGHT / 2;
	}

	public float getScreenX() {
		return WIDTH / 2 - MapDisplay.TILE_SIZE / 2 - x;
	}

	public float getScreenY() {
		return HEIGHT / 2 - MapDisplay.TILE_SIZE / 2 - y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
