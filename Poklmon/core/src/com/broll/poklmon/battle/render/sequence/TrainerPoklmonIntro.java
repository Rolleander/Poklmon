package com.broll.poklmon.battle.render.sequence;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.battle.util.message.BattleMessages;
import com.broll.poklmon.data.TextContainer;
import com.broll.poklmon.data.basics.Graphics;

public class TrainerPoklmonIntro extends SequenceRender {

	public TrainerPoklmonIntro(BattleManager battle) {
		super(battle);
		// TODO
	}

	private boolean waitForText = true;

	@Override
	protected void start() {
		 waitForText = true;
		String pokl=battle.getParticipants().getEnemy().getName();
		String name=battle.getParticipants().getEnemyName();
		String text = TextContainer.get("trainerPoklmonIntro",name,pokl);
			battle.getBattleRender().getHudRender().showText(text, new SelectionListener() {

				public void selectionDone() {
					//TODO ball werfen
					battle.getBattleRender().getPoklmonRender().setEnemyPoklmonVisible(true);
					waitForText = false;
				}
			});
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
