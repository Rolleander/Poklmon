package com.broll.poklmon.battle.render.weather;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;

public class SnowRender extends StageEffectRender
{
    private ParticleRender particleRender=new ParticleRender(4);
    

    public SnowRender(DataContainer data)
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
      
        particleRender.updateParticles(delta);
    }

    private void addDrop()
    {
       
        float xpos = (float)(Math.random() * 1500)+200;
        float ypos = -50;
        float length = (float)(5 + Math.random() * 10);
        float speed = (float)(Math.random() * 3 + 7);
        float transparency = (float)(Math.random()/2 + 0.3);
   
        float angle = (float)Math.toRadians(130+Math.random()*60);
        particleRender.addParticle(xpos,ypos,angle,speed,length,transparency);
    }

    @Override
    public void renderForeground(Graphics g)
    {
        g.setColor(ColorUtil.newColor(100,100,100, 50));
        g.fillRect(0, 0, 800, 600);
        
        particleRender.renderParticles(g);
    }

    @Override
    public void renderBackground(Graphics g)
    {
        g.setColor(ColorUtil.newColor(100,100,100, 100));
        g.fillRect(0, 0, 800, 600);

    }


}
