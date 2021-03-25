package com.broll.poklmon.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
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
