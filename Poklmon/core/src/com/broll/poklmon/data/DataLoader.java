package com.broll.poklmon.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.animation.AnimationDex;
import com.broll.pokllib.animation.AnimationID;
import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackDex;
import com.broll.pokllib.attack.AttackID;
import com.broll.pokllib.item.Item;
import com.broll.pokllib.item.ItemDex;
import com.broll.pokllib.item.ItemID;
import com.broll.pokllib.main.PoklLib;
import com.broll.pokllib.poklmon.PoklDex;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonID;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.resource.ResourceUtils;

public class DataLoader {
	private static String graphicsPath = "graphics/";
	public static boolean SKIP_SOUNDS = false;
	private static Graphics graphics;
	
	public static void setGraphics(Graphics graphics) {
		DataLoader.graphics = graphics;
	}
	
	public static List<Poklmon> loadPoklmons() throws DataException {
		List<Poklmon> list = new ArrayList<Poklmon>();
		try {
			PoklDex dex = PoklLib.data().readPoklDex();
			for (PoklmonID poklid : dex.getPoklmonList()) {
				int id = poklid.getId();
				// load poklmon
				Poklmon poklmon = PoklLib.data().readPoklmon(id);
				list.add(poklmon);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("Failed to load Pokldata!");
		}
		return list;
	}

	public static List<Item> loadItems() throws DataException {
		List<Item> list = new ArrayList<Item>();
		try {
			ItemDex dex = PoklLib.data().readItemDex();
			for (ItemID itemId : dex.getItems()) {
				int id = itemId.getId();
				list.add(PoklLib.data().readItem(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("Failed to load Itemdata!");
		}
		return list;
	}

	public static List<Attack> loadAttacks() throws DataException {
		List<Attack> list = new ArrayList<Attack>();
		try {
			AttackDex dex = PoklLib.data().readAttackDex();
			for (AttackID attackid : dex.getAttack()) {
				int id = attackid.getId();
				list.add(PoklLib.data().readAttack(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("Failed to load Attackdata!");
		}
		return list;
	}

	public static List<Animation> loadAnimations() throws DataException {
		List<Animation> list = new ArrayList<Animation>();
		try {
			AnimationDex dex = PoklLib.data().readAnimationDex();
			for (AnimationID animid : dex.getAnimations()) {
				int id = animid.getId();
				list.add(PoklLib.data().readAnimation(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("Failed to load Animationdata!");
		}
		return list;
	}

	public static HashMap<String, Sound> loadSounds() throws DataException {
		HashMap<String, Sound> list = new HashMap<String, Sound>();
		FileHandle file=Gdx.files.internal(ResourceUtils.DATA_PATH + "sounds");
		if (!SKIP_SOUNDS) {
			for (FileHandle f : file.list()) {
				if (!f.isDirectory()) {
					String name = f.name();
					String path = ResourceUtils.DATA_PATH + "sounds/" + name;
					Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
					name = name.split("\\.")[0];
					list.put(name, sound);
				}
			}
			 file=Gdx.files.internal(ResourceUtils.DATA_PATH + "sounds/battle");
			// battle sounds
			for (FileHandle f : file.list()) {
				if (!f.isDirectory()) {
					String name = f.name();
					String path = ResourceUtils.DATA_PATH + "sounds/battle/" + name;
					Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
					name = "battle_" + name.split("\\_")[0];
					list.put(name, sound);
				}
			}
		}
		return list;
	}

	public static SpriteSheet loadTileSet() throws DataException {

		return loadSprites(ResourceUtils.DATA_PATH + graphicsPath + "tileset.png", 16, 16);

	}

	public static SpriteSheet loadAnimationSet() throws DataException {

		return loadSprites(ResourceUtils.DATA_PATH + graphicsPath + "animations.png", 100, 100);

	}

	public static HashMap<String, Image> loadPoklmonImages() throws DataException {
		HashMap<String, Image> list = new HashMap<String, Image>();
		FileHandle file=Gdx.files.internal(ResourceUtils.DATA_PATH + graphicsPath + "poklmon");
		for (FileHandle f : file.list()) {
			String name = f.name();
			Image image;
			image = loadImage(ResourceUtils.DATA_PATH + graphicsPath + "poklmon/" + name);
			list.put(name, image);
		}
		return list;
	}

	public static HashMap<String, SpriteSheet> loadCharImages() throws DataException {
		HashMap<String, SpriteSheet> list = new HashMap<String, SpriteSheet>();
		FileHandle file=Gdx.files.internal(ResourceUtils.DATA_PATH + graphicsPath + "chars");
		for (FileHandle f : file.list()) {
			String name = f.name();

			SpriteSheet image = loadSprites(ResourceUtils.DATA_PATH + graphicsPath + "chars/" + name, 32, 32);

			list.put(name, image);

		}
		return list;
	}

	public static SpriteSheet loadSprites(String file, int w, int h) throws DataException  {
		return new SpriteSheet(loadImage(file), w, h);
	}

	public static Image loadImage(String file) throws DataException  {
		Texture texture = new Texture(Gdx.files.internal(file));
		return new Image(graphics,texture);
	}
}
