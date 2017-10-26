package com.broll.pokleditor.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class BugSplashDialog
{

 
    public static void showError(String error)
    {        
        Icon icon=null;    
        JTextArea l=new JTextArea(error);
        l.setEditable(false);
        l.setMaximumSize(new Dimension(600,600));
        l.setPreferredSize(new Dimension(600,200));
        l.setForeground(new Color(200,0,0));
        l.setFont(l.getFont().deriveFont(16f));
        JOptionPane.showMessageDialog(null,l, "Poklmon Error Splash", JOptionPane.ERROR_MESSAGE, icon);
        System.exit(0);
    }
    
   
}
