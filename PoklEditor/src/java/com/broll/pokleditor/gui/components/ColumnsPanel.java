package com.broll.pokleditor.gui.components;

import com.broll.pokleditor.window.VerticalLayout;

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class ColumnsPanel extends JPanel {

    private JPanel column;

    public ColumnsPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT,15,5));
        column = new JPanel(new VerticalLayout(VerticalLayout.TOP));
    }

    public void addCell(JComponent component) {
        column.add(component);
    }

    public void endColumn() {
        add(column);
        column = new JPanel(new VerticalLayout(VerticalLayout.TOP));
    }

}
