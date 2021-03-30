package com.broll.pokleditor.gui.components.preview;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.components.ColumnsPanel;
import com.broll.pokleditor.gui.components.SearchEntry;
import com.broll.pokleditor.resource.ImageLoader;
import com.broll.pokleditor.window.VerticalLayout;
import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.poklmon.Poklmon;

import org.apache.commons.lang3.StringUtils;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AttackPreview extends TextSearchEntry {


    private Attack attack;

    public AttackPreview(Attack attack) {
        super(attack.getName(), attack.getId());
        this.attack = attack;

        add(new JLabel(attack.getDescription()), BorderLayout.SOUTH);

        ColumnsPanel center = new ColumnsPanel();
        center.addCell(new JLabel(attack.getElementType().getName()));
        center.endColumn();
        center.addCell(new JLabel(attack.getAttackType().name()));
        center.endColumn();
        center.addCell(new JLabel("Damage: " + attack.getDamage().getDamage()));
        center.endColumn();
        center.addCell(new JLabel("AP: " + attack.getDamage().getAp()));
        center.endColumn();
        add(center, BorderLayout.CENTER);
    }

    @Override
    public boolean filtered(String text) {

        if (StringUtils.containsIgnoreCase(text, attack.getAttackType().name())) {
            return true;
        }
        if (StringUtils.containsIgnoreCase(text, attack.getElementType().getName())) {
            return true;
        }

        return super.filtered(text);
    }


    public static List<SearchEntry> all() {
        return PoklDataUtil.getAllAttacks().stream().map(AttackPreview::new).collect(Collectors.toList());
    }


}
