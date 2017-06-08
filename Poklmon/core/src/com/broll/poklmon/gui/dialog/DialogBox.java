package com.broll.poklmon.gui.dialog;

import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.resource.GUIFonts;

public class DialogBox {
	public final static int STYLE_PLAIN = 0, STYLE_BATTLE = 1;
	public final static int TEXT_FASTEST = 0, TEXT_FAST = 1, TEXT_MIDDLE = 2, TEXT_SLOW = 3;
	private final static int VERTICAL_SPACE = 50;

	public final static int HEIGHT = 157;

	private int xpos, ypos;
	private int style = STYLE_PLAIN;
	private DialogTimer timer;
	private String[] lines;
	private boolean withTimer = false;
	private SelectionListener exitListener;
	private int letters;
	private int textWait = TEXT_MIDDLE;
	private float timerSeconds;
	private DataContainer data;
	private boolean clicked = false;

	public DialogBox(DataContainer data) {
		this.data = data;
		xpos = 0;
		int h = HEIGHT;
		ypos = 600 - h;
		timer = new DialogTimer(new TimerListener() {

			public void timerStopped() {
				clicked = true;
				closeMessage();
			}
		});
	}

	public void setTextSpeed(int textWait) {
		this.textWait = textWait;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public void showMessage(String message, SelectionListener listener) {
		clicked = false;
		timer.cancel();
		this.exitListener = listener;
		lines = MessageLineCutter.cutMessage(message);
		withTimer = false;
		letters = 0;
	}

	public void showInfo(String text1, String text2) {
		clicked = true;
		lines = new String[] { text1, text2 };
		withTimer = false;
		letters = text1.length() + text2.length();

	}

	public void showMessage(String message, float seconds, SelectionListener listener) {
		clicked = false;
		timer.cancel();
		this.exitListener = listener;
		lines = MessageLineCutter.cutMessage(message);
		timerSeconds = seconds;
		withTimer = true;
		letters = 0;
	}

	private void closeMessage() {
		// check if multi message
		int l = lines.length;
		if (l > 2) {
			data.getSounds().playSound(GUIDesign.CLICK_SOUND);

			// start next message
			String[] newLines = new String[l - 2];
			for (int i = 0; i < newLines.length; i++) {

				newLines[i] = lines[i + 2];
			}
			letters = 0;
			lines = newLines;
			if (withTimer) {
				timer.startAgain();
			}
		} else {
			if (exitListener != null) {
				if (!clicked) {
					data.getSounds().playSound(GUIDesign.CLICK_SOUND);
					clicked = true;
				}
				exitListener.selectionDone();

			}
		}
	}

	public void render(Graphics g) {
		if (style == STYLE_PLAIN) {
			GUIDesign.textbox.draw(xpos, ypos);
		} else {
			GUIDesign.textbox2.draw(xpos, ypos);
		}

		int x = xpos + 30;
		int y = ypos + 25;
		g.setFont(GUIFonts.dialogText);
		int lastl = 0;
		for (int i = 0; i < 2; i++) {
			if (lines != null) {

				if (i < lines.length) {

					String message = lines[i];

					int l = message.length();
					int s = letters - lastl;
					if (s < 0) {
						break;
					}
					if (s < l) {
						message = message.substring(0, s);
					}
					lastl = l;

					if (style == STYLE_PLAIN) {
						g.setColor(ColorUtil.newColor(50, 50, 50));
					} else {
						g.setColor(ColorUtil.newColor(50, 50, 50));
						g.drawString(message, x + 2, y + 2);
						g.setColor(ColorUtil.newColor(250, 250, 250));
					}
					g.drawString(message, x, y);
					y += VERTICAL_SPACE;

				}
			}
		}
	}

	private int getMaxLength() {
		int l = 0;
		if (lines != null) {
			l += lines[0].length();
			if (lines.length > 1) {
				l += lines[1].length();
			}
		}
		return l;
	}

	private int wait = 0;

	public void update() {

		wait++;
		if (wait > textWait) {
			if (letters < getMaxLength()) {
				letters++;
				if (letters == getMaxLength()) {
					if (withTimer) {
						timer.start(timerSeconds);
					}
				}
			}
			wait = 0;
		}

		if (GUIUpdate.isClick() || GUIUpdate.isCancel()) {
			if (letters == getMaxLength()) {
				closeMessage();
			}
		}
	}

	public void setMaxLetters() {
		letters = getMaxLength();
	}

}
