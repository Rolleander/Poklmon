package com.broll.poklmon.gui;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;

public class DialogBoxRender
{

    private int xpos, ypos;
    private int width, height;
    private Image border, corner, border2;
    private static int CORNER_SIZE = 32;
    private Color content;

    public DialogBoxRender(Image border, Image border2, Image corner, Color content)
    {
    
        this.border = border;
        this.corner = corner;
        this.content = content;
        this.border2 = border2;
       
    }

    public void setSize(int x, int y, int w, int h)
    {
        this.xpos = x;
        this.ypos = y;
        this.width = w;
        this.height = h;
    }

    public void render(Graphics g)
    {
        int x, y, w, h;
       
        //fill content
        x = xpos + CORNER_SIZE;
        y = ypos + CORNER_SIZE;
        w = width - CORNER_SIZE * 2;
        h = height - CORNER_SIZE * 2;
        g.setColor(content);
        g.fillRect(x, y, w, h);

        w += 10;
        //draw borders
        x = xpos + CORNER_SIZE;
        y = ypos;
        g.drawImage(border.getScaledCopy(w, CORNER_SIZE), x, y);

        x = xpos;
        y = ypos + CORNER_SIZE;
        g.drawImage(border2.getScaledCopy(CORNER_SIZE, h), x, y);

        x = xpos + CORNER_SIZE;
        y = ypos + height - CORNER_SIZE;
        g.drawImage(border.getFlippedCopy(false, true).getScaledCopy(w, CORNER_SIZE), x, y);

        x = xpos + width - CORNER_SIZE;
        y = ypos + CORNER_SIZE;
        g.drawImage(border2.getFlippedCopy(true, false).getScaledCopy(CORNER_SIZE, h), x, y);

       

        //draw corners
        x = xpos;
        y = ypos;
        g.drawImage(corner, x, y);

        y = ypos + height - CORNER_SIZE;
        g.drawImage(corner.getFlippedCopy(false, true), x, y);

        x = xpos + width - CORNER_SIZE;
        g.drawImage(corner.getFlippedCopy(true, true), x, y);

        y = ypos;
        g.drawImage(corner.getFlippedCopy(true, false), x, y);
    }

    public void move(int xp,int yp)
    {
        xpos+=xp;
        ypos+=yp;
        
    }

}
