package com.broll.poklmon.data.basics;

import java.util.List;

public class SpriteSheet {

	private Image[][] sprites;
	private int countX,countY;

	public SpriteSheet(Image image, int w, int h) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		int wc = width / w;
		int hc = height / h;
		countX=wc;
		countY=hc;
		sprites = new Image[wc][hc];
		fill(image,0,0,w,h,wc,hc);
	}

	private void fill(Image image, int deltax,int deltay,int w, int h, int wc, int hc){
		for (int x = 0; x < wc; x++) {
			for (int y = 0; y < hc; y++) {
				sprites[x+deltax][y+deltay] = image.getSubImage(x * w, y * h, w, h);
			}
		}
	}

    public SpriteSheet(List<Image> tilesets, int w, int h) {
		int wc=0;
		int hc=0;
		for(Image image: tilesets){
			int wd= (int) image.getWidth()/w;
			if(wd>wc){
				wc=wd;
			}
			hc+=(int)image.getHeight()/h;
		}
		countX=wc;
		countY=hc;
		sprites = new Image[wc][hc];
		int deltay=0;
		for(Image image: tilesets){
			int wcc=(int)(image.getWidth()/w);
			int hcc= (int) (image.getHeight()/h);
			fill(image,0,deltay,w,h,wcc,hcc);
			deltay+=hcc;
		}
    }

    public int getCountX() {
		return countX;
	}

	public int getCountY() {
		return countY;
	}

	public Image getSprite(int x, int y) {
		return sprites[x][y];
	}

	@Override
	public String toString() {
		return sprites.length+"  "+sprites[0].length;
	}
}
