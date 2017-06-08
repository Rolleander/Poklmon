package com.broll.poklmon.battle.render.weather;

import com.broll.poklmon.battle.util.BattleRandom;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;

public class StormRender extends StageEffectRender
{
    private ParticleRender particleRender = new ParticleRender(2);

    public StormRender(DataContainer data)
    {
        super(data);
    }


    @Override
    public void update(float delta)
    {
        //add drop
        addDrop();
        addDrop();
        addDrop();
        addDrop();

        particleRender.updateParticles(delta);
    }

    private void addDrop()
    {
        float xpos = (float)(Math.random() * 1500);
        float ypos = -50;
        float length = (float)(40 + Math.random() * 50);
        float speed = (float)(Math.random() * 25 + 20);
        float transparency = (float)(Math.random() / 2 + 0.3);
        float angle = (float)Math.toRadians(140 + Math.random() * 5);

        particleRender.addParticle(xpos, ypos, angle, speed, length, transparency);
    }

    @Override
    public void renderForeground(Graphics g)
    {
        particleRender.renderParticles(g);

        if ((int)(BattleRandom.random() * 35) == 0)
        {
            g.setColor(ColorUtil.newColor(255, 240, 220, 230));
            g.fillRect(0, 0, 800, 600);
        }
        else
        {
            g.setColor(ColorUtil.newColor(0, 0, 0, 100));
            g.fillRect(0, 0, 800, 600);
        }

    }

    @Override
    public void renderBackground(Graphics g)
    {
        g.setColor(ColorUtil.newColor(50, 50, 50, 150));
        g.fillRect(0, 0, 800, 600);

    }



}
