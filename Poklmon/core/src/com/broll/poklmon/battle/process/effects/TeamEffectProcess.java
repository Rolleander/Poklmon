package com.broll.poklmon.battle.process.effects;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.field.PoklmonTeamEffect;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.poklmon.WildPoklmon;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.message.BattleMessages;

public class TeamEffectProcess extends BattleProcessControl {

	public TeamEffectProcess(BattleManager manager, BattleProcessCore handler) {
		super(manager, handler);
	}

	public void preRound() {

		tryRemoveEffect(TeamEffect.REFLECTOR, 5);
		tryRemoveEffect(TeamEffect.ENERGYBLOCK, 5);
	    tryRemoveEffect(TeamEffect.LUCKBARRIERE, 5);
	}

	public void postRound() {

	}

	public void tryRemoveEffect(TeamEffect effect, int minRounds) {
		FieldEffects fieldEffects = manager.getFieldEffects();
		PoklmonTeamEffect team;
		FightPoklmon pokl;
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				pokl = manager.getParticipants().getPlayer();
				team = fieldEffects.getTeamEffects(pokl);
			} else {
				pokl = manager.getParticipants().getEnemy();
				team = fieldEffects.getTeamEffects(pokl);
			}
			if (team.hasTeamEffect(effect)) {
				if (team.getTeamEffectDuration(effect) >= minRounds) {
					removeTeamEffect(pokl, effect);
				}
			}
		}
	}

	public void addTeamEffect(FightPoklmon user, TeamEffect teamEffect) {
		FieldEffects fieldEffects = manager.getFieldEffects();
		PoklmonTeamEffect team = fieldEffects.getTeamEffects(user);
		if (!team.hasTeamEffect(teamEffect)) {
			// add effect
			team.addTeamEffect(teamEffect);
			// show message
			String name = getInvokerName(user);
			String text = teamEffect.getEntryName();
			if (text != null) {
				text = BattleMessages.putName(text, name);
				showText(text);
			}
		}
	}

	public void removeTeamEffect(FightPoklmon poklmon, TeamEffect teamEffect) {
		FieldEffects fieldEffects = manager.getFieldEffects();
		PoklmonTeamEffect team = fieldEffects.getTeamEffects(poklmon);
		if (team.hasTeamEffect(teamEffect)) {
			team.removeTeamEffect(teamEffect);
			String name = getInvokerName(poklmon);
			String text = teamEffect.getExitName();
			if (text != null) {
				text = BattleMessages.putName(text, name);
				showText(text);
			}
		}
	}

	private String getInvokerName(FightPoklmon user) {
		if (user instanceof PlayerPoklmon) {
			return manager.getParticipants().getPlayerName() + " Team";
		} else if (user instanceof WildPoklmon) {
			return user.getName();
		} else {
			return manager.getParticipants().getEnemyName() + " Team";
		}
	}

}
