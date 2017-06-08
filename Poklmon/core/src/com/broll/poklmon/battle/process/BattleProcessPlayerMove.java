package com.broll.poklmon.battle.process;

import java.util.ArrayList;
import java.util.Collections;

import com.broll.pokllib.animation.Animation;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.calc.EscapeBattleCalculation;
import com.broll.poklmon.battle.player.ChangePoklmonDialog;
import com.broll.poklmon.battle.player.ChangePoklmonListener;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.process.effects.EffectProcessInflict;
import com.broll.poklmon.battle.render.BattleSequences;
import com.broll.poklmon.battle.util.BattleMove;
import com.broll.poklmon.battle.util.BattleMoveType;
import com.broll.poklmon.battle.util.PoklmonTeamCheck;
import com.broll.poklmon.battle.util.message.BattleMessages;
import com.broll.poklmon.game.items.callbacks.PoklmonEnterCallback;
import com.broll.poklmon.game.items.callbacks.PoklmonLeaveCallback;
import com.broll.poklmon.game.items.callbacks.PoklmonSwitchCallback;
import com.broll.poklmon.game.items.callbacks.XpReceiverCalculationCallback;
import com.broll.poklmon.network.NetworkException;

public class BattleProcessPlayerMove extends BattleProcessControl {
	private FightPoklmon nextPoklmon;
	private int escapeTries;
	private boolean catchedPoklmon;

	public BattleProcessPlayerMove(BattleManager manager, BattleProcessCore handler) {
		super(manager, handler);

	}

	public boolean isCatchedPoklmon() {
		return catchedPoklmon;
	}

	public void init(BattleMove playerMove) {
		catchedPoklmon = false;
		if (playerMove.getMoveType() == BattleMoveType.TRY_ESCAPE) {
			escapeTries++;
		} else {
			escapeTries = 1;
		}
	}

	public void doSpecialMove(BattleMove move) throws NetworkException {
		catchedPoklmon = false;
		switch (move.getMoveType()) {
		case ATTACK: // cant occur, attack is no special move
			System.err.println("Undefined Battle Process: Attack is no special Move!");
			break;
		case CHANGE_POKLMON:
			FightPoklmon poklmon = move.getSwitchTo();
			switchToPoklmon(poklmon);
			break;

		case TRY_ESCAPE:
			showText(manager.getParticipants().getPlayerName() + " versucht zu fl�chten!");

			if (EscapeBattleCalculation.canEscape(manager.getParticipants().getPlayer(),
					manager.getParticipants().getEnemy(), escapeTries)) {
				manager.getData().getSounds().playSound("b_escape_battle");
				showText("Du bist gefl�chtet!");
				core.escapeBattle();
			} else {
				showText("Flucht gescheitert!");
			}
			break;

		case USE_ITEMS:
			core.getItemProcess().useItem(move.getItemId(), manager.getParticipants().getPlayerName(),
					move.getItemTarget());
			if (core.getItemProcess().isCatchedPoklmon()) {
				// escape battle
				catchedPoklmon = true;
			}
			break;
		}
	}

	public void connectionWaiting() {
		showInfo("Warten auf Gegner...");
	}

	public void connectionLost(String message) {
		manager.getBattleRender().getHudRender().setShowHud(false);
		showText("Connection lost!");
		showText("Network Error: " + message);
	}

	private void switchToPoklmon(FightPoklmon poklmon) throws NetworkException {
		FightPoklmon oldPoklmon = manager.getParticipants().getPlayer();
		String oldName = oldPoklmon.getName();
		String text = BattleMessages.putName(BattleMessages.playerPoklmonOutro, oldName);
		showText(text);
		manager.getData().getSounds().playSound("b_swap_poklmon");
		// TODO show outro animation
		sendNextPoklmon(poklmon);
	}

	public void playerPoklmonIntro() {
		manager.getBattleRender().getBackgroundRender().setMoving(false);
		manager.getBattleRender().getHudRender().setShowHud(false);
		manager.getBattleRender().getPoklmonRender().setPlayerPoklmonVisible(false);
		manager.getBattleRender().getSequenceRender().showAnimation(BattleSequences.PLAYER_INTRO, processThreadHandler);
		waitForResume();

		if (manager.getParticipants().getPlayer().isHasPowerfulGenes()) {
			Animation showAnimation = manager.getData().getAnimations()
					.getAnimation(EffectProcessInflict.STRONG_GENES_ANIMATION_ID);
			core.getAttackProcess().showOverlayAnimation(showAnimation, false);
		}
		manager.getBattleRender().getBackgroundRender().setMoving(true);
		manager.getBattleRender().getHudRender().setShowHud(true);
	}

