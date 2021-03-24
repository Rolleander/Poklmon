package com.broll.pokllib.map;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapDex {

	private List<MapID> maps;

	public List<MapID> getMaps() {
		return maps;
	}

	public void setMaps(List<MapID> maps) {
		this.maps = maps;
	}

}
