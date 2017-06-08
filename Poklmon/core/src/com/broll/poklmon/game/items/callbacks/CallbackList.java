package com.broll.poklmon.game.items.callbacks;

import java.util.Set;

import org.reflections.Reflections;

import com.broll.poklmon.battle.process.CustomScriptCall;

public class CallbackList {

	// just for the editor lib window
	public static Set<Class<? extends CustomScriptCall>> getCallbacks() {
		Reflections reflections = new Reflections("com.broll.poklmon.game.items.callbacks");
		Set<Class<? extends CustomScriptCall>> allClasses = reflections.getSubTypesOf(CustomScriptCall.class);
		return allClasses;
	}

}
