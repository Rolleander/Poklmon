package com.broll.poklmon.battle.poklmon;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.AttackLearnEntry;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.process.CustomScriptCall;
import com.broll.poklmon.battle.util.ScriptValue;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.battle.process.callbacks.StatAttackCallback;
import com.broll.poklmon.battle.process.callbacks.StatDefenceCallback;
import com.broll.poklmon.battle.process.callbacks.StatInitiativeCallback;
import com.broll.poklmon.battle.process.callbacks.StatKpCallback;
import com.broll.poklmon.battle.process.callbacks.StatSpecialAttackCallback;
import com.broll.poklmon.battle.process.callbacks.StatSpecialDefenceCallback;
import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;
import com.broll.poklmon.poklmon.PoklmonAttributeCalculator;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.PoklmonData;

import java.util.List;

public class FightPokemonBuilder {

    public static FightPoklmon createTrainerPoklmon(DataContainer data, Poklmon poklmon, int level, int[] attacks) {
        FightPoklmon p = new FightPoklmon();
        initEnemyPoklmon(p, data, poklmon, level, attacks);
        short[] DV = {10, 10, 10, 10, 10, 10};
        short[] FP = {50, 50, 50, 50, 50, 50};
        FightAttributes att = createAttributes(poklmon, level, DV, FP, PoklmonWesen.ERNST);
        // set max kp
        att.setHealth(att.getMaxhealth());
        p.setAttributes(att);
        return p;
    }

    public static WildPoklmon createWildPoklmon(DataContainer data, Poklmon poklmon, int level) {
        WildPoklmon p = new WildPoklmon();
        initEnemyPoklmon(p, data, poklmon, level);
        short[] DV = CaughtPoklmonMeasurement.generateRandomDVs();
        short[] FP = {0, 0, 0, 0, 0, 0};
        p.setDv(DV);
        //p.setHasPowerfulGenes(RarePoklmonCalc.hasPowerfulGenes(DV));
        PoklmonWesen wesen = CaughtPoklmonMeasurement.getRandomWesen();
        p.setWesen(wesen);
        FightAttributes att = createAttributes(poklmon, level, DV, FP, wesen);
        // set max kp
        att.setHealth(att.getMaxhealth());
        p.setAttributes(att);
        return p;
    }

    public static FightPoklmon createPlayerPoklmon(DataContainer data, PoklmonData poklmon) {
        PlayerPoklmon p = new PlayerPoklmon(poklmon);
        Poklmon pokl = data.getPoklmons().getPoklmon(poklmon.getPoklmon());
        p.setPoklmon(pokl);
        String name = poklmon.getName();
        if (name == null) {
            p.setName(pokl.getName());
        } else {
            p.setName(name);
        }
        int level = poklmon.getLevel();
        p.setPoklball(poklmon.getPoklball());
        p.setImage(data.getGraphics().getPoklmonImage(pokl.getGraphicName()));
        p.setLevel(level);
        p.setMainStatus(poklmon.getStatus());
        p.updateExp();
        short[] DV = poklmon.getDv();
        //p.setHasPowerfulGenes(RarePoklmonCalc.hasPowerfulGenes(DV));
        FightAttributes att = createAttributes(pokl, level, DV, poklmon.getFp(), poklmon.getWesen());
        // set current kp
        int health = poklmon.getKp();
        if (health > att.getMaxhealth()) {
            health = att.getMaxhealth();
        } else if (health < 0) {
            health = 0;
        }
        p.setCarryItem(poklmon.getCarryItem());
        att.setHealth(health);
        p.setAttributes(att);
        // set attacks
        FightAttack[] attacks = new FightAttack[4];
        for (int i = 0; i < 4; i++) {
            attacks[i] = createPlayerAttack(data, poklmon.getAttacks()[i]);
        }
        p.setAttacks(attacks);
        return p;
    }

