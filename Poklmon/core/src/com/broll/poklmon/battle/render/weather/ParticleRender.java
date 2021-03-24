package com.broll.poklmon.battle.render.weather;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ParticleRender
{
    private List<Drop> drops = new ArrayList<Drop>();
    private int width;

    public ParticleRender(int w)
    {
        this.width = w;
    }


    public void addParticle(float x, float y, float angle, float speed, float length, float transparency)
    {
        Drop d = new Drop();
        d.xpos = x;
        d.ypos = y;
        d.angle = angle;
        d.speed = speed;
        d.length = length;
        d.transparency = transparency;
        drops.add(d);
    }

    public void updateParticles(float delta)
    {
        Iterator<Drop> iterator = drops.iterator();
        while (iterator.hasNext())
        {
            Drop drop = iterator.next();

            float angle = drop.angle;
            float speed = drop.speed;
            drop.xpos += Math.cos(angle) * speed;
            drop.ypos += Math.sin(angle) * speed;
            if (drop.ypos > 650)
            {
                iterator.remove();
            }
        }
    }

    public void renderParticles(Graphics g)
    {
        for (Drop drop : drops)
        {
            float x = drop.xpos;
            float y = drop.ypos;
            float length = drop.length;
            float angle = drop.angle;
            Color c = ColorUtil.newColor(0.8f, 0.8f, 1f, drop.transparency);
            g.setColor(c);
            float x2 = x + (float)(Math.cos(angle) * length);
            float y2 = y + (float)(Math.sin(angle) * length);
            for (int i = 0; i < width; i++)
            {
                g.drawLine(x + i, y, x2 + i, y2);
            }
        }
    }

    public class Drop
    {
        public float xpos;
        public float ypos;
        public float length;
        public float angle;
        public float speed;
        public float transparency;
    }
}
