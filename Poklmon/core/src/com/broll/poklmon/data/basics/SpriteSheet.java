package com.broll.poklmon.data.basics;

public class SpriteSheet {

	private Image[][] sprites;

	public SpriteSheet(Image image, int w, int h) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		int wc = width / w;
		int hc = height / h;
		sprites = new Image[wc][hc];
		for (int x = 0; x < wc; x++) {
			for (int y = 0; y < hc; y++) {
				sprites[x][y] = image.getSubImage(x * w, y * h, w, h);
			}
		}
	}

	public Image getSprite(int x, int y) {
		return sprites[x][y];
	}

	@Override
	public String toString() {
		return sprites.length+"  "+sprites[0].length;
	}
}
