package com.broll.poklmon.battle.render.sequence;

import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.AttributeChange;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;

public class PoklmonAttributeChangeAnimation extends SequenceRender {

	private FightPoklmon target;
	private Image targetImage;
	private int x;
	private boolean positive;
	private int strength;
	private Image arrow;
	private float arrowy;
	private float trans = 0;
	private String text;
	private boolean textRead = false;

	public PoklmonAttributeChangeAnimation(BattleManager battle) {
		super(battle);
	}

	public void init(FightPoklmon target, AttributeChange change, String text) {
		this.target = target;
		targetImage = target.getImage().getScaledCopy(2);
		if (target == battle.getParticipants().getEnemy()) {
			targetImage = targetImage.getFlippedCopy(true, false);
			x = 640;
			battle.getBattleRender().getPoklmonRender().setEnemyPoklmonVisible(false);
		} else {
			x = 160;
			battle.getBattleRender().getPoklmonRender().setPlayerPoklmonVisible(false);
		}
		strength = change.getStrength();
		positive = strength > 0;
		if (strength < 0) {
			strength *= -1;
		}
		int id = change.getAttribute().ordinal();
		arrow = battle.getData().getGraphics().getBattleGraphicsContainer().getAttributeArrows().getSprite(id, 0);
		if (!positive) {
			arrow = arrow.getFlippedCopy(false, true);
		}
		textRead = false;
		battle.getBattleRender().getHudRender().showText(text, new SelectionListener() {

			@Override
			public void selectionDone() {
				textRead = true;
			}
		});
	}

	@Override
	protected void start() {
		trans = 1;
		arrowy = 0;
		if(positive){
			 data.getSounds().playSound("b_upgrade");
		}
		else{
			 data.getSounds().playSound("b_downgrade");
		}
	}

	@Override
	public void render(Graphics g) {

		float yp = battle.getBattleRender().getBackgroundRender().getMoveY();
		float y = 200 + yp;

		targetImage.drawCentered(x, y);

		float ax = x - 32;
		Color c = ColorUtil.newColor(1f, 1f, 1f, trans);
		float ay = y - 32 + arrowy;

		for(int i=0; i<strength; i++)
		{
			arrow.draw(ax, ay, c);
			ay+=arrowy/2;
		}

	}

	@Override
	public void update(float delta) {

		float speed = 0.45f;
		if (positive) {
			arrowy -= speed;

		} else {

			arrowy += speed;
		}
		trans -= 0.01f;
		if (trans < 0) {
			trans = 0;
		}

		if (trans <= 0 && textRead) {
			if (target == battle.getParticipants().getEnemy()) {
				battle.getBattleRender().getPoklmonRender().setEnemyPoklmonVisible(true);
			} else {
				battle.getBattleRender().getPoklmonRender().setPlayerPoklmonVisible(true);
			}
			stop();

		}
	}

}
