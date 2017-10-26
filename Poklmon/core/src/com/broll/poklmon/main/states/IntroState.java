package com.broll.poklmon.main.states;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.resource.MenuGraphics;

public class IntroState extends GameState {

	public static int STATE_ID = 1;
	private DataContainer data;
	private Class<? extends GameState> followState;

	private float timer = 0;
	private float logoY = 1000;

	public IntroState(DataContainer data,Class<? extends GameState> followState) {
		this.data = data;
		this.followState=followState;
	}
	
	public void setFollowState(Class<? extends GameState> followState) {
		this.followState = followState;
	}

	@Override
	public void onInit() {
	}

	@Override
	public void onEnter() {
		timer = 0;
		data.getMusics().playMusic("intro.ogg", true);
	}

	@Override
	public void onExit() {
	}

	@Override
	public void update(float delta) {
		timer ++;
		if (logoY > 100) {
			logoY -= 7;
			MenuGraphics.logo.rotate(15);
		} else {
			MenuGraphics.logo.setRotation(0);
		}
		if (timer > 180) {
			states.transition(followState);
		}
	}

	@Override
	public void render(Graphics g) {
		MenuGraphics.background.draw();

		float scale = 1 + ((logoY - 100f) / 250f);
		MenuGraphics.logo.draw(150, logoY - 90,scale);
	}

}
