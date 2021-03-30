package com.broll.pokleditor.window;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.broll.pokleditor.gui.GraphicLoader;

public class LoadingWindow
{
    
    private static JFrame frame;
    
    

    public static void open()
    {
        frame=new JFrame("PoklEditor - Loading");
        frame.setSize(500, 200);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        JLabel loading=new JLabel(GraphicLoader.loadIcon("loading.gif"));
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.add(loading,BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    
    public static void close()
    {
        frame.dispose();
    }
}
