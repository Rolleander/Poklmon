package com.broll.pokleditor.map.areas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JDialog;

import com.broll.pokleditor.gui.components.IDListSelector;
import com.broll.pokleditor.gui.components.ScriptBox;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.map.MapFile;

public class MapAreaScriptPanel extends JDialog
{

    private MapFile map;
    private IDListSelector selection = new IDListSelector("AreaScript");
    private MapAreaScriptBox script = new MapAreaScriptBox();

    public MapAreaScriptPanel()
    {
        setModal(true);
        setTitle("Map Area Scripts");
        setIconImage(GraphicLoader.loadImage("script.png"));
        setSize(new Dimension(800, 500));
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(EditorWindow.frame);

        setLayout(new BorderLayout());
        add(selection, BorderLayout.NORTH);
        add(script,BorderLayout.CENTER);
        selection.addAddButton("Add AreaScript", new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
                addNewScript();
            }
        });

        selection.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
                int sel = selection.getSelectedID();
                if (sel > -1)
                {
                    viewScript(sel);
                }
            }
        });
        script.setVisible(false);

        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent arg0)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowIconified(WindowEvent arg0)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent arg0)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeactivated(WindowEvent arg0)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent arg0)
            {
                // TODO Auto-generated method stub
                saveLast();
            }

            @Override
            public void windowClosed(WindowEvent arg0)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowActivated(WindowEvent arg0)
            {
                // TODO Auto-generated method stub

            }
        });
    }

    int lastID = -1;

    private void addNewScript()
    {
        String script = "";
        map.getAreaScripts().add(script);
        selection.addNewItem();
        int id = map.getAreaScripts().size() - 1;
        viewScript(id);
    }

    private void viewScript(int nr)
    {
        saveLast();
        lastID = nr;
        script.setVisible(true);
        script.setScript(map.getAreaScripts().get(nr));
    }

    public void open(MapFile map)
    {
        this.map = map;
        lastID=-1;
        script.setScript("");
        selection.clearList();
        if (map.getAreaScripts() != null)
        {
            selection.addItems(map.getAreaScripts().size());
        }
        else
        {
            script.setVisible(false);
            map.setAreaScripts(new ArrayList<String>());
        }
        this.setVisible(true);
    }

    public void saveLast()
    {
        if (lastID != -1)
        {
            String scr = script.getScript();
            map.getAreaScripts().set(lastID, scr);
        }
    }

}
