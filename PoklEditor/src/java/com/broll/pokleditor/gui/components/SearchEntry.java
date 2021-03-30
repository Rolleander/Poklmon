package com.broll.pokleditor.gui.components;

import org.apache.commons.lang3.StringUtils;

import javax.swing.JComponent;

public abstract class SearchEntry extends JComponent {

    private String text;
    private int key;

    public SearchEntry(String text, int key) {
        this.text = text;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    public boolean filtered(String text) {
        return StringUtils.containsIgnoreCase(this.text, text);
    }

    public abstract void updateFocus(boolean isSelected, boolean cellHasFocus);
}
