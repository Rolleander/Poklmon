package com.broll.poklmon.player;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.data.player.PlayerGraphics;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.map.MapDisplay;
import com.broll.poklmon.map.Viewport;
import com.broll.poklmon.model.OverworldCharacter;

public class OverworldPlayer extends OverworldCharacter {

	private GameManager game;
	private final static float WALKING_SPEED = 0.05f;
	private final static float RUNNING_SPEED = 0.1f;
	private boolean running = false;
	private PlayerGraphics playerGraphics;

	public OverworldPlayer(GameManager game) {
		super(game.getData(), game.getMap());
		this.game = game;
	}

	public void initGraphic(PlayerGraphics playerGraphics) {
		this.playerGraphics=playerGraphics;
		activateWalkMode();
	}

	@Override
	public void teleport(float x, float y) {
		super.teleport(x, y);
		updateViewport();
	}

	public void activateSpeedMode() {		
		setMovementSpeed(RUNNING_SPEED);
		setGraphic(playerGraphics.getBicycleChar());
		running = true;
	}

	public void activateWalkMode() {
		setMovementSpeed(WALKING_SPEED);
		setGraphic(playerGraphics.getMapChar());
		running = false;
	}

	public boolean isRunning() {
		return running;
	}

	@Override
	public void update(float delta) {

		super.update(delta);
		// update controls
		if (isMovementAllowed()) {
			if (GUIUpdate.isMoveDownPressed()) {
				this.move(ObjectDirection.DOWN);
			} else if (GUIUpdate.isMoveUpPressed()) {
				this.move(ObjectDirection.UP);
			} else if (GUIUpdate.isMoveLeftPressed()) {
				this.move(ObjectDirection.LEFT);
			} else if (GUIUpdate.isMoveRightPressed()) {
				this.move(ObjectDirection.RIGHT);
			}
		}
		updateViewport();
	}

	public void updateViewport() {

		float x = xpos * MapDisplay.TILE_SIZE;
		float y = ypos * MapDisplay.TILE_SIZE;
		game.getViewport().centerViewport(x, y);
	}

	@Override
	public void render(Graphics g, Viewport viewport) {

		super.render(g, viewport);
		// g.setColor(Color.red);
		// g.drawString("x: " + xpos, 10, 10);
		// g.drawString("y: " + ypos, 10, 25);

	}

}
