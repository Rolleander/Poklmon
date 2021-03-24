package com.broll.poklmon.battle.render.sequence;

import com.broll.pokllib.poklmon.TypeCompare;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.data.basics.Graphics;

public class PoklmonDamageSequence extends SequenceRender {

	private FightPoklmon target;
	private int damage;
	private String text;
	private boolean waitForText = false;
	private int damageIntro;

	public PoklmonDamageSequence(BattleManager battle) {
		super(battle);
	}

	public void init(FightPoklmon target, int damage, String text) {
		this.target = target;
		this.damage = damage;
		this.text = text;
	}

	@Override
	protected void start() {
		if (damage > 0) {
			damageIntro = 20;
			String sound = "b_damage";
			// check for lethal
			if (TypeCompare.EFFECTIVE.getName().equals(text)) {
				// lethal
				sound = "b_damage_effective";
			} else if (TypeCompare.NOTEFFECTIVE.getName().equals(text)) {
				// big damage
				sound = "b_damage_ineffective";
			}
			data.getSounds().playSound(sound);
		} else {
			damageIntro = 0;
		}

	}

	@Override
	public void render(Graphics g) {
		if (damageIntro > 0) {
			if (damageIntro % 4 == 0) {
				hideTarget(true);
			} else {
				hideTarget(false);
			}
		}
	}

	private int wait = 0;

	private void tryStop() {
		if (!waitForText) {
			stop();
		}
	}

	private void hideTarget(boolean hide) {
		if (target == battle.getParticipants().getPlayer()) {
			battle.getBattleRender().getPoklmonRender().setPlayerPoklmonVisible(!hide);
		} else {
			battle.getBattleRender().getPoklmonRender().setEnemyPoklmonVisible(!hide);
		}
	}

	@Override
	public void update(float delta) {
		if (damageIntro >= 0) {
			if (damageIntro <= 0) {
				hideTarget(false);
				battle.getBattleRender().getHudRender().setShowHud(true);
				if (text != null) {
					battle.getBattleRender().getHudRender().showText(text, new SelectionListener() {

						public void selectionDone() {
							waitForText = false;
						}
					});
					waitForText = true;
				} else {
					waitForText = false;
				}
			}
			damageIntro--;
		} else {
			if (damage > 0) {
				if (wait > 0) {
					wait--;
				} else {
					damage--;
					target.doDamage(1);
					wait = 0;
					if (target.isFainted()) {
						tryStop();
					}
				}
			} else {
				tryStop();
			}
		}
	}

}
