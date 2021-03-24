package com.broll.poklmon.player.control.impl;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.player.control.PoklmonControlInterface;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.PoklmonData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PoklmonControl implements PoklmonControlInterface {

	private ArrayList<PoklmonData> poklmons;
	private PokldexControl pokldexControl;
	private DataContainer data;

	public PoklmonControl(DataContainer data, Player player) {
		this.data = data;
		poklmons = player.getData().getPoklmons();
		pokldexControl = player.getPokldexControl();
	}

	@Override
	public void addNewPoklmon(PoklmonData poklmon) {
		int id = poklmon.getPoklmon();
		// set free pos (team or pc)
		poklmon.setTeamPlace(getNewTeamPlace());
		// add to pokldex
		pokldexControl.cachedPoklmon(id);
		// add to team
		poklmons.add(poklmon);
	}

	@Override
	public void releasePoklmon(PoklmonData poklmon) {
		poklmons.remove(poklmon);
	}

	@Override
	public HashMap<Integer, PoklmonData> getPoklmonsInTeam() {
		HashMap<Integer, PoklmonData> team = new HashMap<Integer, PoklmonData>();
		for (PoklmonData pokl : poklmons) {
			int teamPlace = pokl.getTeamPlace();
			if (teamPlace != PoklmonData.NOT_IN_TEAM) { // not a pc poklmon =>
														// add to team
				if (!team.containsKey(teamPlace)) { // place free
					team.put(teamPlace, pokl);
				}
			}
		}
		return team;
	}

	@Override
	public ArrayList<PoklmonData> getPoklmonsInPC() {
		ArrayList<PoklmonData> pc = new ArrayList<PoklmonData>();
		for (PoklmonData pokl : poklmons) {
			int teamPlace = pokl.getTeamPlace();
			if (teamPlace == PoklmonData.NOT_IN_TEAM) {
				pc.add(pokl);
			}
		}
		return pc;
	}

	@Override
	public HashMap<Integer, PoklmonData> switchTeamPositions(int pos1, int pos2) {

		HashMap<Integer, PoklmonData> team = getPoklmonsInTeam();
		PoklmonData pokl1 = team.get(pos1);
		PoklmonData pokl2 = team.get(pos2);

		if (pokl1 == null) {
			if (pokl2 != null) {
				pokl2.setTeamPlace(pos1);
			}
		} else {
			if (pokl2 == null) {
				pokl1.setTeamPlace(pos2);
			} else {
				pokl1.setTeamPlace(pos2);
				pokl2.setTeamPlace(pos1);
			}
		}

		return getPoklmonsInTeam();
	}

	@Override
	public void movePoklmonToPC(PoklmonData poklmon) {
		poklmon.setTeamPlace(PoklmonData.NOT_IN_TEAM);
	}

	@Override
	public void takePoklmonIntoTeam(int boxPlace, int teamPlace) {
		HashMap<Integer, PoklmonData> team = getPoklmonsInTeam();
		ArrayList<PoklmonData> pc = getPoklmonsInPC();
		// check if place is empty
		PoklmonData poklmon = null;

		if (boxPlace < pc.size()) {
			poklmon = pc.get(boxPlace);
		}

		if (!team.containsKey(teamPlace)) {
			// add
			team.put(teamPlace, poklmon);
		} else {
			// swap poklmons => remove the one from team
			PoklmonData swapPoklmon = team.get(teamPlace);

			if (poklmon != null) {
				int index = poklmons.indexOf(poklmon);
				int index2 = poklmons.indexOf(swapPoklmon);
				// swap
				Collections.swap(poklmons, index, index2);
			}
			movePoklmonToPC(swapPoklmon);
		}
		// set new place
		if (poklmon != null) {
			poklmon.setTeamPlace(teamPlace);
		}
	}

	private int getNewTeamPlace() {
		HashMap<Integer, PoklmonData> team = getPoklmonsInTeam();
		for (int i = 0; i < 6; i++) {
			if (!team.containsKey(i)) {
				return i;
			}
		}
		return PoklmonData.NOT_IN_TEAM;
	}

	@Override
	public List<PoklmonData> getAttackLearningTargets(int atk) {
		HashMap<Integer, PoklmonData> team = getPoklmonsInTeam();
		List<PoklmonData> targets = new ArrayList<PoklmonData>();
		for (int i = 0; i < 6; i++) {
			if (team.containsKey(i)) {
				PoklmonData pokl = team.get(i);
				if (canLearnAttack(pokl, atk)) {
					targets.add(pokl);
				}
			}
		}
		return targets;
	}

	@Override
	public boolean canLearnAttack(PoklmonData poklmon, int atk) {
		// check if already learned
		AttackData[] attacks = poklmon.getAttacks();
		for (int i = 0; i < 4; i++) {
			if (attacks[i] != null) {
				if (attacks[i].getAttack() == atk) {
					// already learned
					return false;
				}
			}
		}
		// check type
		int poklId = poklmon.getPoklmon();
		Poklmon pokl = data.getPoklmons().getPoklmon(poklId);
		Attack attack = data.getAttacks().getAttack(atk);
		if (attack.getElementType() == pokl.getBaseType() || attack.getElementType() == pokl.getSecondaryType()) {
			return true;
		}
		// check learn list
		return pokl.getAttackList().getTeachableAttacks().contains(atk);
	}

}
