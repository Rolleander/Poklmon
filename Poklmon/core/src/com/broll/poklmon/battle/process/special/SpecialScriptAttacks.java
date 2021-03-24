package com.broll.poklmon.battle.process.special;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackType;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.attack.script.SpecialScript;
import com.broll.poklmon.battle.calc.StateEffectCalc;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.process.effects.TeamEffectProcess;
import com.broll.poklmon.battle.util.BattleRandom;
import com.broll.poklmon.battle.util.flags.BattleEventFlags;
import com.broll.poklmon.battle.util.flags.DamageTaken;
import com.broll.poklmon.battle.util.message.BattleMessages;
import com.broll.poklmon.data.AttackContainer;
import com.broll.poklmon.data.TextContainer;

public class SpecialScriptAttacks extends BattleProcessControl
{

    public SpecialScriptAttacks(BattleManager manager, BattleProcessCore handler)
    {
        super(manager, handler);
    }

    public void useSpecialScript(UseAttack attack, FightPoklmon user, FightPoklmon target)
    {
        SpecialScript script = attack.getSpecialFunction();

        if (script == SpecialScript.JUSTICE)
        {
            //aufteilen der kp
            int userkp = user.getAttributes().getHealth();
            int targetkp = target.getAttributes().getHealth();
            if (userkp != targetkp)
            {
                showText("Die KP werden gerecht verteilt!");
                int newkp = (userkp + targetkp) / 2;
                adaptKP(user, newkp);
                adaptKP(target, newkp);
            }
            else
            {
                noEffect();
            }
        }

        if (script == SpecialScript.METRONOM)
        {
            //use random attack
            AttackContainer attacks = manager.getData().getAttacks();
            int id = (int)(attacks.getNumberOfAttacks() * BattleRandom.random());
            Attack atk = attacks.getAttack(id);
            FightAttack newAttack = new FightAttack(atk);
            core.getAttackProcess().processAttack(newAttack, user, target);
        }

        if (script == SpecialScript.IMITATOR)
        {
            //use last attack from enemy
            int lastAttack = core.getEventFlags().getPoklmonFlags(target).getLastAttackID();
            if (lastAttack != BattleEventFlags.NONE)
            {
                //use attack
                FightAttack newAttack = new FightAttack(manager.getData().getAttacks().getAttack(lastAttack));
                core.getAttackProcess().processAttack(newAttack, user, target);
            }
            else
            {
                noEffect();
            }
        }

        if (script == SpecialScript.MIMIKRY)
        {
            //learn last used attack from enemy
            int lastAttack = core.getEventFlags().getPoklmonFlags(target).getLastAttackID();
            if (lastAttack != BattleEventFlags.NONE)
            {
                int mimikryID = 129;
                int pos = getAttackIDPosition(user, mimikryID);
                if (pos != -1)
                {
                    //learn attack at pos
                    learnAttack(user, lastAttack, pos);
                }
                else
                {
                    //has no mimikry as attack to replace => fail
                    noEffect();
                }
            }
            else
            {
                //enemy has no last attack
                noEffect();
            }
        }

        if (script == SpecialScript.REVENGE)
        {
            //racheangriff
            int targetKP = target.getAttributes().getHealth();
            int userKP = user.getAttributes().getHealth();
            if (targetKP > userKP)
            {
                //gegnerische kp auf eigene absenken
                adaptKP(target, userKP);
            }
            else
            {
                noEffect();
            }
        }

        if (script == SpecialScript.KONTER)
        {
            //kontert physischen treffer des gegners
            DamageTaken damageTaken = core.getEventFlags().getPoklmonFlags(user).getLastDamageTaken();
            if (damageTaken != null)
            {
                if (damageTaken.getAttackType() == AttackType.PHYSICAL)
                {
                    //nur physische attacken kontern
                    int damage = damageTaken.getDamage();
                    if (damage > 0)
                    {
                        //schaden auf gegner
                        int newdamage = damage * 2;
                        //TODO konter hit animation zeigen
                        core.getAttackProcess().doDamage(target, null, newdamage);
                        return;
                    }
                }
            }
            noEffect();
        }

        if (script == SpecialScript.SPECIALKONTER)
        {
            //kontert speziall treffer des gegners
            DamageTaken damageTaken = core.getEventFlags().getPoklmonFlags(user).getLastDamageTaken();
            if (damageTaken != null)
            {
                if (damageTaken.getAttackType() == AttackType.SPECIAL)
                {
                    //nur speziall attacken kontern
                    int damage = damageTaken.getDamage();
                    if (damage > 0)
                    {
                        //schaden auf gegner
                        int newdamage = damage * 2;
                        //TODO spiegelwand hit animation zeigen
                        core.getAttackProcess().doDamage(target, null, newdamage);
                        return;
                    }
                }
            }
            noEffect();
        }

        if (script == SpecialScript.VERGELTUNG)
        {
            //angriffstärke verdoppeln wenn getroffen 
            DamageTaken damageTaken = core.getEventFlags().getPoklmonFlags(user).getLastDamageTaken();
            if (damageTaken != null)
            {
                if (damageTaken.getDamage() > 0)
                {
                    //stärke verdoppeln
                    int damage = attack.getDamage() * 2;
                    attack.setDamage(damage);
                }
            }
        }

        if (script == SpecialScript.REFRESH)
        {
            //selbstheilung von statusveränderungen
            PoklmonStatusChanges status = user.getStatusChanges();
            if (status.hasMainStateChangeEffect())
            {
                //heal
                core.getEffectProcess().getHealProcess().healMainChangeStatus(user);
            }
            else
            {
                noEffect();
            }
        }

        if (script == SpecialScript.MAGNITUDE)
        {
            //intensität
            int[] intensStrength = {10, 30, 50, 70, 90, 110, 150};
            double[] chances = {0.05, 0.10, 0.20, 0.30, 0.20, 0.10, 0.5};
            int nr = StateEffectCalc.getChance(chances);
            String text = "Intensität " + (4 + nr) + "!";
            showText(text);
            showInfo(text);
            int damage = intensStrength[nr];
            attack.setDamage(damage);
        }

        if (script == SpecialScript.WASSERDRUCK)
        {
            //wasserdruck
            int[] strength = {20, 40, 50, 60, 80, 100, 200};

            double[] chances = {0.05, 0.10, 0.35, 0.20, 0.15, 0.10, 0.5};
            int nr = StateEffectCalc.getChance(chances);
            int damage = strength[nr];
            String text = "Wassdruck bei " + damage + "% !";
            showText(text);
            showInfo(text);
            attack.setDamage(damage);
        }


        if (script == SpecialScript.ATTACKWISH)
        {
            AttackContainer attacks = manager.getData().getAttacks();
            int id = (int)(attacks.getNumberOfAttacks() * BattleRandom.random());
            int oldId = 159;
            int pos = getAttackIDPosition(user, oldId);
            learnAttack(user, id, pos);
        }


        if (script == SpecialScript.KPMULTIPLYDAMAGE)
        {
            //kp mit schaden multiplitzieren
            float health = user.getAttributes().getHealthPercent();
            int damage = attack.getDamage();
            attack.setDamage(StateEffectCalc.rint(health * damage));
        }
        
        if(script ==SpecialScript.REMOVESHIELDS){          
            //schilder des gegners entfernen
            TeamEffectProcess teamEffects = core.getEffectProcess().getTeamEffectProcess();
            teamEffects.removeTeamEffect(target, TeamEffect.ENERGYBLOCK);
            teamEffects.removeTeamEffect(target, TeamEffect.LUCKBARRIERE);
            teamEffects.removeTeamEffect(target, TeamEffect.REFLECTOR);
            teamEffects.removeTeamEffect(target, TeamEffect.STATEBARRIERE);
            teamEffects.removeTeamEffect(target, TeamEffect.VALUEBARRIERE);
        }
    }

