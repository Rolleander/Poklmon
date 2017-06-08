package com.broll.poklmon.battle.render.weather;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;

public class SunRender extends StageEffectRender
{

    public SunRender(DataContainer data)
    {
        super(data);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void renderForeground(Graphics g)
    {
        g.setColor(ColorUtil.newColor(1f,1f,1f,0.3f));
        g.fillRect(0,0,800,600);
    }

    @Override
    public void renderBackground(Graphics g)
    {
        g.setColor(ColorUtil.newColor(250,200,0,70));
        g.fillRect(0,0,800,600);
    }

    @Override
    public void update(float delta)
    {
        // TODO Auto-generated method stub
        
    }

}
