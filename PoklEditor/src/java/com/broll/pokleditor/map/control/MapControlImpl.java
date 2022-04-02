package com.broll.pokleditor.map.control;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.broll.pokleditor.debug.GameDebugger;
import com.broll.pokleditor.map.MapPanel;
import com.broll.pokleditor.map.MapTileEditor;
import com.broll.pokleditor.map.dialog.MoveMapDialog;
import com.broll.pokleditor.map.dialog.ResizeMapDialog;
import com.broll.pokleditor.map.history.MapEditControl;
import com.broll.pokleditor.map.objects.ObjectUtil;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.game.StartInformation;
import com.broll.pokllib.map.MapData;
import com.broll.pokllib.object.MapObject;

public class MapControlImpl implements MapControlInterface {
	private MapData map;
	private int selectionx, selectiony;
	private MapObject copyObject;
	private MapTileEditor editor;
	private MapEditControl editControl;

	public MapControlImpl(MapTileEditor editor, MapEditControl editControl) {
		this.editor = editor;
		this.editControl = editControl;
	}

	public void setMap(MapData map) {
		this.map = map;
	}

	public void setSelectedTile(int x, int y) {
		this.selectionx = x;
		this.selectiony = y;
	}

	public int getSelectionx() {
		return selectionx;
	}

	public int getSelectiony() {
		return selectiony;
	}

	@Override
	public void debugFromHere() {
		// save
		EditorWindow.writeDebugData();
		// start game in debug mode
		int mapId = map.getFile().getId();
		int x = selectionx;
		int y = selectiony;

		StartInformation startInformation = new StartInformation();
		startInformation.debugMap(mapId, x, y);
		GameDebugger.debugGame(startInformation);
	}

	@Override
	public void clearTiles() {
		int[][][] tiles = new int[map.getWidth()][map.getHeight()][MapData.LAYERS];
		map.setTiles(tiles);
		editor.repaint();
	}

	@Override
	public void clearAreas() {
		int[][] areas = new int[map.getWidth()][map.getHeight()];
		map.setAreas(areas);
		editor.repaint();
	}

	@Override
	public void deleteAreaScripts() {
		if (map.getFile().getAreaScripts() != null) {
			map.getFile().getAreaScripts().clear();
			MapPanel.updateAreaPanel();
		}
	}

	@Override
	public void addObject(MapObject object) {
		// if object == null > wizard cancelled
		if (object != null) {
			object.setXpos(selectionx);
			object.setYpos(selectiony);
			spawnNewObject(object);
		}
	}

	@Override
	public void deleteObjects() {
		if (map.getFile().getObjects() != null) {
			map.getFile().getObjects().clear();
		}
		editor.repaint();
	}

	@Override
	public void deleteSelectedObject() {
		MapObject object = ObjectUtil.findObjectAt(map.getFile().getObjects(), selectionx, selectiony);
		if (object != null) {
			map.getFile().getObjects().remove(object);
		}
		editor.repaint();
	}

	@Override
	public void editObject() {
		MapObject editObject = ObjectUtil.findObjectAt(map.getFile().getObjects(), selectionx, selectiony);
		if (editObject != null) {
			editor.openEditDialog(editObject);
		}
	}

	private boolean copyCut = false;

	@Override
	public void cutObject() {
		copyCut = true;
		copyObject = ObjectUtil.findObjectAt(map.getFile().getObjects(), selectionx, selectiony);
	}

	@Override
	public void copyObject() {
		copyCut = false;
		copyObject = ObjectUtil.findObjectAt(map.getFile().getObjects(), selectionx, selectiony);
	}

	@Override
	public void pasteObject() {
		if (copyObject != null) {
			if (copyCut) {
				copyCut = false;
				// just update position
				copyObject.setXpos(selectionx);
				copyObject.setYpos(selectiony);
				editor.repaint();
			} else {
				MapObject paste = ObjectUtil.copyObject(copyObject);
				paste.setXpos(selectionx);
				paste.setYpos(selectiony);
				spawnNewObject(paste);
			}

		} else {
			EditorWindow.showErrorMessage("No Object copied!");
		}
	}

	@Override
	public void resizeMapDialog() {
		ResizeMapDialog dialog = new ResizeMapDialog(map.getWidth(), map.getHeight());
		if (acceptDialog(dialog, "Resize Map")) {
			int neww = dialog.getWidthValue();
			int newh = dialog.getHeightValue();
			int oldw = map.getWidth();
			int oldh = map.getHeight();
			if (neww > 0 && newh > 0) {
				int[][][] newtiles = new int[neww][newh][MapData.LAYERS];
				int[][] newareas = new int[neww][newh];
				for (int x = 0; x < neww; x++) {
					for (int y = 0; y < newh; y++) {
						if (x < oldw && y < oldh) {
							newtiles[x][y] = map.getTiles()[x][y];
							newareas[x][y] = map.getAreas()[x][y];
						}
					}
				}
				map.setAreas(newareas);
				map.setTiles(newtiles);
				map.setWidth(neww);
				map.setHeight(newh);
				map.getFile().setWidth(neww);
				map.getFile().setHeight(newh);
			}
		}
		editor.updateSize();

		editor.repaint();
	}

	@Override
	public void moveMapDialog() {
		MoveMapDialog dialog = new MoveMapDialog();
		if (acceptDialog(dialog, "Move Map")) {
			int movex = dialog.getMoveX() * -1;
			int movey = dialog.getMoveY() * -1;
			int w = map.getWidth();
			int h = map.getHeight();
			int[][][] tiles = new int[w][h][MapData.LAYERS];
			int[][] areas = new int[w][h];
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					int xc = x + movex;
					int yc = y + movey;
					if (xc > -1 && yc > -1 && xc < w && yc < h) {
						tiles[x][y] = map.getTiles()[xc][yc];
						areas[x][y] = map.getAreas()[xc][yc];
					}
				}
			}
			map.setTiles(tiles);
			map.setAreas(areas);
		}
		editor.repaint();
	}

	private void spawnNewObject(MapObject object) {
		// set object id
		int spawnID = map.getFile().getObjectSpawnID();
		object.setObjectID(spawnID);
		// inc spawn id
		map.getFile().setObjectSpawnID(spawnID + 1);
		// add object
		map.getFile().getObjects().add(object);
		editor.repaint();
	}

	public static boolean acceptDialog(JPanel content, String title) {
		return JOptionPane.showConfirmDialog(null, content, title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
	}

}
