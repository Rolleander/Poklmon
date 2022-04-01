package com.broll.pokleditor.debug;


import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashSet;
import java.util.Set;

public class CallbackList {

    public static Set<Class> getCallbacks() {
        try {
            Class customScriptCallClass = DebuggerClasses.getClass("com.broll.poklmon.battle.process.CustomScriptCall");
            Reflections reflections = new Reflections(DebuggerClasses.debugerClassLoader, "com.broll.poklmon.game.items.callbacks");
            Set<Class> allClasses = reflections.getSubTypesOf(customScriptCallClass);
            return allClasses;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }
}
