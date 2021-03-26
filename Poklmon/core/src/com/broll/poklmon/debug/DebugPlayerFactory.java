package com.broll.poklmon.debug;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.poklmon.WildPoklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;
import com.broll.poklmon.poklmon.PoklmonDataFactory;
import com.broll.poklmon.save.GameData;
import com.broll.poklmon.save.GameVariables;
import com.broll.poklmon.save.ItemEntry;
import com.broll.poklmon.save.PlayerData;
import com.broll.poklmon.save.PokldexEntry;
import com.broll.poklmon.save.PoklmonData;

import java.util.ArrayList;
import java.util.HashMap;

public class DebugPlayerFactory {

	private DataContainer data;
	private PoklmonDataFactory poklmonFactory;

	public DebugPlayerFactory(DataContainer data) {
		this.data = data;
		poklmonFactory = new PoklmonDataFactory(data);
	}

	public GameData createDebugPlayer(int mapNr, int mapX, int mapY) {
		GameData data = new GameData();
		data.setPlayerData(createPlayer(mapNr, mapX, mapY));
		data.setPoklmons(createPoklmons());
		data.setVariables(new GameVariables());
		for (int i = 0; i < 10; i++) {
			data.getVariables().getPokldex().getPokldex()
					.put(i, new PokldexEntry(CaughtPoklmonMeasurement.getCaughtDayInfo()));
		}

		for (PoklmonData pokl : data.getPoklmons()) {
			PokldexEntry entry = new PokldexEntry(CaughtPoklmonMeasurement.getCaughtDayInfo());
			entry.setCacheCount(1);
			data.getVariables().getPokldex().getPokldex().put(pokl.getPoklmon(), entry);
		}
		data.getVariables().getInventar().setMoney(1500);
		HashMap<Integer, ItemEntry> items = data.getVariables().getInventar().getItems();
		for (int i = 0; i < this.data.getItems().getNumberOfItems(); i++) {
			ItemEntry entry = new ItemEntry();
			entry.setCount((int) (Math.random() * 10 + 1)+5);
			items.put(i, entry);
		}

		return data;
	}

	private PlayerData createPlayer(int mapNr, int mapX, int mapY) {
		PlayerData p = new PlayerData();
		p.setCharacter(0);
		p.setName("Timo Tester");
		p.setMapNr(mapNr);
		p.setView(0);
		p.setXpos(mapX);
		p.setYpos(mapY);
		return p;
	}

	private ArrayList<PoklmonData> createPoklmons() {
		ArrayList<PoklmonData> pokls = new ArrayList<PoklmonData>();
		for (int i = 0; i < 5; i++) {
			int level = (int) (Math.random() * 10 + 1);
			int pokl = (int) (data.getPoklmons().getNumberOfPoklmons() * Math.random());
			pokls.add(createDebugPoklmon(pokl, level));

		}
		int count=10 + (int) (Math.random() * 20);
	//	count=2000;
		addPcPoklmons(pokls, count);
		return pokls;
	}

	private int teamPlace = 0;

	private void addPcPoklmons(ArrayList<PoklmonData> pokls, int nr) {
		for (int i = 0; i < nr; i++) {
			int level = (int) (Math.random() * 96 + 5);
			level=50;
			int pokl = (int) (data.getPoklmons().getNumberOfPoklmons() * Math.random());
			PoklmonData p = createDebugPoklmon(pokl, level);
			p.setTeamPlace(PoklmonData.NOT_IN_TEAM);
			pokls.add(p);
		}

	}

	public PoklmonData createDebugPoklmon(int poklmonID, int level) {
		Poklmon poklmon = data.getPoklmons().getPoklmon(poklmonID);
		WildPoklmon pokl = FightPokemonBuilder.createWildPoklmon(data, poklmon, level);
		PoklmonData d = poklmonFactory.getPoklmon(pokl);
		d.setTeamPlace(teamPlace);
		teamPlace++;
		return d;
	}
}
