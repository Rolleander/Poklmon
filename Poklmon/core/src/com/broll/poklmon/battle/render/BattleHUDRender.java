package com.broll.poklmon.battle.render;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.render.hud.EnemyStateBar;
import com.broll.poklmon.battle.render.hud.PlayerStateBar;
import com.broll.poklmon.battle.util.ProcessThreadHandler;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.dialog.DialogBox;
import com.broll.poklmon.gui.input.NameInputField;
import com.broll.poklmon.gui.input.NameInputListener;
import com.broll.poklmon.gui.selection.SelectionBox;
import com.broll.poklmon.gui.selection.SelectionBoxListener;

public class BattleHUDRender {

	private BattleManager battle;
	private PlayerStateBar playerBar;
	private EnemyStateBar enemyBar;
	private DialogBox dialogBox ;
	private SelectionBox selectionBox;
	private boolean showHud = false, showText;
	private int selectedOption;
	private NameInputField nameInput ;
	private DataContainer data;

	public BattleHUDRender(BattleManager battleManager) {
		this.battle = battleManager;
		this.data=battle.getData();
		dialogBox=new DialogBox(data);
		dialogBox.setStyle(DialogBox.STYLE_BATTLE);
		dialogBox.setTextSpeed(DialogBox.TEXT_FASTEST);
		nameInput=new NameInputField(data);
		playerBar = new PlayerStateBar(data);
		enemyBar = new EnemyStateBar(data, battleManager);
	}

	public void init() {
		enemyBar.init(battle.getParticipants().getEnemyTeam());
	}

	private final static float TEXT_WAIT_SECONDS = 1.5f;

	public void render(Graphics g) {
		if (showHud) {
			playerBar.render(g, (PlayerPoklmon) battle.getParticipants().getPlayer());
			enemyBar.render(g, battle.getParticipants().getEnemy());
		}
		if (showText) {
			dialogBox.render(g);
			nameInput.render(g);
			if (selectionBox != null) {
				selectionBox.render(g);
			}
		}
	}

	public void keyPressed(int key, char c) {
		nameInput.keyPressed(key, c);
	}

	public void update(float delta) {
		if (showText) {
			if (selectionBox != null) {
				selectionBox.update();
			}
			dialogBox.update();
		}
	}

	public void showText(String text, SelectionListener selectionListener) {
		activatedText = false;
		dialogBox.showMessage(text, TEXT_WAIT_SECONDS, selectionListener);
		showText = true;
	}

	public void showTextWaiting(String text, SelectionListener selectionListener) {
		activatedText = false;
		dialogBox.showMessage(text, selectionListener);
		showText = true;
	}

	public void showInfo(String text) {
		dialogBox.showMessage(text, null);
		dialogBox.setMaxLetters();
		showText = true;
	}

	public void setShowHud(boolean showHud) {
		this.showHud = showHud;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
	}

	private boolean activatedText;

	public void showSelection(String[] selections, final ProcessThreadHandler processCore) {
		int x = 800;
		int y = (600 - 157);
		selectionBox = new SelectionBox(data,selections, x, y, false);
		selectionBox.setListener(new SelectionBoxListener() {
			@Override
			public void select(int item) {
				selectedOption = item;
				processCore.resume();
				selectionBox = null;
			}

			@Override
			public void cancelSelection() {

			}
		});
	}

	public void showCancelableSelection(String[] selections, final ProcessThreadHandler processCore) {
		int x = 800;
		int y = (600 - 157);
		selectionBox = new SelectionBox(data,selections, x, y, false);
		selectionBox.setListener(new SelectionBoxListener() {
			@Override
			public void select(int item) {
				selectedOption = item;
				processCore.resume();
				selectionBox = null;
			}

			@Override
			public void cancelSelection() {
				selectedOption = -1;
				processCore.resume();
				selectionBox = null;
			}
		});
	}

	public int getSelectedOption() {
		return selectedOption;
	}

	public String getInputText() {
		return nameInput.getName();
	}

	public void showInput(String defaultText, final ProcessThreadHandler processCore) {
		showText = true;
		nameInput.openNameInput(defaultText, new NameInputListener() {
			@Override
			public void inputName(String name) {
				processCore.resume();
			}
		});
	}

	public void showText(String text, final ProcessThreadHandler processCore) {
		activatedText = false;

		dialogBox.showMessage(text, TEXT_WAIT_SECONDS, new SelectionListener() {

			@Override
			public void selectionDone() {
				if (showText && !activatedText) {
					activatedText = true;
					processCore.resume();

				}
			}
		});
		showText = true;
	}

	public void showTextWaiting(String text, final ProcessThreadHandler processCore) {
		activatedText = false;

		dialogBox.showMessage(text, new SelectionListener() {

			@Override
			public void selectionDone() {
				if (showText && !activatedText) {
					activatedText = true;
					processCore.resume();

				}
			}
		});
		showText = true;
	}

}
