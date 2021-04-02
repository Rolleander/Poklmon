package com.broll.pokleditor.gui.components.preview;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.gui.components.ColumnsPanel;
import com.broll.pokleditor.gui.components.SearchEntry;
import com.broll.pokleditor.resource.ImageLoader;
import com.broll.pokleditor.window.VerticalLayout;
import com.broll.pokllib.poklmon.Poklmon;

import org.apache.commons.lang3.StringUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PoklmonPreview extends TextSearchEntry {


    private Poklmon poklmon;
    private static Map<String, ImageIcon> images = new HashMap<>();
    private static Poklmon NONE = new Poklmon();

    public static void init() {
        ImageLoader.listPoklmonImages().forEach(resoucre -> {
            ImageIcon image = new ImageIcon(ImageLoader.loadPoklmonImage(resoucre).getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH));
            images.put(resoucre, image);
        });
        NONE.setName("None");
        NONE.setGraphicName("todo.png");
        NONE.setId(-1);
    }

    public PoklmonPreview(Poklmon poklmon, Poklmon preStage) {
        super(poklmon.getName(), poklmon.getId());
        this.poklmon = poklmon;
        if (poklmon.getId() == -1) {
            return;
        }
        ColumnsPanel center = new ColumnsPanel();
        if (poklmon.getGraphicName() != null) {
            JLabel label = new JLabel();
            label.setIcon(images.get(poklmon.getGraphicName()));
            //  label.setPreferredSize(new Dimension(48, 48));
            center.addCell(label);
        }
        center.endColumn();

        if (poklmon.getBaseType() != null) {
            center.addCell(new JLabel(poklmon.getBaseType().getName()));
        }
        if (poklmon.getSecondaryType() != null) {
            center.addCell(new JLabel(poklmon.getSecondaryType().getName()));
        }
        center.endColumn();
        center.addCell(new JLabel("#" + poklmon.getPokldexNumber()));
        int evolveTo = poklmon.getEvolveIntoPoklmon();
        if (evolveTo != -1 || preStage != null) {
            center.endColumn();
            if (preStage != null) {
                center.addCell(new JLabel("Base:  " + preStage.getId() + ". " + preStage.getName() + " evolves Lv. " + preStage.getEvolveLevel()));
            }
            if (evolveTo != -1) {
                Poklmon evolve = PoklData.loadPoklmon(evolveTo);
                String into = evolve.getName();
                center.addCell(new JLabel("Lv." + poklmon.getEvolveLevel() + " evolves into " + evolve.getId() + ". " + into));
            }
        }
        center.endColumn();

        add(center, BorderLayout.CENTER);
    }

    @Override
    public boolean filtered(String text) {
        if (poklmon.getSecondaryType() != null) {
            if (StringUtils.containsIgnoreCase(text, poklmon.getSecondaryType().getName())) {
                return true;
            }
        }
        if (poklmon.getBaseType() != null) {
            if (StringUtils.containsIgnoreCase(text, poklmon.getBaseType().getName())) {
                return true;
            }
        }
        return super.filtered(text);
    }

    public static List<SearchEntry> all() {
        return all(false);
    }

    public static List<SearchEntry> all(boolean allowNone) {
        List<Poklmon> poklmons = PoklDataUtil.getAllPoklmons();
        Map<Poklmon, Poklmon> preStages = new HashMap<>();
        poklmons.forEach(poklmon -> {
            if (poklmon.getEvolveIntoPoklmon() != -1) {
                preStages.put(PoklData.loadPoklmon(poklmon.getEvolveIntoPoklmon()), poklmon);
            }
        });
        List<SearchEntry> entries = poklmons.stream().map(pokl -> new PoklmonPreview(pokl, preStages.get(pokl))).collect(Collectors.toList());
        if (allowNone) {
            entries.add(0, new PoklmonPreview(NONE, null));
        }
        return entries;
    }


}
