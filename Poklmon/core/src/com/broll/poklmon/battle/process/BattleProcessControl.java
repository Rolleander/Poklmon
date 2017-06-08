package com.broll.poklmon.battle.process;

import java.util.stream.Stream;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.util.ProcessThreadHandler;

public abstract class BattleProcessControl {

	protected BattleProcessCore core;
	protected BattleManager manager;
	protected ProcessThreadHandler processThreadHandler;

	public BattleProcessControl(BattleManager manager, BattleProcessCore handler) {
		this.manager = manager;
		this.core = handler;
		this.processThreadHandler = handler.getProcessThreadHandler();
	}


	public void showText(String text) {
		if (text == null)
			return;
		manager.getBattleRender().getHudRender().showText(text, processThreadHandler);
		waitForResume();
	}

	public String showInput(String defaultText) {
		manager.getBattleRender().getHudRender().showInput(defaultText, processThreadHandler);
		waitForResume();
		return manager.getBattleRender().getHudRender().getInputText();
	}

	public int showSelection(String text, String[] selections) {
		manager.getBattleRender().getHudRender().showTextWaiting(text, processThreadHandler);
		waitForResume();
		manager.getBattleRender().getHudRender().showSelection(selections, processThreadHandler);
		waitForResume();
		return manager.getBattleRender().getHudRender().getSelectedOption();
	}

	public int showCancelableSelection(String text, String[] selections) {
		manager.getBattleRender().getHudRender().showTextWaiting(text, processThreadHandler);
		waitForResume();
		manager.getBattleRender().getHudRender().showCancelableSelection(selections, processThreadHandler);
		waitForResume();
		return manager.getBattleRender().getHudRender().getSelectedOption();
	}

	public void showInfo(String text) {
		manager.getBattleRender().getHudRender().showInfo(text);
	}

	protected void waitForResume() {
		core.waitForResume();
	}
}
