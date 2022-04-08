package com.broll.poklmon.battle.attack.script;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.flags.BattleEventFlags;
import com.broll.poklmon.battle.util.flags.FightPoklmonFlags;

import java.util.HashMap;
import java.util.Map;

public class ScriptContext {

    private BattleProcessCore core;
    private final Map<String, Object> context = new HashMap<>();

    public ScriptContext(BattleProcessCore core) {
        this.core = core;
    }

    private String getGlobalContextName(String key) {
        return "global_" + key;
    }

    private String getTeamContextName(boolean playerTeam, String key) {
        if (playerTeam) {
            return "player_team_" + key;
        } else {
            return "enemy_team_" + key;
        }
    }

    private String getPoklmonContextName(FightPoklmon poklmon, String key) {
        return "poklmon_" + poklmon.toString() + "_" + key;
    }

    public Object setPoklmonContext(FightPoklmon poklmon, String key, Object value) {
        return context.put(getPoklmonContextName(poklmon, key), value);
    }

    public Object getPoklmonContext(FightPoklmon poklmon, String key) {
        return context.get(getPoklmonContextName(poklmon, key));
    }

    public void clearPoklmonContext(FightPoklmon poklmon, String key) {
        context.remove(getPoklmonContextName(poklmon, key));
    }

    public void setPlayerTeamContext(String key, Object value) {
        context.put(getTeamContextName(true, key), value);
    }

    public Object getPlayerTeamContext(String key) {
        return context.get(getTeamContextName(true, key));
    }

    public void setEnemyTeamContext(String key, Object value) {
        context.put(getTeamContextName(true, key), value);
    }

    public Object getEnemyTeamContext(String key) {
        return context.get(getTeamContextName(false, key));
    }

    public void clearPlayerTeamContext(String key) {
        context.remove(getTeamContextName(true, key));
    }

    public void clearEnemyTeamContext(String key) {
        context.remove(getTeamContextName(false, key));
    }

    public void setGlobalContext(String key, Object value) {
        context.put(getGlobalContextName(key), value);
    }

    public Object getGlobalContext(String key) {
        return context.get(getGlobalContextName(key));
    }

    public void clearGlobalContext(String key) {
        context.remove(getGlobalContextName(key));
    }

    public FightPoklmonFlags getFightFlags(FightPoklmon poklmon) {
        return core.getEventFlags().getPoklmonFlags(poklmon);
    }
}

