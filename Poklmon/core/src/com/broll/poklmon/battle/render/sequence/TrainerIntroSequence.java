package com.broll.poklmon.battle.render.sequence;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.data.basics.Graphics;

public class TrainerIntroSequence extends SequenceRender {

	public TrainerIntroSequence(BattleManager battle) {
		super(battle);
		// TODO
	}

	private boolean waitForText = true;

	@Override
	protected void start() {
		 waitForText = true;
		String text = battle.getParticipants().getIntroText();
		if (text != null) {
			battle.getBattleRender().getHudRender().showText(text, new SelectionListener() {

				public void selectionDone() {
					waitForText = false;
				}
			});
		} else {
			waitForText = false;
		}
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void update(float delta) {
		if (!waitForText) {
			stop();
		}
	}

}
