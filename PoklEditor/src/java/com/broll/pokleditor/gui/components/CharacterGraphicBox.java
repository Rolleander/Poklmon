package com.broll.pokleditor.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.preview.AttackPreview;
import com.broll.pokleditor.gui.components.preview.CharacterPreview;
import com.broll.pokleditor.resource.ImageLoader;

public class CharacterGraphicBox extends JPanel {

    private JButton image = new JButton();
    private String graphics;

    public CharacterGraphicBox() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createRaisedBevelBorder());
        image.setPreferredSize(new Dimension(128, 128));

        add(image, BorderLayout.CENTER);

        image.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> names = ImageLoader.listCharacterImages();
                int index = names.indexOf(graphics);
                SearchableList.showList("Character", CharacterPreview.all(), index).ifPresent(it -> {
                    if (it == -1) {
                        graphics = null;
                    } else {
                        graphics = names.get(it);
                    }
                    updateImage();
                });
            }
        });
    }

    private void updateImage() {
        if (graphics == null) {
            image.setIcon(null);
            return;
        }
        image.setIcon(ImageLoader.loadCharacterImage(graphics));
    }

    public void setImage(String i) {
        graphics = i;
        updateImage();
    }

    public String getImage() {
        return graphics;
    }

}
