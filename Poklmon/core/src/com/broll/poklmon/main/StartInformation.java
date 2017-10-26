package com.broll.poklmon.main;

import java.io.File;

import com.broll.poklmon.main.states.TitleMenuState;

public class StartInformation {

	private boolean debugGame;
	private boolean debugMap;
	private boolean debugAttack;
	private boolean debugAnimation;
	private boolean touchControling=false;

	private Class debugScene;
	private int mapId, mapX, mapY;
	private int attackId;
	private int animationId;
	private File path;
	
	public StartInformation(File path){
		this.path=path;
		debugGame = false;
		debugMap = false;
	}

	public void setTouchControling(boolean touchControling) {
		this.touchControling = touchControling;
	}

	public static String parseToConsole(StartInformation info) {
		if (info.isDebugAnimation()) {
			return "debug:animation:" + info.getAnimationId();
		} else if (info.isDebugAttack()) {
			return "debug:attack:" + info.getAttackId();
		} else if (info.isDebugMap()) {
			return "debug:map:" + info.getMapId() + "," + info.getMapX() + "," + info.getMapY();
		} else if (info.isDebugGame()) {
			return "debug:game";
		}
		return "game";
	}

	public static StartInformation parseFromConsole(String in) {
		StartInformation info = new StartInformation(null);
		in = in.toLowerCase();
		if (in.equals("debug:game")) {
			info.debugScene(TitleMenuState.class);
		} else if (in.startsWith("debug:map:")) {
			String[] params = in.substring("debug:map:".length()).split(",");
			info.debugMap(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
		} else if (in.startsWith("debug:attack:")) {
			String param = in.substring("debug:attack:".length());
			info.debugAttack(Integer.parseInt(param));
		} else if (in.startsWith("debug:animation:")) {
			String param = in.substring("debug:animation:".length());
			info.debugAnimation(Integer.parseInt(param));
		}
		return info;
	}
	
	public void debugScene(Class scene) {
		this.debugScene = scene;
		debugGame = true;
	}

	public void debugAttack(int attack) {
		debugGame = true;
		debugAttack = true;
		attackId = attack;
	}

	public void debugAnimation(int animation) {
		animationId = animation;
		debugAnimation = true;
		debugGame = true;
	}

	public void debugMap(int map, int x, int y) {
		debugGame = true;
		debugMap = true;
		this.mapId = map;
		this.mapX = x;
		this.mapY = y;
	}

	public Class getDebugScene() {
		return debugScene;
	}

	public int getMapId() {
		return mapId;
	}

	public int getMapX() {
		return mapX;
	}

	public int getMapY() {
		return mapY;
	}

	public int getAttackId() {
		return attackId;
	}

	public int getAnimationId() {
		return animationId;
	}

	public boolean isDebugMap() {
		return debugMap;
	}

	public boolean isDebugGame() {
		return debugGame;
	}

	public boolean isDebugAnimation() {
		return debugAnimation;
	}

	public boolean isDebugAttack() {
		return debugAttack;
	}

	public File getDataPath() {
		return path;
	}

	public boolean isTouchControling() {
		return touchControling;
	}
}
