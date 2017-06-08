package com.broll.poklmon.data;

import java.util.HashMap;

import com.badlogic.gdx.audio.Sound;

public class SoundContainer {

	private HashMap<String, Sound> sounds = new HashMap<String, Sound>();

	public SoundContainer(HashMap<String, Sound> sounds) {

		this.sounds = sounds;
	}

	public void playSound(String sound) {
		if (sounds.containsKey(sound)) {
			sounds.get(sound).play(1);
		} else {
			try {
				throw new DataException("Cant find Sound: " + sound + "!");
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
