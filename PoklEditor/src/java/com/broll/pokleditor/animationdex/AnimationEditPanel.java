package com.broll.pokleditor.animationdex;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.debug.GameDebugger;
import com.broll.pokleditor.gui.components.StringBox;
import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.game.StartInformation;

public class AnimationEditPanel extends JPanel{

	private Animation animation;
	private StringBox name=new StringBox("Name", 20);
	private AnimationEditFX fx=new AnimationEditFX();
	private AnimationEditDisplay display;
	
	public AnimationEditPanel()
	{
		 display=new AnimationEditDisplay(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		JPanel north=new JPanel();
		north.setLayout(new FlowLayout(FlowLayout.LEFT));
		north.add(name);
		   JButton debug=new JButton("Debug",GraphicLoader.loadIcon("control_play_blue.png"));
		   north.add(debug);
		   debug.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
				EditorWindow.save();
				StartInformation startInformation = new StartInformation();
				startInformation.debugAnimation(animation.getId());
				GameDebugger.debugGame(startInformation);
			}
		});
		setLayout(new BorderLayout());
		
		add(display,BorderLayout.CENTER);
		add(fx,BorderLayout.SOUTH);
		add(north,BorderLayout.NORTH);
	}
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
		name.setText(animation.getName());
		fx.setAnimation(animation);
		display.setAnimation(animation);
	}
	
	public void save()
	{
		animation.setName(name.getText());
		fx.save();
		display.save();
	}
	
	public Animation getAnimation() {
		return animation;
	}
}
