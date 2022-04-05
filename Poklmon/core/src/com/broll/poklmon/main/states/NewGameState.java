package com.broll.poklmon.main.states;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.input.CharReceiver;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.newgame.NewGameFactory;
import com.broll.poklmon.newgame.NewgameGUI;
import com.broll.poklmon.newgame.NewgameListener;
import com.broll.poklmon.newgame.NewgameProcess;
import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;
import com.broll.poklmon.resource.MenuGraphics;
import com.broll.poklmon.save.GameData;
import com.broll.poklmon.save.PokldexEntry;
import com.broll.poklmon.save.manage.SaveFileManager;

public class NewGameState extends GameState {

	public static int STATE_ID = 3;
	private DataContainer data;
	private NewgameGUI gui;
	private NewgameProcess process;

	public NewGameState(DataContainer data) {
		this.data = data;
	}

	@Override
	public void onInit() {
	}

	@Override
	public void onEnter() {
		game.getInput().setCharReceiver(new CharReceiver() {
			@Override
			public void typed(int keycode, char typedChar) {
				gui.keyPressed(keycode, typedChar);
			}
		});
		process = new NewgameProcess(new NewgameListener() {

			@Override
			public void finishedSelection(int character, String name, int starterPoklmonID, String poklmonName) {

				// create new gamedata
				GameData gamedata = new GameData();
				gamedata.setPlayerData(NewGameFactory.createNewPlayer(character, name));
				gamedata.setVariables(NewGameFactory.initGameVariables());
				gamedata.getPoklmons().add(NewGameFactory.createStarterPoklmon(data, starterPoklmonID, poklmonName));
				PokldexEntry entry = new PokldexEntry(CaughtPoklmonMeasurement.getCaughtDayInfo());
				entry.setCacheCount(1);
				gamedata.getVariables().getPokldex().getPokldex().put(starterPoklmonID, entry);
				// open game
				MapState gamestate = (MapState) states.getState(MapState.class);
				gamestate.openGame(gamedata);
				// switch to game screen
				SaveFileManager.createNewSaveFile(gamedata);
				states.transition(MapState.class);
			}
		});
		gui = new NewgameGUI(data, process);
		process.setGui(gui);

		Thread processThread = new Thread(process);
		processThread.start();
	}

	@Override
	public void onExit() {
	}

	@Override
	public void update(float delta) {
		gui.update(delta);
		GUIUpdate.consume();
	}

	@Override
	public void render(Graphics g) {
		MenuGraphics.background.draw();
		gui.render(g);
	}

}
