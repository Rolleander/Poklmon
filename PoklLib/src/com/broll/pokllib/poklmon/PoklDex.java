package com.broll.pokllib.poklmon;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PoklDex {

	
	private List<PoklmonID> poklmon;

	public List<PoklmonID> getPoklmonList() {
		return poklmon;
	}

	public void setPoklmonList(List<PoklmonID> poklmonList) {
		this.poklmon = poklmonList;
	}
	
}