    public static void updateFightPoklmon(BattleManager battle, PlayerPoklmon pokl) {
        PoklmonData poklmon = pokl.getPoklmonData();
        short[] DV = poklmon.getDv();
        int level = poklmon.getLevel();
        FightAttributes att = createAttributes(pokl.getPoklmon(), level, DV, poklmon.getFp(), poklmon.getWesen());
        ScriptValue v = new ScriptValue(att.getAttack());
        for (StatAttackCallback script : battle.getScriptCalls(StatAttackCallback.class)) {
            v.value = script.call(v.value);
        }
        pokl.getAttributes().setAttack(v.value);
        v.value = att.getDefence();
        for (StatDefenceCallback script : battle.getScriptCalls(StatDefenceCallback.class)) {
            v.value = script.call(v.value);
        }
        pokl.getAttributes().setDefence(v.value);
        v.value = att.getInit();
        for (StatInitiativeCallback script : battle.getScriptCalls(StatInitiativeCallback.class)) {
            v.value = script.call(v.value);
        }
        pokl.getAttributes().setInit(v.value);
        v.value = att.getSpecial_attack();
        for (StatSpecialAttackCallback script : battle.getScriptCalls(StatSpecialAttackCallback.class)) {
            v.value = script.call(v.value);
        }
        pokl.getAttributes().setSpecial_attack(v.value);
        v.value = att.getSpecial_defence();
        for (StatSpecialDefenceCallback script : battle.getScriptCalls(StatSpecialDefenceCallback.class)) {
            v.value = script.call(v.value);
        }
        pokl.getAttributes().setSpecial_defence(v.value);
        v.value = att.getMaxhealth();
        for (StatKpCallback script : battle.getScriptCalls(StatKpCallback.class)) {
            v.value = script.call(v.value);
        }
        pokl.getAttributes().setMaxhealth(v.value);
    }

    private static FightPoklmon initEnemyPoklmon(FightPoklmon p, DataContainer data, Poklmon poklmon, int level) {
        p.setPoklmon(poklmon);
        p.setLevel(level);
        p.setImage(data.getGraphics().getPoklmonImage(poklmon.getGraphicName()));
        p.setName(poklmon.getName());

        // calc attacks random
        FightAttack[] attacks = new FightAttack[4];
        int foundAttacks = 0;
        List<AttackLearnEntry> atks = poklmon.getAttackList().getAttacks();
        for (int i = atks.size() - 1; i >= 0; i--) {
            AttackLearnEntry atk = atks.get(i);
            if (atk.getLearnLevel() <= level) {
                Attack atkData = data.getAttacks().getAttack(atk.getAttackNumber());
                if (foundAttacks < 4) {
                    // learn
                    attacks[foundAttacks] = new FightAttack(atkData);
                    foundAttacks++;
                } else {
                    // maybe forget
                    if (Math.random() <= 0.3f - i / 20) {
                        int r = (int) (Math.random() * 4);
                        attacks[r] = new FightAttack(atkData);
                    }
                }
            }
        }
        p.setAttacks(attacks);
        return p;
    }

    private static FightPoklmon initEnemyPoklmon(FightPoklmon p, DataContainer data, Poklmon poklmon, int level,
                                                 int[] attacks) {
        p.setPoklmon(poklmon);
        p.setLevel(level);
        p.setImage(data.getGraphics().getPoklmonImage(poklmon.getGraphicName()));
        p.setName(poklmon.getName());

        FightAttack[] atks = new FightAttack[4];
        for (int i = 0; i < 4; i++) {
            int atk = attacks[i];
            if (atk > -1) {
                Attack attack = data.getAttacks().getAttack(atk);
                atks[i] = new FightAttack(attack);
            }
        }
        p.setAttacks(atks);
        return p;
    }

    private static FightAttack createPlayerAttack(DataContainer data, AttackData attack) {
        if (attack == null) {
            return null;
        }
        int id = attack.getAttack();
        if (id == -1) {
            return null;
        }
        Attack atkdata = data.getAttacks().getAttack(id);
        FightAttack atk = new FightAttack(atkdata, attack.getAp());
        return atk;
    }

    private static FightAttributes createAttributes(Poklmon poklmon, int level, short[] DV, short[] FP,
                                                    PoklmonWesen wesen) {
        FightAttributes att = new FightAttributes();
        att.setMaxhealth(PoklmonAttributeCalculator.getKP(poklmon, level, DV[0], FP[0]));
        att.setAttack(PoklmonAttributeCalculator.getAttack(poklmon, level, DV[1], FP[1], wesen));
        att.setDefence(PoklmonAttributeCalculator.getDefence(poklmon, level, DV[2], FP[2], wesen));
        att.setSpecial_attack(PoklmonAttributeCalculator.getSpecialAttack(poklmon, level, DV[3], FP[3], wesen));
        att.setSpecial_defence(PoklmonAttributeCalculator.getSpecialDefence(poklmon, level, DV[4], FP[4], wesen));
        att.setInit(PoklmonAttributeCalculator.getInitiative(poklmon, level, DV[5], FP[5], wesen));
        return att;
    }

}
