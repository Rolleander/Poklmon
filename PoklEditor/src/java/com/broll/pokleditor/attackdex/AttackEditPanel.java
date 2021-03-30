package com.broll.pokleditor.attackdex;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.SaveListener;
import com.broll.pokllib.attack.Attack;

public class AttackEditPanel extends JPanel{

	private Attack attack;
	private AttackEditStatus status=new AttackEditStatus();
	private AttackEditDamage damage=new AttackEditDamage();
	private AttackEditScript script;
	
	public AttackEditPanel(SaveListener saveListener)
	{
		script=new AttackEditScript(saveListener);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(2, 1));
		p.add(status);
		p.add(damage);
		
		setLayout(new BorderLayout());
		add(p,BorderLayout.NORTH);
		add(script,BorderLayout.CENTER);
	}
	
	public void setAttack(Attack attack) {
		this.attack = attack;
		status.setAttack(attack);
		damage.setAttack(attack);
		script.setAttack(attack);
	}
	
	public void save()
	{
		status.save();
		damage.save();
		script.save();
	}
	
	public Attack getAttack() {
		return attack;
	}
	
}
