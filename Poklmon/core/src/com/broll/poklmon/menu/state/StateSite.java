package com.broll.poklmon.menu.state;

import com.badlogic.gdx.graphics.Color;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.menu.MenuUtils;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.save.PoklmonData;

public abstract class StateSite
{

    protected Poklmon poklmonInfo;
    protected PoklmonData poklmon;
    protected DataContainer data;
    protected FontUtils fontUtils=new FontUtils();


    public StateSite(Poklmon poklmonInfo, PoklmonData poklmon, DataContainer data)
    {
        this.poklmon = poklmon;
        this.poklmonInfo = poklmonInfo;
        this.data = data;
        initData();
    }

    protected abstract void initData();

    public abstract void render(Graphics g, float x, float y);


    protected final static int lineHeight = 40;
    protected final static Color back = ColorUtil.newColor(160, 200, 250);
    protected float x, y;
    protected int lines;
    protected int lineWidth = 150;


    protected void renderLine(Graphics g, String info, String value)
    {

        float valuex = x + lineWidth;

        // draw background
        if (lines % 2 == 0)
        {
            g.setColor(back);
            g.fillRect(x, y, 450, lineHeight);
        }

        int w=MenuUtils.getTextWidth(g,fontUtils, value);
   
        if(value!=null&&value.length()>0&&w>0)
        {
        g.setColor(ColorUtil.newColor(255,255,255,100));
        g.fillRect(x+lineWidth-5,y,w+10,lineHeight);
        }
        
        g.setColor(ColorUtil.newColor(50, 50, 50));

        float ty = y;
        g.drawString(info, x + 3, ty);

        Color c =ColorUtil.newColor(250, 170, 0);
        Color s =ColorUtil.newColor(20, 20, 20);
        s = ColorUtil.newColor(100, 50, 0);

        MenuUtils.drawFancyString(g, value, valuex, ty, s, c);
        y += lineHeight;
        lines++;
    }

    protected void renderImageLine(Graphics g, String info, Image image)
    {
        float valuex = x + lineWidth + image.getWidth() / 2;
        float valuey = y + lineHeight / 2;
        renderLine(g, info, "");
        image.drawCentered(valuex, valuey);

    }

}
