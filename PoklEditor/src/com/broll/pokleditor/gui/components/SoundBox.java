package com.broll.pokleditor.gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.resource.SoundLoader;

public class SoundBox extends JPanel {

	private int sound;
	private JLabel soundName = new JLabel();
	private JButton test = new JButton("Play");

	private void playSound(String name){
		String path = SoundLoader.getBattleSoundsPath() + "/" + name;
		try {
			Sound sound = new Sound(path);
			sound.play();
		} catch (SlickException e1) {

			e1.printStackTrace();
		}

	}
	
	public SoundBox(String text) {
		soundName.setForeground(Color.BLUE);
		add(new JLabel(text));
		add(soundName);
		JButton select = GraphicLoader.newIconButton("setting_tools.png");
		add(select);
		add(test);
		test.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = SoundLoader.listBattleSounds().get(sound);
				playSound(name);
			}
		});
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				ArrayList<String> names = SoundLoader.listBattleSounds();
				int index = sound;
				int select = ListSelection.openSelection(names, index, new SelectionListener() {				
					@Override
					public void select(int id) {
						playSound(names.get(id));
					}
				});
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
		soundName.setText(SoundLoader.listBattleSounds().get(sound));
	}

}
