package com.broll.pokleditor.attackstatistic;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackID;

public class StatisticOverview extends JPanel
{

    private ElementPanel elementPanel=new ElementPanel();
    private ScriptPanel scriptPanel=new ScriptPanel();
    
    public StatisticOverview()
    {
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();


        tabs.addTab("Overview", elementPanel);
        tabs.addTab("Script & Effects", scriptPanel);
        
        add(tabs, BorderLayout.CENTER);
    }

    public void calcStatistic()
    {
        //load all attacks
        ArrayList<Attack> attacks = new ArrayList<Attack>();
        for (AttackID id : PoklData.attacks.getAttack())
        {
            int i = id.getId();
            attacks.add(PoklData.loadAttack(i));
        }
        elementPanel.calc(attacks);
        scriptPanel.calc(attacks);
    }

}
