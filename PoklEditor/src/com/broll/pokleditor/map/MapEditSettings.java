package com.broll.pokleditor.map;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.StringBox;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.map.areas.MapAreaScriptPanel;
import com.broll.pokllib.map.MapData;

public class MapEditSettings extends JPanel
{
    private MapData map;
    
    
    private StringBox name=new StringBox("Name", 20);
    private MapAreaScriptPanel areascripts=new MapAreaScriptPanel();
    
    public MapEditSettings()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(name);
        
        JButton editScripts=new JButton("Edit Area Scripts",GraphicLoader.loadIcon("script.png"));
        add(editScripts);
        
        editScripts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
                areascripts.open(map.getFile());
                MapPanel.updateAreaPanel();
            }
        });
        
        JButton initScript=new JButton("Edit Init Script");
        add(initScript);
        initScript.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
    }  
    
    public void setMap(MapData data) {
        map = data;
        name.setText(data.getFile().getName());
    }

    public void save() {
        map.getFile().setName(name.getText());
    }

}