	private void sendNextPoklmon(FightPoklmon poklmon) throws NetworkException {
		((PlayerPoklmon) poklmon).setUsedInBattle(true);
		// leave callback
		FightPoklmon oldPoklmon = manager.getParticipants().getPlayer();
		oldPoklmon.setFighting(false);
		for (CustomScriptCall script : manager.getScriptCalls()) {
			if (script instanceof PoklmonLeaveCallback) {
				((PoklmonLeaveCallback) script).call(oldPoklmon);
			}
		}
		// repalce positions in array
		ArrayList<FightPoklmon> team = manager.getParticipants().getPlayerTeam();
		int oldPos = team.indexOf(oldPoklmon);
		poklmon.setFighting(true);
		int newPos = team.indexOf(poklmon);
		// swawp
		Collections.swap(team, oldPos, newPos);
		// add enemy to new poklmons exp list
		((PlayerPoklmon) poklmon).addEXPSource(manager.getParticipants().getEnemy());

		// set new poklmon
		manager.getParticipants().changePlayerPoklmon(poklmon);
		// show intro animation
		playerPoklmonIntro();
		// enter callback
		for (CustomScriptCall script : manager.getScriptCalls()) {
			if (script instanceof PoklmonSwitchCallback) {
				((PoklmonSwitchCallback) script).call(oldPoklmon,poklmon);
			}
		}
		for (CustomScriptCall script : manager.getScriptCalls()) {
			if (script instanceof PoklmonEnterCallback) {
				((PoklmonEnterCallback) script).call(poklmon);
			}
		}
	}

	private void openTeamDialog(boolean canCancelSelection, ChangePoklmonListener changePoklmonListener) {
		ChangePoklmonDialog dialog = manager.getPlayerMoveSelection().getChangePoklmonDialog();
		dialog.open(canCancelSelection, changePoklmonListener);
	}

	public void showPoklmonChangeDialog(boolean forcedSelection) throws NetworkException {
		String text = BattleMessages.chooseNextPoklmon;
		showText(text);
		showInfo(text);
		// open select dialog
		openTeamDialog(!forcedSelection, new ChangePoklmonListener() {
			@Override
			public void changePoklmon(FightPoklmon poklmon) {
				nextPoklmon = poklmon;
				core.resume();
			}
		});
		waitForResume();
		if (nextPoklmon != null) {
			// send to other player the change
			if (core.isNetworkBattle()) {
				core.getNetworkEndpoint()
						.send(NetworkUtil.convert(manager.getParticipants(), new BattleMove(nextPoklmon)));
			}
			sendNextPoklmon(nextPoklmon);
		}
	}

	public void nextPoklmon() throws NetworkException {
		int okPoklNum = PoklmonTeamCheck.getNumberOfUnfaintedPoklmons(manager.getParticipants().getPlayerTeam());
		if (okPoklNum > 1) {
			showPoklmonChangeDialog(true);
		} else {
			FightPoklmon lastPoklmon = PoklmonTeamCheck.getLivingPoklmon(manager.getParticipants().getPlayerTeam());

			// send to other player the change
			if (core.isNetworkBattle()) {
				core.getNetworkEndpoint()
						.send(NetworkUtil.convert(manager.getParticipants(), new BattleMove(lastPoklmon)));
			}
			// auto choose last living poklmon
			String text = BattleMessages.playerLastPoklmon;
			showText(text);
			sendNextPoklmon(lastPoklmon);
		}
	}

	public void playerLost() {
		// show player lost dialog
		if (core.isNetworkBattle()) {
			String enemyName = manager.getParticipants().getEnemyName();
			String text = BattleMessages.putName(BattleMessages.networkLost, enemyName);
			showText(text);
		} else {
			String playerName = manager.getParticipants().getPlayerName();
			String text = BattleMessages.putName(BattleMessages.playerLost, playerName);
			showText(text);
		}
	}

}
