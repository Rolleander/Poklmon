package com.broll.poklmon.menu.state.sites;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.menu.state.StateSite;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.PoklmonData;
import com.broll.poklmon.save.PoklmonStatistic;

public class StatisticSite extends StateSite
{

    public StatisticSite(Poklmon poklmonInfo, PoklmonData poklmon, DataContainer data)
    {
        super(poklmonInfo, poklmon, data);

    }



    private PoklmonStatistic statistic;
    
    @Override
    protected void initData()
    {
        statistic=poklmon.getStatistic();
    }

    @Override
    public void render(Graphics g, float x, float y)
    {

        this.x = x;
        this.y = y;


        g.setFont(GUIFonts.hudText);

        lines = 0;

        this.lineWidth = 300;

        renderLine(g, "Kämpfe", ""+statistic.getFightedBattles());
        int wild=statistic.getFightedBattles()-statistic.getTrainerBattles();
        renderLine(g, "Gegen Trainer", ""+statistic.getTrainerBattles());
        renderLine(g, "Gegen Poklmon", ""+wild);
        renderLine(g, "", "");
        renderLine(g, "Poklmon besiegt", ""+statistic.getKilledPoklmons());
        renderLine(g, "Wurde besiegt", ""+statistic.getFaintedCount());
    }

}
