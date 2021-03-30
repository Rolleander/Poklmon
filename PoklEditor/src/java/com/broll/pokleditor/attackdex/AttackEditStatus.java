package com.broll.pokleditor.attackdex;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.AttackTypeBox;
import com.broll.pokleditor.gui.components.StringBox;
import com.broll.pokleditor.gui.components.TypeBox;
import com.broll.pokllib.attack.Attack;

public class AttackEditStatus extends JPanel{

	private Attack attack;
	private StringBox name=new StringBox("Name",20); 
	private AttackTypeBox type=new AttackTypeBox("Type");
	private TypeBox element=new TypeBox("Element");
	
	public AttackEditStatus()
	{
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(name);
		add(type);
		add(element);
	}
	
	
	public void setAttack(Attack attack) {
		this.attack = attack;
		name.setText(attack.getName());
		type.setType(attack.getAttackType());
		element.setType(attack.getElementType());
		
	}
	
	public void save()
	{
		attack.setName(name.getText());
		attack.setAttackType(type.getType());
		attack.setElementType(element.getType());
	}
	
	
}
