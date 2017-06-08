package com.broll.poklmon.transition;

import com.badlogic.gdx.Screen;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.main.GameState;

public abstract class ScreenTransition implements Screen{

	protected GameState currentState,nextState;
	private TransitionListener transitionListener;
	protected Graphics g;
	
	public void start(Graphics g,GameState currentState, GameState nextState, TransitionListener listener){
		this.transitionListener=listener;
		this.nextState=nextState;
		this.currentState=currentState;
		this.g=g;
	}
	
	protected void finish(){
		transitionListener.transitionFinished();
	}
	
	@Override
	public void render(float delta) {
		
	}
	
	@Override
	public void show() {
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

}
