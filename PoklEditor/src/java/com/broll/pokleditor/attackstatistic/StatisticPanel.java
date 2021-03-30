package com.broll.pokleditor.attackstatistic;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokllib.attack.Attack;

public abstract class StatisticPanel extends JPanel
{

    public void calc(ArrayList<Attack> attacks)
    {
        this.removeAll();
        calcData(attacks);
        this.revalidate();
        this.repaint();
    }

    protected double getMean(int[] values)
    {
        double d = 0;
        for (int i = 0; i < values.length; i++)
        {
            d += values[i];
        }
        return d / values.length;
    }
    
    protected double getMean(double[] values)
    {
        double d = 0;
        for (int i = 0; i < values.length; i++)
        {
            d += values[i];
        }
        return d / values.length;
    }
    

    protected void writeNumber(int n, double d)
    {
        JLabel v = new JLabel("" + n);
        if (n >= d)
        {
            v.setForeground(new Color(0, 200, 0));
        }
        else
        {
            v.setForeground(new Color(200, 0, 0));
        }
        add(v);
    }

    protected void writeNumber(double n, double d)
    {
        JLabel v = new JLabel("" + n);
        if (n >= d)
        {
            v.setForeground(new Color(0, 200, 0));
        }
        else
        {
            v.setForeground(new Color(200, 0, 0));
        }
        add(v);
    }

    protected void addTitle(String text)
    {
        JLabel l = new JLabel(text);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        add(l);
    }

    protected abstract void calcData(ArrayList<Attack> attacks);
}
