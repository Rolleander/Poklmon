package com.broll.poklmon.data;

import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.data.player.PlayerGraphics;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphicsContainer 
{

	
    private SpriteSheet tileSet;
    private BattleGraphicsContainer battleGraphicsContainer=new BattleGraphicsContainer();
    private MenuGraphicsContainer menuGraphicsContainer=new MenuGraphicsContainer();
    private EventGraphicsContainer eventGraphicsContainer=new EventGraphicsContainer();
    private MapGraphicsContainer mapGraphicsContainer=new MapGraphicsContainer();
    private HashMap<String,Image> poklmonGraphics=new HashMap<String,Image>();
    private HashMap<String,SpriteSheet> charGraphics=new HashMap<String,SpriteSheet>();
    private ArrayList<PlayerGraphics> player=new ArrayList<PlayerGraphics>();
    
  
    
    public GraphicsContainer()
    {
        
    }
    
    public void load() throws DataException
    {
        tileSet=DataLoader.loadTileSet();
        poklmonGraphics=DataLoader.loadPoklmonImages();
        charGraphics=DataLoader.loadCharImages();
        

        for(int i=0; i<2; i++)
        {
        	player.add(new PlayerGraphics(i));
        }
        
        battleGraphicsContainer.load();
        menuGraphicsContainer.load();
        eventGraphicsContainer.load();
        mapGraphicsContainer.load();
    }
    
    public MapGraphicsContainer getMapGraphicsContainer() {
		return mapGraphicsContainer;
	}
    
    public BattleGraphicsContainer getBattleGraphicsContainer() {
		return battleGraphicsContainer;
	}
    
    public EventGraphicsContainer getEventGraphicsContainer() {
		return eventGraphicsContainer;
	}
    
    public MenuGraphicsContainer getMenuGraphicsContainer() {
		return menuGraphicsContainer;
	}

    

    
    public ArrayList<PlayerGraphics> getPlayer() {
		return player;
	}
    
    public SpriteSheet getTileSet()
    {
        return tileSet;
    }
    

    public Image getPoklmonImage(String name)
    {
        if(poklmonGraphics.containsKey(name))
        {
            return poklmonGraphics.get(name);
        }
        else
        {
            try
            {
                throw new DataException("Cant find Poklmon Graphic: "+name);
            }
            catch (DataException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    public SpriteSheet getCharImage(String name)
    {
        if(charGraphics.containsKey(name))
        {
            return charGraphics.get(name);
        }
        else
        {
            try
            {
                throw new DataException("Cant find Char Graphic: "+name);
            }
            catch (DataException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
    
 
   
}
