package com.broll.pokleditor.data;

import java.util.ArrayList;

import com.broll.pokleditor.window.EditorWindow;
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
import com.broll.pokllib.map.MapDex;
import com.broll.pokllib.map.MapFile;
import com.broll.pokllib.map.MapID;
import com.broll.pokllib.poklmon.PoklDex;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonID;

public class PoklData
{

    public static PoklDex pokldex;
    public static AttackDex attacks;
    public static AnimationDex animations;
    public static MapDex maps;
    public static ItemDex items;

    public static void initData()
    {
        pokldex = new PoklDex();
        pokldex.setPoklmonList(new ArrayList<PoklmonID>());
        attacks = new AttackDex();
        attacks.setAttack(new ArrayList<AttackID>());
        animations = new AnimationDex();
        animations.setAnimations(new ArrayList<AnimationID>());
        maps = new MapDex();
        maps.setMaps(new ArrayList<MapID>());
        try
        {
            PoklLib.data().saveAnimationDex(animations);
            PoklLib.data().saveAttackDex(attacks);
            PoklLib.data().saveMapDex(maps);
            PoklLib.data().savePoklDex(pokldex);
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
    }

    public static void loadIndexes() throws Exception
    {
        pokldex = PoklLib.data().readPoklDex();
        if (pokldex.getPoklmonList() == null)
        {
            pokldex.setPoklmonList(new ArrayList<PoklmonID>());
        }
        attacks = PoklLib.data().readAttackDex();
        if (attacks.getAttack() == null)
        {
            attacks.setAttack(new ArrayList<AttackID>());
        }
        animations = PoklLib.data().readAnimationDex();
        if (animations.getAnimations() == null)
        {
            animations.setAnimations(new ArrayList<AnimationID>());
        }
        maps = PoklLib.data().readMapDex();
        if (maps.getMaps() == null)
        {
            maps.setMaps(new ArrayList<MapID>());
        }
        items = PoklLib.data().readItemDex();
        if (items.getItems() == null)
        {
            items.setItems(new ArrayList<ItemID>());
        }
    }

    private static void addNewPoklmon(String name)
    {
        PoklmonID id = new PoklmonID();
        id.setName(name);
        id.setId(pokldex.getPoklmonList().size());
        pokldex.getPoklmonList().add(id);
        try
        {
            PoklLib.data().savePoklDex(pokldex);
        }
        catch (Exception e)
        {
            EditorWindow.showErrorMessage("Error while creating new PoklmonIndex: \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addNewAttack(String name)
    {
        AttackID id = new AttackID();
        id.setName(name);
        id.setId(attacks.getAttack().size());
        attacks.getAttack().add(id);
        try
        {
            PoklLib.data().saveAttackDex(attacks);
        }
        catch (Exception e)
        {
            EditorWindow.showErrorMessage("Error while creating new AttackIndex: \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addNewAnimation(String name)
    {
        AnimationID id = new AnimationID();
        id.setName(name);
        id.setId(animations.getAnimations().size());
        animations.getAnimations().add(id);
        try
        {
            PoklLib.data().saveAnimationDex(animations);
        }
        catch (Exception e)
        {
            EditorWindow.showErrorMessage("Error while creating new AnimationIndex: \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addNewItem(String name)
    {
        ItemID id = new ItemID();
        id.setName(name);
        id.setId(items.getItems().size());
        items.getItems().add(id);
        try
        {
            PoklLib.data().saveItemDex(items);
        }
        catch (Exception e)
        {
            EditorWindow.showErrorMessage("Error while creating new Item: \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addNewMap(String name)
    {
        MapID id = new MapID();
        id.setName(name);
        id.setId(maps.getMaps().size());
        maps.getMaps().add(id);
        try
        {
            PoklLib.data().saveMapDex(maps);
        }
        catch (Exception e)
        {
            EditorWindow.showErrorMessage("Error while creating new MapIndex: \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Poklmon loadPoklmon(int id)
    {
        try
        {
            return PoklLib.data().readPoklmon(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            EditorWindow.showErrorMessage("Error while loading Poklmon: \n" + e.getMessage());
        }
        return null;
    }

    public static Attack loadAttack(int id)
    {
        try
        {
            return PoklLib.data().readAttack(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            EditorWindow.showErrorMessage("Error while loading Attack: \n" + e.getMessage());
        }
        return null;
    }

    public static Animation loadAnimation(int id)
    {
        try
        {
            return PoklLib.data().readAnimation(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            EditorWindow.showErrorMessage("Error while loading Animation: \n" + e.getMessage());
        }
        return null;
    }

    public static Item loadItem(int id)
    {
        try
        {
            return PoklLib.data().readItem(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            EditorWindow.showErrorMessage("Error while loading Item: \n" + e.getMessage());
        }
        return null;
    }

    public static MapFile loadMap(int id)
    {
        try
        {
            return PoklLib.data().readMap(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            EditorWindow.showErrorMessage("Error while loading MapFile: \n" + e.getMessage());
        }
        return null;
    }

    public static void savePoklmon(Poklmon poklmon, int id)
    {
        String name = poklmon.getName();
        if (id >= pokldex.getPoklmonList().size())
        {
            // add new index
            addNewPoklmon(name);
        }
        pokldex.getPoklmonList().get(id).setName(name);
        try
        {
            PoklLib.data().savePoklmon(id, poklmon);
            PoklLib.data().savePoklDex(pokldex);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            EditorWindow.showErrorMessage("Error while saving Poklmon: \n" + e.getMessage());
        }
    }

    public static void saveAttack(Attack attack, int id)
    {
        String name = attack.getName();
        if (id >= attacks.getAttack().size())
        {
            // add new index
            addNewAttack(name);
        }
        attacks.getAttack().get(id).setName(name);
        try
        {
           PoklLib.data().saveAttack(id, attack);
            PoklLib.data().saveAttackDex(attacks);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            EditorWindow.showErrorMessage("Error while saving Attack: \n" + e.getMessage());
        }
    }

    public static void saveAnimation(Animation animation, int id)
    {
        String name = animation.getName();
        if (id >= animations.getAnimations().size())
        {
            // add new index
            addNewAnimation(name);
        }
        animations.getAnimations().get(id).setName(name);
        try
        {
            PoklLib.data().saveAnimation(id, animation);
            PoklLib.data().saveAnimationDex(animations);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            EditorWindow.showErrorMessage("Error while saving Animation: \n" + e.getMessage());
        }
    }
    
    public static void saveItem(Item item, int id)
    {
        String name = item.getName();
        if (id >= items.getItems().size())
        {
            // add new index
            addNewItem(name);
        }
        items.getItems().get(id).setName(name);
        try
        {
            PoklLib.data().saveItem(id, item);
            PoklLib.data().saveItemDex(items);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            EditorWindow.showErrorMessage("Error while saving Item: \n" + e.getMessage());
        }
    }

    public static void saveMap(MapFile map, int id)
    {
        String name = map.getName();
        if (id >= maps.getMaps().size())
        {
            // add new index
            addNewMap(name);
        }
        maps.getMaps().get(id).setName(name);
        try
        {
            PoklLib.data().saveMap(id, map);
            PoklLib.data().saveMapDex(maps);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            EditorWindow.showErrorMessage("Error while saving Map: \n" + e.getMessage());
        }
    }

}
