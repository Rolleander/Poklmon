package com.broll.poklmon.battle.util.flags;

import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.poklmon.ElementType;

public class DamageTaken
{

    private int damage;
    private ElementType element;
    private AttackType attackType;

    public DamageTaken()
    {

    }

    public void setAttackType(AttackType attackType)
    {
        this.attackType = attackType;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
    }

    public void setElement(ElementType element)
    {
        this.element = element;
    }

    public AttackType getAttackType()
    {
        return attackType;
    }

    public int getDamage()
    {
        return damage;
    }

    public ElementType getElement()
    {
        return element;
    }

}
