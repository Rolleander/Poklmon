package com.broll.poklmon.gui;

import com.badlogic.gdx.Input.Keys;
import com.broll.poklmon.PoklmonGame;

public class GUIUpdate {

	private static boolean moveLeft, moveUp, moveDown, moveRight;
	private static boolean moveLeftPressed, moveUpPressed, moveDownPressed, moveRightPressed;
	private static boolean click, cancel, shortcuta, shortcutb;
	private static boolean enter, backspace;

	private static void consumeKey(int key, boolean down) {
		switch (key) {
		case Keys.A:
			moveLeft = down;
			moveLeftPressed = down;
			break;
		case Keys.D:
			moveRight = down;
			moveRightPressed = down;
			break;
		case Keys.S:
			moveDown = down;
			moveDownPressed = down;
			break;
		case Keys.W:
			moveUp = down;
			moveUpPressed = down;
			break;
		case Keys.O:
			click = down;
			break;
		case Keys.P:
			cancel = down;
			break;
		case Keys.ENTER:
			enter = down;
			break;
		case Keys.BACKSPACE:
			backspace = down;
			break;
		case Keys.K:
			shortcuta = down;
			break;
		case Keys.L:
			shortcutb = down;
			break;
		}
	}

	public static void keyDown(int key) {
		consumeKey(key, true);
	}

	public static void keyReleaesed(int key) {
		consumeKey(key, false);
	}

	public static String getKeyShortcutA() {
		if(PoklmonGame.TOUCH_MODE){
			return "1";
		}
		return "K";
	}

	public static String getKeyShortcutB() {
		if(PoklmonGame.TOUCH_MODE){
			return "2";
		}
		return "L";
	}

	public static void consume() {
		moveLeft = false;
		moveRight = false;
		moveUp = false;
		moveDown = false;
		click = false;
		cancel = false;
		shortcuta = false;
		shortcutb = false;
	}

	public static boolean isShortcutA() {
		return shortcuta;
	}

	public static boolean isShortcutB() {
		return shortcutb;
	}

	public static boolean isBackspace() {
		return backspace;
	}

	public static boolean isEnter() {
		return enter;
	}

	public static boolean isMoveLeft() {
		return moveLeft;
	}

	public static boolean isMoveUp() {
		return moveUp;
	}

	public static boolean isMoveDown() {
		return moveDown;
	}

	public static boolean isMoveRight() {
		return moveRight;
	}

	public static boolean isClick() {
		return click;
	}

	public static boolean isCancel() {
		return cancel;
	}

	public static boolean isMoveDownPressed() {
		return moveDownPressed;
	}

	public static boolean isMoveLeftPressed() {
		return moveLeftPressed;
	}

	public static boolean isMoveRightPressed() {
		return moveRightPressed;
	}

	public static boolean isMoveUpPressed() {
		return moveUpPressed;
	}

}
