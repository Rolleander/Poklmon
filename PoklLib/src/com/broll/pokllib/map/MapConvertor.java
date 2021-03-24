package com.broll.pokllib.map;

public class MapConvertor {

	private int[][] areas;
	private int[][][] tiles;
	public final static String tileSeperator = ";";
	public final static String infoSeperator = ",";
	public final static String layerSeperator = "_";

	public MapConvertor() {

	}

	public void readMapTiles(MapFile map) {
		int w = map.getWidth();
		int h = map.getHeight();
		areas = new int[w][h];
		tiles = new int[w][h][MapData.LAYERS];
		String data = map.getMapTiles();
		String[] tils = data.split(tileSeperator);
		int x = 0;
		int y = 0;
		for (int i = 0; i < tils.length; i++) {
			String inf = tils[i];
			if (inf.contains(infoSeperator)) {
				// with area
				String[] parts = inf.split(infoSeperator);
				readTile(tiles,x,y, parts[0]);
				areas[x][y] = Integer.parseInt(parts[1]);
			} else {
				// no area
				areas[x][y] = 0;
				readTile(tiles,x,y, inf);
			}
			x++;
			if (x >= w) {
				x = 0;
				y++;
			}
		}
	}
	
	private void readTile(int[][][] tiles,int x, int y, String inf) {
		if (inf.contains(layerSeperator)) {
			String[] parts = inf.split(layerSeperator);
			for(int i=0; i<parts.length; i++) {
				tiles[x][y][i]=Integer.parseInt(parts[i]);
			}
		}
		else {
			tiles[x][y][0]= Integer.parseInt(inf);
		}
	}

	public String writeMapTiles(MapData data) {

		StringBuilder builder = new StringBuilder();

		for (int y = 0; y < data.getHeight(); y++) {
			for (int x = 0; x < data.getWidth(); x++) {
				int area = data.getAreas()[x][y];
				String tile="";
				for(int i=0; i<MapData.LAYERS; i++) {
					int t=data.getTiles()[x][y][i];
					tile+=t;
					if(i<MapData.LAYERS-1) {
						tile+=layerSeperator;
					}
				}
				if (area > 0) {
					builder.append(tile + infoSeperator + area + tileSeperator);
				} else {
					builder.append(tile + tileSeperator);
				}
			}
		}
		return builder.toString();
	}

	public int[][] getAreas() {
		return areas;
	}

	public int[][][] getTiles() {
		return tiles;
	}
}
