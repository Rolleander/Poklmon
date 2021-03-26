package com.broll.poklmon.battle.render;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;

public class BattlePoklmonRender {

	private BattleManager battle;
	private boolean playerPoklmonVisible=false;
	private boolean enemyPoklmonVisible=false;
	public final static int Y_SHIFT = 15;
    
	public BattlePoklmonRender(BattleManager battleManager) {
		battle=battleManager;
	}

	public void render(Graphics g)
	{
	    float yp=battle.getBattleRender().getBackgroundRender().getMoveY();
	    float y=200+yp+Y_SHIFT;
	    float x1=160;
	    float x2=640;
	    
	    //draw shadows
    /*    int sw=160;
        int sh=50;   draw shadow for flying poklmons
        float sy=y+55;
        g.setColor(ColorUtil.newColor(0,0,0,50));
        g.fillOval(x1-sw/2,sy-sh/2,sw,sh);
        g.fillOval(x2-sw/2,sy-sh/2,sw,sh);*/
        
	    //draw poklmon
	    if(playerPoklmonVisible)
	    {
		Image player=battle.getParticipants().getPlayer().getImage().getScaledCopy(2);
		player.drawCentered(x1, y);
	    }
	    
	    if(enemyPoklmonVisible)
	    {
		Image enemy=battle.getParticipants().getEnemy().getImage().getFlippedCopy(true, false).getScaledCopy(2);
		enemy.drawCentered(x2, y);
        
	    }
	}
	
	public void setEnemyPoklmonVisible(boolean enemyPoklmonVisible)
    {
        this.enemyPoklmonVisible = enemyPoklmonVisible;
    }
	
	public void setPlayerPoklmonVisible(boolean playerPoklmonVisible)
    {
        this.playerPoklmonVisible = playerPoklmonVisible;
    }
	
	public void update(float delta)
	{
		
	}
	
}
