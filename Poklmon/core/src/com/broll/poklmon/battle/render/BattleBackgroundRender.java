package com.broll.poklmon.battle.render;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.data.BattleGraphicsContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.map.areas.AreaType;

public class BattleBackgroundRender {

	private Image background, sky;
	private float moveY;
	private float angle;
	private static float moveYmax = 15;
	private boolean moving = false;
	private BattleManager battleManager;
	public BattleBackgroundRender(BattleManager battleManager) {
		this.battleManager=battleManager;
	}
	
	public void init(){
		AreaType area = battleManager.getParticipants().getAreaType();
		BattleGraphicsContainer grapihcs = battleManager.getData().getGraphics().getBattleGraphicsContainer();
		switch (area) {
		case CAVE:
			background = grapihcs.getCaveForeground();
			sky = grapihcs.getCaveBackground();
			break;
		
		case GRASS: // falltrough to default
		default:
			background = grapihcs.getBattleBackground();
			sky = grapihcs.getBattleSky();
			break;
		}
	}

	public void render(Graphics g) {
		g.drawImage(sky, 0, 0);
		background.draw(0, moveY + moveYmax);
	}

	public void update(float delta) {
		if (moving) {
			angle += 0.015f;
			moveY = (float) (Math.sin(angle) * moveYmax);
		}
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public float getMoveY() {
		return moveY;
	}

}
