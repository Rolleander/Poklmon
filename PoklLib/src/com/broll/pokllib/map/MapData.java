package com.broll.pokllib.map;

public class MapData {

	private int width;
	private int height;
	private int[][] areas;
	private int[][][] tiles;
	private MapConvertor convertor = new MapConvertor();
	private MapFile file;
	public final static int LAYERS=4;

	public MapData(MapFile file) {
		this.file=file;
		this.width = file.getWidth();
		this.height = file.getHeight();
		convertor.readMapTiles(file);
		areas = convertor.getAreas();
		tiles = convertor.getTiles();
	}

	public MapData(int width, int height) {
		this.width = width;
		this.height = height;
		areas = new int[width][height];
		tiles = new int[width][height][LAYERS];

	}

	public String getTileData() {
		return convertor.writeMapTiles(this);
	}

	public int[][][] getTiles() {
		return tiles;
	}

	public int[][] getAreas() {
		return areas;
	}

	public int getHeight() {
		return height;
	}



	public int getWidth() {
		return width;
	}

	public void setAreas(int[][] areas) {
		this.areas = areas;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}


	public void setTiles(int[][][] tiles) {
		this.tiles = tiles;
	}

	public MapFile getFile() {
		return file;
	}
	
	public void setFile(MapFile file) {
		this.file = file;
	}
}
