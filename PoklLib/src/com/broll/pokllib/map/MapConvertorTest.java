package com.broll.pokllib.map;

import static org.junit.Assert.*;

import org.junit.Test;

public class MapConvertorTest {

	@Test
	public void readTest() {
		MapFile file=new MapFile();
		file.setWidth(2);
		file.setHeight(2);
		String tiles="1_0_0_0;2_0_0_0;3_0_0_0;4_5_0_0";
		file.setMapTiles(tiles);
		MapData data=new MapData(file);
		int t[][][]=data.getTiles();
		assertEquals(1, t[0][0][0]);
		assertEquals(2, t[1][0][0]);
		assertEquals(3, t[0][1][0]);
		assertEquals(4, t[1][1][0]);
		assertEquals(5, t[1][1][1]);		
	}
	
	@Test
	public void writeTest() {
		MapConvertor conv=new MapConvertor();
		
	}

}
