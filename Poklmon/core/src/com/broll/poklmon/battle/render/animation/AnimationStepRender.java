package com.broll.poklmon.battle.render.animation;

import com.badlogic.gdx.graphics.Color;
import com.broll.pokllib.animation.AnimationFXTarget;
import com.broll.pokllib.animation.AnimationSprite;
import com.broll.pokllib.animation.AnimationStep;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;

public class AnimationStepRender
{
    private DataContainer data;
    private BattleManager battle;
    private AnimationFXDisplay fxDisplay;

    public AnimationStepRender(BattleManager battle, AnimationFXDisplay animationFXDisplay)
    {
        this.fxDisplay = animationFXDisplay;
        this.data = battle.getData();
        this.battle = battle;
    }

    private static int MIRROR_EDGE = 400;

    public void render(Graphics g, AnimationStep step, boolean mirrored, float x, float y)
    {
        for (AnimationSprite sprite : step.getSprites())
        {
            int imageId = sprite.getSpriteID();
            float size = sprite.getSize();
            float ix = sprite.getX();
            float iy = sprite.getY();
            Image image = null;
            float transp = sprite.getTransparency();
            Color filter = ColorUtil.newColor(255, 255, 255, transp);

            if (imageId > 1)
            {
                image = data.getGraphics().getBattleGraphicsContainer().getAnimationSet().getSprite(getSpriteX(imageId), getSpriteY(imageId));
            }
            else if (imageId == 0)
            {
                //player
                if (mirrored)
                {
                    image = battle.getParticipants().getEnemy().getImage();

                }
                else
                {
                    image = battle.getParticipants().getPlayer().getImage();
                }
                Color c = fxDisplay.getColor(AnimationFXTarget.USER);
                if (c != null)
                {
                    filter = c;
                }
            }
            else if (imageId == 1)
            {
                //enemy
                if (mirrored)
                {
                    image = battle.getParticipants().getPlayer().getImage().getFlippedCopy(true, false);
                }
                else
                {
                    image = battle.getParticipants().getEnemy().getImage().getFlippedCopy(true, false);
                }
                Color c = fxDisplay.getColor(AnimationFXTarget.TARGET);
                if (c != null)
                {
                    filter = c;
                 }
            }
            if (mirrored)
            {
                ix -= MIRROR_EDGE * 2;
                ix *= -1;
                image = image.getFlippedCopy(true, false);
            }
            float angle=0;
            if(mirrored)
            {
                angle = (float)Math.toDegrees((Math.PI*2)-sprite.getAngle());
            }
            else
            {
                angle = (float)Math.toDegrees(sprite.getAngle());
            }
            
            float w = image.getWidth() / 2;
            float h = image.getHeight() / 2;
           // System.out.println(w+" "+h);
            image.setRotation(angle);

            float xpos = x + ix-w;
            float ypos = y + iy-h;
            image.setCenterOfRotation(w , h );
        
            image.draw(xpos, ypos, size, filter);
        }
    }

    private static int getSpriteX(int id)
    {
        return id % 9;
    }

    private static int getSpriteY(int id)
    {
        return id / 9;
    }
}
