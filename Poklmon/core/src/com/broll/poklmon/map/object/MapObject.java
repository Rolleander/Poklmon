package com.broll.poklmon.map.object;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.map.MapContainer;
import com.broll.poklmon.model.OverworldCharacter;
import com.broll.poklmon.model.movement.WalkingPath;

public class MapObject extends OverworldCharacter {

	private String name, triggerScript, initScript;
	private int objectId;
	private boolean touchTrigger = false;
	private boolean isVisible = true;
	private boolean triggerActive = true;
	private int viewTriggerRange = 0;
	private WalkingPath path;
	private boolean pathInit = false;

	public MapObject(DataContainer data,MapContainer map) {
		super(data,map);
	}

	public void setLedge(ObjectDirection ledgeDir) {
		map.ledgeTile((int) xpos, (int) ypos, ledgeDir);
	}

	public void removeLedge(ObjectDirection ledgeDir) {
		setLedge(null);
	}

	public void init(String name, int id, String triggerScript, String initScript) {
		this.name = name;
		this.objectId = id;
		this.triggerScript = triggerScript;
		this.initScript = initScript;
	}

	public void reinitPath() {
		if (pathInit) {
			path.init(this);
		}
	}

	public void doneInit() {
		// init path after init script is run
		if (path != null) {
			path.init(this);
		}
		pathInit = true;
	}

	@Override
	public void update(float delta) {
		if (path != null && isMovementAllowed()) {
			path.update(delta);
		}
		super.update(delta);
	}

	public WalkingPath getPath() {
		return path;
	}

	public void setPath(WalkingPath path) {
		this.path = path;
	}

	public void setViewTriggerRange(int viewTriggerRange) {
		this.viewTriggerRange = viewTriggerRange;
	}

	public int getViewTriggerRange() {
		return viewTriggerRange;
	}

	public boolean isTriggerActive() {
		return triggerActive;
	}

	public void setTriggerActive(boolean triggerActive) {
		this.triggerActive = triggerActive;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTriggerScript(String triggerScript) {
		this.triggerScript = triggerScript;
	}

	public void setInitScript(String initScript) {
		this.initScript = initScript;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public void setTouchTrigger(boolean touchTrigger) {
		this.touchTrigger = touchTrigger;
	}

	public boolean isTouchTrigger() {
		return touchTrigger;
	}

	public String getInitScript() {
		return initScript;
	}

	public String getName() {
		return name;
	}

	public int getObjectId() {
		return objectId;
	}

	public String getTriggerScript() {
		return triggerScript;
	}

}
