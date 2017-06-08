package com.broll.pokleditor.gui.components;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.graphics.GraphicLoader;

public class WildPoklmonBox extends JPanel
{

    private PoklmonBox poklmon=new PoklmonBox("");
    private FloatBox chance=new FloatBox("%", 4);
    private LevelBox level1=new LevelBox("");
    private LevelBox level2=new LevelBox("-");
    
    
    public WildPoklmonBox()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        chance.setValue(0.2f);
        level1.setLevel(5);
        level2.setLevel(5);
        level1.setListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int l=level1.getLevel();
                if(l>level2.getLevel())
                {
                    level2.setLevel(l);
                }
            }
        });
        level2.setListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int l=level2.getLevel();
                if(l<level1.getLevel())
                {
                    level1.setLevel(l);
                }
            }
        });
        add(chance);
        add(level1);
        add(level2);
        add(poklmon);
    }
    
    public void setActionListener(ActionListener l) {
        JButton kill = GraphicLoader.newIconButton("cross.png");
        kill.addActionListener(l);
        add(kill);
    }
    
    public float getChance()
    {
        return chance.getValue();
    }
    
    public int getPoklmon()
    {
        return poklmon.getPoklmon();
    }
    
    public int getLevel1()
    {
        return level1.getLevel();
    }
    
    public int getLevel2()
    {
        return level2.getLevel();
    }
}
