package com.broll.pokleditor.gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.resource.SoundLoader;
import com.broll.pokllib.poklmon.ElementType;

public class SoundBox extends JPanel {

	private int sound;
	private JLabel soundName = new JLabel();

	public SoundBox(String text) {
		soundName.setForeground(Color.BLUE);
		add(new JLabel(text));
		add(soundName);
		JButton select = GraphicLoader.newIconButton("setting_tools.png");
		add(select);

		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				ArrayList<String> names = SoundLoader.listSounds();
				int index = sound;
				int select = ListSelection.openSelection(names, index);
				sound = select;
				soundName.setText(names.get(select));

			}
		});
	}

	public int getSound() {
		return sound;
	}

	public void setSound(int atk) {
		this.sound = atk;
		soundName.setText(SoundLoader.listSounds().get(atk));
	}

}
