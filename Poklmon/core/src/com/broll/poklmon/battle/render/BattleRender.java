package com.broll.poklmon.battle.render;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.data.basics.Graphics;

public class BattleRender
{


    private AttackAnimationRender attackAnimationRender;
    private BattleBackgroundRender backgroundRender;
    private BattleHUDRender hudRender;
    private BattleSequenceRender sequenceRender;
    private BattlePoklmonRender poklmonRender;
    private FieldEffectRender fieldEffectRender;

    public BattleRender(BattleManager battleManager)
    {
        attackAnimationRender = new AttackAnimationRender(battleManager);
        backgroundRender = new BattleBackgroundRender(battleManager);
        hudRender = new BattleHUDRender(battleManager);
        sequenceRender = new BattleSequenceRender(battleManager);
        poklmonRender = new BattlePoklmonRender(battleManager);
        fieldEffectRender = new FieldEffectRender(battleManager);
    }
    
    public void init() {
    	backgroundRender.init();
    	hudRender.init();
	}

    public void render(Graphics g)
    {
        backgroundRender.render(g);
        fieldEffectRender.renderBackground(g);
        poklmonRender.render(g);
        attackAnimationRender.render(g);
        sequenceRender.render(g);
        fieldEffectRender.renderForeground(g);
        hudRender.render(g);
    }

    public void update(float delta)
    {
        backgroundRender.update(delta);
        attackAnimationRender.update(delta);
        hudRender.update(delta);
        sequenceRender.update(delta);
        poklmonRender.update(delta);
        fieldEffectRender.update(delta);
    }

    public BattleSequenceRender getSequenceRender()
    {
        return sequenceRender;
    }

    public AttackAnimationRender getAttackAnimationRender()
    {
        return attackAnimationRender;
    }

    public BattleBackgroundRender getBackgroundRender()
    {
        return backgroundRender;
    }

    public BattleHUDRender getHudRender()
    {
        return hudRender;
    }

    public BattlePoklmonRender getPoklmonRender()
    {
        return poklmonRender;
    }

    public FieldEffectRender getFieldEffectRender()
    {
        return fieldEffectRender;
    }

	
}
