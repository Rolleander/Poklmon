package com.broll.pokleditor.map.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.broll.pokleditor.map.objects.MapObjectGenerator;
import com.broll.pokleditor.map.objects.ObjectType;

public class PopupMenu
{

    private static JMenu newobject;
    private static JMenuItem debug,edit,cut, copy, paste,delete;

    public static void activateObjectOptions(boolean active)
    {
        edit.setEnabled(active);
        delete.setEnabled(active);       
        copy.setEnabled(active);
        cut.setEnabled(active);
        newobject.setEnabled(!active);
        paste.setEnabled(!active);
    }

    public static JPopupMenu createPopupmenu(final MapControlInterface control)
    {

        JPopupMenu m = new JPopupMenu();
        
        debug = new JMenuItem("Debug Game");
        debug.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.debugFromHere();
            }
        });
        m.add(debug);
        
        JMenu menu = new JMenu("Map");
        menu.add(addItem("Resize Map", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.resizeMapDialog();
            }
        }));
        menu.add(addItem("Move Map", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.moveMapDialog();
            }
        }));
        m.add(menu);
        menu = new JMenu("Delete");
        menu.add(addItem("Clear Areas", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (areYourSure("clear all Areas"))
                {
                    control.clearAreas();
                }
            }
        }));
        menu.add(addItem("Delete AreaScripts", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (areYourSure("delete all AreaScripts"))
                {
                    control.deleteAreaScripts();
                }
            }
        }));
        menu.add(addItem("Clear Tiles", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (areYourSure("clear all Tiles"))
                {
                    control.clearTiles();
                }
            }
        }));
        menu.add(addItem("Delete all Objects", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (areYourSure("delete all Objects"))
                {
                    control.clearTiles();
                }
            }
        }));
        m.add(menu);

        newobject = new JMenu("New");
        newobject.add(addItem("Plain Object", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.addObject(MapObjectGenerator.openWizard(ObjectType.PLAIN));
                control.editObject();
            }
        }));
        newobject.add(addItem("Teleporter", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.addObject(MapObjectGenerator.openWizard(ObjectType.TELEPORTER));
            }
        }));
        newobject.add(addItem("Citizen", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.addObject(MapObjectGenerator.openWizard(ObjectType.CITIZEN));
                control.editObject();
            }
        }));
        newobject.add(addItem("Trainer", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.addObject(MapObjectGenerator.openWizard(ObjectType.TRAINER));
                control.editObject();
            }
        }));
        newobject.add(addItem("Item", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.addObject(MapObjectGenerator.openWizard(ObjectType.ITEM));
                control.editObject();              
            }
        }));
        newobject.add(addItem("Ledge", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.addObject(MapObjectGenerator.openWizard(ObjectType.LEDGE));
            }
        }));
        newobject.add(addItem("MapTile", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.addObject(MapObjectGenerator.openWizard(ObjectType.MAPTILE));
            }
        }));
        newobject.add(addItem("Remote", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.addObject(MapObjectGenerator.openWizard(ObjectType.REMOTE));
            }
        }));
        m.add(newobject);

        edit = new JMenuItem("Edit Object");
        edit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.editObject();
            }
        });
        m.add(edit);
        
        delete = new JMenuItem("Delete Object");
        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (areYourSure("delete this Object"))
                {
                    control.deleteSelectedObject();
                }
            }
        });
        m.add(delete);

        copy = new JMenuItem("Copy Object");
        copy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.copyObject();
            }
        });
        m.add(copy);
        
        cut = new JMenuItem("Cut Object");
        cut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.cutObject();
            }
        });
        m.add(cut);

        paste = new JMenuItem("Paste Object");
        paste.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                control.pasteObject();
            }
        });
        m.add(paste);

        return m;
    }

    private static JMenuItem addItem(String name, ActionListener action)
    {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(action);
        return item;
    }

    private static boolean areYourSure(String text)
    {
        return JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "Are you sure to " + text + "?", "Confirm Action",
                                                                     JOptionPane.OK_CANCEL_OPTION);
    }
    
  
}
