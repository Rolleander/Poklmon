package com.broll.poklmon.game.scene.script.commands;

import com.broll.pokllib.main.PoklLib;
import com.broll.pokllib.map.MapFile;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.script.CommandControl;

public class InitCommands extends CommandControl {

	private boolean destroyObject = false;
	private String remoteInit = null;

	public InitCommands(GameManager game) {
		super(game);
	}

	public void remoteInit(int mapid, int oid) {
		try {
			MapFile map = PoklLib.data().readMap(mapid);
			String initScript = map.getObjects().get(oid).getAttributes();
			remoteInit = initScript;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Disable speed mode for the map
	public void disableSpeedMode(){
		game.getPlayer().getOverworld().activateWalkMode();
		game.getMap().disableSpeedMode();
	}
	

	public void destroy() {
		destroyObject = true;
	}

	public int getMapId() {
		return game.getMap().getMapId();
	}

	public void destroyIfKnown() {
		if (game.getPlayer().getVariableControl().isKnownobject(game.getMap().getMapId(), object.getObjectId())) {
			destroyObject = true;
		}
	}

	public void resetKnown() {
		game.getPlayer().getVariableControl().setObjectToKnown(game.getMap().getMapId(), object.getObjectId(), false);
	}
	

	public void setKnown() {
		game.getPlayer().getVariableControl().setObjectToKnown(game.getMap().getMapId(), object.getObjectId(), true);
	}

	public boolean isObjectKnown(int mapNr, int oid) {
		return game.getPlayer().getVariableControl().isKnownobject(mapNr, oid);
	}

	public boolean isKnown() {
		return game.getPlayer().getVariableControl().isKnownobject(game.getMap().getMapId(), object.getObjectId());
	}

	public void teleportObject(int x, int y) {
		object.teleport(x, y);
	}

	public void turnObject(ObjectDirection dir) {
		object.setDirection(dir);
	}

	public void setObjectCharacterSprite(String sprite) {
		object.setGraphic(game.getData().getGraphics().getCharImage(sprite));
	}

	public void setObjectTilesetSprite(int tileX, int tileY) {
		Image image = game.getData().getGraphics().getTileSet().getSprite(tileX, tileY);
		object.setFixGraphic(image);
	}

	public void setFixCharGraphic(int x, int y) {
		object.setFixedChar(x, y);
	}

	public boolean isDestroyObject() {
		return destroyObject;
	}
	
	public String getRemoteInitScript() {
		return remoteInit;
	}

}
