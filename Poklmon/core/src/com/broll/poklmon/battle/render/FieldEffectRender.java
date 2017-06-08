package com.broll.poklmon.battle.render;

import java.util.HashMap;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.field.WeatherEffect;
import com.broll.poklmon.battle.render.weather.FogRender;
import com.broll.poklmon.battle.render.weather.NightRender;
import com.broll.poklmon.battle.render.weather.RainRender;
import com.broll.poklmon.battle.render.weather.SnowRender;
import com.broll.poklmon.battle.render.weather.StageEffectRender;
import com.broll.poklmon.battle.render.weather.StormRender;
import com.broll.poklmon.battle.render.weather.SunRender;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;

public class FieldEffectRender
{
    private BattleManager battle;
    private HashMap<WeatherEffect, StageEffectRender> weatherRenders = new HashMap<WeatherEffect, StageEffectRender>();

    public FieldEffectRender(BattleManager battle)
    {
        this.battle = battle;
        DataContainer data = battle.getData();
        weatherRenders.put(WeatherEffect.RAIN, new RainRender(data));
        weatherRenders.put(WeatherEffect.FOG, new FogRender(data));
        weatherRenders.put(WeatherEffect.NIGHT, new NightRender(data));
        weatherRenders.put(WeatherEffect.SUN, new SunRender(data));
        weatherRenders.put(WeatherEffect.SNOW, new SnowRender(data));
        weatherRenders.put(WeatherEffect.STORM, new StormRender(data));
    }

    public void update(final float delta)
    {
        iterate(new RenderIterator() {
            public void item(StageEffectRender render)
            {
                render.update(delta);
            }
        });
    }

    public void renderBackground(final Graphics g)
    {
        iterate(new RenderIterator() {
            public void item(StageEffectRender render)
            {
                render.renderBackground(g);
            }
        });
    }

    public void renderForeground(final Graphics g)
    {
        iterate(new RenderIterator() {
            public void item(StageEffectRender render)
            {
                render.renderForeground(g);
            }
        });
    }

    private interface RenderIterator
    {

        public void item(StageEffectRender render);
    }

    private void iterate(RenderIterator iterator)
    {
        FieldEffects fieldEffects = battle.getFieldEffects();
        if (fieldEffects.hasWeatherEffect())
        {
            WeatherEffect weather = fieldEffects.getWeatherEffect();

            if (weatherRenders.containsKey(weather))
            {
                iterator.item(weatherRenders.get(weather));
            }
        }
    }
}
