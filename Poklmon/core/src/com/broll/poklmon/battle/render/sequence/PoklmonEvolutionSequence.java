package com.broll.poklmon.battle.render.sequence;

import com.badlogic.gdx.Gdx;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;

public class PoklmonEvolutionSequence extends SequenceRender {

	private Image from, to;
	private String pokl, evo;
	private boolean intro, outro;
	private float time;
	private static int DURATION = 300;
	private float change;
	private FightPoklmon poklmon;

	public PoklmonEvolutionSequence(BattleManager battle) {
		super(battle);
	}

	public void setPoklmon(FightPoklmon poklmon) {
		this.poklmon = poklmon;
	}

	@Override
	protected void start() {
		battle.getBattleRender().getHudRender().setShowHud(false);
		float scale = 3;
		pokl = poklmon.getName();
		from = poklmon.getImage().getScaledCopy(scale);
		int evo = poklmon.getPoklmon().getEvolveIntoPoklmon();
		if (evo == -1) {
			stop();
			return;
		}
		this.evo = data.getPoklmons().getPoklmon(evo).getName();
		to = data.getGraphics().getPoklmonImage(data.getPoklmons().getPoklmon(evo).getGraphicName())
				.getScaledCopy(scale);
		intro = false;
		outro = false;
		battle.getBattleRender().getHudRender().showTextWaiting("Hey! " + pokl + " entwickelt sich!",
				new SelectionListener() {
					@Override
					public void selectionDone() {
						intro = true;
					}
				});
		change = 50;
	}

	@Override
	public void render(Graphics g) {

		Image back = data.getGraphics().getBattleGraphicsContainer().getEvolutionBackground();
		back.draw();
		float x = 400;
		float y = 260;
		float scale = ((float) (Math.sin(time / (change / 5)) * 0.1));
		Image ani = data.getGraphics().getBattleGraphicsContainer().getEvolutionAnimation()
				.getScaledCopy(1 + scale * 3);
		ani.drawCentered(x, y);
		scale += 1;
		if (time == 0) {
			from.draw(x - from.getWidth() / 2, y - from.getHeight() / 2);
		} else {
			x -= (from.getWidth() * scale) / 2;
			y -= (from.getHeight() * scale) / 2;
			if (time >= DURATION) {
				to.getScaledCopy(scale).draw(x, y);
			} else {
				Image img;
				if ((int) (time / change) % 2 == 0) {
					img = from;
				} else {
					img = to;
				}
				img.getScaledCopy(scale).draw(x, y, ColorUtil.newColor(0, 0, 0));
			}
		}
	}

	@Override
	public void update(float delta) {
		if (intro == true) {
			// update
			if (time < DURATION) {
				time += Gdx.graphics.getDeltaTime() +1;
				if (change > 10) {
					change -= .1;
				}
			} else {
				if (outro == false) {
					outro = true;
					battle.getBattleRender().getHudRender().showTextWaiting(
							"Glückwunsch! " + pokl + " hat sich in " + evo + " entwickelt!", new SelectionListener() {
								@Override
								public void selectionDone() {
									stop();
								}
							});
				}
			}
		}
	}

}
