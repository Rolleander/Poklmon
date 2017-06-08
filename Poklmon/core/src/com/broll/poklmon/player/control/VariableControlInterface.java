package com.broll.poklmon.player.control;

public interface VariableControlInterface {

	public boolean getBoolean(String no);
	
	public int getInt(String no);
	
	public float getFloat(String no);
	
	public String getString(String no);
	
	public void setBoolean(String no, boolean value);
	
	public void setInt(String no, int value);
	
	public void setFloat(String no, float value);
	
	public void setString(String no, String value);
	
	public boolean isKnownobject(int mapNr, int objectNr);

	public void setObjectToKnown(int mapNr, int objectNr, boolean isKnown);
}
