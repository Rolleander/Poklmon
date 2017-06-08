package com.broll.poklmon.transition;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.basics.ColorUtil;

public class WildBattleTransition extends ScreenTransition {

	private float time;
	private final static float blinkSpeed = 10;
	private final static float blinkTime = 40;
	private final static float animationTime = 70;
	private final static int rectSize=8;

	@Override
	public void render(float delta) {
		currentState.render(g);
		int w =800;
		int h =600;
		// blinken
		if (time < blinkTime) {
			float blink = (time % blinkSpeed) / blinkSpeed;
			Color c;
			if (time % (blinkSpeed * 2) == 0) {
				c = ColorUtil.newColor(0f, 0f, 0f, blink);
			} else {
				c = ColorUtil.newColor(1f, 1f, 1f, blink);
			}
			g.setColor(c);
			g.fillRect(0, 0, w, h);
		} else {
			// mosaic
			float percent = (time-blinkTime) / (animationTime - blinkTime);
			float width=percent*w;
			g.setColor(ColorUtil.newColor(0,0,0));
			for (int y = 0; y <= h; y += rectSize) {
				float x=0;
				if(y%(rectSize*2)==0)
				{
					x=w-width;
				}
				g.fillRect(x, y, width, rectSize);
			}
		}
		time+=delta*PoklmonGame.FPS;
		if(time>=animationTime){
			finish();
		}
	}

	

}
