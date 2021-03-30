package com.broll.pokleditor.pokldex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokllib.poklmon.AttributeCalculator;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonAttributes;

public class PoklEditAttributes extends JPanel
{

    private Poklmon poklmon;
    private String[] fields = {"KP", "Attack", "Defence", "SpecialAttack", "SpecialDefence", "Initiative"};
    private JTable table;
    private PoklEditAttacks attacks = new PoklEditAttacks();
    private JLabel info = new JLabel("");
    private JLabel info2 = new JLabel("");
    
    public PoklEditAttributes()
    {
        setLayout(new BorderLayout());
        info.setForeground(Color.BLUE);
        info2.setForeground(Color.BLUE);
        
        String[][] data = new String[2][6];
        table = new JTable(data, fields) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return row == 0;
            }
        };

        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setRowHeight(25);
        
        JPanel north = new JPanel(new BorderLayout());

        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(700, 75));

        north.add(pane, BorderLayout.CENTER);

        JPanel inf = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel ex=new JLabel(GraphicLoader.loadIcon("information.png"));
        ex.setToolTipText("|1100 Vorentwicklung |1500 Schwach |1600 Normal |1700 Stark | 1800+ Legend�r |");
        inf.add(ex);
       
        inf.add(new JLabel("MAX"));
        inf.add(info);
        inf.add(new JLabel("Base"));
        inf.add(info2);
        
        north.add(inf, BorderLayout.SOUTH);

        add(north, BorderLayout.NORTH);

        add(attacks, BorderLayout.CENTER);

        table.getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {
            public void editingStopped(ChangeEvent e)
            {
                updateTable();

            }

            public void editingCanceled(ChangeEvent e)
            {
            }
        });
    }

    public void setPoklmon(Poklmon poklmon)
    {
        this.poklmon = poklmon;
        PoklmonAttributes att = poklmon.getAttributes();
        table.setValueAt("" + att.getBaseKP(), 0, 0);
        table.setValueAt("" + att.getBaseAttack(), 0, 1);
        table.setValueAt("" + att.getBaseDefence(), 0, 2);
        table.setValueAt("" + att.getBaseSpecialAttack(), 0, 3);
        table.setValueAt("" + att.getBaseSpecialDefence(), 0, 4);
        table.setValueAt("" + att.getBaseInitiative(), 0, 5);
        attacks.setPoklmon(poklmon);
        updateTable();
    }

    public void save()
    {

        PoklmonAttributes att = poklmon.getAttributes();
        att.setBaseKP(read(0, 0));
        att.setBaseAttack(read(0, 1));
        att.setBaseDefence(read(0, 2));
        att.setBaseSpecialAttack(read(0, 3));
        att.setBaseSpecialDefence(read(0, 4));
        att.setBaseInitiative(read(0, 5));
        attacks.save();
    }

    private void updateTable()
    {
        int DV = 31;
        int FP = 252;
        float wesen = 1f;
        int level = 100;
        table.setValueAt("" + AttributeCalculator.calcKP(read(0, 0), level, DV, FP), 1, 0);
        table.setValueAt("" + AttributeCalculator.calcAttribute(read(0, 1), level, DV, FP, wesen), 1, 1);
        table.setValueAt("" + AttributeCalculator.calcAttribute(read(0, 2), level, DV, FP, wesen), 1, 2);
        table.setValueAt("" + AttributeCalculator.calcAttribute(read(0, 3), level, DV, FP, wesen), 1, 3);
        table.setValueAt("" + AttributeCalculator.calcAttribute(read(0, 4), level, DV, FP, wesen), 1, 4);
        table.setValueAt("" + AttributeCalculator.calcAttribute(read(0, 5), level, DV, FP, wesen), 1, 5);
        info.setText("" + getAttributeSumme());
        info2.setText("" + getAttributeBaseSumme());
        
    }

    public int getAttributeSumme()
    {
        return read(1, 0) + read(1, 1) + read(1, 2) + read(1, 3) + read(1, 4) + read(1, 5);
    }
    
    public int getAttributeBaseSumme()
    {
        return read(0, 0) + read(0, 1) + read(0, 2) + read(0, 3) + read(0, 4) + read(0, 5);
    }


    public int read(int row, int col)
    {
        try
        {
            return Integer.parseInt("" + table.getValueAt(row, col));
        }
        catch (Exception e)
        {
            return 0;
        }
    }
}
