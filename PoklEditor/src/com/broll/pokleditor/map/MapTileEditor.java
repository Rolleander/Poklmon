package com.broll.pokleditor.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.map.control.MapControlImpl;
import com.broll.pokleditor.map.control.PopupMenu;
import com.broll.pokleditor.map.dialog.ObjectEditDialog;
import com.broll.pokleditor.map.objects.MapObjectGenerator;
import com.broll.pokleditor.map.objects.ObjectType;
import com.broll.pokleditor.map.objects.ObjectUtil;
import com.broll.pokleditor.map.tileset.AreaColors;
import com.broll.pokleditor.map.tools.CurrentTool;
import com.broll.pokleditor.map.tools.MapPaintTools;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.map.MapData;
import com.broll.pokllib.object.MapObject;

public class MapTileEditor extends JPanel {

	private MapData map;
	public final static int TILE_SIZE = 32;
	public static boolean showGrid = false;
	public static boolean showEvents = true;

	public static Image[] tiles;
	private Image collisionTile, eventTile;
	private MousePlacementListener mouse;
	private JPopupMenu menu;
	private MapControlImpl control;
	private ObjectEditDialog editObject = new ObjectEditDialog();

	public MapTileEditor() {
		collisionTile = GraphicLoader.loadImage("collisionTile.png");
		eventTile = GraphicLoader.loadImage("eventTile.png");

		control = new MapControlImpl(this);
		mouse = new MousePlacementListener(this);
		this.setBackground(MapEditorPanel.background);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		menu = PopupMenu.createPopupmenu(control);
	}

	public void setMap(MapData map) {
		this.map = map;
		mouse.setMap(map);
		control.setMap(map);
		updateSize();
	}

	public void openEditDialog(MapObject object) {
		editObject.open(object);
	}

	public void fastEditEventTile(int x, int y) {
		control.setSelectedTile(x, y);
		MapObject select = ObjectUtil.findObjectAt(map.getFile().getObjects(), x, y);
		if (select == null) {
			// new event wizard
			String[] newTypes = new String[ObjectType.values().length];
			int nr = 0;
			for (ObjectType type : ObjectType.values()) {
				newTypes[nr] = type.name();
				nr++;
			}
			int selected = JOptionPane.showOptionDialog(EditorWindow.frame, "Create new Event", "New Event",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, newTypes, 0);
			ObjectType create = ObjectType.values()[selected];
			MapObject newobject = MapObjectGenerator.openWizard(create);
			control.addObject(newobject);
			if (create != ObjectType.TELEPORTER && create != ObjectType.LEDGE && create != ObjectType.REMOTE&&create!=ObjectType.MAPTILE) {
				control.editObject();
			}
		} else {
			// edit event
			control.editObject();
		}
	}

	public void focusTile(int x, int y) {
		control.setSelectedTile(x, y);
		repaint();
	}

	public void openMenu(int x, int y) {
		int xpos = x / TILE_SIZE;
		int ypos = y / TILE_SIZE;
		focusTile(xpos, ypos);
		MapObject select = ObjectUtil.findObjectAt(map.getFile().getObjects(), xpos, ypos);
		PopupMenu.activateObjectOptions(select != null);
		menu.show(this, x, y);
	}

	public void updateSize() {
		int w = map.getWidth() * TILE_SIZE;
		int h = map.getHeight() * TILE_SIZE;
		this.setPreferredSize(new Dimension(w, h));
		revalidate();
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
				int area = map.getAreas()[x][y];
				int xp = x * TILE_SIZE;
				int yp = y * TILE_SIZE;
				if (tile > 0) {
					g.drawImage(tiles[tile - 1], xp, yp, TILE_SIZE, TILE_SIZE, null);
				}
				if (MapPanel.isAreaPanelSelected()) {
					if (area == 1) {
						// collision
						g.drawImage(collisionTile, xp, yp, null);
					} else if (area > 1) {
						// area
						g.setColor(AreaColors.getAreaColor(area - 2));
						g.fillRect(xp, yp, TILE_SIZE, TILE_SIZE);
					}
				}
				if (showGrid) {
					g.setColor(new Color(50, 50, 50));
					g.drawRect(xp, yp, TILE_SIZE - 1, TILE_SIZE - 1);
				}
			}
		}
		FontMetrics fm = g.getFontMetrics();

		if (map.getFile().getObjects() != null) {
			for (MapObject object : map.getFile().getObjects()) {
				int xp = object.getXpos() * TILE_SIZE;
				int yp = object.getYpos() * TILE_SIZE;
				g.drawImage(eventTile, xp, yp, null);
				int id = object.getObjectID();
				String txt = "" + id;

				int tx = xp + TILE_SIZE / 2 - fm.stringWidth(txt) / 2;
				int ty = yp + 20;
				g.setColor(new Color(0, 0, 0));
				int b = 1;
				g.drawString(txt, tx - b, ty - b);
				g.drawString(txt, tx - b, ty);
				g.drawString(txt, tx + b, ty);
				g.drawString(txt, tx, ty - b);
				g.drawString(txt, tx, ty + b);
				g.drawString(txt, tx + b, ty + b);

				g.setColor(new Color(250, 250, 250));
				g.drawString(txt, tx, ty);

			}
		}
		if (MapPaintTools.selectedTool == CurrentTool.SELECTOR) {
			int x = control.getSelectionx() * TILE_SIZE;
			int y = control.getSelectiony() * TILE_SIZE;
			g.setColor(new Color(0, 0, 0));
			g.drawRect(x, y, TILE_SIZE - 1, TILE_SIZE - 1);
			g.setColor(new Color(1f, 1f, 1f));
			g.drawRect(x + 1, y + 1, TILE_SIZE - 3, TILE_SIZE - 3);

		}
	}

	public void save() {
		// write tile string into map file
		String tiles = map.getTileData();
		map.getFile().setMapTiles(tiles);
	}

}
