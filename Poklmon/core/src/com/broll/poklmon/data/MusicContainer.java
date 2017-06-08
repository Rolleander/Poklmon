package com.broll.poklmon.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.broll.poklmon.resource.ResourceUtils;

public class MusicContainer {

	private Music lastMusic;
	private String musicName;
	private float volume = 0.5f;

	public MusicContainer() {

	}

	public void playMusic(String name, boolean playNew) {
		if (!DataLoader.SKIP_SOUNDS) {
			if (name.equals(musicName)) {
				if (playNew) {
					lastMusic.setPosition(0);
					lastMusic.setVolume(volume);
					lastMusic.setLooping(true);
					lastMusic.play();
				}
			} else {
				stop();
				musicName = name;
				String file = ResourceUtils.DATA_PATH + "music/" + name;
				lastMusic = Gdx.audio.newMusic(Gdx.files.internal(file));
				lastMusic.setVolume(volume);
				lastMusic.setLooping(true);
				lastMusic.play();
			}
		}
	}

	public void stop() {
		if (lastMusic != null) {
			lastMusic.stop();
			lastMusic.setVolume(0);
			musicName = null;
		}
	}
}
