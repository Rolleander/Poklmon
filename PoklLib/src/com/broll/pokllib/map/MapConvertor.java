package com.broll.pokllib.map;

public class MapConvertor {

	private int[][] areas;
	private int[][] tiles;
	public final static String tileSeperator = ";";
	public final static String infoSeperator = ",";

	public MapConvertor() {

	}

	public void readMapTiles(MapFile map) {
		int w = map.getWidth();
		int h = map.getHeight();

		areas = new int[w][h];
		tiles = new int[w][h];
		String data = map.getMapTiles();
		String[] tils = data.split(tileSeperator);
		int x = 0;
		int y = 0;
		for (int i = 0; i < tils.length; i++) {
			String inf = tils[i];
			if (inf.contains(infoSeperator)) {
				// with area
				String[] parts = inf.split(infoSeperator);
				tiles[x][y] = Integer.parseInt(parts[0]);
				areas[x][y] = Integer.parseInt(parts[1]);
			} else {
				// no area
				areas[x][y] = 0;
				tiles[x][y] = Integer.parseInt(inf);
			}
			x++;
			if (x >= w) {
				x = 0;
				y++;
			}
		}
	}

	public String writeMapTiles(MapData data) {

		StringBuilder builder = new StringBuilder();

		for (int y = 0; y < data.getHeight(); y++) {
			for (int x = 0; x < data.getWidth(); x++) {
				int tile = data.getTiles()[x][y];
				int area = data.getAreas()[x][y];
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

	public int[][] getTiles() {
		return tiles;
	}
}
