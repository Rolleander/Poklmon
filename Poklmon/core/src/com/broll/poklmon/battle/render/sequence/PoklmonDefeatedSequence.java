package com.broll.poklmon.battle.render.sequence;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.battle.util.message.BattleMessages;
import com.broll.poklmon.data.TextContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;

public class PoklmonDefeatedSequence extends SequenceRender
{

    private FightPoklmon target;
    private boolean textAccepted;
    private Image image;
    private float x, y;
    private boolean rotDir;

    public PoklmonDefeatedSequence(BattleManager battle)
    {
        super(battle);
    }

    public void init(FightPoklmon target)
    {
        this.target = target;
        textAccepted = false;
        String name = target.getName();
        String text = TextContainer.get("poklmonDefeated",name);
        battle.getBattleRender().getHudRender().showText(text, new SelectionListener() {
			
			@Override
			public void selectionDone() {
			     textAccepted = true;
			}
		});
    }

    @Override
    protected void start()
    {
    	battle.getData().getSounds().playSound("b_defeat");
        battle.getBattleRender().getBackgroundRender().setMoving(false);
        if (target == battle.getParticipants().getPlayer())
        {
            battle.getBattleRender().getPoklmonRender().setPlayerPoklmonVisible(false);
            x = 160;
            image = battle.getParticipants().getPlayer().getImage().getScaledCopy(2);
            rotDir=false;
        }
        else
        {
            battle.getBattleRender().getPoklmonRender().setEnemyPoklmonVisible(false);
            x = 640;
            image = battle.getParticipants().getEnemy().getImage().getScaledCopy(2).getFlippedCopy(true, false);
            rotDir=true;
        }
        image.setRotation(0);
        filter = ColorUtil.newColor(255, 255, 255, 255);
        y = 200 + battle.getBattleRender().getBackgroundRender().getMoveY();
    }


    private Color filter;

    @Override
    public void render(Graphics g)
    {
        image.draw(x - image.getWidth() / 2, y - image.getHeight() / 2, filter);
    }

    @Override
    public void update(float delta)
    {
    	image.setCenterOfRotation();
        float r = image.getRotation();
        float s = 0.1f;
        if (rotDir)
        {
            if (r > -90)
            {
                float p = (float)(image.getRotation() / 15 - s);
                image.rotate(p);
                y += 0.8;
            }
            else
            {
                decAlpha();
            }
        }
        else
        {
            if (r < 90)
            {
                float p = (float)(image.getRotation() / 15 + s);
                image.rotate(p);
                y += 0.5;
            }
            else
            {
                decAlpha();
            }
        }
    }

    private void decAlpha()
    {
        int alpha = (int) (filter.a*255);
        if (alpha > 0)
        {
            alpha -= 3;
            if (alpha < 0)
            {
                alpha = 0;
            }
            filter = ColorUtil.newColor(255, 255, 255, alpha);
        }
        else
        {
            if (textAccepted)
            {
                stop();
            }
        }
    }
}
