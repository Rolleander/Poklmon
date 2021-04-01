/*
package com.broll.pokllib.main;

import java.io.File;
import java.io.IOException;
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
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.Configuration;

public class Db4oDataControl2 implements DataControlInterface
{
    //indexes
    private AnimationDex animationDex = new AnimationDex();
    private AttackDex attackDex = new AttackDex();
    private ItemDex itemDex = new ItemDex();
    private MapDex mapDex = new MapDex();
    private PoklDex poklDex = new PoklDex();
    //containers
    private Map<Integer, Animation> animations = new HashMap<Integer, Animation>();
    private Map<Integer, Attack> attacks = new HashMap<Integer, Attack>();
    private Map<Integer, Item> items = new HashMap<Integer, Item>();
    private Map<Integer, MapFile> maps = new HashMap<Integer, MapFile>();
    private Map<Integer, Poklmon> poklmons = new HashMap<Integer, Poklmon>();


    public Db4oDataControl2()
    {
    }

    private ObjectContainer getConnection(String path)
    {
        Configuration config = Db4o.newConfiguration();
        config.activationDepth(10);
        config.updateDepth(10);
        return Db4o.openFile(config, path);
    }

    public void readData(String path) throws Exception
    {

        ObjectContainer db = getConnection(path);

        if (db == null)
        {
            throw new IOException("Failed to open DB!");
        }
        try
        {

            ObjectSet<AnimationDex> set = db.query(AnimationDex.class);
            if (set.hasNext())
            {
                animationDex = (AnimationDex)set.next();
            }
            ObjectSet<AttackDex> attackSet = db.query(AttackDex.class);
            if (attackSet.hasNext())
            {
                attackDex = (AttackDex)attackSet.next();
            }
            ObjectSet<ItemDex> itemSet = db.query(ItemDex.class);
            if (itemSet.hasNext())
            {
                itemDex = (ItemDex)itemSet.next();
            }
            ObjectSet<MapDex> mapSet = db.query(MapDex.class);
            if (mapSet.hasNext())
            {
                mapDex = (MapDex)mapSet.next();
            }
            ObjectSet<PoklDex> poklSet = db.query(PoklDex.class);
            if (poklSet.hasNext())
            {
                poklDex = (PoklDex)poklSet.next();
            }

            ObjectSet<Map> mapSets = db.query(Map.class);
            for (Map map : mapSets)
            {
                if (map.size() > 0)
                {
                    Class itemClass = map.values().iterator().next().getClass();
                    if (itemClass == Animation.class)
                    {
                        animations = map;
                    }
                    else if (itemClass == Attack.class)
                    {
                        attacks = map;
                    }
                    else if (itemClass == Item.class)
                    {
                        items = map;
                    }
                    else if (itemClass == MapFile.class)
                    {
                        maps = map;
                    }
                    else if (itemClass == Poklmon.class)
                    {
                        poklmons = map;
                    }
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("Failed to read data!");
        }
        finally
        {
            db.close();
        }
    }

    public void saveData(String path) throws Exception
    {
  
        ObjectContainer db = getConnection(path);
        if (db == null)
        {
            throw new IOException("Failed to open DB!");
        }
        
        db.store(animationDex);
        db.store(animations);
        db.store(attackDex);
        db.store(attacks);
        db.store(itemDex);
        db.store(items);
        db.store(poklDex);
        db.store(poklmons);
        db.store(mapDex);
        db.store(maps);
        
        db.commit();
        db.close();
    }

    @Override
    public MapFile readMap(int nr) throws Exception
    {
        return maps.get(nr);
    }

    @Override
    public void saveMap(int nr, MapFile map) throws Exception
    {
      //  map.setId(nr);
     //   maps.put(nr, map);
    }

    @Override
    public Poklmon readPoklmon(int nr) throws Exception
    {
        return poklmons.get(nr);
    }

    @Override
    public void savePoklmon(int nr, Poklmon poklmon) throws Exception
    {
     //   poklmon.setId(nr);
     //   poklmons.put(nr, poklmon);
    }

    @Override
    public Attack readAttack(int nr) throws Exception
    {
        return attacks.get(nr);
    }

    @Override
    public void saveAttack(int nr, Attack attack) throws Exception
    {
   //     attack.setId(nr);
    //    attacks.put(nr, attack);
    }

    @Override
    public Animation readAnimation(int nr) throws Exception
    {
        return animations.get(nr);
    }

    @Override
    public void saveAnimation(int nr, Animation animation) throws Exception
    {
     //   animation.setId(nr);
     //   animations.put(nr, animation);
    }

    @Override
    public Item readItem(int nr) throws Exception
    {
        return items.get(nr);
    }

    @Override
    public void saveItem(int nr, Item item) throws Exception
    {
      //  item.setId(nr);
      //  items.put(nr, item);
    }

    @Override
    public AnimationDex readAnimationDex() throws Exception
    {
        return animationDex;
    }

    @Override
    public void saveAnimationDex(AnimationDex animationDex) throws Exception
    {
        this.animationDex = animationDex;
    }

    @Override
    public PoklDex readPoklDex() throws Exception
    {
        return poklDex;
    }

    @Override
    public void savePoklDex(PoklDex poklDex) throws Exception
    {
        this.poklDex = poklDex;
    }

    @Override
    public AttackDex readAttackDex() throws Exception
    {
        return attackDex;
    }

    @Override
    public void saveAttackDex(AttackDex attackDex) throws Exception
    {
        this.attackDex = attackDex;
    }

    @Override
    public ItemDex readItemDex() throws Exception
    {
        return itemDex;
    }

    @Override
    public void saveItemDex(ItemDex itemDex) throws Exception
    {
        this.itemDex = itemDex;
    }

    @Override
    public MapDex readMapDex() throws Exception
    {
        return mapDex;
    }

    @Override
    public void saveMapDex(MapDex dex) throws Exception
    {
        this.mapDex = dex;
    }




}
*/
