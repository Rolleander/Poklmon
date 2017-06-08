package com.broll.poklmon.battle.render.weather;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;

public class NightRender extends StageEffectRender
{

    public NightRender(DataContainer data)
    {
        super(data);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void renderForeground(Graphics g)
    {
        g.setColor(ColorUtil.newColor(0f,0f,0f,0.15f));
        g.fillRect(0,0,800,600);
    }

    @Override
    public void renderBackground(Graphics g)
    {
        g.setColor(ColorUtil.newColor(0f,0f,0f,0.7f));
        g.fillRect(0,0,800,600);
    }

    @Override
    public void update(float delta)
    {
        // TODO Auto-generated method stub
        
    }

}
