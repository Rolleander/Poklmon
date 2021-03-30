package com.broll.pokleditor.gui.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.GraphicLoader;

public class IDListSelector extends JPanel
{

    private JComboBox<String> box=new JComboBox<String>();
    private String itemNames;
    
    public IDListSelector(String itemNames)
    {
        this.itemNames=itemNames;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(new Color(200,200,200));
        add(box);
    }
    
    public void addNewItem()
    {
        int id=box.getItemCount();
        box.addItem(itemNames+" "+id);
        box.setSelectedIndex(id);
    }
    
    public void addItems(int count)
    {
        for(int i=0; i<count; i++)
        {
            int id=box.getItemCount();
            box.addItem(itemNames+" "+id);
        }
    }
    
    public void clearList()
    {
        box.removeAllItems();
     
    }
    
    public int getSelectedID()
    {
        return box.getSelectedIndex();
    }
    
    public void addSelectionListener(ActionListener action)
    {
        box.addActionListener(action);
    }
    
    public void addAddButton(String name, ActionListener action)
    {
        JButton add=new JButton(name,GraphicLoader.loadIcon("plus.png"));
        add(add);
        add.addActionListener(action);
    }
}
