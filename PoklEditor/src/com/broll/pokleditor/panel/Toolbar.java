package com.broll.pokleditor.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.map.MapTileEditor;
import com.broll.pokleditor.map.tools.MapPaintTools;
import com.broll.pokleditor.window.EditorWindow;

public class Toolbar
{

    private JToolBar bar;
    
    public Toolbar()
    {
        bar = new JToolBar();
        bar.setFloatable(false);

        addButton("","save_as.png", new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                EditorWindow.save();
            }
        });

        addButton("Edit Pokldex","poklball.png", new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                EditorWindow.openPokldex();
            }
        });

        addButton("Edit Attacks","fire.png", new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                EditorWindow.openAttackdex();
            }
        });

        addButton("Edit Animations","rainbow.png", new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                EditorWindow.openAnimationdex();
            }
        });
        
        addButton("Edit Items","key.png", new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                EditorWindow.openItemdex();
            }
        });
        
        addButton("Poklmon Statistic","poklball.png", new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
             
            }
        });
        
        addButton("Attack Statistic","fire.png", new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                EditorWindow.openAttackStatistic();
            }
        });

        bar.add(Box.createHorizontalStrut(100));

    
        
        MapPaintTools paint=new MapPaintTools();
        bar.add(paint);
        

    }

  

    private void addButton(String name, String icon, ActionListener action)
    {
        JButton button = new JButton(name, GraphicLoader.loadIcon(icon));

        button.addActionListener(action);

        bar.add(button);
    }

    public JToolBar getBar()
    {
        return bar;
    }

}
