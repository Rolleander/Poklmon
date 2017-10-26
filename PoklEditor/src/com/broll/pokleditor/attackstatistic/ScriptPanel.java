package com.broll.pokleditor.attackstatistic;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.ElementType;

public class ScriptPanel extends StatisticPanel
{
    public ScriptPanel()
    {
        int rows = ElementType.values().length;
        setLayout(new GridLayout(rows + 1, 4));

    }

    @Override
    protected void calcData(ArrayList<Attack> attacks)
    {
        addTitle("Element");
        addTitle("Effect-Attacks");
        addTitle("Amount (%)");
        addTitle("Script-Lines");
        addTitle("Special Script Attacks");


        int[] effectAttacks = new int[ElementType.values().length];
        int[] ges = new int[effectAttacks.length];
        int[] scriptLines = new int[effectAttacks.length];
        int[] specialScripts = new int[effectAttacks.length];


        for (Attack a : attacks)
        {
            int nr = a.getElementType().ordinal();
            String script = a.getEffectCode();
            if (script != null && script.length() > 0)
            {
                effectAttacks[nr]++;
                scriptLines[nr]+=countLines(script);
                if(script.toLowerCase().contains("set specialscript to"))
                {
                    specialScripts[nr]++;
                }
            }
            ges[nr]++;
        }
        
        double[] amount=new double[effectAttacks.length];
        for(int i=0; i<amount.length; i++){            
            amount[i]=(double)effectAttacks[i]/ges[i];            
        }
            
        double dges = getMean(effectAttacks);
        double dlines = getMean(scriptLines);
        double dspec = getMean(specialScripts);
        double damount=getMean(amount)*100;

        for (int i = 0; i < effectAttacks.length; i++)
        {
            JLabel l = new JLabel(ElementType.values()[i].getName());
            add(l);

            writeNumber(effectAttacks[i], dges);
            
            int am=(int)(amount[i]*100);
            
            writeNumber(am, damount);
            writeNumber(scriptLines[i], dlines);
            writeNumber(specialScripts[i], dspec);

        }
    }




    private static int countLines(String str)
    {
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }



}
