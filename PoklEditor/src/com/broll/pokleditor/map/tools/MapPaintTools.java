package com.broll.pokleditor.map.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.tools.Tool;

import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.map.MapTileEditor;
import com.broll.pokleditor.window.EditorWindow;

public class MapPaintTools extends JPanel{

	public  static CurrentTool selectedTool=CurrentTool.SELECTOR;
	public static int SELECTED_LAYER=0;
	private static JLabel coordinates=new JLabel("-x-");
	public static boolean hideOtherLayers=false;
    
	public MapPaintTools()
	{
		setBorder(BorderFactory.createSoftBevelBorder(0));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		ArrayList<JToggleButton> tools=new ArrayList<JToggleButton>();
		ToolListener toolListener=new ToolListener() {	
			@Override
			public void selected(int nr) {
				   selectedTool=CurrentTool.values()[nr];
			}
		};
		addTool("cursor.png",tools,toolListener);		
		addTool("pencil.png",tools,toolListener);
		addTool("draw_eraser.png",tools,toolListener);
		addTool("paintcan.png",tools,toolListener);
		
		ArrayList<JToggleButton> layers=new ArrayList<JToggleButton>();
		toolListener=new ToolListener() {	
			@Override
			public void selected(int nr) {
				  SELECTED_LAYER=nr;
				  if(hideOtherLayers) {
					  EditorWindow.repaint();
				  }
			}
		};
		addTool("layer.png", layers, toolListener);
		addTool("layer.png",  layers, toolListener);
		addTool("layer_stack.png",  layers, toolListener);
		addTool("layer_stack.png", layers, toolListener);
		
		
        
		tools.get(0).setSelected(true);
		tools.get(0).setBackground(selected);
		layers.get(0).setSelected(true);
		layers.get(0).setBackground(selected);
		add(coordinates);
		JCheckBox hide=new JCheckBox("Hide Other Layers");
		hide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hideOtherLayers=hide.isSelected();
			     EditorWindow.repaint();
			}
		});
		add(hide);
		
		JCheckBox box = new JCheckBox("Show Grid", false);
        box.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                MapTileEditor.showGrid = box.isSelected();
                EditorWindow.repaint();
            }
        });
        add(box);
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
    
	
	private void addTool( String icon, ArrayList<JToggleButton> tools, ToolListener toolListener )
	{
	    final JToggleButton b=new JToggleButton(		GraphicLoader.loadIcon(icon));
	    b.setPreferredSize(new Dimension(20,20));
	    b.setHorizontalAlignment(JToggleButton.CENTER);
	    b.setVerticalAlignment(JToggleButton.CENTER);	    
	    b.setBackground(unselected);
	    tools.add(b);
	    add(b);
	    b.addActionListener(new ActionListener() {         
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 int id=tools.indexOf(b);
                 toolListener.selected(id);
                 b.setBackground(selected);
              
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
