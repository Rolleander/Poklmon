package com.broll.poklmon.player.control.impl;

import com.broll.poklmon.player.PlayerGameData;
import com.broll.poklmon.player.control.PokldexControlInterface;
import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;
import com.broll.poklmon.save.PokldexData;
import com.broll.poklmon.save.PokldexEntry;

public class PokldexControl implements PokldexControlInterface {

	private PokldexData pokldex;

	public PokldexControl(PlayerGameData data) {
		pokldex = data.getGameVariables().getPokldex();
	}

	@Override
	public boolean hasSeenPoklmon(int poklmonID) {

		return pokldex.getPokldex().containsKey(poklmonID);
	}

	@Override
	public boolean hasCachedPoklmon(int poklmonID) {

		return getCachedCount(poklmonID) > 0;
	}

	@Override
	public int getCachedCount(int poklmonID) {
		if (hasSeenPoklmon(poklmonID)) {
			return pokldex.getPokldex().get(poklmonID).getCacheCount();
		}
		return 0;
	}

	@Override
	public void foundNewPoklmon(int poklmonID) {
		if (!hasSeenPoklmon(poklmonID)) {
			String date = CaughtPoklmonMeasurement.getCaughtDayInfo();
			pokldex.getPokldex().put(poklmonID, new PokldexEntry(date));
		}
	}

	@Override
	public void cachedPoklmon(int poklmonID) {
		if (!hasSeenPoklmon(poklmonID)) {
			foundNewPoklmon(poklmonID);
		}
		PokldexEntry entry = pokldex.getPokldex().get(poklmonID);
		entry.setCacheCount(entry.getCacheCount() + 1);
	}

	@Override
	public int getDifferentPoklmonSeen() {
		return pokldex.getPokldex().size();
	}

	@Override
	public int getDifferentPoklmonCached() {
		int count = 0;
		for (PokldexEntry entry : pokldex.getPokldex().values()) {
			if (entry.getCacheCount() > 0) {
				count++;
			}
		}
		return count;
	}

	@Override
	public String getSeenTimestamp(int poklmonID) {
		if (hasSeenPoklmon(poklmonID)) {
			PokldexEntry entry = pokldex.getPokldex().get(poklmonID);
			return entry.getSeenDate();
		}
		return null;
	}

}
