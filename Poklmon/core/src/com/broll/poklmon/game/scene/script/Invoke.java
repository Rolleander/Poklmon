package com.broll.poklmon.game.scene.script;

import com.broll.pokllib.script.syntax.VariableException;

@FunctionalInterface
public interface Invoke {

	public void invoke() throws VariableException;
	
}
