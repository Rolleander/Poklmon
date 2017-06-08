package com.broll.poklmon.battle.render;

import com.broll.pokllib.animation.Animation;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.render.animation.AnimationRender;
import com.broll.poklmon.battle.util.ProcessThreadHandler;
import com.broll.poklmon.data.basics.Graphics;

public class AttackAnimationRender {

    private AnimationRender animationRender;
	private BattleManager battle;
	private boolean isRunning=false;
	private float frameWait;
	private ProcessThreadHandler exitListener;
	private static float ANIMATION_SPEED=0.035f;
	private boolean hidePoklmon=false;
	
	public AttackAnimationRender(BattleManager battleManager) {
	    battle=battleManager;
	    animationRender=new AnimationRender(battleManager);
	}
	
	public void showAnimation(Animation animation,boolean mirrored,boolean justOverlay, ProcessThreadHandler exitListener)
	{
	    isRunning=true;
	    hidePoklmon=!justOverlay;
	    frameWait=0;
	    animationRender.startAnimation(animation, mirrored);
	    this.exitListener=exitListener;
	    if(hidePoklmon)
	    {
	    battle.getBattleRender().getPoklmonRender().setEnemyPoklmonVisible(false);
        battle.getBattleRender().getPoklmonRender().setPlayerPoklmonVisible(false);
	    }
	}

	public void render(Graphics g)
	{
	    if(isRunning)
	    {
	    float y=battle.getBattleRender().getBackgroundRender().getMoveY();
		animationRender.render(g, 0, y);
	    }
	}
	
	public void update(float delta)
	{
	    if(isRunning)
        {
	        frameWait+=delta;
	        if(frameWait>=ANIMATION_SPEED)
	        {
	            frameWait=0;
	            if(animationRender.invokeNextStep())
	            {
	                //end
	                isRunning=false;	        
	                if(hidePoklmon)
	        	    {
	                battle.getBattleRender().getPoklmonRender().setEnemyPoklmonVisible(true);
	                battle.getBattleRender().getPoklmonRender().setPlayerPoklmonVisible(true);
	        	    }
	                exitListener.resume();
	            }
	        }
        }
	}
	
	
}
