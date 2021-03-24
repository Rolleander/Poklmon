package com.broll.poklmon.battle.player;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.data.DataContainer;

public class GiveItemDialog extends PoklmonTeamDialog {

	private UseItemListener itemListener;
	private int itemId;

	public GiveItemDialog(DataContainer data, BattleManager manager) {
		super(data, manager);

	}

	public void open(int itemId, UseItemListener listener) {
		this.itemId = itemId;
		String itemName = data.getItems().getItem(itemId).getName();
		actions = new String[] { itemName + " geben!", "Bericht" };
		this.itemListener = listener;
		super.open(true);
	}

	@Override
	protected void cancelDialog() {
		itemListener.useItem(-1, null);
	}

	@Override
	protected void openendSelectionBox(FightPoklmon poklmon) {

	}

	@Override
	protected void clickedSelection(int nr, FightPoklmon poklmon) {
		switch (nr) {
		case 0:
			itemListener.useItem(itemId, poklmon);
			closeDialog();
			break;
		case 1:
			showPoklmonStatus(poklmon);
			break;
		}
	}

}
