package com.broll.poklmon.newgame;

import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.dialog.DialogBox;
import com.broll.poklmon.gui.input.NameInputField;
import com.broll.poklmon.gui.input.NameInputListener;
import com.broll.poklmon.gui.selection.SelectionBox;
import com.broll.poklmon.gui.selection.SelectionBoxListener;

public class NewgameGUI {

	private DataContainer data;
	private DialogBox dialogBox ;
	private NewgameProcess process;
	private SelectionBox selectionBox;
	private EventDisplay eventDisplay;
	private NameInputField nameInput ;

	public NewgameGUI(DataContainer data, NewgameProcess process) {
		dialogBox=new DialogBox(data);
		nameInput=new NameInputField(data);
		this.data = data;
		this.process = process;
		dialogBox.setStyle(DialogBox.STYLE_PLAIN);
	//	dialogBox.setTextSpeed(DialogBox.TEXT_FASTEST);
		eventDisplay = new EventDisplay(data);
	}

	private boolean acceptedText = false;

	public void showText(String text) {
		acceptedText = false;
		dialogBox.showMessage(text, new SelectionListener() {
			@Override
			public void selectionDone() {
				if (acceptedText == false) {
					acceptedText = true;
					process.resume();
				}
			}
		});
	}

	public void showSelection(String[] items) {

		int x = 800;
		int y = (600 - 157);
		selectionBox = new SelectionBox(data,items, x, y,false);
		selectionBox.setListener(new SelectionBoxListener() {
			@Override
			public void select(int item) {
				process.setSelectedAnswer(item);
				process.resume();
				selectionBox = null;
			}

			@Override
			public void cancelSelection() {

			}
		});
	}

	public void render(Graphics g) {

		eventDisplay.render(g);
		dialogBox.render(g);
		if (selectionBox != null) {
			selectionBox.render(g);
		}
		nameInput.render(g);
	}

	public void update(float delta) {
		if (selectionBox != null) {
			selectionBox.update();
		}
		dialogBox.update();
	}

	public void openNameInput(String t) {
		nameInput.openNameInput(t, new NameInputListener() {
			public void inputName(String name) {
				process.setInputName(name);
				process.resume();
			}
		});
	}

	public void keyPressed(int key, char c) {
		nameInput.keyPressed(key, c);
	}

	public void showInfo(String text) {
		dialogBox.showMessage(text, null);
		dialogBox.setMaxLetters();
	}

	public EventDisplay getEventDisplay() {
		return eventDisplay;
	}

	public DataContainer getData() {
		return data;
	}

	public void setTextEnd() {
		dialogBox.setMaxLetters();
	}
}
