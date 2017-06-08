package com.broll.poklmon.main.states;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.debug.DebugPlayerFactory;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.menu.PlayerMenu;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.save.GameData;

public class MenuDebugState extends GameState {

	public static int STATE_ID = -2;

	private DataContainer data;
	private Player player;
	private PlayerMenu menu;

	public MenuDebugState(DataContainer data) {
		this.data = data;

	}

	@Override
	public void onInit() {
	}

	@Override
	public void onEnter() {
		DebugPlayerFactory f = new DebugPlayerFactory(data);
		GameData dat = f.createDebugPlayer(0, 0, 0); // location irrelevant

		GameManager gameManager = new GameManager(data, null, null);
		gameManager.startGame(dat);
		player = new Player(gameManager);

		player.init(dat);
		menu = new PlayerMenu(player, data, gameManager);

		// menu.getControl().openPokldex();
		menu.getControl().openInventar();
	}

	@Override
	public void onExit() {
	}

	@Override
	public void update(float delta) {
		menu.update(delta);
		GUIUpdate.consume();
	}

	@Override
	public void render(Graphics g) {
		menu.render(g);
	}

}
