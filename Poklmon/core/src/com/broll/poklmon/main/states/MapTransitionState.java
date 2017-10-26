package com.broll.poklmon.main.states;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.player.TeleportDestination;

public class MapTransitionState extends GameState {

	public static int STATE_ID = 6;

	private GameManager game;
	private TeleportDestination teleportDestination;
	private boolean gameOver = false;

	public void teleportPlayer(GameManager game, TeleportDestination teleportDestination) {
		this.game = game;
		this.teleportDestination = teleportDestination;
	}

	public void gameOverTransition() {
		gameOver = true;
	}

	@Override
	public void onInit() {
	}

	@Override
	public void onEnter() {
		int currentMap = game.getMap().getMapId();
		int mapId = teleportDestination.getMap();
		int x = teleportDestination.getX();
		int y = teleportDestination.getY();
		ObjectDirection dir = teleportDestination.getDir();
		if (currentMap != mapId || gameOver) {
			// change map, reenter map always on game over
			game.enterMap(mapId, x, y);
		} else {
			// just teleport
			game.getPlayer().getOverworld().teleport(x, y);
		}
		if (dir != null) {
			game.getPlayer().getOverworld().setDirection(dir);
		}
		System.out.println("telport "+teleportDestination.getX()+" "+teleportDestination.getY()+"  map "+teleportDestination.getMap());
		if (teleportDestination.isDoStep()) {
			game.getPlayer().getOverworld().move(dir);
		}
		if (gameOver) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gameOver = false;
		}

		states.transition(MapState.class);
	}

	@Override
	public void onExit() {
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Graphics g) {
	}

}
