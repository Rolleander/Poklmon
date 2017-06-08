package com.broll.poklmon.battle.item;

import com.broll.pokllib.attack.Attack;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.message.BattleMessages;
import com.broll.poklmon.game.items.MedicineItemScript;

public class MedicineItemRunner extends BattleProcessControl implements MedicineItemScript {

	private FightPoklmon target;
	private boolean cancel;

	public MedicineItemRunner(BattleManager manager, BattleProcessCore handler) {
		super(manager, handler);
	}

	public void init(FightPoklmon target) {
		this.target = target;
		cancel = false;
	}

	public boolean isCancel() {
		return cancel;
	}

	private void healProcess(int kp) {
		int before = target.getAttributes().getHealth();
		core.getEffectProcess().getInflictprocess().healPoklmon(target,null, kp);
		int after = target.getAttributes().getHealth();
		String healNumber = "" + (after - before);
		showText(BattleMessages.putName(BattleMessages.itemHeal, target.getName(), healNumber));
	}

	@Override
	public void heal(int kp) {
		healProcess(kp);
	}

	@Override
	public void heal(double percent) {
		int kp = (int) (percent * target.getAttributes().getMaxhealth());
		healProcess(kp);
	}

	@Override
	public boolean isFullHealth() {
		return target.getAttributes().getHealthPercent() >= 1;
	}

	@Override
	public void cancel() {
		cancel = true;
	}

	@Override
	public boolean isFainted() {
		return target.getAttributes().getHealth() == 0;
	}

	@Override
	public boolean hasStatusChange() {
		return target.getStatusChanges().getMainStatus() != null
				&& target.getStatusChanges().getMainStatus() != MainFightStatus.FAINTED;
	}

	@Override
	public MainFightStatus getStatus() {
		return target.getStatusChanges().getMainStatus();
	}

	@Override
	public void healStatus() {
		if (hasStatusChange()) {
			// heal status
			core.getEffectProcess().getHealProcess().healMainChangeStatus(target);
		}
	}

	@Override
	public boolean hasFullAp() {
		for (int i = 0; i < 4; i++) {
			if (target.getAttacks()[i] != null) {
				int id = target.getAttacks()[i].getAttack().getId();
				if (id > -1) {
					int ap = target.getAttacks()[i].getAp();
					int maxAp = manager.getData().getAttacks().getAttack(id).getDamage().getAp();
					if (ap < maxAp) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private int selectAttack() {
		FightAttack[] atks = target.getAttacks();
		String[] select = new String[4];
		for (int i = 0; i < 4; i++) {
			if (atks[i] != null) {
				int id = atks[i].getAttack().getId();
				int ap = atks[i].getAp();
				Attack attack = manager.getData().getAttacks().getAttack(id);
				int maxAp = attack.getDamage().getAp();
				String name = attack.getName();
				select[i] = name + " [" + ap + "/" + maxAp + "]";
				continue;
			}
			select[i] = "-";
		}
		return showSelection("Welche Attacke soll aufgefüllt werden?", select);
	}

	@Override
	public void giveApToSingleAttack(int apPlus) {
		int id = selectAttack();
		if (target.getAttacks()[id] != null) {
			int maxAp = manager.getData().getAttacks().getAttack(target.getAttacks()[id].getAttack().getId())
					.getDamage().getAp();
			int ap = target.getAttacks()[id].getAp();
			byte newAp = (byte) Math.min(maxAp, ap + apPlus);
			target.getAttacks()[id].setAp(newAp);
			if (target instanceof PlayerPoklmon) {
				((PlayerPoklmon) target).getPoklmonData().getAttacks()[id].setAp(newAp);
			}
			showText(BattleMessages.putName(BattleMessages.itemAp, target.getName()));
		} else {
			cancel();
		}
	}

	@Override
	public void giveFullApToSingleAttack() {
		giveApToSingleAttack(Byte.MAX_VALUE);
	}

	@Override
	public void giveApToAll(int apPlus) {
		for (int id = 0; id < 4; id++) {
			if (target.getAttacks()[id] != null) {
				int maxAp = manager.getData().getAttacks().getAttack(target.getAttacks()[id].getAttack().getId())
						.getDamage().getAp();
				int ap = target.getAttacks()[id].getAp();
				byte newAp = (byte) Math.min(maxAp, ap + apPlus);
				target.getAttacks()[id].setAp(newAp);
				if (target instanceof PlayerPoklmon) {
					((PlayerPoklmon) target).getPoklmonData().getAttacks()[id].setAp(newAp);
				}
			}
		}
		showText(BattleMessages.putName(BattleMessages.itemAp, target.getName()));
	}

	@Override
	public void giveFullApToAll() {
		giveApToAll(Byte.MAX_VALUE);
	}

}
