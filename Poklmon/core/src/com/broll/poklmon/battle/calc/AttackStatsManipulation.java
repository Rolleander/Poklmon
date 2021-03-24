package com.broll.poklmon.battle.calc;

import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.field.PoklmonTeamEffect;
import com.broll.poklmon.battle.field.TeamEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;

public class AttackStatsManipulation
{

    private static FieldEffects fieldEffects;

    public static void init(FieldEffects fieldEffects)
    {
        AttackStatsManipulation.fieldEffects = fieldEffects;
    }


    public static void doChanges(UseAttack attack, FightPoklmon user, FightPoklmon target)
    {

        PoklmonTeamEffect targetTeam = fieldEffects.getTeamEffects(target);
        PoklmonTeamEffect userTeam = fieldEffects.getTeamEffects(user);
        PoklmonStatusChanges userStatus = user.getStatusChanges();
        PoklmonStatusChanges targetStatus = target.getStatusChanges();

        if (targetTeam.hasTeamEffect(TeamEffect.LUCKBARRIERE))
        {
            // volltreffer und zurückschrecken verhindern
            attack.setVolltrefferChance(0);
            if (attack.getEffectStatusTarget() == EffectStatus.CRINGE)
            {
                attack.setEffectStatusTarget(null);
            }
        }

        if (targetTeam.hasTeamEffect(TeamEffect.VALUEBARRIERE))
        {
            //weißnebel verhindert veränderung der statuswerte
            attack.clearAttrbiuteChangeTarget();
        }

        if (userStatus.hasEffectChange(EffectStatus.ENERGYFOCUS))
        {
            //volltrefferchance erhöhen bei energiefokus
            int vtchance = attack.getVolltrefferChance();
            attack.setVolltrefferChance(vtchance + 2);
        }

        //verhindern von statusänderungen 
        if (targetTeam.hasTeamEffect(TeamEffect.STATEBARRIERE))
        {
            attack.setChangeStatusTarget(null);
        }
        if (userTeam.hasTeamEffect(TeamEffect.STATEBARRIERE))
        {
            attack.setChangeStatusUser(null);
        }
    }


}
