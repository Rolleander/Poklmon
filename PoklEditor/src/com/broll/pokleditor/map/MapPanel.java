package com.broll.pokleditor.map;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.map.tileset.AreaSetPanel;
import com.broll.pokleditor.map.tileset.TileSetPanel;
import com.broll.pokleditor.map.util.MapGenerator;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.main.PoklLib;
import com.broll.pokllib.map.MapData;
import com.broll.pokllib.map.MapFile;

public class MapPanel extends JPanel
{

    private MapList list;
    private MapEditorPanel editor;
    private TileSetPanel tileset;
    private static AreaSetPanel areaset;
    private int lastID = -1;
    private static  JTabbedPane eastPane;
    private static JLabel tileInfo=new JLabel("");

    public MapPanel()
    {
        list = new MapList(this);
        list.setPreferredSize(new Dimension(150, 0));

        setLayout(new BorderLayout());
        add(list, BorderLayout.WEST);

        editor = new MapEditorPanel();
        add(editor, BorderLayout.CENTER);

      
        
        tileset = new TileSetPanel();
        areaset=new AreaSetPanel();
        JScrollPane scroll = new JScrollPane(tileset, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(32);
        
        JPanel tilePanel=new JPanel(new BorderLayout());
        tilePanel.add(scroll,BorderLayout.CENTER);
        tilePanel.add(tileInfo,BorderLayout.NORTH);
        
        eastPane=new JTabbedPane();
        eastPane.addTab("Map Tileset", GraphicLoader.loadIcon("map.png"), tilePanel);
        eastPane.addTab("Areas", GraphicLoader.loadIcon("areachart.png"), areaset);
        
        
        add(eastPane, BorderLayout.EAST);  
        
        eastPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
               editor.repaint();
            }
        });

        editor.setVisible(false);
        setBackground(MapEditorPanel.background);
    }
    
    public static void selectTile(int x, int y, int w, int h) {
    	if(w==1&&h==1) {
        	tileInfo.setText("X: "+x+" Y: "+y+ " [ID:"+(y*10+x)+"]");
    	}
    	else {
        	tileInfo.setText("X: "+x+" Y: "+y+ " W:"+w+" H:"+h);    		
    	}
    }
    
    public static boolean isAreaPanelSelected()
    {
        return eastPane.getSelectedIndex()==1;
    }
    
    public static void updateAreaPanel()
    {
        areaset.updateAreas();
    }

    public void view(int id)
    {
        save();
        lastID = id;
        try
        {
            MapData map = new MapData(PoklLib.data().readMap(id));
            editor.setVisible(true);
            editor.openMap(map);
            areaset.setMap(map);
        }
        catch (Exception e)
        {
            EditorWindow.showErrorMessage("Error loading Map: \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void save()
    {
        // Save to file
        MapData currentMap = editor.getMap();
        if (currentMap != null)
        {
            editor.save();
            MapFile file = currentMap.getFile();
            list.updateListEntry(currentMap.getFile().getName(), lastID);           
            PoklData.saveMap(file, lastID);
        }
    }

    public void addNewMap(int id)
    {
        save();
        MapData map = MapGenerator.generateNewMap();
        lastID = id;

        editor.setVisible(true);
        editor.openMap(map);
        PoklData.saveMap(map.getFile(), id);
    }
}
