package com.broll.poklmon.battle.attack.script;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackDamage;
import com.broll.pokllib.poklmon.ElementType;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.AttackAttributePlus;
import com.broll.poklmon.battle.attack.AttributeChange;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.field.GlobalEffect;
import com.broll.poklmon.battle.field.PoklmonTeamEffect;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.field.WeatherEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.process.CustomScriptCall;
import com.broll.poklmon.battle.util.BattleRandom;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class ScriptAttackActions {

    private BattleManager battleManager;
    private BattleProcessCore battleProcessCore;
    private FightPoklmon user, target;
    private FightAttack fightAttack;
    private UseAttack attack;

    public ScriptAttackActions(BattleManager battle, BattleProcessCore core) {
        this.battleManager = battle;
        this.battleProcessCore = core;
    }

    public void init(FightAttack fightAttack, UseAttack attack, FightPoklmon user, FightPoklmon target) {
        this.fightAttack = fightAttack;
        this.attack = attack;
        this.target = target;
        this.user = user;
    }

    public void decreaseAp(){
        user.useAttack(fightAttack);
    }

    public Attack copyAttack(int nr) {
        Attack a = new Attack();
        Attack old = battleManager.getData().getAttacks().getAttack(nr);
        a.setAnimationID(old.getAnimationID());
        a.setAttackType(old.getAttackType());
        AttackDamage d = new AttackDamage();
        d.setHitchance(old.getDamage().getHitchance());
        d.setDamage(old.getDamage().getDamage());
        d.setPriority(old.getDamage().getPriority());
        a.setDamage(d);
        a.setDescription(old.getDescription());
        a.setElementType(old.getElementType());
        a.setEffectCode(old.getEffectCode());
        a.setName(old.getName());
        return a;
    }

    public BattleManager getBattleManager() {
        return battleManager;
    }

    public BattleProcessCore getBattleProcessCore() {
        return battleProcessCore;
    }

    public void showText(String text) {
        battleProcessCore.getAttackProcess().showText(text);
    }

    public String showInput(String defaultText) {
        return battleProcessCore.getAttackProcess().showInput(defaultText);
    }

    public int showSelection(String text, String[] selections) {
        return battleProcessCore.getAttackProcess().showSelection(text, selections);
    }

    public int showCancelableSelection(String text, String[] selections) {
        return battleProcessCore.getAttackProcess().showCancelableSelection(text, selections);
    }

    public void showInfo(String text) {
        battleProcessCore.getAttackProcess().showInfo(text);
    }

    public void addCallback(CustomScriptCall call) {
        battleManager.addScriptCall(attack, call);
    }

    public void removeCallbacks(UseAttack attack) {
        battleManager.removeScriptCalls(attack);
    }

    public double random() {
        return BattleRandom.random();
    }

    public boolean addUserEffect(String effect) {
        return addEffectChance(effect, 1, false);
    }

    public boolean addTargetEffect(String effect) {
        return addEffectChance(effect, 1, true);
    }

    public boolean addUserEffectChance(String effect, double chance) {
        return addEffectChance(effect, chance, false);
    }

    public boolean addTargetEffectChance(String effect, double chance) {
        return addEffectChance(effect, chance, true);
    }

    private boolean addEffectChance(String effect, double chance, boolean target) {
        // check main status changes
        try {
            MainFightStatus status = MainFightStatus.valueOf(effect.toUpperCase());
            return addMainStatusChange(status, chance, target);

        } catch (IllegalArgumentException e) {

        }

        try {
            EffectStatus status = EffectStatus.valueOf(effect.toUpperCase());
            return addEffectStatusChange(status, chance, target);
        } catch (IllegalArgumentException e) {

        }
        throw new RuntimeException("No Effect could be matched to: " + effect);
    }

    private boolean addMainStatusChange(MainFightStatus status, double chance, boolean target) {
        if (target) {
            if (!this.target.getStatusChanges().hasMainStatusChange(status)) {
                if (BattleRandom.random() <= chance) {
                    attack.setChangeStatusTarget(status);
                }
                return false;
            }
        } else {
            if (!this.user.getStatusChanges().hasMainStatusChange(status)) {
                if (BattleRandom.random() <= chance) {
                    attack.setChangeStatusUser(status);
                }
                return false;
            }
        }
        return true;
    }

    private boolean addEffectStatusChange(EffectStatus status, double chance, boolean target) {
        if (target) {
            if (!this.target.getStatusChanges().hasEffectChange(status)) {
                if (BattleRandom.random() <= chance) {
                    attack.setEffectStatusTarget(status);
                }
                return false;
            }
        } else {
            if (!this.user.getStatusChanges().hasEffectChange(status)) {
                if (BattleRandom.random() <= chance) {
                    attack.setEffectStatusUser(status);
                }
                return false;
            }
        }
        return true;
    }

    public void addUserAttributeChange(String attribute, int value, double chance) {
        addAttributeChange(false, attribute, value, chance);
    }

    public void addTargetAttributeChange(String attribute, int value, double chance) {
        addAttributeChange(true, attribute, value, chance);
    }

    private void addAttributeChange(boolean target, String attribute, int value, double chance) {
        AttackAttributePlus type = AttackAttributePlus.valueOf(attribute.toUpperCase());
        if (type != null) {
            if (BattleRandom.random() <= chance) {
                if (target) {
                    attack.getAttributeChangeTarget().add(new AttributeChange(type, value));
                } else {
                    attack.getAttributeChangeUser().add(new AttributeChange(type, value));
                }
            }
        } else {
            throw new RuntimeException("Attribute \"" + attribute + "\" could not be resolved!");
        }
    }

    public void addMultihit(int min, int max) {
        int multihit = min + (int) (((max + 1) - min) * BattleRandom.random());
        attack.setMultihitCount(multihit);
    }

    public void setSpecialEffect(String effectName) {
        try {
            if (effectName != null && !effectName.isEmpty()) {
                SpecialScript func = SpecialScript.valueOf(effectName.toUpperCase());
                attack.setSpecialFunction(func);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cant create Special Script from Identifier: " + effectName);
        }
    }

    public boolean hasState(FightPoklmon poklmon, String stateName) {
        PoklmonStatusChanges status = poklmon.getStatusChanges();
        try {
            MainFightStatus effect = MainFightStatus.valueOf(stateName.toUpperCase());
            if (effect == MainFightStatus.POISON) {
                if (status.getMainStatus() == MainFightStatus.TOXIN) {
                    return true;
                }
            }
            return status.getMainStatus() == effect;
        } catch (IllegalArgumentException e) {

        }

        try {
            EffectStatus effect = EffectStatus.valueOf(stateName.toUpperCase());
            return status.hasEffectChange(effect);
        } catch (IllegalArgumentException e) {

        }

        throw new RuntimeException("StateName " + stateName + " could not be resolved!");
    }

    public boolean isGlobalEffect(String name) {
        try {
            GlobalEffect effect = GlobalEffect.valueOf(name.toUpperCase());
            return battleManager.getFieldEffects().hasGlobalEffect(effect);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cant map global effect name: " + name);
        }

    }

    public boolean isWeatherEffect(String name) {
        try {
            WeatherEffect effect = WeatherEffect.valueOf(name.toUpperCase());
            return battleManager.getFieldEffects().getWeatherEffect() == effect;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cant map global effect name: " + name);
        }
    }

    public boolean isUserTeamEffect(String name) {
        return isTeamEffect(name, true);
    }

    public boolean isTargetTeamEffect(String name) {
        return isTeamEffect(name, false);
    }

    private boolean isTeamEffect(String name, boolean userTeam) {
        try {
            TeamEffect effect = TeamEffect.valueOf(name.toUpperCase());
            PoklmonTeamEffect teamEffect = null;
            if (userTeam) {
                teamEffect = battleManager.getFieldEffects().getTeamEffects(user);
            } else {
                teamEffect = battleManager.getFieldEffects().getTeamEffects(target);
            }
            return teamEffect.hasTeamEffect(effect);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cant map global effect name: " + name);
        }

    }

    public boolean isPoklmonType(FightPoklmon poklmon, String param) {
        ElementType baseType = poklmon.getPoklmon().getBaseType();
        ElementType secondType = poklmon.getPoklmon().getSecondaryType();

        if (baseType.name().toLowerCase().equals(param)) {
            return true;
        }
        if (secondType != null) {
            if (secondType.name().toLowerCase().equals(param)) {
                return true;
            }
        }
        return false;
    }

    public boolean addGlobalEffect(String global, double chance) {
        try {
            GlobalEffect effect = GlobalEffect.valueOf(global.toUpperCase());
            if (!battleManager.getFieldEffects().hasGlobalEffect(effect)) {
                if (BattleRandom.random() <= chance) {
                    attack.setGlobalEffect(effect);
                }
                return false;
            }
            return true;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Could not match global effect to: " + global);
        }
    }

    public boolean addWeatherEffect(String global, double chance) {
        try {
            WeatherEffect effect = WeatherEffect.valueOf(global.toUpperCase());
            if (!battleManager.getFieldEffects().isWeatherEffect(effect)) {
                if (BattleRandom.random() <= chance) {
                    attack.setWeatherEffect(effect);
                }
            } else {
                // cant set
                return true;
            }

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Could not match weather effect to: " + global);
        }
        return false;
    }

    public boolean addUserTeamEffect(String effect, double chance) {
        return addTeamEffect(effect, true, chance);
    }

    public boolean addTargetTeamEffect(String effect, double chance) {
        return addTeamEffect(effect, false, chance);
    }

    private boolean addTeamEffect(String global, boolean userTeam, double chance) {
        try {
            TeamEffect effect = TeamEffect.valueOf(global.toUpperCase());
            if (userTeam) {
                if (!battleManager.getFieldEffects().getTeamEffects(user).hasTeamEffect(effect)) {
                    if (BattleRandom.random() <= chance) {
                        attack.setTeamEffectUser(effect);
                    }
                    return false;
                }
            } else {

                if (!battleManager.getFieldEffects().getTeamEffects(target).hasTeamEffect(effect)) {
                    if (BattleRandom.random() <= chance) {
                        attack.setTeamEffectTarget(effect);
                    }
                    return false;
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Could not match team effect to: " + global);
        }
    }

}
