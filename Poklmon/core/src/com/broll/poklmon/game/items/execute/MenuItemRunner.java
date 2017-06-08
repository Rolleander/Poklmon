package com.broll.poklmon.game.items.execute;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.control.MessageGuiControl;
import com.broll.poklmon.game.scene.SceneProcessManager;

public abstract class MenuItemRunner {

	protected MessageGuiControl gui;
	protected SceneProcessManager process;
	protected DataContainer data;
	protected GameManager game;
	
	public MenuItemRunner(GameManager game) {
		this.gui=game.getMessageGuiControl();
		this.data=game.getData();
		this.process=game.getSceneProcessManager();
		this.game=game;
	}
	
}
