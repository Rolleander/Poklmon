package com.broll.poklmon.main.states;

import com.broll.pokllib.animation.Animation;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.util.DebugProcessThreadHandler;
import com.broll.poklmon.battle.util.ProcessThreadHandler;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.debug.DebugPlayerFactory;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.save.GameData;
import com.broll.poklmon.save.PoklmonData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationDebugState extends GameState {




	private BattleManager battleManager;
	private DataContainer data;
	private ProcessThreadHandler debugProcess;
	private boolean mirrored = false;
	private int animationID = 0;

	public AnimationDebugState(DataContainer data) {
		this.data=data;
	}
	

	public void debugAnimation(int animationId) {
		this.animationID = animationId;
	}

	private void startAnimation() {
		Animation animation = data.getAnimations().getAnimation(animationID);
		battleManager.getBattleRender().getAttackAnimationRender().showAnimation(animation, mirrored,false, debugProcess);
	}


	@Override
	public void onEnter() {
		DebugPlayerFactory f = new DebugPlayerFactory(data);
		GameData dat = f.createDebugPlayer(0, 0, 0); // location irrelevant

		GameManager gameManager = new GameManager(data,null, null);
		gameManager.startGame(dat);
		gameManager.getPlayer().init(dat);
		battleManager = new BattleManager(data, gameManager);

		BattleParticipants parc = new BattleParticipants(false);
		PoklmonData playerpokl = null;
		DebugPlayerFactory dpf = new DebugPlayerFactory(data);

		playerpokl = dpf.createDebugPoklmon(15, 10);

		parc.addPlayerPoklmon(FightPokemonBuilder.createPlayerPoklmon(data, playerpokl));
		parc.addEnemyPoklmon(FightPokemonBuilder.createWildPoklmon(data, data.getPoklmons().getPoklmon(20), 10));

		battleManager.debugInit(parc);

		debugProcess = new DebugProcessThreadHandler(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mirrored = !mirrored;
				startAnimation();
			}
		});
		startAnimation();
	}

	@Override
	public void onExit() {
	}

	@Override
	public void update(float delta) {
		battleManager.update(delta);

		if (GUIUpdate.isMoveDown()) {
			animationID--;
			if (animationID < 0) {
				animationID = data.getAnimations().getNumberOfAnimations() - 1;
			}
			startAnimation();
		} else if (GUIUpdate.isMoveUp()) {
			animationID++;
			if (animationID >= data.getAnimations().getNumberOfAnimations()) {
				animationID = 0;
			}
			startAnimation();
		}

		if (GUIUpdate.isMoveLeft()) {
			animationID -= 10;
			if (animationID < 0) {
				animationID += data.getAnimations().getNumberOfAnimations();
			}
			startAnimation();
		} else if (GUIUpdate.isMoveRight()) {
			animationID += 10;
			if (animationID >= data.getAnimations().getNumberOfAnimations()) {
				animationID -= data.getAnimations().getNumberOfAnimations();
			}
			startAnimation();
		}
		GUIUpdate.consume();
	}

	@Override
	public void render(Graphics g) {
		battleManager.render(g);

		g.setColor(ColorUtil.newColor(255, 0, 0,1));
		String animName = animationID + ": " + data.getAnimations().getAnimation(animationID).getName();
		g.drawString(animName, 20, 20);
	}

	@Override
	public void onInit() {
	}

}
