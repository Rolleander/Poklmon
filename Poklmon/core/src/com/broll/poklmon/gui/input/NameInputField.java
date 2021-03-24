package com.broll.poklmon.gui.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.DialogBoxRender;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.resource.GUIFonts;

public class NameInputField {

	private int w, h;
	private int x, y;
	private DialogBoxRender box;
	private String name;
	private NameInputListener listener;
	private boolean visible;
	private int maxLength = 13;
	private DataContainer data;
	private boolean customInput = false;
	private FontUtils fontUtils=new FontUtils();

	public NameInputField(DataContainer data) {
		w = 450;
		this.data = data;
		h = 100;
		x = 400 - w / 2;
		y = 300 - h / 2;
		if(PoklmonGame.TOUCH_MODE){
			y-=150;
		}
		box = new DialogBoxRender(GUIDesign.selectionBoxBorder, GUIDesign.selectionBoxBorder2,
				GUIDesign.selectionBoxCorner, ColorUtil.newColor(248, 248, 248));
		box.setSize(x, y, w, h);
	}

	public void openNameInput(String name, NameInputListener listener) {
		if (name == null) {
			name = "";
		}
		if(PoklmonGame.TOUCH_MODE){
			Gdx.input.setOnscreenKeyboardVisible(true);
		}
		maxLength = 13;
		this.name = name;
		this.listener = listener;
		visible = true;
		customInput = false;
	}

	public void openCustomInput(String name, int length, NameInputListener listener) {
		openNameInput(name, listener);
		maxLength = length;
		customInput = true;
	}

	private int c = 0;

	public void render(Graphics g) {
		if (visible) {
			box.render(g);

			String n = name;
			String end = "";
			if (n.length() < maxLength) {
				if (c < 15) {
					end = "_";
				}
			}

			c++;
			if (c > 30) {
				c = 0;
			}
			g.setFont(GUIFonts.dialogText);
			g.setColor(ColorUtil.newColor(0, 0, 200));
			int sx = x + w / 2 - fontUtils.getWidth(GUIFonts.dialogText, n) / 2;
			int sy = y + h / 2 - fontUtils.getHeight(GUIFonts.dialogText, n) / 2;
			g.drawString(n, sx, sy);

			g.drawString(end, sx + fontUtils.getWidth(GUIFonts.dialogText, n), sy);
		}
	}

	public void keyPressed(int key, char c) {
		if (visible) {
			data.getSounds().playSound(GUIDesign.CLICK_SOUND);
			if (key == Keys.BACKSPACE) {
				if (name.length() > 0) {
					name = name.substring(0, name.length() - 1);
				}
			} else if (key == Keys.ENTER) {
				if (name.length() == 0 || name.isEmpty()) {
					name = null;
				}
				if(PoklmonGame.TOUCH_MODE){
					Gdx.input.setOnscreenKeyboardVisible(false);
				}
				listener.inputName(name);
				visible = false;
			} else {
				if (name.length() < maxLength) {
					if (key == Keys.SPACE) {
						name += " ";
					} else if (customInput) {
						name += c;
					} else {
						if (Character.isDigit(c) || Character.isAlphabetic(c)) {
							name += c;
						}
					}

				}
			}
		}
	}

	public String getName() {
		return name;
	}
}
