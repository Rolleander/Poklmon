package com.broll.pokleditor.map.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.broll.pokleditor.gui.graphics.GraphicLoader;

public class MapPaintTools extends JPanel{

	public  static CurrentTool selectedTool=CurrentTool.SELECTOR;
	private ArrayList<JToggleButton> tools=new ArrayList<JToggleButton>();
	private static JLabel coordinates=new JLabel("-x-");
    
	public MapPaintTools()
	{
		setBorder(BorderFactory.createSoftBevelBorder(0));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		addTool("cursor.png");		
		addTool("pencil.png");
		addTool("draw_eraser.png");
		addTool("paintcan.png");
		
        
		tools.get(0).setSelected(true);
		tools.get(0).setBackground(selected);
		
		add(coordinates);
	}
	
	  public static void setMapCoordinates(int x, int y)
	    {
	        coordinates.setText(x+"x"+y);
	    }
	
	  public static void resetMapCoordinates()
	  {
	      coordinates.setText("-x-");
	  }
	  
	private Color selected=new Color(255,255,255);
	private Color unselected=new Color(55,55,55);
    
	
	private void addTool( String icon)
	{
	    final JToggleButton b=new JToggleButton(GraphicLoader.loadIcon(icon));
	    b.setPreferredSize(new Dimension(20,20));
	    b.setBackground(unselected);
	    tools.add(b);
	    add(b);
	    b.addActionListener(new ActionListener() {         
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 int id=tools.indexOf(b);
                 b.setBackground(selected);
                 selectedTool=CurrentTool.values()[id];
                 for(int i=0; i<tools.size(); i++)
                 {
                     if(i!=id)
                     {
                         tools.get(i).setSelected(false);
                         tools.get(i).setBackground(unselected);
                     }
                 }
            }
        });
	}
	
	
	
	
	
}
