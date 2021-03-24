package com.broll.poklmon.battle.player;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.data.DataContainer;

public class ChangePoklmonDialog extends PoklmonTeamDialog {

	private ChangePoklmonListener listener;

	public ChangePoklmonDialog(DataContainer data, BattleManager manager) {
		super(data, manager);
		actions = new String[] { "Tausch", "Bericht" };
	}

	public void open(boolean canCanel, ChangePoklmonListener listener) {
		this.listener = listener;
		super.open(canCanel);
	}

	@Override
	protected void cancelDialog() {
		listener.changePoklmon(null);
	}

	@Override
	protected void openendSelectionBox(FightPoklmon poklmon) {
		// cant switch to the same poklmon
		selectionBox.blockItem(0, poklmon.isFainted() || manager.getParticipants().getPlayer() == poklmon);
	}

	@Override
	protected void clickedSelection(int nr, FightPoklmon poklmon) {
		switch (nr) {
		case 0: // switch
			if (!poklmon.isFainted()) {
				if (poklmon != manager.getParticipants().getPlayer()) {
					closeDialog();
					listener.changePoklmon(poklmon);
				}
			}
			break;

		case 1:
			showPoklmonStatus(poklmon);
			break;
		}
	}

}
