package com.broll.poklmon.menu.state.sites;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.menu.state.StateSite;
import com.broll.poklmon.poklmon.PoklmonAttributeCalculator;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.PoklmonData;
import com.broll.poklmon.save.PoklmonStatistic;

public class FameSite extends StateSite
{

    public FameSite(Poklmon poklmonInfo, PoklmonData poklmon, DataContainer data)
    {
        super(poklmonInfo, poklmon, data);

    }



    private PoklmonStatistic statistic;
    private int battleMedal,levelMedal,dvMedal,trainMedal,statMedal,arenaMedal,allMedal;

    @Override
    protected void initData()
    {
        statistic = poklmon.getStatistic();
        battleMedal=getMedalID(statistic.getFightedBattles()+statistic.getKilledPoklmons(), 1000);
        int l=poklmon.getLevel()-poklmon.getLevelCaught();
        levelMedal=getMedalID(l, 60);
        
        arenaMedal=getMedalID(statistic.getDefeatedArenas()+statistic.getLigaWins(), 7);
        
        int g=0;
        for(int dv: poklmon.getDv())
        {
            g+=dv;
        }       
        dvMedal=getMedalID(g, 145);
        
        g=0;
        for(int fp: poklmon.getFp())
        {
            g+=fp;
        }
        trainMedal=getMedalID(g, 450);
        
        
        int level = poklmon.getLevel();
        short[] dv = poklmon.getDv();
        short[] fp = poklmon.getFp();
         PoklmonWesen wesen = poklmon.getWesen();
         
        g=0;
        g+= PoklmonAttributeCalculator.getKP(poklmonInfo, level, dv[0], fp[0]);
        g+= PoklmonAttributeCalculator.getAttack(poklmonInfo, level, dv[1], dv[1], wesen);
        g+= PoklmonAttributeCalculator.getDefence(poklmonInfo, level, dv[2], dv[2], wesen);
        g+= PoklmonAttributeCalculator.getSpecialAttack(poklmonInfo, level, dv[3], dv[3], wesen);
        g+= PoklmonAttributeCalculator.getSpecialDefence(poklmonInfo, level, dv[4], dv[4], wesen);
        g+= PoklmonAttributeCalculator.getInitiative(poklmonInfo, level, dv[5], dv[5], wesen);
        statMedal=getMedalID(g, 1500);
        
        allMedal=getMedalID(battleMedal+levelMedal+arenaMedal+dvMedal+trainMedal+statMedal, 5*3);
    }

    @Override
    public void render(Graphics g, float x, float y)
    {



        this.x = x;
        this.y = y;


        g.setFont(GUIFonts.hudText);

        lines = 0;

        this.lineWidth = 300;


        renderLine(g, "Laborleiterkämpfe", "" + statistic.getDefeatedArenas());

        int liga = statistic.getLigaWins();

        if (liga > 0)
        {
            if (liga == 1)
            {
                renderLine(g, "Poklmonliga geschafft", "Ja");
            }
            else
            {
                renderLine(g, "Poklmonliga geschafft", "x" + liga);
            }
        }
        else
        {
            renderLine(g, "Poklmonliga besiegt", "Nein");
        }

        //medals

        SpriteSheet medals = data.getGraphics().getMenuGraphicsContainer().getMedals();
        renderLine(g, "Abzeichen:", "");
        renderImageLine(g, "Kämpfer", medals.getSprite(battleMedal, 0));        
        renderImageLine(g, "Entwicklung", medals.getSprite(levelMedal, 0));
        renderImageLine(g, "Genom", medals.getSprite(dvMedal, 0));
        renderImageLine(g, "Training", medals.getSprite(trainMedal, 0));
        renderImageLine(g, "Stärke", medals.getSprite(statMedal, 0));
        renderImageLine(g, "Meister", medals.getSprite(arenaMedal, 0));
        renderImageLine(g, "Gesamt", medals.getSprite(allMedal, 0));

    }

    private int getMedalID(float value, float max)
    {
        if (value >= max)
        {
            return 3;
        }
        else if (value >= max * 0.5)
        {
            return 2;
        }
        else if (value >= max * 0.25)
        {
            return 1;
        }
        return 0;
    }

}
