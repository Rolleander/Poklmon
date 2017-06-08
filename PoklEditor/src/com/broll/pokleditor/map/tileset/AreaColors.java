package com.broll.pokleditor.map.tileset;

import java.awt.Color;

public class AreaColors
{
    
    private static Color[] areas={Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN,Color.CYAN,Color.MAGENTA,Color.ORANGE,Color.GRAY,Color.PINK};
    
    public static void prepareColors()
    {
        for(int i=0; i<areas.length; i++){
            Color c=areas[i];
            areas[i]=new Color(c.getRed(),c.getGreen(),c.getBlue(),200);
        }
    }
    
    public static Color getAreaColor(int id)
    {
        return areas[id];
    }
    
    

}
