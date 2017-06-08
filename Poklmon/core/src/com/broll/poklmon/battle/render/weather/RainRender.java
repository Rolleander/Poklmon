package com.broll.poklmon.battle.render.weather;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;

public class RainRender extends StageEffectRender
{
    private ParticleRender particleRender=new ParticleRender(2);
  
    public RainRender(DataContainer data)
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
        float xpos = (float)(Math.random() * 900)+20;
        float ypos = -50;
        float length = (float)(25 + Math.random() * 30);
        float speed = (float)(Math.random() * 5 + 20);
        float transparency = (float)(Math.random()/2 + 0.3);
        float angle = (float)Math.toRadians(100+Math.random()*5);
        particleRender.addParticle(xpos,ypos,angle,speed,length,transparency);
    }

    @Override
    public void renderForeground(Graphics g)
    {
        particleRender.renderParticles(g);
    }

    @Override
    public void renderBackground(Graphics g)
    {
        g.setColor(ColorUtil.newColor(50, 50, 200, 100));
        g.fillRect(0, 0, 800, 600);

    }

}
