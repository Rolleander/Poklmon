package com.broll.poklmon.battle.render.sequence;

import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.render.poklball.PoklballThrow;
import com.broll.poklmon.battle.util.AnimationEndListener;
import com.broll.poklmon.data.basics.Graphics;

public class CatchPoklmonSequence extends SequenceRender {

	private PoklballThrow ball;
	private boolean ballLanded = false;
	private int poklballIcon;
	private boolean catched;
	private int wobbleCount;

	private float groundX, groundY, landX;
	private float wobbleSin;
	private float wobbleWait;

	private final static int WOBBLE_WAIT = 15;

	public CatchPoklmonSequence(BattleManager battle) {
		super(battle);
		ball = new PoklballThrow(battle.getData());

	}

	public void init(int poklballIcon, boolean catched, int wobbleCount) {
		this.poklballIcon = poklballIcon;
		this.catched = catched;
		this.wobbleCount = wobbleCount;
	}

	@Override
	protected void start() {
		ball.setStartPos(0, 630);
		ball.setSpeed(7);
		ballLanded = false;
		data.getSounds().playSound("throw_poklball");
		ball.throwPoklball(poklballIcon, new AnimationEndListener() {
			@Override
			public void animationEnd() {
				ballLanded = true;
				groundX = ball.getX();
				landX = groundX;
				groundY = ball.getY();
				wobbleWait = WOBBLE_WAIT;
				data.getSounds().playSound("ball_wiggle_sound");
				battle.getBattleRender().getPoklmonRender().setEnemyPoklmonVisible(false);
			}
		});
		ball.setYchange(-18);
	}

	@Override
	public void render(Graphics g) {
		if (ballLanded) {
			ball.getBall().drawCentered(groundX, groundY);
		} else {
			ball.render(g);

		}
	}

	@Override
	public void update(float delta) {
		if (ballLanded) {
			if (wobbleWait > 0) {
				wobbleWait -= delta* PoklmonGame.FPS;
			} else {

				if (wobbleSin < Math.PI * 2) {
					groundX = (float) (landX + Math.sin(wobbleSin) * 10);
					ball.getBall().setRotation((float) Math.toDegrees(wobbleSin * 2));
					wobbleSin += 0.1;
				} else {
					wobbleCount--;
					wobbleSin = 0;
					wobbleWait = WOBBLE_WAIT;
					if (wobbleCount == 0) {
						if (catched) {
							data.getSounds().playSound("poklmon_catched");
						} else {
							data.getSounds().playSound("ball_escape");
							battle.getBattleRender().getPoklmonRender().setEnemyPoklmonVisible(true);
						}
						stop();
					} else {
						data.getSounds().playSound("ball_wiggle_sound");
					}
				}

			}

		} else {
			ball.update(delta);
		}
	}

}
