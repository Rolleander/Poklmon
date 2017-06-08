package com.broll.pokllib.map;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.broll.pokllib.object.MapObject;

@XmlRootElement
public class MapFile
{


    private int width;
    private int height;
    private String name;
    private String initScript;
    private String music;
    private int objectSpawnID;
    private List<MapObject> objects;
    private List<String> areaScripts;
    private int id;
    
    private String mapTiles;

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<MapObject> getObjects()
    {
        return objects;
    }

    public void setObjects(List<MapObject> objects)
    {
        this.objects = objects;
    }

    public List<String> getAreaScripts()
    {
        return areaScripts;
    }

    public void setAreaScripts(List<String> areaScripts)
    {
        this.areaScripts = areaScripts;
    }

    public String getMapTiles()
    {
        return mapTiles;
    }

    public void setMapTiles(String mapTiles)
    {
        this.mapTiles = mapTiles;
    }

    public void setMusic(String music)
    {
        this.music = music;
    }

    public String getMusic()
    {
        return music;
    }

    public int getObjectSpawnID()
    {
        return objectSpawnID;
    }

    public void setObjectSpawnID(int objectSpawnID)
    {
        this.objectSpawnID = objectSpawnID;
    }

    public String getInitScript()
    {
        return initScript;
    }

    public void setInitScript(String initScript)
    {
        this.initScript = initScript;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
}
