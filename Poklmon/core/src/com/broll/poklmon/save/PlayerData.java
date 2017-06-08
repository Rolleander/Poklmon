package com.broll.poklmon.save;

public class PlayerData {

	private int character;
	private String name;
	private int xpos, ypos;
	private int mapNr;
	private int view;
	private int recoverX, recoverY, recoverMap, recoverView;
	private int deaths;

	public int getDeaths() {
		return deaths;
	}

	public void playerDied() {
		deaths++;
	}

	public int getRecoverX() {
		return recoverX;
	}

	public void setRecoverX(int recoverX) {
		this.recoverX = recoverX;
	}

	public int getRecoverY() {
		return recoverY;
	}

	public void setRecoverY(int recoverY) {
		this.recoverY = recoverY;
	}

	public int getRecoverMap() {
		return recoverMap;
	}

	public void setRecoverMap(int recoverMap) {
		this.recoverMap = recoverMap;
	}

	public int getRecoverView() {
		return recoverView;
	}

	public void setRecoverView(int recoverView) {
		this.recoverView = recoverView;
	}

	public int getMapNr() {
		return mapNr;
	}

	public int getXpos() {
		return xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public int getCharacter() {
		return character;
	}

	public String getName() {
		return name;
	}

	public void setCharacter(int character) {
		this.character = character;
	}

	public void setMapNr(int mapNr) {
		this.mapNr = mapNr;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public void setView(int view) {
		this.view = view;
	}

	public int getView() {
		return view;
	}
}
