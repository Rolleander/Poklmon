package com.broll.poklmon.game.scene.script.commands;

import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.script.CommandControl;

public class VariableCommands extends CommandControl {

	public VariableCommands(GameManager game) {
		super(game);
	}

	public boolean isBool(String no) {
		return game.getPlayer().getVariableControl().getBoolean(no);
	}

	public int getInt(String no) {
		return game.getPlayer().getVariableControl().getInt(no);
	}

	public float getFloat(String no) {
		return game.getPlayer().getVariableControl().getFloat(no);
	}

	public String getString(String no) {
		return game.getPlayer().getVariableControl().getString(no);
	}

	public void setBool(String no, boolean value) {
		game.getPlayer().getVariableControl().setBoolean(no, value);
	}

	public void setInt(String no, int value) {
		game.getPlayer().getVariableControl().setInt(no, value);
	}

	public void setFloat(String no, float value) {
		game.getPlayer().getVariableControl().setFloat(no, value);
	}

	public void setString(String no, String value) {
		game.getPlayer().getVariableControl().setString(no, value);
	}

}
