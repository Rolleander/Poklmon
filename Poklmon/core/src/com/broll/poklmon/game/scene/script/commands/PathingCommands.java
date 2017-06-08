package com.broll.poklmon.game.scene.script.commands;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.pokllib.script.syntax.VariableException;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.script.CommandControl;
import com.broll.poklmon.game.scene.script.Invoke;
import com.broll.poklmon.model.movement.TurnAction;
import com.broll.poklmon.model.movement.WaitAction;
import com.broll.poklmon.model.movement.WalkAction;
import com.broll.poklmon.model.movement.WalkingPath;
import com.broll.poklmon.model.movement.WalkingPathListener;

public class PathingCommands extends CommandControl {

	private WalkingPath path;

	public PathingCommands(GameManager game) {
		super(game);
	}

	public void setSimplePath() {
		path = new WalkingPath();
		path.add(new WalkAction(true, 1));
		path.add(new WaitAction(0, 2));
		setPath();
	}

	public void initPath() {
		path = new WalkingPath();
	}

	public void removePath() {
		object.setPath(null);
	}

	public void stopPathObject(int oid) {
		game.getObject(oid).stopWalkingPath();
	}

	public void stopPathPlayer() {
		game.getPlayer().getOverworld().stopWalkingPath();
	}

	public void removePath(int oid) {
		game.getObject(oid).setPath(null);
	}

	public void nonBlocking() {
		path.setNonBlocking();
	}

	public void setPath() {
		object.setPath(path);
		object.reinitPath();
	}

	public void doPathObject(final int oid) {
		invoke(new Invoke() {
			public void invoke() throws VariableException {
				game.getObject(oid).walkPath(path, new WalkingPathListener() {

					@Override
					public void pathWalked() {
						resume();
					}
				});
			}
		});
	}

	public void doPathPlayer() {
		invoke(new Invoke() {
			public void invoke() throws VariableException {
				game.getPlayer().getOverworld().walkPath(path, new WalkingPathListener() {

					@Override
					public void pathWalked() {
						resume();
					}
				});
			}
		});
	}

	public void pause(float seconds) {
		path.add(new WaitAction(seconds));
	}

	public void pause(float min, float max) {
		path.add(new WaitAction(min, max));
	}

	public void stepRandom() {
		path.add(new WalkAction(true, 1));
	}

	public void step(ObjectDirection direction) {
		path.add(new WalkAction(direction, 1));
	}

	public void move(ObjectDirection direction, int count) {
		path.add(new WalkAction(direction, count));
	}

	public void move(int count) {
		path.add(new WalkAction(false, count));
	}

	public void turnTo(ObjectDirection direction) {
		path.add(new TurnAction(direction));
	}

	public void turnTo(ObjectDirection direction, float wait) {
		path.add(new TurnAction(direction, wait));
	}

	public void turnLeft(int count, float wait) {
		path.add(new TurnAction(-count, wait));
	}

	public void turnRight(int count, float wait) {
		path.add(new TurnAction(count, wait));
	}

	public void turnRandom() {
		path.add(new TurnAction());
	}

	public void turnLeft(int count) {
		path.add(new TurnAction(-count));
	}

	public void turnRight(int count) {
		path.add(new TurnAction(count));
	}

	public void turnLeft() {
		turnLeft(1);
	}

	public void turnRight() {
		turnRight(1);
	}
}
