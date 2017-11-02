package com.broll.poklmon.game.scene.script.commands.subtasks;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.script.syntax.VariableException;
import com.broll.poklmon.data.TextContainer;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.script.CommandControl;
import com.broll.poklmon.game.scene.script.Invoke;
import com.broll.poklmon.game.scene.script.commands.PlayerCommands;
import com.broll.poklmon.poklmon.util.LevelCalcListener;
import com.broll.poklmon.poklmon.util.PoklmonAttackLearning;
import com.broll.poklmon.save.PoklmonData;

public class LevelUpHandler extends CommandControl implements LevelCalcListener {

	private PoklmonData poklmon;
	private PlayerCommands playerCommands;
	private PoklmonAttackLearning attackLearning;

	public LevelUpHandler(GameManager game, PlayerCommands playerCommands) {
		super(game);
		this.playerCommands = playerCommands;
		if (game != null) {
			attackLearning = new PoklmonAttackLearning(game.getData());
		}
	}

	public void initPoklmon(PoklmonData poklmon) {

		this.poklmon = poklmon;
	}

	@Override
	public void newLevel(final int level) {
		game.getData().getSounds().playSound("levelup");
		final String name = playerCommands.getPoklmonName(poklmon);
		invoke(new Invoke() {
			public void invoke() throws VariableException {
				game.getMessageGuiControl().showText(TextContainer.get("levelup",name,level));
			}
		});
	}

	@Override
	public boolean canLearnAttack(final int attack) {
		final String name = playerCommands.getPoklmonName(poklmon);
		final Attack atk = game.getData().getAttacks().getAttack(attack);
		if (attackLearning.tryLearnAttack(poklmon, attack)) {
			invoke(new Invoke() {
				public void invoke() throws VariableException {
					game.getMessageGuiControl().showText(TextContainer.get("dialog_LearnTM_Success",name,atk.getName()));
				}
			});
			return true;
		} else {
			boolean learning = true;
			while (learning) {

				invoke(new Invoke() {
					public void invoke() throws VariableException {
						game.getMessageGuiControl().showText(TextContainer.get("dialog_LearnTM_Try",name,atk.getName()));
					}
				});
				invoke(new Invoke() {
					public void invoke() throws VariableException {
						game.getMessageGuiControl().showSelection(new String[] { TextContainer.get("option_Yes"), TextContainer.get("option_No") });
					}
				});
				int selection = game.getMessageGuiControl().getSelectedOption();

				if (selection == 0) {
					final String[] atkNames = new String[4];
					for (int i = 0; i < 4; i++) {
						atkNames[i] = game.getData().getAttacks().getAttack(poklmon.getAttacks()[i].getAttack())
								.getName();
					}
					invoke(new Invoke() {
						public void invoke() throws VariableException {
							game.getMessageGuiControl().showText(TextContainer.get("dialog_LearnTM_ForgetWhat",atk.getName()));
						}
					});
					invoke(new Invoke() {
						public void invoke() throws VariableException {
							game.getMessageGuiControl()
									.showSelection(
											new String[] { TextContainer.get("dialog_LearnTM_Cancel"), atkNames[0], atkNames[1], atkNames[2],
													atkNames[3] });
						}
					});
					selection = game.getMessageGuiControl().getSelectedOption();
					if (selection > 0) {
						final int place = selection - 1;
						attackLearning.learnAttack(poklmon, attack, place);
						invoke(new Invoke() {
							public void invoke() throws VariableException {
								game.getMessageGuiControl().showText(TextContainer.get("dialog_LearnTM_Forget",name,atkNames[place]));
							}
						});
						invoke(new Invoke() {
							public void invoke() throws VariableException {
								game.getMessageGuiControl().showText(TextContainer.get("dialog_LearnTM_Success",name ,atk.getName()));
							}
						});
						learning = false;
						return true;
					}
				} else {
					learning = false;
				}
			}
		}
		return false;
	}

	@Override
	public void evolvesTo(int id) {
		final String name = playerCommands.getPoklmonName(poklmon);
		final Poklmon pokl = game.getData().getPoklmons().getPoklmon(id);
		final String name2 = pokl.getName();
		invoke(new Invoke() {
			public void invoke() throws VariableException {
				game.getMessageGuiControl().showText(TextContainer.get("evolved",name,name2));
			}
		});
		poklmon.setPoklmon(id);
	}
}
