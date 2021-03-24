package com.broll.poklmon.menu.pc;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.menu.MenuUtils;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.PoklmonData;

public class PcBlockRender
{
    
    private DataContainer data;
    public static int WIDTH=180;
    public static int HEIGHT=40;
    private FontUtils fontUtils=new FontUtils();

    public PcBlockRender(DataContainer data)
    {
        this.data=data;
    }
    
    public void renderBox(Graphics g, PoklmonData poklmon, float x, float y, boolean selected)
    {
        Image block;        
        SpriteSheet boxSprites=data.getGraphics().getMenuGraphicsContainer().getBoxBlock();
        if(poklmon==null)
        {
            block=boxSprites.getSprite(0, 0);    
            //draw background
            block.draw(x, y);
            if(selected)
            {
                g.setColor(ColorUtil.newColor(250,250,250,100));
                g.fillRect(x,y,WIDTH,HEIGHT);
            }
            return;
        }
        else
        {
            if(selected)
            {
                block=boxSprites.getSprite(0, 2);    
            }
            else
            {
                block=boxSprites.getSprite(0, 1);                
            }
            block.draw(x, y);
        }
        
        int id=poklmon.getPoklmon();
        Poklmon poklInfo=data.getPoklmons().getPoklmon(id);

        
        //draw image      
        Image poklImage=data.getGraphics().getPoklmonImage(poklInfo.getGraphicName()).getSubImage(30, 30, 36, 36);
        poklImage.drawCentered(x+20, y+20);
        
        //draw name
        g.setColor(ColorUtil.newColor(250,250,250));
        String name=poklmon.getName();
        if(name==null)
        {
            name=poklInfo.getName();
        }
        g.setFont(GUIFonts.smallText);
        MenuUtils.drawFancyString(g, name, x+42, y-3);
    //    g.drawString(name,x+42,y-3);
        
        g.setColor(ColorUtil.newColor(0,0,100));
        int level=poklmon.getLevel();
        String t="Lv."+level;
        g.drawString(t,x+176-MenuUtils.getTextWidth(g,fontUtils, t),y+16);

    }

}
