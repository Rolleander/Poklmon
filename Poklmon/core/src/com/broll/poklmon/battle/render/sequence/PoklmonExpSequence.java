package com.broll.poklmon.battle.render.sequence;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.poklmon.util.LevelCalcListener;
import com.broll.poklmon.poklmon.util.PoklmonLevelCalc;

public class PoklmonExpSequence extends SequenceRender {

	private PoklmonLevelCalc levelCalc;
	private int gainExp;
	private PlayerPoklmon poklmon;
	private boolean close = false;
	private int newLevel, evolvesTo, learnAttack;

	public PoklmonExpSequence(BattleManager battle) {
		super(battle);
	}

	public int getNewLevel() {
		return newLevel;
	}

	public int getEvolvesTo() {
		return evolvesTo;
	}

	public int getLearnAttack() {
		return learnAttack;
	}

	public void init(PoklmonLevelCalc levelCalc, int gainExp) {
		this.levelCalc = levelCalc;
		this.gainExp = gainExp;
		poklmon = (PlayerPoklmon) battle.getParticipants().getPlayer();
		levelCalc.setLevelCalcListener(new LevelCalcListener() {
			@Override
			public void newLevel(int level) {
				newLevel = level;
				close = true;
			}

			@Override
			public void evolvesTo(int poklmonID) {
				evolvesTo = poklmonID;
			}

			@Override
			public boolean canLearnAttack(int attack) {
				learnAttack = attack;
				return true;
			}
		});
	}

	@Override
	protected void start() {
		close = false;
		newLevel = -1;
		evolvesTo = -1;
		learnAttack = -1;
		// TODO
		// data.getSounds().playSound("exp_gain");
	}

	@Override
	public void render(Graphics g) {

	}

	public boolean isFinished() {
		return gainExp <= 0;
	}

	@Override
	public void update(float delta) {
		int min = poklmon.getMinExp();
		int max = poklmon.getMaxExp();
		int gainSpeed = (int) ((max - min) / 100f);
		if (gainSpeed < 1) {
			gainSpeed = 1;
		}
		int gain = 0;
		if (gainSpeed > gainExp) {
			// rest
			gain = gainExp;
		} else {
			gain = gainSpeed;
		}
		gainExp -= gain;
		levelCalc.addEXP(poklmon.getPoklmonData(), gain);
		poklmon.updateExp();
		if (gainExp == 0 || close) {
			stop();
		}
	}

}
