package com.broll.pokleditor.gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.gui.components.preview.PoklmonPreview;

public class PoklmonBox extends JPanel {

    public final static String nopoke = "NONE";
    private int poklmon;
    private JLabel pokename = new JLabel(nopoke);

    public PoklmonBox(String text) {
        pokename.setForeground(Color.BLUE);
        add(new JLabel(text));
        add(pokename);
        JButton select = GraphicLoader.newIconButton("setting_tools.png");
        add(select);

        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SearchableList.showList("Poklmon", PoklmonPreview.all(true), poklmon).ifPresent(it -> setPoklmon(it));
            }
        });
    }

    public int getPoklmon() {
        return poklmon;
    }

    public void setPoklmon(int poklmon) {
        this.poklmon = poklmon;
        if (poklmon == -1) {
            pokename.setText(nopoke);
        } else {
            pokename.setText(PoklDataUtil.getAllPoklmonNames().get(poklmon));
        }
    }
}