    public boolean cancelPlainAttack(UseAttack useAttack)
    {
        SpecialScript script = useAttack.getSpecialFunction();
        switch (script)
        {
            case VERGELTUNG:
                return false;
            case MAGNITUDE:
                return false;
            case KPMULTIPLYDAMAGE:
                return false;
            case WASSERDRUCK:
                return false;
            case REMOVESHIELDS:
                return false;
            default:
                return true;
        }
    }

    private int getAttackIDPosition(FightPoklmon poklmon, int attack)
    {
        for (int i = 0; i < 4; i++)
        {
            FightAttack fatk = poklmon.getAttacks()[i];
            if (fatk == null)
            {
                continue;
            }
            Attack atk = fatk.getAttack();
            int id = manager.getData().getAttacks().getAttackID(atk);
            if (id == attack)
            {
                return i;
            }
        }
        return -1;
    }

    private void learnAttack(FightPoklmon poklmon, int attack, int place)
    {
        Attack atk = manager.getData().getAttacks().getAttack(attack);
        int ap=poklmon.getAttacks()[place].getAp();//keep ap
        FightAttack learnAttack = new FightAttack(atk, ap);
        String atkName = atk.getName();
        String poklName = poklmon.getName();
        poklmon.getAttacks()[place] = learnAttack;
        String text =  TextContainer.get("dialog_LearnTM_Success",poklName,atkName);
        showText(text);
    }

    private void noEffect()
    {
        showText(TextContainer.get("attackNoEffect"));
    }

    private void adaptKP(FightPoklmon poklmon, int newkp)
    {
        int kp = poklmon.getAttributes().getHealth();
        if (kp > newkp)
        {
            //damage
            int damage = kp - newkp;
            core.getAttackProcess().doDamage(poklmon, null, damage);
        }
        else if (kp < newkp)
        {
            //heal
            int heal = newkp - kp;
            core.getEffectProcess().getInflictprocess().healPoklmon(poklmon,null, heal);
        }
    }

}
