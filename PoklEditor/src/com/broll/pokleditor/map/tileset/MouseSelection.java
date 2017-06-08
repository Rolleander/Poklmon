package com.broll.pokleditor.map.tileset;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.broll.pokleditor.map.MapTileEditor;

public class MouseSelection implements MouseMotionListener,MouseListener{

	private int tilex,tiley;
	private int width=1,height=1;
	
	private TileSetPanel panel;
	
	private int startx,starty;
	private boolean drag=false;
	
	public  MouseSelection(TileSetPanel panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		this.panel=panel;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(drag==false)
		{
			 startx=e.getX()/MapTileEditor.TILE_SIZE;
			 starty=e.getY()/MapTileEditor.TILE_SIZE;
		}
		drag=true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x=e.getX()/MapTileEditor.TILE_SIZE;
		int y=e.getY()/MapTileEditor.TILE_SIZE;
		set(x, y, 1, 1);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(drag)
		{
			int endx=e.getX()/MapTileEditor.TILE_SIZE;
			int endy=e.getY()/MapTileEditor.TILE_SIZE;
			if(endx>=startx&&endy>=starty)
			{
				int w=endx-startx+1;
				int h=endy-starty+1;
				set(startx, starty, w, h);
			}
			drag=false;
		}
	}
	
	private void set(int x, int y, int w, int h)
	{
		this.tilex=x;
		this.tiley=y;
		this.width=w;
		this.height=h;
		panel.repaint();
	}
	
	public int getHeight() {
		return height;
	}
	public int getTilex() {
		return tilex;
	}
	
	public int getTiley() {
		return tiley;
	}
	public int getWidth() {
		return width;
	}

}
