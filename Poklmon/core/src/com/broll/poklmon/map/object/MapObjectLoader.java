package com.broll.poklmon.map.object;

import com.broll.pokllib.map.MapFile;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.map.MapContainer;

import java.util.ArrayList;
import java.util.List;

public abstract class MapObjectLoader {

	public static List<MapObject> loadObjects(MapFile map, DataContainer data, MapContainer mapContainer) {
		List<MapObject> list = new ArrayList<MapObject>();
		for (com.broll.pokllib.object.MapObject dataObject : map.getObjects()) {
			String initScript = dataObject.getAttributes();
			ObjectDirection direction = dataObject.getDirection();
			String graphic = dataObject.getGraphic();
			String objectName = dataObject.getName();
			int objectId = dataObject.getObjectID();
			String triggerScript = dataObject.getTriggerScript();
			int xpos = dataObject.getXpos();
			int ypos = dataObject.getYpos();

			MapObject o = new MapObject(data,mapContainer);
			o.setMovementSpeed(0.04f);
			o.setDirection(direction);
			o.setBlocking(true);
			o.init(objectName, objectId, triggerScript, initScript);
			o.teleport(xpos, ypos);
			if(graphic!=null&&!graphic.isEmpty())
			{
			o.setGraphic(data.getGraphics().getCharImage(graphic));
			}
			list.add(o);
		}
		return list;
	}
}
