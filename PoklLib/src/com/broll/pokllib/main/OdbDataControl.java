/*
package com.broll.pokllib.main;

import java.io.IOException;

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

public class OdbDataControl implements DataControlInterface
{

    private ObjectContainer db;
    private final static String failed = "Failed to access DB: ";

    public OdbDataControl()
    {

    }

    private void startOperation() throws IOException
    {
        db = Db4o.openFile("data/poklmon.data");
        if (db == null)
        {
            throw new IOException("Failed to open DB!");
        }
    }

    private void endOperation()
    {
        if (db != null)
        {
            db.close();
        }
    }

    private void setObjectID(Object object, int id)
    {
        if (object instanceof Poklmon)
        {
            ((Poklmon)object).setId(id);
        }
        else if (object instanceof Attack)
        {
            ((Attack)object).setId(id);
        }
        else if (object instanceof Animation)
        {
            ((Animation)object).setId(id);
        }
        else if (object instanceof MapFile)
        {
            ((MapFile)object).setId(id);
        }
    }

    private Object queryByID(Object object, int id) throws IOException
    {
        //set dummy object id
        setObjectID(object, id);
        //query
        return queryBy(object);
    }

    private Object queryBy(Object object) throws IOException
    {
        ObjectSet<Object> resultSet = db.queryByExample(object);
        if (resultSet.size() == 0)
        {
            throw new IOException(failed + object);
        }
        return resultSet.get(0);
    }

    private boolean existsObjectWithID(Object object, int id)
    {
        setObjectID(object, id);
        return existsObject(object);
    }

    private boolean existsObject(Object object)
    {
        ObjectSet<Object> resultSet = db.queryByExample(object);
        if (resultSet.size() == 0)
        {
            return false;
        }
        return true;
    }

    @Override
    public MapFile readMap(int nr) throws Exception
    {
        startOperation();
        MapFile map = (MapFile)queryByID(new MapFile(), nr);
        endOperation();
        return map;
    }

    @Override
    public void saveMap(int nr, MapFile map) throws Exception
    {
        startOperation();
        if (!existsObjectWithID(new MapFile(), nr))
        {
            map.setId(nr);
            db.store(map);
            db.commit();
        }
        endOperation();
    }

    @Override
    public Poklmon readPoklmon(int nr) throws Exception
    {
        startOperation();
        Poklmon object = (Poklmon)queryByID(new Poklmon(), nr);
        endOperation();
        return object;
    }

    @Override
    public void savePoklmon(int nr, Poklmon poklmon) throws Exception
    {
        startOperation();
        if (!existsObjectWithID(new Poklmon(), nr))
        {
            poklmon.setId(nr);
            db.store(poklmon);
            db.commit();
        }
        endOperation();
    }

    @Override
    public Attack readAttack(int nr) throws Exception
    {
        startOperation();
        Attack object = (Attack)queryByID(new Attack(), nr);
        endOperation();
        return object;
    }

    @Override
    public void saveAttack(int nr, Attack attack) throws Exception
    {
        startOperation();
        if (!existsObjectWithID(new Attack(), nr))
        {
            attack.setId(nr);
            db.store(attack);
            db.commit();
        }
        endOperation();
    }

    @Override
    public Animation readAnimation(int nr) throws Exception
    {
        startOperation();
        Animation object = (Animation)queryByID(new Animation(), nr);
        endOperation();
        return object;
    }

    @Override
    public void saveAnimation(int nr, Animation animation) throws Exception
    {
        startOperation();
        if (!existsObjectWithID(new Animation(), nr))
        {
            animation.setId(nr);
            db.store(animation);
            db.commit();
        }
        endOperation();
    }

    @Override
    public AnimationDex readAnimationDex() throws Exception
    {
        startOperation();
        AnimationDex object = null;
        if (existsObject(new AnimationDex()))
        {
            object = (AnimationDex)queryBy(new AnimationDex());
        }
        else
        {
            //create new in db
            object = new AnimationDex();
            db.store(object);
            db.commit();
        }
        endOperation();
        return object;
    }

    @Override
    public void saveAnimationDex(AnimationDex animationDex) throws Exception
    {
        //nothing to do here
    }

    @Override
    public PoklDex readPoklDex() throws Exception
    {
        startOperation();
        PoklDex object = null;
        if (existsObject(new PoklDex()))
        {
            object = (PoklDex)queryBy(new PoklDex());
        }
        else
        {
            //create new in db
            object = new PoklDex();
            db.store(object);
            db.commit();
        }
        endOperation();
        return object;
    }

    @Override
    public void savePoklDex(PoklDex poklDex) throws Exception
    {
        //nothing to do here
    }

    @Override
    public AttackDex readAttackDex() throws Exception
    {
        startOperation();
        AttackDex object = null;
        if (existsObject(new AttackDex()))
        {
            object = (AttackDex)queryBy(new AttackDex());
        }
        else
        {
            //create new in db
            object = new AttackDex();
            db.store(object);
            db.commit();
        }
        endOperation();
        return object;
    }

    @Override
    public void saveAttackDex(AttackDex attackDex) throws Exception
    {
        //nothing to do here
    }

    @Override
    public MapDex readMapDex() throws Exception
    {
        startOperation();
        MapDex object = null;
        if (existsObject(new MapDex()))
        {
            object = (MapDex)queryBy(new MapDex());
        }
        else
        {
            //create new in db
            object = new MapDex();
            db.store(object);
            db.commit();
        }
        endOperation();
        return object;
    }

    @Override
    public void saveMapDex(MapDex dex) throws Exception
    {
        //nothing to do here
    }

    @Override
    public Item readItem(int nr) throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveItem(int nr, Item item) throws Exception
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ItemDex readItemDex() throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveItemDex(ItemDex itemDex) throws Exception
    {
        // TODO Auto-generated method stub
        
    }

}
*/
