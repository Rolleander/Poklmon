package com.broll.pokllib.attack;

import javax.xml.bind.annotation.XmlRootElement;

import com.broll.pokllib.poklmon.ElementType;

@XmlRootElement
public class Attack {

	private String name;
	private AttackType attackType;
	private ElementType elementType;
	private String effectCode;
	private int animationID;
	private AttackDamage damage;
	private String description;
	private int id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AttackType getAttackType() {
		return attackType;
	}

	public void setAttackType(AttackType attackType) {
		this.attackType = attackType;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	public String getEffectCode() {
		return effectCode;
	}

	public void setEffectCode(String effectCode) {
		this.effectCode = effectCode;
	}

	public void setAnimationID(int animationID) {
		this.animationID = animationID;
	}
	
	public int getAnimationID() {
		return animationID;
	}
	
	public AttackDamage getDamage() {
		return damage;
	}
	
	public void setDamage(AttackDamage damage) {
		this.damage = damage;
	}

	public String getDescription()
    {
        return description;
    }
	
	public void setDescription(String description)
    {
        this.description = description;
    }
	
	public int getId()
    {
        return id;
    }
	
	public void setId(int id)
    {
        this.id = id;
    }
}
