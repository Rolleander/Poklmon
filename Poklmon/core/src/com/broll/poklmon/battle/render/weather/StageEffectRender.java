package com.broll.poklmon.battle.render.weather;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;

public abstract class StageEffectRender
{
    protected DataContainer data;
    
    public StageEffectRender(DataContainer data)
    {
        this.data=data;
    }

    public abstract void renderForeground(Graphics g);
    
    public abstract void renderBackground(Graphics g);
    
    public abstract void update(float delta);
    
    
}
