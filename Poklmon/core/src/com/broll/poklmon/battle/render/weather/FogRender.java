package com.broll.poklmon.battle.render.weather;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;

public class FogRender extends StageEffectRender
{
    private float fogx, width;
    private Color fogColor = ColorUtil.newColor(1f, 1f, 1f, 0.6f);

    public FogRender(DataContainer data)
    {
        super(data);
        width = 800;
    }

    @Override
    public void renderForeground(Graphics g)
    {
        Image fog = data.getGraphics().getBattleGraphicsContainer().getFog().getScaledCopy(2);
        fog.draw(fogx, 0, fogColor);
        fog.getFlippedCopy(true, false).draw(fogx + width, 0, fogColor);
        fog.draw(fogx+width*2, 0, fogColor);
    }

    @Override
    public void renderBackground(Graphics g)
    {

    }

    @Override
    public void update(float delta)
    {
        fogx -= 0.5;
        if (fogx < -width*2)
        {
            fogx = 0;
        }
    }

}
