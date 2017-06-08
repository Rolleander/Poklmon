package com.broll.pokleditor.data;

import java.util.ArrayList;
import java.util.List;

import com.broll.pokllib.animation.AnimationID;
import com.broll.pokllib.attack.AttackID;
import com.broll.pokllib.item.ItemID;
import com.broll.pokllib.map.MapID;
import com.broll.pokllib.poklmon.PoklmonID;

public class PoklDataUtil {


	public static ArrayList<String> getAllPoklmonNames() {
		ArrayList<String> names = new ArrayList<String>();
		List<PoklmonID> list = PoklData.pokldex.getPoklmonList();
		for (int i = 0; i < list.size(); i++) {
			String name = i + ":" + list.get(i).getName();
			names.add(name);
		}
		return names;
	}

	public static ArrayList<String> getAllAttackNames() {
		ArrayList<String> names = new ArrayList<String>();
		List<AttackID> list = PoklData.attacks.getAttack();
		for (int i = 0; i < list.size(); i++) {
			String name = i + ":" + list.get(i).getName();
			names.add(name);
		}
		return names;
	}

	public static ArrayList<String> getAllAnimationNames() {

		ArrayList<String> names = new ArrayList<String>();
		List<AnimationID> list = PoklData.animations.getAnimations();
		for (int i = 0; i < list.size(); i++) {
			String name = i + ":" + list.get(i).getName();
			names.add(name);
		}
		return names;
	}
	
	public static ArrayList<String> getAllItemNames() {

        ArrayList<String> names = new ArrayList<String>();
        List<ItemID> list = PoklData.items.getItems();
        for (int i = 0; i < list.size(); i++) {
            String name = i + ":" + list.get(i).getName();
            names.add(name);
        }
        return names;
    }

	public static ArrayList<String> getAllMapNames() {

		ArrayList<String> names = new ArrayList<String>();
		List<MapID> list = PoklData.maps.getMaps();
		for (int i = 0; i < list.size(); i++) {
			String name = i + ":" + list.get(i).getName();
			names.add(name);
		}
		return names;
	}


}
