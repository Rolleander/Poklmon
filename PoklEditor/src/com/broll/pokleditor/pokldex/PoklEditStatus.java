package com.broll.pokleditor.pokldex;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.IntBox;
import com.broll.pokleditor.gui.components.LevelBox;
import com.broll.pokleditor.gui.components.PoklmonBox;
import com.broll.pokleditor.gui.components.StringBox;
import com.broll.pokllib.poklmon.Poklmon;

public class PoklEditStatus extends JPanel{

	
	private Poklmon poklmon;
	private StringBox name=new StringBox("Name", 20);
	private LevelBox evolveLevel=new LevelBox("Evolve at");
	private PoklmonBox evolveTo=new PoklmonBox("into");
	
	public PoklEditStatus()
	{
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(name);
		add(evolveLevel);
		add(evolveTo);
	}
	
	
	public void setPoklmon(Poklmon poklmon) {
		this.poklmon = poklmon;
		name.setText(poklmon.getName());
		evolveLevel.setLevel(poklmon.getEvolveLevel());
		evolveTo.setPoklmon(poklmon.getEvolveIntoPoklmon());
	}
	
	public void save()
	{
		poklmon.setName(name.getText());
		poklmon.setEvolveLevel(evolveLevel.getLevel());
		poklmon.setEvolveIntoPoklmon(evolveTo.getPoklmon());
	}
	
}
