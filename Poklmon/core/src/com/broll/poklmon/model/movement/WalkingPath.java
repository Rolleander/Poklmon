package com.broll.poklmon.model.movement;

import com.broll.poklmon.model.OverworldCharacter;

import java.util.ArrayList;
import java.util.List;

public class WalkingPath {

	private List<PathAction> actions = new ArrayList<PathAction>();
	private int currentAction;
	private OverworldCharacter character;
	private boolean startNext = false;
	private boolean loop = true;
	private boolean done = false;
	private boolean blocking =true;
	private WalkingPathListener pathListener;

	public WalkingPath() {

	}

	public void init(OverworldCharacter character) {
		this.character = character;
		currentAction = 0;
		startNext = true;
		done = false;
		loop = true;
	}

	public void setPathListener(WalkingPathListener pathListener) {
		this.pathListener = pathListener;
	}
	
	public void setNonBlocking(){
		blocking=false;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public void add(PathAction action) {
		actions.add(action);
	}

	private void iterationDone() {
		if (loop) {
			currentAction = 0;
		} else {
			done = true;
		}
		if (pathListener != null) {
			pathListener.pathWalked();
		}
	}

	public void update(float time) {
		if (!done) {
			if (startNext) {
				// start first
				actions.get(currentAction).start(character,blocking);
				startNext = false;
			}
			PathAction action = actions.get(currentAction);
			if (action.update(time)) {
				currentAction++;
				if (currentAction >= actions.size()) {
					iterationDone();
				}
				startNext = true;
			}
		}
	}

	
}
