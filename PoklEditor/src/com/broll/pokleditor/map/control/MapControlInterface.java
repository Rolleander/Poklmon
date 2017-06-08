package com.broll.pokleditor.map.control;

import com.broll.pokllib.object.MapObject;

public interface MapControlInterface
{
    
    public void clearTiles();
    
    public void resizeMapDialog();

    public void moveMapDialog();
    
    public void clearAreas();
    
    public void deleteAreaScripts();
    
    public void deleteObjects();
    
    public void deleteSelectedObject();
    
    public void addObject(MapObject object);

    public void copyObject();

    public void pasteObject();

	public void editObject();
	
	public void debugFromHere();
}
