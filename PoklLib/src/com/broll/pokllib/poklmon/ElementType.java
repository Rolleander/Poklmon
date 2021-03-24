package com.broll.pokllib.poklmon;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum ElementType {

	
    
	NORMAL("Normal"),FIRE("Feuer"),WATER("Wasser"),ELECTRO("Elektro"),PLANT("Pflanze"),
	ICE("Eis"),BATTLE("Kampf"),POISON("Gift"),GROUND("Boden"),FLYING("Flug"),
	PYSCHO("Psycho"),BUG("Käfer"),STONE("Gestein"),GHOST("Geist"),DRAGON("Drache"),
	STRANGE("Seltsam"),STEEL("Stahl");

	private String name;
	
	private ElementType(String n) {
		name=n;
	}
	
	public String getName() {
		return name;
	}
}
