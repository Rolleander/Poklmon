package com.broll.pokllib.poklmon;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class AttackList {

	
	private List<AttackLearnEntry> learnAttack;
	private List<Integer> teachableAttacks;
	
	@XmlElement(name="learnAttack")
	public List<AttackLearnEntry> getAttacks() {
		return learnAttack;
	}

	public void setAttacks(List<AttackLearnEntry> attacks) {
		this.learnAttack = attacks;
	}
	
	public List<Integer> getTeachableAttacks()
    {
        return teachableAttacks;
    }
	
	public void setTeachableAttacks(List<Integer> teachableAttacks)
    {
        this.teachableAttacks = teachableAttacks;
    }
	
}
