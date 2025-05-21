package com.broll.poklmon.battle.process.effects;

import com.broll.pokllib.poklmon.ElementType;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.calc.PoklmonTypeCheck;
import com.broll.poklmon.battle.calc.StateEffectCalc;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.field.WeatherEffect;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.process.BattleProcessControl;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.util.message.BattleMessages;

public class WeatherEffectProcess extends BattleProcessControl
{

    public WeatherEffectProcess(BattleManager manager, BattleProcessCore handler)
    {
        super(manager, handler);
    }

    public void preRound()
    {

    }

    public void postRound()
    {
        //check weather effects
        FieldEffects fieldEffects = manager.getFieldEffects();

        FightPoklmon player = manager.getParticipants().getPlayer();
        FightPoklmon enemy = manager.getParticipants().getEnemy();


        if (fieldEffects.isWeatherEffect(WeatherEffect.NIGHT))
        {
            //random chance poklmon einzuschläfern
            checkNightSleep(enemy);
            checkNightSleep(player);
        }

        if (fieldEffects.isWeatherEffect(WeatherEffect.SNOW))
        {
            //do damage to not ice poklmon
            doSnowDamage(enemy);
            doSnowDamage(player);
        }

        if (fieldEffects.isWeatherEffect(WeatherEffect.STORM))
        {
            //do damage to flying poklmon
            doStormDamage(enemy);
            doStormDamage(player);
        }

    }

    private void checkNightSleep(FightPoklmon poklmon)
    {
        if (!PoklmonTypeCheck.hasType(poklmon, ElementType.GHOST))
        {
            if (StateEffectCalc.nightSleepChance())
            {
                String text = "Der Mond strahlt in der Dunkelheit!";
                showText(text);
                core.getEffectProcess().getInflictprocess().setStatusChange(poklmon, MainFightStatus.SLEEPING);
            }
        }
    }

    private void doSnowDamage(FightPoklmon poklmon)
    {
        if (!PoklmonTypeCheck.hasType(poklmon, ElementType.ICE))
        {
            int maxKp = poklmon.getAttributes().getMaxhealth();
            int damage = StateEffectCalc.getSnowDamage(maxKp);
            String text = "# wird durch die Kälte verletzt!";
            String name = poklmon.getName();
            text = BattleMessages.putName(text, name);
            showText(text);
            core.getEffectProcess().getInflictprocess().damagePoklmon(poklmon, -1, damage);
        }

    }

    private void doStormDamage(FightPoklmon poklmon)
    {
        //flying poklmon not from type electro
        if (PoklmonTypeCheck.hasType(poklmon, ElementType.FLYING)&&!PoklmonTypeCheck.hasType(poklmon, ElementType.ELECTRO))
        {
            int maxKp = poklmon.getAttributes().getMaxhealth();
            int damage = StateEffectCalc.getStormDamage(maxKp);
            String text = "# wird durch das Gewitter verletzt!";
            String name = poklmon.getName();
            text = BattleMessages.putName(text, name);
            showText(text);
            core.getEffectProcess().getInflictprocess().damagePoklmon(poklmon, -1, damage);
        }

    }


    public void addWeatherEffect(WeatherEffect weatherEffect)
    {
        FieldEffects fieldEffects = manager.getFieldEffects();

        //change effect
        fieldEffects.setWeatherEffect(weatherEffect);
        String text = weatherEffect.getEntryName();
        if (text != null)
        {
            showText(text);
        }

    }
}
