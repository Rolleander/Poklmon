package com.broll.poklmon.battle.poklmon;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.AttackLearnEntry;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.battle.process.callbacks.StatAttackCallback;
import com.broll.poklmon.battle.process.callbacks.StatDefenceCallback;
import com.broll.poklmon.battle.process.callbacks.StatInitiativeCallback;
import com.broll.poklmon.battle.process.callbacks.StatKpCallback;
import com.broll.poklmon.battle.process.callbacks.StatSpecialAttackCallback;
import com.broll.poklmon.battle.process.callbacks.StatSpecialDefenceCallback;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.poklmon.PoklmonAttributeCalculator;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.PoklmonData;

import java.util.List;

public class FightPokemonBuilder {

    public static TrainerPoklmon createTrainerPoklmon(GameManager game, Poklmon poklmon, int level, int[] attacks) {
        TrainerPoklmon p = new TrainerPoklmon(game.getPlayer().getVariableControl(), poklmon, level);
        initEnemyPoklmon(p, game.getData(), poklmon, level, attacks);
        p.getAttributes().fullHealh();
        return p;
    }

    public static WildPoklmon createWildPoklmon(DataContainer data, Poklmon poklmon, int level) {
        WildPoklmon p = new WildPoklmon(poklmon, level);
        initEnemyPoklmon(p, data, poklmon, level);
        p.getAttributes().fullHealh();
        return p;
    }

    public static FightPoklmon createPlayerPoklmon(DataContainer data, PoklmonData poklmon) {
        Poklmon pokl = data.getPoklmons().getPoklmon(poklmon.getPoklmon());
        PlayerPoklmon p = new PlayerPoklmon(pokl, poklmon);
        String name = poklmon.getName();
        if (name == null) {
            p.setName(pokl.getName());
        } else {
            p.setName(name);
        }
        if (poklmon.getKp() >= p.getAttributes().getMaxhealth()) {
            p.getAttributes().fullHealh();
        } else {
            p.getAttributes().setHealth(poklmon.getKp());
        }
        p.setPoklball(poklmon.getPoklball());
        p.setImage(data.getGraphics().getPoklmonImage(pokl.getGraphicName()));
        p.setMainStatus(poklmon.getStatus());
        p.updateExp();
        p.setCarryItem(poklmon.getCarryItem());
        // set attacks
        FightAttack[] attacks = new FightAttack[4];
        for (int i = 0; i < 4; i++) {
            attacks[i] = createPlayerAttack(data, poklmon.getAttacks()[i]);
        }
        p.setAttacks(attacks);
        return p;
    }

    public static void updateFightPoklmon(BattleManager battle, FightPoklmon pokl) {
        short[] DV = pokl.getDv();
        int level = pokl.getLevel();
        FightAttributes att = createAttributes(pokl.getPoklmon(), level, DV, pokl.getFp(), pokl.getWesen());
        int value = att.getAttack();
        for (StatAttackCallback script : battle.getScriptCalls(StatAttackCallback.class)) {
            value = script.call(value);
        }
        pokl.getAttributes().setAttack(value);
        value = att.getDefence();
        for (StatDefenceCallback script : battle.getScriptCalls(StatDefenceCallback.class)) {
            value = script.call(value);
        }
        pokl.getAttributes().setDefence(value);
        value = att.getInit();
        for (StatInitiativeCallback script : battle.getScriptCalls(StatInitiativeCallback.class)) {
            value = script.call(value);
        }
        pokl.getAttributes().setInit(value);
        value = att.getSpecial_attack();
        for (StatSpecialAttackCallback script : battle.getScriptCalls(StatSpecialAttackCallback.class)) {
            value = script.call(value);
        }
        pokl.getAttributes().setSpecial_attack(value);
        value = att.getSpecial_defence();
        for (StatSpecialDefenceCallback script : battle.getScriptCalls(StatSpecialDefenceCallback.class)) {
            value = script.call(value);
        }
        pokl.getAttributes().setSpecial_defence(value);
        value = att.getMaxhealth();
        for (StatKpCallback script : battle.getScriptCalls(StatKpCallback.class)) {
            value = script.call(value);
        }
        pokl.getAttributes().setMaxhealth(value);
    }

    private static FightPoklmon initEnemyPoklmon(FightPoklmon p, DataContainer data, Poklmon poklmon, int level) {
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

    public static FightAttributes createAttributes(Poklmon poklmon, int level, short[] DV, short[] FP,
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
