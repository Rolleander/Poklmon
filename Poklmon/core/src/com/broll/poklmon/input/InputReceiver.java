package com.broll.poklmon.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.TouchIconsRender;

public class InputReceiver extends InputAdapter {

	private CharReceiver receiver;
	private KeyPressReceiver keyPressReceiver;
	private int lastKey;
	private Viewport viewport;

	public InputReceiver(Viewport viewport){
		this.viewport=viewport;
	}

	public void setCharReceiver(CharReceiver receiver) {
		this.receiver = receiver;
	}
	
	public void setKeyPressReceiver(KeyPressReceiver keyPressReceiver) {
		this.keyPressReceiver = keyPressReceiver;
	}

	private void convertTouch(ObjectDirection dir, boolean down){
		if(down) {
			switch (dir) {
				case DOWN:
					GUIUpdate.keyDown(Input.Keys.S);
					break;
				case UP:
					GUIUpdate.keyDown(Input.Keys.W);
					break;
				case LEFT:
					GUIUpdate.keyDown(Input.Keys.A);
					break;
				case RIGHT:
					GUIUpdate.keyDown(Input.Keys.D);
					break;
			}
		}else {
			GUIUpdate.keyReleaesed(Input.Keys.S);
			GUIUpdate.keyReleaesed(Input.Keys.D);
			GUIUpdate.keyReleaesed(Input.Keys.A);
			GUIUpdate.keyReleaesed(Input.Keys.W);
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(PoklmonGame.TOUCH_MODE) {
			Vector2 newPoints = new Vector2(screenX, screenY);
			Vector2 location = viewport.unproject(newPoints);
			int code=TouchIconsRender.checkButtonPress(location);
			if(code!=-1)
			{
				GUIUpdate.keyDown(code);
			}
			else
			{
				convertTouch(ScreenLocationCalc.getTouchDir(location), true);
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(PoklmonGame.TOUCH_MODE) {
			Vector2 newPoints = new Vector2(screenX, screenY);
			Vector2 location = viewport.unproject(newPoints);
			convertTouch(ScreenLocationCalc.getTouchDir(location), false);
		}
		return true;
	}

	@Override
	public boolean keyDown(int keycode) {
		lastKey=keycode;
		GUIUpdate.keyDown(keycode);
		if(keyPressReceiver!=null){
			keyPressReceiver.keyPressed(keycode);
		}
		return true;
	}
	

	@Override
	public boolean keyTyped(char character) {
		if(receiver!=null){
			receiver.typed(lastKey,character);
		}
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		GUIUpdate.keyReleaesed(keycode);
		return true;
	}
	
}
