package com.broll.pokleditor.map.tileset;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.broll.pokleditor.map.MapTileEditor;

public class TileSetPanel extends JPanel {

	private int width, height;
	public static MouseSelection selection;
	
	public TileSetPanel() {
		width = 10;
		height = MapTileEditor.tiles.length / 10 ;
		setPreferredSize(new Dimension(width * MapTileEditor.TILE_SIZE, height * MapTileEditor.TILE_SIZE));
		selection=new MouseSelection(this);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				int id = y * width + x;
				
				if(id<MapTileEditor.tiles.length)
				{
				int xp=x*MapTileEditor.TILE_SIZE;
				int yp=y*MapTileEditor.TILE_SIZE;
				
				g.drawImage(MapTileEditor.tiles[id],xp,yp,MapTileEditor.TILE_SIZE,MapTileEditor.TILE_SIZE,null);
				g.setColor(new Color(200,200,200));
				g.drawRect(xp,yp,MapTileEditor.TILE_SIZE-1,MapTileEditor.TILE_SIZE-1);
				}
			}
		}
		g.setColor(new Color(0,0,255,100));
		int x=selection.getTilex()*MapTileEditor.TILE_SIZE;
		int y=selection.getTiley()*MapTileEditor.TILE_SIZE;
		int w=selection.getWidth()*MapTileEditor.TILE_SIZE;
		int h=selection.getHeight()*MapTileEditor.TILE_SIZE;
		g.fillRect(x,y,w,h);
	}

}
