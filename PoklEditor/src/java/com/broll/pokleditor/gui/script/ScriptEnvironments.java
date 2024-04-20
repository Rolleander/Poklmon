package com.broll.pokleditor.gui.script;

import com.broll.pokleditor.debug.GameDebugger;

import org.fife.rsta.ac.java.JarManager;
import org.fife.rsta.ac.js.engine.JavaScriptEngineFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptEnvironments {

    private static Map<Type, RhinoJavaScriptLanguageSupport> LANGUAGE_SUPPORTS = new HashMap<>();

    public enum Type {
        ITEM_POKLBALL(
                new GlobalScriptVariable("poklball", "com.broll.poklmon.battle.item", "PoklballItemRunner")
        ),
        ITEM_MEDICINE(
                new GlobalScriptVariable("medicine", "com.broll.poklmon.battle.item", "MedicineItemRunner")
        ),
        ITEM_WEARABLE(
                new GlobalScriptVariable("wearable", "com.broll.poklmon.battle.item", "WearableItemRunner")
        ),
        ITEM_OTHER(
                new GlobalScriptVariable("other", "com.broll.poklmon.game.items.execute", "OtherItemRunner")
        ),
        ITEM_ATTACK(
                new GlobalScriptVariable("attack", "com.broll.poklmon.game.items.execute", "AttackItemRunner")
        ),
        ITEM_BASIS(
                new GlobalScriptVariable("basis", "com.broll.poklmon.game.items.execute", "BasisItemRunner")
        ),
        ATTACK(
                new GlobalScriptVariable("atk", "com.broll.pokllib.attack", "Attack"),
                new GlobalScriptVariable("user", "com.broll.poklmon.battle.poklmon", "FightPoklmon"),
                new GlobalScriptVariable("target", "com.broll.poklmon.battle.poklmon", "FightPoklmon"),
                new GlobalScriptVariable("util", "com.broll.poklmon.battle.attack.script", "ScriptAttackActions"),
                new GlobalScriptVariable("flags", "com.broll.poklmon.battle.attack.script", "ScriptContext")
        ),
        OBJECT_INIT(
                new GlobalScriptVariable("self", "com.broll.poklmon.map.object", "MapObject"),
                new GlobalScriptVariable("init", "com.broll.poklmon.script.commands", "InitCommands"),
                new GlobalScriptVariable("game", "com.broll.poklmon.script.commands", "VariableCommands"),
                new GlobalScriptVariable("path", "com.broll.poklmon.script.commands", "PathingCommands"),
                new GlobalScriptVariable("system", "com.broll.poklmon.script.commands", "SystemCommands")
        ),
        OBJECT_RUNTIME(
                new GlobalScriptVariable("self", "com.broll.poklmon.map.object", "MapObject"),
                new GlobalScriptVariable("battle", "com.broll.poklmon.script.commands", "BattleCommands"),
                new GlobalScriptVariable("dialog", "com.broll.poklmon.script.commands", "DialogCommands"),
                new GlobalScriptVariable("menu", "com.broll.poklmon.script.commands", "MenuCommands"),
                new GlobalScriptVariable("object", "com.broll.poklmon.script.commands", "ObjectCommands"),
                new GlobalScriptVariable("system", "com.broll.poklmon.script.commands", "SystemCommands"),
                new GlobalScriptVariable("game", "com.broll.poklmon.script.commands", "VariableCommands"),
                new GlobalScriptVariable("path", "com.broll.poklmon.script.commands", "PathingCommands"),
                new GlobalScriptVariable("network", "com.broll.poklmon.script.commands", "NetworkCommands")
        ),
        AREA_INIT(
                new GlobalScriptVariable("area", "com.broll.poklmon.map.areas", "AreaScriptActions")
        );

        final GlobalScriptVariable[] globalVariables;

        Type(GlobalScriptVariable... globalVariables) {
            this.globalVariables = globalVariables;
        }

        public String getEngineName(){
            return "POKL-JS-"+name();
        }

    }

    public static void init(){
        for (Type type : Type.values()){
            JavaScriptEngineFactory.Instance().addEngine(type.getEngineName(), new CustomRhinoJavaScriptEngine(type.globalVariables));
            RhinoJavaScriptLanguageSupport.SETUP_TYPE = type;
            LANGUAGE_SUPPORTS.put(type, new RhinoJavaScriptLanguageSupport());
        }
    }

    public static RhinoJavaScriptLanguageSupport getScriptLanguageSupport(Type type){
        return LANGUAGE_SUPPORTS.get(type);
    }

}
