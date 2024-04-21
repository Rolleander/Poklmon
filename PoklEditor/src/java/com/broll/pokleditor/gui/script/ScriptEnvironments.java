package com.broll.pokleditor.gui.script;

import com.broll.pokleditor.debug.GameDebugger;

import org.fife.rsta.ac.java.JarManager;
import org.fife.rsta.ac.js.engine.JavaScriptEngineFactory;
import org.fife.rsta.ac.js.engine.RhinoJavaScriptEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptEnvironments {

    private static Map<Type, RhinoJavaScriptLanguageSupport> LANGUAGE_SUPPORTS = new HashMap<>();

    public final static String[] STANDARD_IMPORT_PACKAGES = new String[]{
            "com.broll.poklmon.battle.enemy",
            "com.broll.poklmon.save",
            "com.broll.pokllib.poklmon",
            "com.broll.pokllib.object",
            "com.broll.pokllib.item",
            "com.broll.pokllib.attack",
            "com.broll.poklmon.map",
            "com.broll.poklmon.map.areas",
            "com.broll.poklmon.network.transfer",
            "com.broll.poklmon.model",
            "com.broll.poklmon.battle.attack",
            "com.broll.poklmon.battle.field",
            "com.broll.poklmon.battle.poklmon",
            "com.broll.poklmon.battle.calc",
            "com.broll.poklmon.battle.process.callbacks",
            "com.broll.poklmon.battle.process",
            "com.broll.poklmon.battle.poklmon.states",
    };

    public final static GlobalScriptVariable[] ENUM_TYPES = new GlobalScriptVariable[]{
            new GlobalScriptVariable("com.broll.poklmon.battle.enemy","EnemyKIType"),
            new GlobalScriptVariable("com.broll.pokllib.poklmon","AttributeType"),
            new GlobalScriptVariable("com.broll.pokllib.poklmon","ElementType"),
            new GlobalScriptVariable("com.broll.pokllib.poklmon","EXPLearnTypes"),
            new GlobalScriptVariable("com.broll.pokllib.poklmon","PoklmonWesen"),
            new GlobalScriptVariable("com.broll.pokllib.poklmon","TypeCompare"),
            new GlobalScriptVariable("com.broll.pokllib.object","ObjectDirection"),
            new GlobalScriptVariable("com.broll.pokllib.item","ItemType"),
            new GlobalScriptVariable("com.broll.pokllib.attack","AttackPriority"),
            new GlobalScriptVariable("com.broll.pokllib.attack","AttackType"),
            new GlobalScriptVariable("com.broll.poklmon.map.areas","AreaType"),
            new GlobalScriptVariable("com.broll.poklmon.model","CharacterWorldState"),
            new GlobalScriptVariable("com.broll.poklmon.battle.attack","AttackAttributePlus"),
            new GlobalScriptVariable("com.broll.poklmon.battle.field","GlobalEffect"),
            new GlobalScriptVariable("com.broll.poklmon.battle.field","TeamEffect"),
            new GlobalScriptVariable("com.broll.poklmon.battle.field","WeatherEffect"),
            new GlobalScriptVariable("com.broll.poklmon.battle.poklmon.states","MainFightStatus"),
            new GlobalScriptVariable("com.broll.poklmon.battle.poklmon.states","EffectStatus")
    };

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
                new GlobalScriptVariable("player", "com.broll.poklmon.script.commands", "PlayerCommands"),
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

    }

    public static void init(){
        for (Type type : Type.values()){
            LANGUAGE_SUPPORTS.put(type, new RhinoJavaScriptLanguageSupport(type));
        }
    }

    public static RhinoJavaScriptLanguageSupport getScriptLanguageSupport(Type type){
        return LANGUAGE_SUPPORTS.get(type);
    }

}
