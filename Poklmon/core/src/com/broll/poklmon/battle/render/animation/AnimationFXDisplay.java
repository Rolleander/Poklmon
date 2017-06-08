package com.broll.poklmon.battle.render.animation;

import com.badlogic.gdx.graphics.Color;
import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.animation.AnimationFX;
import com.broll.pokllib.animation.AnimationFXTarget;
import com.broll.pokllib.animation.AnimationFXType;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;

public class AnimationFXDisplay
{
    private DataContainer data;

    public AnimationFXDisplay(DataContainer data)
    {
        this.data = data;
    }

    private Animation animation;

    public void setAnimation(Animation animation)
    {
        this.animation = animation;
    }

    private int frame;

    public void initStep(int step)
    {
        frame = step;
        //check for sound fx
        if (animation.getFx() != null)
        {
            for (AnimationFX fx : animation.getFx())
            {
                if (fx.getType() == AnimationFXType.PLAYSOUND&&fx.getAtFrame()==frame)
                {
                    //play sound
                	String sound="battle_"+fx.getValue();
                	data.getSounds().playSound(sound);
                }
            }
        }
    }


    public Color getColor(AnimationFXTarget target)
    {
        if (animation.getFx() != null)
        {
            for (AnimationFX fx : animation.getFx())
            {
                AnimationFXType type = fx.getType();
                if (type == AnimationFXType.COLOROVERLAY)
                {
                    int start = fx.getAtFrame();
                    int length = fx.getLength();
                    if (frame >= start && frame <= start + length)
                    {
                        if (fx.getTarget() == target)
                        {



                            int r = getFrameRGP(start, length, fx.getR());
                            int g = getFrameRGP(start, length, fx.getG());
                            int b = getFrameRGP(start, length, fx.getB());
                            int a = 255;
                            return ColorUtil.newColor(r, g, b, a);

                        }
                    }
                }
            }
        }
        return null;
    }

    public void render(Graphics g)
    {
        if (animation.getFx() != null)
        {
            for (AnimationFX fx : animation.getFx())
            {
                AnimationFXType type = fx.getType();
                if (type != AnimationFXType.PLAYSOUND)
                {
                    int start = fx.getAtFrame();
                    int length = fx.getLength();
                    if (frame >= start && frame <= start + length)
                    {
                        if (type == AnimationFXType.COLOROVERLAY)
                        {
                            //color overlay
                            AnimationFXTarget target = fx.getTarget();
                            Color c = ColorUtil.newColor(fx.getR(), fx.getG(), fx.getB(), getFrameAlpha(start, start, fx.getA()));
                            if (target == AnimationFXTarget.SCREEN)
                            {
                                g.setColor(c);
                                g.fillRect(0, 0, 800, 600);
                            }
                        }
                        else if(type == AnimationFXType.SHAKE)
                        {
                            //screenshake
                        	
                        }
                    }
                }
            }
        }
    }

    private int getFrameAlpha(int start, int length, int alpha)
    {
        float cur = frame - start;
        return (int)(255 - ((cur / (float)length) * alpha));
    }

    private int getFrameRGP(int start, int length, int rgb)
    {
        float cur = frame - start;
        int dif = 255 - rgb;
        return (int)(rgb + ((cur / (float)length) * dif));
    }
}
