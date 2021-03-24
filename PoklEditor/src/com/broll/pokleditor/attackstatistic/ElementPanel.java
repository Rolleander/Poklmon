package com.broll.pokleditor.attackstatistic;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.ElementType;

public class ElementPanel extends StatisticPanel
{

    public ElementPanel()
    {
        int rows = ElementType.values().length;
        setLayout(new GridLayout(rows + 1, 5));

    }

    @Override
    protected void calcData(ArrayList<Attack> attacks)
    {
        addTitle("Element");
        addTitle("Attacks");
        addTitle("Physical");
        addTitle("Special");
        addTitle("Status");


        int[] elements = new int[ElementType.values().length];
        int[] phys = new int[elements.length];
        int[] spec = new int[elements.length];
        int[] stat = new int[elements.length];

        for (Attack a : attacks)
        {
            int el = a.getElementType().ordinal();
            elements[el] += 1;
            switch (a.getAttackType())
            {
                case PHYSICAL:
                    phys[el]++;
                    break;
                case SPECIAL:
                    spec[el]++;
                    break;
                case STATUS:
                    stat[el]++;
                    break;
            }
        }

        double dges=getMean(elements);
        double dphys=getMean(phys);
        double dspec=getMean(spec);
        double dstat=getMean(stat);
        
        for (int i = 0; i < elements.length; i++)
        {
            JLabel l = new JLabel(ElementType.values()[i].getName());
            add(l);

            writeNumber(elements[i], dges);
            writeNumber(phys[i], dphys);
            writeNumber(spec[i], dspec);
            writeNumber(stat[i], dstat);
        }
    }

    


}
