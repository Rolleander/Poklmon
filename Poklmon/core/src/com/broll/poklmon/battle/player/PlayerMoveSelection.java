package com.broll.poklmon.battle.player;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.calc.StateEffectCalc;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.BattleMove;
import com.broll.poklmon.battle.util.ProcessThreadHandler;
import com.broll.poklmon.battle.util.message.BattleMessages;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.dialog.DialogBox;
import com.broll.poklmon.gui.selection.RectSelectionBox;
import com.broll.poklmon.resource.GUIDesign;

public class PlayerMoveSelection {

	private ProcessThreadHandler exitListener;

	private BattleMove nextMove;
	private boolean active = false;
	private DialogBox text;
	private BattleManager battle;
	private RectSelectionBox actionsBox;
	private AttackSelectionBox attacks;
	private boolean subMenuOpen = false;
	private ChangePoklmonDialog changePoklmonDialog;
	private SelectItemDialog itemDialog;
	private boolean canChooseAttack;

	public PlayerMoveSelection(BattleManager battleManager) {
		battle = battleManager;
		text = new DialogBox(battle.getData());
		changePoklmonDialog = new ChangePoklmonDialog(battleManager.getData(), battleManager);
		itemDialog = new SelectItemDialog(battleManager);
		text.setStyle(DialogBox.STYLE_BATTLE);
		actionsBox = new RectSelectionBox(battle.getData(), BattleMessages.actions, 430, 600 - 157, 370, 157);

		attacks = new AttackSelectionBox(battle.getData());
	}

	public void processInput(ProcessThreadHandler listener) {
		// disable escape when trainer battle		
		actionsBox.blockItem(3, battle.getParticipants().isTrainerFight());  
		// disable items in network battle
		actionsBox.blockItem(1, battle.isNetworkBattle());
		
		subMenuOpen = false;

		this.exitListener = listener;
		nextMove = null;
		active = true;
		// check if all ap is 0
		canChooseAttack = !battle.getParticipants().getPlayer().noApLeft();
		// show what do text
		String poklmon = battle.getParticipants().getPlayer().getName();
		String text = BattleMessages.putName(BattleMessages.commandInfo, poklmon);
		this.text.showInfo(text, BattleMessages.commandInfo2);
	}

	public void render(Graphics g) {
		if (active) {
			text.render(g);
			if (subMenuOpen == false) {
				actionsBox.render(g);
			}
			attacks.render(g);
		}
		changePoklmonDialog.render(g);
		itemDialog.render(g);
	}

	public void update(float delta) {
		changePoklmonDialog.update();
		itemDialog.update();
		attacks.update();
		if (active) {
			if (subMenuOpen == false) {

				if (GUIUpdate.isClick()) {
					battle.getData().getSounds().playSound(GUIDesign.CLICK_SOUND);
					switch (actionsBox.getSelectedIndex()) {
					case 0:
						// fight
						// open attack selection
						if (canChooseAttack) {
							subMenuOpen = true;
							attacks.open(battle.getParticipants().getPlayer(), new AttackSelectionListener() {
								@Override
								public void select(FightAttack attack) {
									if (attack == null) {
										//return to menu
										subMenuOpen = false;
									} else {
										nextMove = new BattleMove(attack);
										stop();
									}
								}
							});

						} else {
							// use verzweiflung attack, cause no more ap
							nextMove = new BattleMove(StateEffectCalc.getDesperationAttack());
							stop();
						}
						break;
					case 1:
						// items
						subMenuOpen = true;
						itemDialog.open(new UseItemListener() {
							@Override
							public void useItem(int itemId, FightPoklmon target) {
								if (itemId == -1) {
									// return to menu
									subMenuOpen = false;
								} else {
									// use Item
									actionsBox.setSelectedItem(0);
									nextMove = new BattleMove(target, itemId);
									stop();
								}
							}
						});

						break;
					case 2:
						// change poklmon
						subMenuOpen = true;
						changePoklmonDialog.open(true, new ChangePoklmonListener() {
							@Override
							public void changePoklmon(FightPoklmon poklmon) {
								if (poklmon == null) {
									// return to menu
									subMenuOpen = false;
								} else {
									actionsBox.setSelectedItem(0);
									// change poklmon
									nextMove = new BattleMove(poklmon);
									stop();
								}
							}
						});

						break;
					case 3:
						// try escape
						if (!battle.getParticipants().isTrainerFight()) {
							nextMove = new BattleMove();
							stop();
						} else {
							// cant escape trainerfights
							// TODO give feedback (maybe sound?)
						}
						break;
					}
				}
				actionsBox.update();
			}

		}
	}

	private void stop() {
		active = false;
		subMenuOpen = false;
		exitListener.resume();
	}

	public BattleMove getNextMove() {
		return nextMove;
	}

	public ChangePoklmonDialog getChangePoklmonDialog() {
		return changePoklmonDialog;
	}

	public void reset() {
		actionsBox.setSelectedItem(0);
	}
}
