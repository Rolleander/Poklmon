package com.broll.poklmon.battle.item;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.AttackAttributePlus;
import com.broll.poklmon.battle.attack.AttributeChange;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.process.CustomScriptCall;
import com.broll.poklmon.battle.util.BattleRandom;
import com.broll.poklmon.game.items.WearableItemScript;
import com.broll.poklmon.script.commands.VariableCommands;

public class WearableItemRunner extends BattleProcessControl implements WearableItemScript {

    private FightPoklmon carrier;
    private boolean playerTeam;
    private VariableCommands variableCmds;

    public WearableItemRunner(BattleManager manager, BattleProcessCore handler) {
        super(manager, handler);
    }

    public void init(FightPoklmon carrier, boolean playerTeam) {
        this.carrier = carrier;
        this.playerTeam = playerTeam;
        this.variableCmds = new VariableCommands(manager.getGame());
    }

    @Override
    public BattleManager getBattleManager() {
        return manager;
    }

    @Override
    public BattleProcessCore getBattleProcessCore() {
        return core;
    }

    @Override
    public FightPoklmon getActivePoklmon() {
        if (playerTeam) {
            return manager.getParticipants().getPlayer();
        }
        return manager.getParticipants().getEnemy();
    }

    @Override
    public FightPoklmon getEnemyPoklmon() {
        if (!playerTeam) {
            return manager.getParticipants().getPlayer();
        }
        return manager.getParticipants().getEnemy();
    }

    @Override
    public FightPoklmon getCarrierPoklmon() {
        return carrier;
    }

    @Override
    public void addCallback(CustomScriptCall call) {
        manager.addScriptCall(this, call);
    }

    @Override
    public void removeCallbacks() {
        manager.removeScriptCalls(this);
    }

    @Override
    public boolean isCarrierActive() {
        return carrier == getActivePoklmon();
    }

    @Override
    public boolean isBool() {
        return core.getItemProcess().getWearableItemVariables().getBool(carrier);
    }

    @Override
    public int getInt() {
        return core.getItemProcess().getWearableItemVariables().getInt(carrier);
    }

    @Override
    public float getFloat() {
        return core.getItemProcess().getWearableItemVariables().getFloat(carrier);
    }

    @Override
    public void setBool(boolean bool) {
        core.getItemProcess().getWearableItemVariables().setBool(carrier, bool);
    }

    @Override
    public void setInt(int value) {
        core.getItemProcess().getWearableItemVariables().setInt(carrier, value);
    }

    @Override
    public void setFloat(float value) {
        core.getItemProcess().getWearableItemVariables().setFloat(carrier, value);
    }

    @Override
    public void changePoklmonAttributes(FightPoklmon target, AttackAttributePlus type, int strength) {
        core.getParamProcess().doAttributeChange(target, new AttributeChange(type, strength));
    }

    @Override
    public void doDamage(FightPoklmon target, String text, int damage) {
        core.getAttackProcess().doDamage(target, text, damage);
    }

    @Override
    public void doHeal(FightPoklmon target, String text, int heal) {
        core.getEffectProcess().getInflictprocess().healPoklmon(target, text, heal);
    }

    @Override
    public void addEffectStatus(FightPoklmon target, EffectStatus status) {
        core.getEffectProcess().getInflictprocess().addEffectStatus(target, status);
    }

    @Override
    public void setStatusChange(FightPoklmon target, MainFightStatus status) {
        core.getEffectProcess().getInflictprocess().setStatusChange(target, status);
    }

    @Override
    public double random() {
        return BattleRandom.random();
    }

    @Override
    public boolean canSetStatusChange(FightPoklmon target, MainFightStatus status) {
        return core.getEffectProcess().getInflictprocess().canSetStatusChange(target, status);
    }

    @Override
    public VariableCommands getVariableCmd() {
        return variableCmds;
    }

}
