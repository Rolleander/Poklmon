package com.broll.poklmon.battle.player;

import java.util.ArrayList;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.selection.SelectionBox;
import com.broll.poklmon.gui.selection.SelectionBoxListener;
import com.broll.poklmon.menu.state.PoklmonStateMenu;

public abstract class PoklmonTeamDialog {

	protected DataContainer data;
	protected BattleManager manager;
	protected int selection;
	protected boolean canCancel;
	protected String[] actions;
	protected boolean isVisible;
	protected PoklmonSelectionRender selectionRender;
	protected PoklmonStateMenu stateMenu;
	protected SelectionBox selectionBox;
	

	public PoklmonTeamDialog(DataContainer data, BattleManager manager) {
		this.data = data;
		this.manager = manager;
		selectionRender = new PoklmonSelectionRender(data, manager);
		stateMenu = new PoklmonStateMenu(data);
	}

	protected void open(boolean canCanel) {
		this.canCancel = canCanel;
		selectionBox = null;
		selection = 0;
		isVisible = true;
	}

	protected abstract void cancelDialog();

	protected abstract void openendSelectionBox(FightPoklmon poklmon);

	protected abstract void clickedSelection(int nr, FightPoklmon poklmon);

	protected void showPoklmonStatus(FightPoklmon poklmon) {
		PlayerPoklmon playerPoklmon = (PlayerPoklmon) poklmon;
		stateMenu.open(playerPoklmon.getPoklmonData());
	}

	public void closeDialog()
	{
		isVisible=false;
	}
	
	public void render(Graphics g) {
		if (isVisible) {
			data.getGraphics().getMenuGraphicsContainer().getMenuBackground().draw();
			ArrayList<FightPoklmon> team = manager.getParticipants().getPlayerTeam();
			selectionRender.render(g, team, selection);
			if (selectionBox != null) {
				selectionBox.render(g);
			}
			stateMenu.render(g);
		}
	}

	public void update() {
		if (isVisible) {
			if (stateMenu.isVisible()) {

				stateMenu.update();
				if (GUIUpdate.isCancel()) {
					stateMenu.close();
				}
			} else {

				if (selectionBox != null) {
					selectionBox.update();
				} else {
					if (canCancel && GUIUpdate.isCancel()) {
						// cancel selection
						isVisible = false;
						cancelDialog();
					}

					if (GUIUpdate.isClick()) {

						int[] pos = selectionRender.getSelectPos(selection);
						selectionBox = new SelectionBox(data,actions, pos[0] + 350, pos[1] + 250,false);
						selectionBox.setListener(new Listener());
						FightPoklmon poklmon = manager.getParticipants().getPlayerTeam().get(selection);
						openendSelectionBox(poklmon);
					}
					updateCursor();
				}

			}
		}

	}

	private class Listener implements SelectionBoxListener {

		@Override
		public void cancelSelection() {
			selectionBox = null;
		}

		@Override
		public void select(int item) {
			FightPoklmon poklmon = manager.getParticipants().getPlayerTeam().get(selection);
			clickedSelection(item, poklmon);
		}

	}

	private void updateCursor() {

		int teamSize = manager.getParticipants().getPlayerTeam().size();

		// move selection
		if (GUIUpdate.isMoveDown()) {
			int s = selection + 2;
			if (s < teamSize) {
				selection = s;
			}
		}
		if (GUIUpdate.isMoveUp()) {
			int s = selection - 2;
			if (s >= 0) {
				selection = s;
			}
		}
		if (GUIUpdate.isMoveLeft()) {
			int s = selection - 1;
			if (s >= 0) {
				selection = s;
			}
		}
		if (GUIUpdate.isMoveRight()) {
			int s = selection + 1;
			if (s < teamSize) {
				selection = s;
			}
		}

	}
}
