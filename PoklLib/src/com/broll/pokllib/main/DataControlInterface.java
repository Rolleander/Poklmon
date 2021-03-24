package com.broll.pokllib.main;

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


public interface DataControlInterface
{

    public MapFile readMap(int nr) throws Exception;
    
    public void saveMap(int nr, MapFile map) throws Exception;
    
    public Poklmon readPoklmon(int nr) throws Exception;
    
    public void savePoklmon(int nr, Poklmon poklmon) throws Exception;
    
    public Attack readAttack(int nr) throws Exception;
    
    public void saveAttack(int nr, Attack attack) throws Exception;
    
    public Animation readAnimation(int nr) throws Exception;
    
    public void saveAnimation(int nr, Animation animation) throws Exception;
    
    public Item readItem(int nr) throws Exception;
    
    public void saveItem(int nr, Item item) throws Exception;
    
    public AnimationDex readAnimationDex() throws Exception;
    
    public void saveAnimationDex(AnimationDex animationDex) throws Exception;
    
    public PoklDex readPoklDex() throws Exception;
    
    public void savePoklDex(PoklDex poklDex) throws Exception;
    
    public AttackDex readAttackDex() throws Exception;
    
    public void saveAttackDex(AttackDex attackDex) throws Exception;
    
    public ItemDex readItemDex() throws Exception;
    
    public void saveItemDex(ItemDex itemDex) throws Exception;
    
    public MapDex readMapDex() throws Exception;
    
    public void saveMapDex(MapDex dex) throws Exception;
    
    
      
}
