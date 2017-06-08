package com.broll.pokleditor.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.broll.pokllib.map.MapData;

public class MapPreviewPanel extends JPanel implements MouseListener {

	private MapData map;
	public final static int TILE_SIZE = 16;
	private int selX, selY;

	public MapPreviewPanel() {

		this.addMouseListener(this);

	}

	public void setMap(MapData map) {
		selX = 0;
		selY = 0;
		this.map = map;
		int w = map.getWidth() * TILE_SIZE;
		int h = map.getHeight() * TILE_SIZE;
		this.setPreferredSize(new Dimension(w, h));
		revalidate();
		repaint();
	}

	public int getMapId() {
		return map.getFile().getId();
	}

	public int getSelectionX() {
		return selX;
	}

	public int getSelectionY() {
		return selY;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		int width = map.getWidth();
		int height = map.getHeight();
		g.setColor(new Color(100, 100, 100));
		g.fillRect(0, 0, width * TILE_SIZE, height * TILE_SIZE);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int tile = map.getTiles()[x][y];
				int xp = x * TILE_SIZE;
				int yp = y * TILE_SIZE;
				if (tile > 0) {
					g.drawImage(MapTileEditor.tiles[tile - 1], xp, yp, TILE_SIZE, TILE_SIZE, null);
				}

				// g.setColor(new Color(50, 50, 50));
				// g.drawRect(xp, yp, TILE_SIZE - 1, TILE_SIZE - 1);

			}
		}
		int x = selX * TILE_SIZE;
		int y = selY * TILE_SIZE;
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y, TILE_SIZE - 1, TILE_SIZE - 1);
		g.setColor(new Color(1f, 1f, 1f));
		g.drawRect(x + 1, y + 1, TILE_SIZE - 3, TILE_SIZE - 3);

	}

	private void updateMouse(MouseEvent e) {
		selX = e.getX() / TILE_SIZE;
		selY = e.getY() / TILE_SIZE;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		updateMouse(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
