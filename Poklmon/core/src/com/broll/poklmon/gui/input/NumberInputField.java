package com.broll.poklmon.gui.input;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.info.InfoBox;

public class NumberInputField {

	public final static int CANCEL_VALUE=-9999999;
	private InfoBox numberBox;
	private int value, min, max;
	private NumberInputListener listener;
	private String info;
	private boolean cancelable = false;

	public NumberInputField(DataContainer data) {
		int x = 800;
		int y = (600 - 157);
		numberBox = new InfoBox(x, y, true);
		numberBox.setExtendUp(true);
	}

	private void updateGraphic() {
		numberBox.showText(info + " " + value);
	}

	private void changeNumber(int change) {
		value += change;
		if (value < min) {
			value = min;
		} else if (value > max) {
			value = max;
		}
		updateGraphic();
	}

	private void close() {
		numberBox.setVisible(false);
	}

	public void show(String info, int value, int min, int max, boolean cancelable, NumberInputListener listener) {
		this.info = info;
		this.listener = listener;
		this.value = value;
		this.max = max;
		this.min = min;
		this.cancelable = cancelable;
		updateGraphic();
	}

	public void update() {
		if (numberBox.isVisible()) {
			if (GUIUpdate.isMoveLeft()) {
				changeNumber(-1);
			} else if (GUIUpdate.isMoveRight()) {
				changeNumber(1);
			}
			if (GUIUpdate.isMoveDown()) {
				changeNumber(-10);
			} else if (GUIUpdate.isMoveUp()) {
				changeNumber(10);
			}
			if (GUIUpdate.isClick()) {
				close();
				listener.input(value);
			}
			if (cancelable && GUIUpdate.isCancel()) {
				close();
				listener.cancel();
			}
		}
	}

	public void render(Graphics g) {
		numberBox.render(g);
	}

}
