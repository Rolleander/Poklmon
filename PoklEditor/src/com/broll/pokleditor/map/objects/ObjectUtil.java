package com.broll.pokleditor.map.objects;

import java.util.List;

import com.broll.pokllib.object.MapObject;

public class ObjectUtil
{

    public static MapObject findObjectAt(List<MapObject> objects, int x, int y)
    {
        for (MapObject o : objects)
        {
            if (o.getXpos() == x && o.getYpos() == y)
            {
                return o;
            }
        }
        return null;
    }

    public static MapObject copyObject(MapObject copy)
    {
      MapObject paste  =new MapObject();
      paste.setAttributes(copy.getAttributes());
      paste.setDirection(copy.getDirection());
      paste.setGraphic(copy.getGraphic());
      paste.setName(copy.getName());
      paste.setTriggerScript(copy.getTriggerScript());
      paste.setXpos(copy.getXpos());
      paste.setYpos(copy.getYpos());
      return paste;
    }
}
