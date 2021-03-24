package com.broll.poklmon.battle.process.effects;

import com.broll.pokllib.poklmon.ElementType;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.calc.PoklmonTypeCheck;
import com.broll.poklmon.battle.calc.StateEffectCalc;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.field.GlobalEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.message.BattleMessages;

public class GlobalEffectProcess extends BattleProcessControl
{

    public GlobalEffectProcess(BattleManager manager, BattleProcessCore handler)
    {
        super(manager, handler);
    }

    public void preRound()
    {
        //müll verschwindet nach 5 runden
        tryRemoveEffect(GlobalEffect.DUMP, 5);
    }

    public void postRound()
    {
        FieldEffects fieldEffects = manager.getFieldEffects();
        FightPoklmon player = manager.getParticipants().getPlayer();
        FightPoklmon enemy = manager.getParticipants().getEnemy();

        //check dump effect
        if (fieldEffects.hasGlobalEffect(GlobalEffect.DUMP))
        {
            doDumpDamage(enemy);
            doDumpDamage(player);
        }
    }


    private void doDumpDamage(FightPoklmon poklmon)
    {
        int maxKp = poklmon.getAttributes().getMaxhealth();
        int damage = StateEffectCalc.getSnowDamage(maxKp);
        String name = poklmon.getName();
        if (!PoklmonTypeCheck.hasType(poklmon, ElementType.POISON))
        {
            //damage
            String text = "# wird durch giftige Gase verletzt!";
            text = BattleMessages.putName(text, name);
            showText(text);
            core.getEffectProcess().getInflictprocess().damagePoklmon(poklmon, -1, damage);
        }
        else
        {
            //heal
            String text = "# erholt sich durch die giftigen Gase!";
            text = BattleMessages.putName(text, name);
          //  showText(text);
            core.getEffectProcess().getInflictprocess().healPoklmon(poklmon,text, damage);
        }
    }

    public void addGlobalEffect(FightPoklmon invoker, GlobalEffect globalEffect)
    {
        FieldEffects fieldEffects = manager.getFieldEffects();
        if (!fieldEffects.hasGlobalEffect(globalEffect))
        {
            //add effect
            fieldEffects.addGlobalEffect(globalEffect);
            String text = globalEffect.getEntryName();
            if (text != null)
            {
                text = BattleMessages.putName(text, invoker.getName());
                showText(text);
            }
        }
    }

    public void tryRemoveEffect(GlobalEffect effect, int minRounds)
    {
        FieldEffects fieldEffects = manager.getFieldEffects();
        if (fieldEffects.hasGlobalEffect(effect))
        {
            if (fieldEffects.getGlobalEffectDuration(effect) >= minRounds)
            {
                removeGlobalEffect(effect);
            }
        }
    }

    private void removeGlobalEffect(GlobalEffect effect)
    {
        FieldEffects fieldEffects = manager.getFieldEffects();
        if (fieldEffects.hasGlobalEffect(effect))
        {
            String text = effect.getExitName();
            if (text != null)
            {
                showText(text);
            }
            fieldEffects.removeGlobalEffect(effect);
        }
    }

}
