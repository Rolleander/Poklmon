package com.broll.poklmon.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.broll.pokllib.main.PoklLib;
import com.broll.pokllib.map.MapData;
import com.broll.pokllib.map.MapFile;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.map.areas.MapAreaController;
import com.broll.poklmon.model.CharacterWorldState;

public class MapContainer {
	private CurrentMap map;
	private MapDisplay mapDisplay;
	private Viewport viewport;
	private int mapId;
	private GameManager game;
	private MapAreaController mapAreaController;
	private List<MapAnimation> mapAnimations = new ArrayList<MapAnimation>();
	private List<MapAnimation> overlayAnimations = new ArrayList<MapAnimation>();
	private boolean speedModeDisabled = false;
	private MapTilePassableCheck tilePassableCheck;

	public MapContainer(GameManager game) {
		this.game = game;
		viewport = game.getViewport();
		mapDisplay = new MapDisplay(game.getData());
		mapAreaController = new MapAreaController(game);
	}

	public void disableSpeedMode() {
		speedModeDisabled = true;
	}

	public boolean isSpeedModeDisabled() {
		return speedModeDisabled;
	}

	public void triggerAreaEffect(int id, int x, int y) {
		mapAreaController.areaTrigger(id, x, y);
	}

	public MapFile openMap(int mapID, int mapX, int mapY) {
		speedModeDisabled = false;// clear speed mode disabled
		try {
			mapAnimations.clear();
			// load map
			MapFile mapData = PoklLib.data().readMap(mapID);
			map = new CurrentMap(new MapData(mapData));
			mapDisplay.setMap(map);
			game.getPlayer().getOverworld().teleport(mapX, mapY);
			viewport.initMapSize(map.getWidth(), map.getHeight());
			viewport.centerViewport(mapX * MapDisplay.TILE_SIZE, mapY * MapDisplay.TILE_SIZE);
			mapAreaController.setAreas(map.getAreas());
			tilePassableCheck = new MapTilePassableCheck(map);
			this.mapId = mapID;
			return mapData;
		} catch (Exception e) {
			e.printStackTrace();			
			return null;
		}
	}

	public boolean noScriptRunning() {
		return !game.getSceneProcessManager().isScriptRunning();
	}

	public int getMapId() {
		return mapId;
	}

	public void blockTile(int x, int y, boolean block) {
		if (map != null) {
			MapTile tile = map.getTile(x, y);
			if (tile != null) {
				tile.setBlocked(block);
			}
		}
	}

	public void ledgeTile(int x, int y, ObjectDirection ledgeDir) {
		if (map != null) {
			MapTile tile = map.getTile(x, y);
			if (tile != null) {
				tile.setLedge(ledgeDir);
			}
		}
	}
	
	public boolean isInsideBounds(int x, int y) {
		return map.getTile(x, y)!=null;
	}

	public boolean isPassable(int x, int y, ObjectDirection moveDir, CharacterWorldState worldState) {
		return tilePassableCheck.isPassable(x, y, moveDir, worldState);
	}

	public boolean isLedge(int x, int y, ObjectDirection moveDir) {
		return tilePassableCheck.isLedge(x, y, moveDir);
	}

	public void render(Graphics g) {
		float x = viewport.getScreenX();
		float y = viewport.getScreenY();
		mapDisplay.renderMap(g, x, y);
		for(MapAnimation animation: mapAnimations){
			animation.draw(x, y);
		}
	}

	public void renderOverlay(Graphics g) {
		float x = viewport.getScreenX();
		float y = viewport.getScreenY();
		for(MapAnimation animation: overlayAnimations){
			animation.draw(x, y);
		}
	}

	public void showAnimation(MapAnimation animation) {
		animation.start();
		mapAnimations.add(animation);
	}

	public void showOverlayAnimation(MapAnimation animation) {
		animation.start();
		overlayAnimations.add(animation);
	}

	public void update(float delta) {
		Iterator<MapAnimation> iterator = mapAnimations.iterator();
		while (iterator.hasNext()) {
			MapAnimation anim = iterator.next();
			anim.update(delta);
			if (anim.isStopped()) {
				iterator.remove();
			}
		}
		iterator = overlayAnimations.iterator();
		while (iterator.hasNext()) {
			MapAnimation anim = iterator.next();
			anim.update(delta);
			if (anim.isStopped()) {
				iterator.remove();
			}
		}
	}

	public CurrentMap getMap() {
		return map;
	}

	public MapAreaController getMapAreaController() {
		return mapAreaController;
	}



}
