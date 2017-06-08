package com.broll.poklmon.main;

import com.badlogic.gdx.Screen;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.TouchIconsRender;

public abstract class GameState implements Screen {

	protected DataContainer data;
	protected PoklmonGame game;
	protected GameStateManager states;
	protected Graphics graphics;
	
	public void init(PoklmonGame game, GameStateManager states, Graphics graphics, DataContainer data) {
		this.data = data;
		this.game=game;
		this.states=states;
		this.graphics=graphics;
		this.data=data;
		onInit();
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		update(delta);
		render(graphics);
		if(PoklmonGame.TOUCH_MODE){
			TouchIconsRender.render(graphics);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

	public abstract void onInit();
	
	public abstract void onEnter();

	public abstract void onExit();

	public abstract void update(float delta);

	public abstract void render(Graphics g);
	
}
