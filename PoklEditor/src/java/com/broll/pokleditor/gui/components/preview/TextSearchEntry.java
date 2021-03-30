package com.broll.pokleditor.gui.components.preview;

import com.broll.pokleditor.gui.components.SearchEntry;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

public class TextSearchEntry extends SearchEntry {
    private JLabel label;
    private Color defaultColor = new Color(200,200,200);
    private Color selectedColor = new Color(50,50,250);

    public TextSearchEntry(String text, int key) {
        super(text, key);
        setLayout(new BorderLayout());
        label = new JLabel(key + ": " + text);
        label.setOpaque(true);
        add(label, BorderLayout.NORTH);
    }

    @Override
    public void updateFocus(boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            label.setBackground(selectedColor);
            label.setForeground(Color.WHITE);
        } else {
            label.setBackground(defaultColor);
            label.setForeground(Color.BLACK);
        }
    }
}
