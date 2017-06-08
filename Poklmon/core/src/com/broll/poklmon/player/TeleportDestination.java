package com.broll.poklmon.player;

import com.broll.pokllib.object.ObjectDirection;

public class TeleportDestination {

	private int map,x,y;
	private ObjectDirection dir;
	private boolean doStep;
	
	public TeleportDestination(int map, int x, int y) {
		this.map=map;
		this.x=x;
		this.y=y;
	}
	
	public TeleportDestination(int map, int x, int y,ObjectDirection dir) {
		this.map=map;
		this.x=x;
		this.y=y;
		this.dir=dir;
	}
	
	public void setDoStep(boolean doStep) {
		this.doStep = doStep;
	}
		
	public boolean isDoStep() {
		return doStep;
	}
	
	public ObjectDirection getDir() {
		return dir;
	}
	
	public int getMap() {
		return map;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	
}
