package com.broll.pokleditor.map.areas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.components.WildPoklmonBox;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.window.VerticalLayout;

public class WildPoklmonWizard extends JPanel
{

    private JPanel poklmons = new JPanel();

    public WildPoklmonWizard()
    {
        setPreferredSize(new Dimension(500, 300));

        poklmons.setLayout(new VerticalLayout(-10));

        setLayout(new BorderLayout());

        add(new JScrollPane(poklmons), BorderLayout.CENTER);

        addPoklmon();
        JButton add = new JButton("Add Poklmon", GraphicLoader.loadIcon("plus.png"));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addPoklmon();
            }
        });
        
        add(add,BorderLayout.SOUTH);
    }

    private void addPoklmon()
    {
        final WildPoklmonBox box = new WildPoklmonBox();
        box.setActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                poklmons.remove(box);
                revalidate();
                repaint();
            }
        });
        poklmons.add(box);
        revalidate();
        repaint();
    }


    public String getScript()
    {

        String script = "";
        ArrayList<String> names = PoklDataUtil.getAllPoklmonNames();
        for (Component c : poklmons.getComponents())
        {
            WildPoklmonBox box = (WildPoklmonBox)c;
            int id = box.getPoklmon();
            float v = box.getChance();
            int l1=box.getLevel1();
            int l2=box.getLevel2();         
            String name = names.get(id);
            script += "//Wild Poklmon: " + name + " (Lvl. "+l1+" - Lvl."+l2+") \n";
            script += "area.addWildPoklmon(" + id + " , " + v + " , " + l1+ " , " + l2 + ")\n";
        }

        return script;
    }




}
