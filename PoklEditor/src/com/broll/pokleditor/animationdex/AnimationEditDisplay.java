package com.broll.pokleditor.animationdex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.broll.pokllib.animation.Animation;

public class AnimationEditDisplay extends JPanel{

	private Animation animation;
	private JPanel panel=new JPanel();
	private AnimationSpriteset sprites=new AnimationSpriteset();
	private AnimationEditBox box=new AnimationEditBox();
	private AnimationEditList list;
	
	public AnimationEditDisplay(ActionListener save)
	{
		list=new AnimationEditList(save);
		setPreferredSize(new Dimension(1000,600));
		setLayout(new BorderLayout());
		JScrollPane scroll=new JScrollPane(sprites,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(100);
		scroll.setPreferredSize(new Dimension(0,100));
		add(scroll,BorderLayout.NORTH);
		add(box,BorderLayout.CENTER);
		list.setPreferredSize(new Dimension(200,0));
		add(list,BorderLayout.EAST);
	}
	
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
		box.setAnimation(animation);
		list.setAnimation(animation);
	}
	
	public void save()
	{
		
	}
}
