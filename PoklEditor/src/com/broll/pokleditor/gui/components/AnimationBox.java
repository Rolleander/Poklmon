package com.broll.pokleditor.gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.graphics.GraphicLoader;

public class AnimationBox extends JPanel {

	private int animation;
	private JLabel animationName = new JLabel();

	public AnimationBox(String text) {
		animationName.setForeground(Color.BLUE);
		add(new JLabel(text));
		add(animationName);
		JButton select = GraphicLoader.newIconButton("setting_tools.png");
		add(select);

		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				ArrayList<String> names = PoklDataUtil.getAllAnimationNames();
				int index = animation;
				int select = ListSelection.openSelection(names, index);
				animation = select;
				animationName.setText(names.get(select));

			}
		});
	}

	public int getAnimation() {
		return animation;
	}

	public void setAnimation(int atk) {
		this.animation = atk;
		animationName.setText(PoklDataUtil.getAllAnimationNames().get(atk));
	}
}
