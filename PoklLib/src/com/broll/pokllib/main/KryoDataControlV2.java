package com.broll.pokllib.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.animation.AnimationDex;
import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackDex;
import com.broll.pokllib.item.Item;
import com.broll.pokllib.item.ItemDex;
import com.broll.pokllib.map.MapDex;
import com.broll.pokllib.map.MapFile;
import com.broll.pokllib.poklmon.PoklDex;
import com.broll.pokllib.poklmon.Poklmon;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoDataControlV2 implements DataControlInterface {

	private final static String CORE_FILE="poklmon.data";
	private final static String MAP_FILE="map#.data";
	
	private String path;
	// indexes
	private AnimationDex animationDex = new AnimationDex();
	private AttackDex attackDex = new AttackDex();
	private ItemDex itemDex = new ItemDex();
	private MapDex mapDex = new MapDex();
	private PoklDex poklDex = new PoklDex();
	// containers
	private Map<Integer, Animation> animations = new HashMap<Integer, Animation>();
	private Map<Integer, Attack> attacks = new HashMap<Integer, Attack>();
	private Map<Integer, Item> items = new HashMap<Integer, Item>();
	private Map<Integer, Poklmon> poklmons = new HashMap<Integer, Poklmon>();

	public KryoDataControlV2(String path) {
		this.path = path;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void read() {
		Kryo kryo = new Kryo();
	    Input input=null;
		try {
			input = new Input(new FileInputStream(path+CORE_FILE));
		    animationDex = kryo.readObject(input, AnimationDex.class);
		    attackDex = kryo.readObject(input, AttackDex.class);
		    itemDex = kryo.readObject(input, ItemDex.class);
		    mapDex = kryo.readObject(input, MapDex.class);
		    poklDex = kryo.readObject(input, PoklDex.class);
		    animations = kryo.readObject(input, HashMap.class);
		    attacks = kryo.readObject(input, HashMap.class);
		    items = kryo.readObject(input, HashMap.class);
		    poklmons = kryo.readObject(input, HashMap.class);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} finally{
	    if(input!=null)input.close();
		}
	}

	public void commit() throws Exception {
		Kryo kryo = new Kryo();

		Output output = null;
		try {
			output = new Output(new FileOutputStream(path+CORE_FILE));
			kryo.writeObject(output, animationDex);
			kryo.writeObject(output, attackDex);
			kryo.writeObject(output, itemDex);
			kryo.writeObject(output, mapDex);
			kryo.writeObject(output, poklDex);
			kryo.writeObject(output, animations);
			kryo.writeObject(output, attacks);
			kryo.writeObject(output, items);
			kryo.writeObject(output, poklmons);
			output.close();
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (output != null)
				output.close();
		}

	}

	@Override
	public MapFile readMap(int nr) throws Exception {
		Kryo kryo = new Kryo();
		Input input = new Input(new FileInputStream(path+MAP_FILE.replace("#", ""+nr)));	
		return kryo.readObject(input, MapFile.class);
	}

	@Override
	public void saveMap(int nr, MapFile map) throws Exception {
		map.setId(nr);
		Kryo kryo = new Kryo();
		Output output = new Output(new FileOutputStream(path+MAP_FILE.replace("#", ""+nr)));
		kryo.writeObject(output, map);	
	}

	@Override
	public Poklmon readPoklmon(int nr) throws Exception {
		return poklmons.get(nr);
	}

	@Override
	public void savePoklmon(int nr, Poklmon poklmon) throws Exception {
		poklmon.setId(nr);
		poklmons.put(nr, poklmon);
	}

	@Override
	public Attack readAttack(int nr) throws Exception {
		return attacks.get(nr);
	}

	@Override
	public void saveAttack(int nr, Attack attack) throws Exception {
		attack.setId(nr);
		attacks.put(nr, attack);
	}

	@Override
	public Animation readAnimation(int nr) throws Exception {
		return animations.get(nr);
	}

	@Override
	public void saveAnimation(int nr, Animation animation) throws Exception {
		animation.setId(nr);
		animations.put(nr, animation);
	}

	@Override
	public Item readItem(int nr) throws Exception {
		return items.get(nr);
	}

	@Override
	public void saveItem(int nr, Item item) throws Exception {
		item.setId(nr);
		items.put(nr, item);
	}

	@Override
	public AnimationDex readAnimationDex() throws Exception {
		return animationDex;
	}

	@Override
	public void saveAnimationDex(AnimationDex animationDex) throws Exception {
		this.animationDex = animationDex;
	}

	@Override
	public PoklDex readPoklDex() throws Exception {
		return poklDex;
	}

	@Override
	public void savePoklDex(PoklDex poklDex) throws Exception {
		this.poklDex = poklDex;
	}

	@Override
	public AttackDex readAttackDex() throws Exception {
		return attackDex;
	}

	@Override
	public void saveAttackDex(AttackDex attackDex) throws Exception {
		this.attackDex = attackDex;
	}

	@Override
	public ItemDex readItemDex() throws Exception {
		return itemDex;
	}

	@Override
	public void saveItemDex(ItemDex itemDex) throws Exception {
		this.itemDex = itemDex;
	}

	@Override
	public MapDex readMapDex() throws Exception {
		return mapDex;
	}

	@Override
	public void saveMapDex(MapDex dex) throws Exception {
		this.mapDex = dex;
	}

}
