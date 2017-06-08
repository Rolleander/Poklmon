package com.broll.poklmon.battle.render.animation;

import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.animation.AnimationStep;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.data.basics.Graphics;

public class AnimationRender
{
    private Animation animation;
    private int animationStep;
    private AnimationStepRender stepRender;
    private AnimationFXDisplay animationFXDisplay;
    private boolean mirrored;

    public AnimationRender(BattleManager battle)
    {
        animationFXDisplay = new AnimationFXDisplay(battle.getData());
        stepRender = new AnimationStepRender(battle,animationFXDisplay);
   
    }

    public void startAnimation(Animation animation, boolean mirrored)
    {
        animationStep = 0;
        this.animation = animation;
        this.mirrored = mirrored;
       
        animationFXDisplay.setAnimation(animation);
        animationFXDisplay.initStep(animationStep);
    }

    public void render(Graphics g, float x, float y)
    {
        //render step
        if(animationStep<animation.getAnimation().size())
        {
        AnimationStep step = animation.getAnimation().get(animationStep);
        stepRender.render(g, step, mirrored, x, y);
        //render effects
        animationFXDisplay.render(g);
        }
    }

    public boolean invokeNextStep()
    {
        animationStep++;
        animationFXDisplay.initStep(animationStep);
        if (animationStep >= animation.getAnimation().size())
        {
            return true;
        }
        return false;
    }
}
