package com.broll.poklmon.data;

import java.util.List;

import com.broll.pokllib.animation.Animation;

public class AnimationContainer
{


    private List<Animation> animation;


    public AnimationContainer(List<Animation> animations)
    {
        this.animation = animations;
    }

    public Animation getAnimation(int id)
    {
        if (id > -1 && id < animation.size())
        {
            return animation.get(id);
        }
        else
        {
            try
            {
                throw new DataException("False Animation ID! ("+id+")");
            }
            catch (DataException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public int getNumberOfAnimations()
    {
        return animation.size();
    }

}
