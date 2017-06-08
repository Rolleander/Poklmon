package com.broll.pokllib.main;

import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.animation.AnimationDex;
import com.broll.pokllib.animation.AnimationID;
import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackDex;
import com.broll.pokllib.attack.AttackID;
import com.broll.pokllib.item.Item;
import com.broll.pokllib.item.ItemDex;
import com.broll.pokllib.item.ItemID;
import com.broll.pokllib.map.MapDex;
import com.broll.pokllib.map.MapFile;
import com.broll.pokllib.map.MapID;
import com.broll.pokllib.poklmon.AttackLearnEntry;
import com.broll.pokllib.poklmon.AttackList;
import com.broll.pokllib.poklmon.PoklDex;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonID;
import com.db4o.foundation.StopWatch;

public class DataConvertor {
/*
	public static void db4oToKryo() {
		Db4oDataControl db4oDataControl = new Db4oDataControl();
		try {
			db4oDataControl.readData("data/poklmon.data");
			KryoDataControl kryo = new KryoDataControl("data/poklmon2.data");
			copy(db4oDataControl, kryo);
			kryo.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public static void xmlToDb4o() throws Exception {
		XmlDataControl xmlDataControl = new XmlDataControl();
		Db4oDataControl db4oDataControl = new Db4oDataControl();

		// save!
		StopWatch w = new StopWatch();
		w.start();
		copy(xmlDataControl, db4oDataControl);
		db4oDataControl.saveData("data/poklmon.data");
		w.stop();
		System.out.println("Saved in: " + w.elapsed());

	}

	private static void copy(DataControlInterface from, DataControlInterface to) throws Exception {
		AttackDex attackDex = from.readAttackDex();
		AnimationDex animationDex = from.readAnimationDex();
		ItemDex itemDex = from.readItemDex();
		MapDex mapDex = from.readMapDex();
		PoklDex poklDex = from.readPoklDex();

		for (AttackID attack : attackDex.getAttack()) {
			int id = attack.getId();
			Attack atk = from.readAttack(id);
			to.saveAttack(id, atk);
		}

		for (AnimationID animation : animationDex.getAnimations()) {
			int id = animation.getId();
			Animation ani = from.readAnimation(id);
			to.saveAnimation(id, ani);
		}

		for (ItemID item : itemDex.getItems()) {
			int id = item.getId();
			Item it = from.readItem(id);
			to.saveItem(id, it);
		}

		for (MapID map : mapDex.getMaps()) {
			int id = map.getId();
			MapFile ma = from.readMap(id);
			to.saveMap(id, ma);
		}

		for (PoklmonID poklmon : poklDex.getPoklmonList()) {
			int id = poklmon.getId();
			Poklmon pokl = from.readPoklmon(id);
			to.savePoklmon(id, pokl);
		}

		to.saveAnimationDex(animationDex);
		to.saveAttackDex(attackDex);
		to.saveItemDex(itemDex);
		to.saveMapDex(mapDex);
		to.savePoklDex(poklDex);
	}

	public static void db4oToXml() {

	}

}
