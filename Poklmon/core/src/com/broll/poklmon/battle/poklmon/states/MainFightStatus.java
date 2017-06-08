package com.broll.poklmon.battle.poklmon.states;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.data.basics.ColorUtil;

public enum MainFightStatus {

	
	PARALYZE("Paralyziert","# wurde paralysiert!","# ist paralysiert und kann nicht angreifen!","# ist nicht mehr paralysiert!",36,ColorUtil.newColor(184,184,24),"PAR"),
	POISON("Vergiftet","# wurde vergiftet!","Die Vergiftung schadet #!","# ist wieder gesund!",12, ColorUtil.newColor(192,96,192),"VGFT"),
	TOXIN("Schwer Vergiftet","# wurde schwer vergiftet!","# leidet unter der schweren Vergiftung!","# ist wieder gesund!",12,ColorUtil.newColor(192,96,192),"TOXIN"),
	BURNING("Brennt","# fängt Feuer!","# brennt!","# hat das Feuer gelöscht!",35,ColorUtil.newColor(224,112,80),"BRN"),
	ICE("Gefroren","# ist zu Eis erstarrt!","# ist tiefgefroren...","# ist wieder aufgetaut!",-1,ColorUtil.newColor(136,176,224),"GFR"),
	SLEEPING("Schläft","# ist eingeschlafen!","# schläft tief und fest...","# ist wieder aufgewacht!",-1,ColorUtil.newColor(160,160,136),"SLP"),
	FAINTED("Besiegt",null,null,"# hat sich erholt!",-1,ColorUtil.newColor(176,32,32),"BSGT");
	
	private String name;
	private String entryName;
	private String durName;
	private String exitName;
	private int animationID;
	private Color color;
	private String icontext;
	
	private MainFightStatus(String n, String entry,String dur, String exit, int animationID,Color color,String icontext)
	{
		name=n;
		durName=dur;
		entryName=entry;
		exitName=exit;
		this.animationID=animationID;
		this.color=color;
		this.icontext=icontext;
	}
	
	public String getIcontext() {
		return icontext;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getAnimationID()
    {
        return animationID;
    }
	
	public String getDurName() {
		return durName;
	}
	
	public String getEntryName() {
		return entryName;
	}
	public String getExitName() {
		return exitName;
	}
	public String getName() {
		return name;
	}
}
