package com.broll.poklmon.battle.render.sequence;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.data.basics.Graphics;

public class PoklmonHealSequence extends SequenceRender
{

    private FightPoklmon target;
    private int heal;
    private String text;
    private boolean waitForText = false;

    public PoklmonHealSequence(BattleManager battle)
    {
        super(battle);
    }

    public void init(FightPoklmon target, int heal, String text)
    {
        this.target = target;
        this.heal = heal;
        this.text = text;
    }

    @Override
    protected void start()
    {
      battle.getBattleRender().getHudRender().setShowHud(true);
        if (text != null)
        {
            battle.getBattleRender().getHudRender().showText(text, new SelectionListener() {

                public void selectionDone()
                {
                    waitForText = false;
                }
            });
            waitForText = true;
        }
        else
        {
            waitForText = false;
        }
    }

    @Override
    public void render(Graphics g)
    {

    }

    private int wait = 0;

    private void tryStop()
    {
        if (!waitForText)
        {
            stop();
        }
    }


    @Override
    public void update(float delta)
    {

        if (heal > 0)
        {
            if (wait > 0)
            {
                wait--;
            }
            else
            {
                heal--;
                target.doHeal(1);
                wait = 4;
                if (target.isFullKP())
                {
                    tryStop();
                }
            }
        }
        else
        {
            tryStop();
        }

    }

}
