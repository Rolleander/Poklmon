package com.broll.pokleditor.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ObjectList extends JPanel
{

    private JList<String> liste;
    private DefaultListModel<String> listModel = new DefaultListModel<String>();
    private boolean isSelfAdjusting = false;
    private JLabel anz = new JLabel("");
    private JScrollPane panel;
    private boolean numbered = true;

    public ObjectList()
    {
        liste = new JList<String>(listModel);
        liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panel = new JScrollPane(liste, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(150, 500));
        add(panel, BorderLayout.CENTER);
        add(anz, BorderLayout.NORTH);
    }

    public void setNumbered(boolean numbered)
    {
        this.numbered = numbered;
    }

    public void setListSize(Dimension s)
    {
        panel.setPreferredSize(s);
    }

    public void addSelectionListener(final ActionListener update)
    {
        liste.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting() && !isSelfAdjusting)
                {
                    update.actionPerformed(null);

                }
                isSelfAdjusting = false;
            }
        });
    }

    public int getSelectedID()
    {
        return liste.getSelectedIndex();
    }

    public void initList(ArrayList<String> text)
    {
    	listModel.clear();
        for (int i = 0; i < text.size(); i++)
        {
            listModel.addElement(text.get(i));
        }
        anz.setText("Elements: " + text.size());
    }

    public int addEntry(String name)
    {
        String text = null;
        if (numbered)
        {
            text = (listModel.getSize() + ":" + name);
        }
        else
        {
            text = name;
        }
        listModel.addElement(text);
        int index = listModel.getSize() - 1;
        isSelfAdjusting = true;
        liste.setSelectedIndex(index);
        anz.setText("Elements: " + (index + 1));
        return index;
    }

    public void updateEntry(int id, String name)
    {
        String text = (id + ":" + name);
        listModel.set(id, text);
    }

    public void addRemoveListener(final ObjectRemoveListener remove)
    {
        liste.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e)
            {
            }

            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_DELETE)
                {
                    int id = getSelectedID();
                    if (id != -1)
                    {
                        listModel.remove(id);
                        anz.setText("Elements: " + (listModel.size()));
                        remove.remove(id);

                    }
                    liste.revalidate();
                }
            }

            public void keyPressed(KeyEvent e)
            {
            }
        });
    }


}
