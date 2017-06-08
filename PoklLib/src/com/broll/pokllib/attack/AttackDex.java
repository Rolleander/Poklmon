package com.broll.pokllib.attack;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttackDex {

	
	private List<AttackID> attack;
	
	public List<AttackID> getAttack() {
		return attack;
	}
	
	public void setAttack(List<AttackID> attack) {
		this.attack = attack;
	}
	
	
}
