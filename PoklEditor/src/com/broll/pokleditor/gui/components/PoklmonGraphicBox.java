package com.broll.pokleditor.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.broll.pokleditor.resource.ImageLoader;

public class PoklmonGraphicBox extends JPanel {

	private JButton image = new JButton();
	private String graphics;

	public PoklmonGraphicBox() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createRaisedBevelBorder());
		image.setPreferredSize(new Dimension(96, 96));

		add(image, BorderLayout.CENTER);

		image.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ArrayList<String> names = ImageLoader.listPoklmonImages();
				int index = names.indexOf(graphics);
				if (index == -1) {
					index = 0;
				}
				int select = ListSelection.openSelection(names, index);

				graphics = names.get(select);
				updateImage();

			}
		});
	}

	private void updateImage() {
		if (graphics == null) {
			image.setIcon(null);
			return;
		}
		image.setIcon(ImageLoader.loadPoklmonImage(graphics));
	}

	public void setImage(String i) {
		graphics = i;
		updateImage();
	}

	public String getImage() {
		return graphics;
	}

}
