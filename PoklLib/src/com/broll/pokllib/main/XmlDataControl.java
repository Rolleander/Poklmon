package com.broll.pokllib.main;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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

public class XmlDataControl implements DataControlInterface
{

    public static String srcPath = "data/";
    public static String mapPath = "maps/map";
    public static String poklmonPath = "poklmon/poklmon";
    public static String animationPath = "animations/animation";
    public static String attackPath = "attacks/attack";
    public static String itemPath = "items/item";

    public static String pokldexName = "pokldex.xml";
    public static String attackdexName = "attackdex.xml";
    public static String animationName = "animations.xml";
    public static String mapName = "maps.xml";
    public static String itemdexName = "itemdex.xml";

    public MapFile readMap(int nr) throws JAXBException
    {
        return (MapFile)loadXmlObject(mapPath + nr + ".xml", MapFile.class);
    }

    public void saveMap(int nr, MapFile map) throws JAXBException
    {
        map.setId(nr);
        saveXmlObject(mapPath + nr + ".xml", MapFile.class, map);
    }

    public Poklmon readPoklmon(int nr) throws JAXBException
    {
        return (Poklmon)loadXmlObject(poklmonPath + nr + ".xml", Poklmon.class);
    }

    public void savePoklmon(int nr, Poklmon object) throws JAXBException
    {
        object.setId(nr);
        saveXmlObject(poklmonPath + nr + ".xml", Poklmon.class, object);
    }

    public Attack readAttack(int nr) throws JAXBException
    {
        return (Attack)loadXmlObject(attackPath + nr + ".xml", Attack.class);
    }

    public void saveAttack(int nr, Attack object) throws JAXBException
    {
        object.setId(nr);
        saveXmlObject(attackPath + nr + ".xml", Attack.class, object);
    }

    public Animation readAnimation(int nr) throws JAXBException
    {
        return (Animation)loadXmlObject(animationPath + nr + ".xml", Animation.class);
    }

    public void saveAnimation(int nr, Animation object) throws JAXBException
    {
        object.setId(nr);
        saveXmlObject(animationPath + nr + ".xml", Animation.class, object);
    }

    @Override
    public Item readItem(int nr) throws Exception
    {
        return (Item)loadXmlObject(itemPath + nr + ".xml", Item.class);
    }

    @Override
    public void saveItem(int nr, Item object) throws Exception
    {
        object.setId(nr);
        saveXmlObject(itemPath + nr + ".xml", Item.class, object);
    }

    public AnimationDex readAnimationDex() throws JAXBException
    {
        return (AnimationDex)loadXmlObject(animationName, AnimationDex.class);
    }

    public void saveAnimationDex(AnimationDex dex) throws JAXBException
    {
        saveXmlObject(animationName, AnimationDex.class, dex);
    }

    public PoklDex readPoklDex() throws JAXBException
    {
        return (PoklDex)loadXmlObject(pokldexName, PoklDex.class);
    }

    public void savePoklDex(PoklDex dex) throws JAXBException
    {
        saveXmlObject(pokldexName, PoklDex.class, dex);
    }

    public AttackDex readAttackDex() throws JAXBException
    {
        return (AttackDex)loadXmlObject(attackdexName, AttackDex.class);
    }

    public void saveAttackDex(AttackDex dex) throws JAXBException
    {
        saveXmlObject(attackdexName, AttackDex.class, dex);
    }

    public MapDex readMapDex() throws JAXBException
    {
        return (MapDex)loadXmlObject(mapName, MapDex.class);
    }

    public void saveMapDex(MapDex dex) throws JAXBException
    {
        saveXmlObject(mapName, MapDex.class, dex);
    }

    @Override
    public ItemDex readItemDex() throws Exception
    {
        return (ItemDex)loadXmlObject(itemdexName, ItemDex.class);
    }

    @Override
    public void saveItemDex(ItemDex itemDex) throws Exception
    {
        saveXmlObject(itemdexName, ItemDex.class, itemDex);
    }

    private Object loadXmlObject(String name, Class classType) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(classType);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return jaxbUnmarshaller.unmarshal(new File(srcPath + name));
    }

    private void saveXmlObject(String name, Class classType, Object object) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(classType);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(object, new File(srcPath + name));
    }



}
