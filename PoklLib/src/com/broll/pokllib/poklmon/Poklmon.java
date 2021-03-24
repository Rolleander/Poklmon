package com.broll.pokllib.poklmon;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Poklmon {

	private String name;
	private PoklmonAttributes attributes;
	private ElementType baseType;
	private ElementType secondaryType;
	private int evolveLevel;
	private int pokldexNumber;
	private int evolveIntoPoklmon;
	private int expBasePoints;
	private int catchRate;
	private EXPLearnTypes expLearnType;
	private AttackList attackList;
	private String graphicName;
	private String description;
	private int id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PoklmonAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(PoklmonAttributes attributes) {
		this.attributes = attributes;
	}

	public ElementType getBaseType() {
		return baseType;
	}

	public void setBaseType(ElementType baseType) {
		this.baseType = baseType;
	}

	public ElementType getSecondaryType() {
		return secondaryType;
	}

	public void setSecondaryType(ElementType secondaryType) {
		this.secondaryType = secondaryType;
	}

	public int getEvolveLevel() {
		return evolveLevel;
	}

	public void setEvolveLevel(int evolveLevel) {
		this.evolveLevel = evolveLevel;
	}

	public int getPokldexNumber() {
		return pokldexNumber;
	}

	public void setPokldexNumber(int pokldexNumber) {
		this.pokldexNumber = pokldexNumber;
	}

	public int getEvolveIntoPoklmon() {
		return evolveIntoPoklmon;
	}

	public void setEvolveIntoPoklmon(int evolveIntoPoklmon) {
		this.evolveIntoPoklmon = evolveIntoPoklmon;
	}

	public int getExpBasePoints() {
		return expBasePoints;
	}

	public void setExpBasePoints(int expBasePoints) {
		this.expBasePoints = expBasePoints;
	}

	public EXPLearnTypes getExpLearnType() {
		return expLearnType;
	}

	public void setExpLearnType(EXPLearnTypes expLearnType) {
		this.expLearnType = expLearnType;
	}

	public AttackList getAttackList() {
		return attackList;
	}

	public void setAttackList(AttackList attackList) {
		this.attackList = attackList;
	}

	public int getCatchRate() {
		return catchRate;
	}

	public void setCatchRate(int catchRate) {
		this.catchRate = catchRate;
	}
	
	public String getGraphicName() {
		return graphicName;
	}
	
	public void setGraphicName(String graphicName) {
		this.graphicName = graphicName;
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
