package com.broll.pokleditor.gui.components.preview;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.components.ColumnsPanel;
import com.broll.pokleditor.gui.components.SearchEntry;
import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.item.Item;

import org.apache.commons.lang3.StringUtils;

import java.awt.BorderLayout;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;

public class ItemPreview extends TextSearchEntry {


    private Item item;

    public ItemPreview(Item item) {
        super(item.getName(), item.getId());
        this.item = item;

        add(new JLabel(item.getDescription()), BorderLayout.SOUTH);

        ColumnsPanel center = new ColumnsPanel();
        center.addCell(new JLabel(item.getType().getName()));
        center.endColumn();
        center.addCell(new JLabel(item.getPrice()+"$"));
        center.endColumn();
        add(center, BorderLayout.CENTER);
    }

    @Override
    public boolean filtered(String text) {

        if (StringUtils.containsIgnoreCase(text, item.getType().getName())) {
            return true;
        }

        return super.filtered(text);
    }


    public static List<SearchEntry> all() {
        return PoklDataUtil.getAllItems().stream().map(ItemPreview::new).collect(Collectors.toList());
    }


}
