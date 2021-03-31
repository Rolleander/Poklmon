package com.broll.pokleditor.gui.components.preview;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.components.ColumnsPanel;
import com.broll.pokleditor.gui.components.SearchEntry;
import com.broll.pokleditor.resource.ImageLoader;
import com.broll.pokllib.poklmon.Poklmon;

import org.apache.commons.lang3.StringUtils;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CharacterPreview extends TextSearchEntry {

    private static List<SearchEntry> entries = new ArrayList<>();

    public static void init() {
        entries.add(new CharacterPreview("NONE", -1, new ImageIcon()));
        int nr = 0;
        for (String resource : ImageLoader.listCharacterImages()) {
            entries.add(new CharacterPreview(resource, nr, ImageLoader.loadCharacterImage(resource)));
            nr++;
        }
    }

    public CharacterPreview(String resource, int nr, ImageIcon image) {
        super(resource, nr);
        JLabel label = new JLabel();
        label.setIcon(image);
        add(label, BorderLayout.CENTER);
    }

    public static List<SearchEntry> all() {
        return entries;
    }

}
