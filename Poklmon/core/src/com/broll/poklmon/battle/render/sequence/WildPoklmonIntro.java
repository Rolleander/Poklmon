package com.broll.poklmon.battle.render.sequence;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.battle.util.message.BattleMessages;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;

public class WildPoklmonIntro extends SequenceRender {
	private float x;
	private float y;

	public WildPoklmonIntro(BattleManager battle) {
		super(battle);

	}

	@Override
	protected void start() {
		x = 1000;
		y = 200;
		showText = true;
	}

	@Override
	public void render(Graphics g) {
		if (x > 640) {
			Image image = battle.getParticipants().getEnemy().getImage().getScaledCopy(2).getFlippedCopy(true, false);
			image.draw(x - image.getWidth() / 2, y - image.getHeight() / 2, ColorUtil.newColor(0, 0, 0));
		}
	}

	private boolean showText = true;

	@Override
	public void update(float delta) {
		if (x > 640) {
			x -= 5;
		} else {
			if (showText) {
				showText = false;
				// show text
				battle.getBattleRender().getPoklmonRender().setEnemyPoklmonVisible(true);
				String poklmon = battle.getParticipants().getEnemy().getName();
				String text = BattleMessages.putName(BattleMessages.wildPoklmonIntro, poklmon);
				battle.getBattleRender().getHudRender().showText(text, new SelectionListener() {

					@Override
					public void selectionDone() {
						stop();
					}
				});
			}
		}
	}

}
