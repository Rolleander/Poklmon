package com.broll.poklmon.menu.state.sites;

import com.broll.pokllib.poklmon.AttributeType;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.menu.state.StateSite;
import com.broll.poklmon.poklmon.PoklmonAttributeCalculator;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.PoklmonData;

public class AttributesSite extends StateSite
{

    public AttributesSite(Poklmon poklmonInfo, PoklmonData poklmon, DataContainer data)
    {
        super(poklmonInfo, poklmon, data);

    }

    private int attack, defence, initiative;
    private int special_attack, special_defence;
    private int kp;
    private PoklmonWesen wesen;

    @Override
    protected void initData()
    {

        wesen = poklmon.getWesen();

        kp = PoklmonAttributeCalculator.getKP(data, poklmon);
        attack = PoklmonAttributeCalculator.getAttack(data, poklmon);
        defence = PoklmonAttributeCalculator.getDefence(data, poklmon);
        special_attack = PoklmonAttributeCalculator.getSpecialAttack(data, poklmon);
        special_defence = PoklmonAttributeCalculator.getSpecialDefence(data, poklmon);
        initiative = PoklmonAttributeCalculator.getInitiative(data, poklmon);
    }

    public int getKp()
    {
        return kp;
    }

    @Override
    public void render(Graphics g, float x, float y)
    {



        this.x = x;
        this.y = y;


        g.setFont(GUIFonts.hudText);

        lines = 0;

        this.lineWidth = 300;
        renderLine(g, "KP", "" + kp);
        renderLine(g, "Angriff", "" + attack);
        renderLine(g, "Verteidigung", "" + defence);
        renderLine(g, "Spez.Angriff", "" + special_attack);
        renderLine(g, "Spez.Verteidigung", "" + special_defence);
        renderLine(g, "Initiative", "" + initiative);
        renderLine(g, "", "");
        this.lineWidth = 150;
        renderLine(g, "Wesen", wesen.getName());
        AttributeType attPlus = wesen.getTypeInc();
        if (attPlus != null)
        {
            renderLine(g, "Plus", attPlus.getName());
        }
        AttributeType attMinus = wesen.getTypeDec();
        if (attMinus != null)
        {
            renderLine(g, "Minus", attMinus.getName());
        }

    }

}
