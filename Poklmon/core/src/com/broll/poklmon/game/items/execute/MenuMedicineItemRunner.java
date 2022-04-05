package com.broll.poklmon.game.items.execute;

import com.broll.pokllib.attack.Attack;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.util.message.BattleMessages;
import com.broll.poklmon.data.TextContainer;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.items.MedicineItemScript;
import com.broll.poklmon.poklmon.PoklmonAttributeCalculator;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.PoklmonData;
import com.broll.poklmon.script.commands.VariableCommands;

public class MenuMedicineItemRunner extends MenuItemRunner implements MedicineItemScript {

	private PoklmonData target;
	private boolean canceled;
	private VariableCommands variableCommands;

	public MenuMedicineItemRunner(GameManager game) {
		super(game);
	}


	public void init(PoklmonData target) {
		this.target = target;
		this.variableCommands = new VariableCommands(game);
	}


	public boolean isCanceled() {
		return canceled;
	}

	private String getName() {
		String name = target.getName();
		if (name == null) {
			name = data.getPoklmons().getPoklmon(target.getPoklmon()).getName();
		}
		return name;
	}

	private int getMaxKp() {
		short kpDV = target.getDv()[0];
		short kpFP = target.getFp()[0];
		return PoklmonAttributeCalculator.getKP(data.getPoklmons().getPoklmon(target.getPoklmon()), target.getLevel(),
				kpDV, kpFP);
	}

	private void healPoklmon(int kp) {
		int before = target.getKp();
		target.setKp(before + kp);
		if (target.getKp() > getMaxKp()) {
			target.setKp(getMaxKp());
		}
		int after = target.getKp();
		int healed = after - before;
		if (target.getStatus() == MainFightStatus.FAINTED) {
			target.setStatus(null);
		}
		gui.showText(TextContainer.get("itemHeal",getName(),healed));
		process.waitForResume();
	}

	@Override
	public void heal(int kp) {
		healPoklmon(kp);
	}

	@Override
	public void healPercent(double percent) {
		healPoklmon((int) (getMaxKp() * percent));
	}

	@Override
	public boolean isFullHealth() {
		return target.getKp() >= getMaxKp();
	}

	@Override
	public boolean isFainted() {
		return target.getKp() == 0;
	}

	@Override
	public void cancel() {
		canceled = true;
	}

	@Override
	public VariableCommands getVariableCmd() {
		return variableCommands;
	}

	@Override
	public boolean hasStatusChange() {
		return target.getStatus() != null && target.getStatus() != MainFightStatus.FAINTED;
	}

	@Override
	public MainFightStatus getStatus() {
		return target.getStatus();
	}

	@Override
	public void healStatus() {
		if (hasStatusChange()) {
			// heal status
			String exit = target.getStatus().getExitName();
			gui.showText(BattleMessages.putName(exit, getName()));
			target.setStatus(null);
			process.waitForResume();
		}
	}

	@Override
	public boolean hasFullAp() {
		for (int i = 0; i < 4; i++) {
			if (target.getAttacks()[i] != null) {
				int id = target.getAttacks()[i].getAttack();
				if (id > -1) {
					int ap = target.getAttacks()[i].getAp();
					int maxAp = game.getData().getAttacks().getAttack(id).getDamage().getAp();
					if (ap < maxAp) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private int selectAttack() {
		AttackData[] atks = target.getAttacks();
		String[] select = new String[4];
		boolean[] blocked = new boolean[4];
		for (int i = 0; i < 4; i++) {
			if (atks[i] != null) {
				int id = atks[i].getAttack();
				if (id > -1) {
					int ap = atks[i].getAp();
					Attack attack = game.getData().getAttacks().getAttack(id);
					int maxAp = attack.getDamage().getAp();
					String name = attack.getName();
					select[i] = name + " [" + ap + "/" + maxAp + "]";
					continue;
				}
			}
			select[i] = "-";
			blocked[i] = true;
		}
		gui.showText(TextContainer.get("item_Ap_Select"));
		process.waitForResume();
		gui.showSelection(select, blocked, false, false);
		process.waitForResume();
		return gui.getSelectedOption();
	}

	@Override
	public void giveApToSingleAttack(int apPlus) {
		int id = selectAttack();
		int maxAp = game.getData().getAttacks().getAttack(target.getAttacks()[id].getAttack()).getDamage().getAp();
		int ap = target.getAttacks()[id].getAp();
		byte newAp = (byte) Math.min(maxAp, ap + apPlus);
		target.getAttacks()[id].setAp(newAp);
		gui.showText( TextContainer.get("itemAp",getName()));
		process.waitForResume();
	}

	@Override
	public void giveFullApToSingleAttack() {
		giveApToSingleAttack(Byte.MAX_VALUE);
	}

	@Override
	public void giveApToAll(int apPlus) {
		for (int id = 0; id < 4; id++) {
			if(target.getAttacks()[id]!=null){
				if(target.getAttacks()[id].getAttack()>-1){
					int maxAp = game.getData().getAttacks().getAttack(target.getAttacks()[id].getAttack()).getDamage().getAp();
					int ap = target.getAttacks()[id].getAp();
					byte newAp = (byte) Math.min(maxAp, ap + apPlus);
					target.getAttacks()[id].setAp(newAp);
				}
			}	
		}
		gui.showText( TextContainer.get("itemAp",getName()));
		process.waitForResume();
	}

	@Override
	public void giveFullApToAll() {
		giveApToAll(Byte.MAX_VALUE);
	}

}
