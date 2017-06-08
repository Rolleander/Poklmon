package com.broll.pokleditor.animationdex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.broll.pokleditor.gui.components.AnimationFXLine;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.window.VerticalLayout;
import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.animation.AnimationFX;
import com.broll.pokllib.animation.AnimationFXTarget;
import com.broll.pokllib.animation.AnimationFXType;

public class AnimationEditFX extends JPanel {

	private Animation animation;
	private JPanel panel = new JPanel();
	private ArrayList<AnimationFXLine> lines = new ArrayList<AnimationFXLine>();

	public AnimationEditFX() {
		setPreferredSize(new Dimension(0, 200));
		setLayout(new BorderLayout());
		panel.setLayout(new VerticalLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(new JScrollPane(panel), BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton b;

		b = new JButton("Play Sound", GraphicLoader.loadIcon("plus.png"));
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnimationFX fx=new AnimationFX();
				fx.setType(AnimationFXType.PLAYSOUND);
				fx.setAtFrame(0);
				addAnimationFX(fx);
				revalidate();
				repaint();
			}
		});
		south.add(b);

		b = new JButton("Add Coloroverlay", GraphicLoader.loadIcon("plus.png"));
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnimationFX fx=new AnimationFX();
				fx.setType(AnimationFXType.COLOROVERLAY);
				fx.setAtFrame(0);
				fx.setLength(1);
				fx.setValue(5);
				fx.setR(255);
				fx.setG(255);
				fx.setB(255);
				fx.setA(255);
				fx.setTarget(AnimationFXTarget.SCREEN);
				addAnimationFX(fx);
				revalidate();
				repaint();
			}
		});
		south.add(b);

		b = new JButton("Add Screenshake", GraphicLoader.loadIcon("plus.png"));
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnimationFX fx=new AnimationFX();
				fx.setType(AnimationFXType.SHAKE);
				fx.setAtFrame(0);
				fx.setLength(1);
				fx.setValue(0);
				fx.setTarget(AnimationFXTarget.SCREEN);
				addAnimationFX(fx);
				revalidate();
				repaint();
			}
		});
		south.add(b);

		add(south, BorderLayout.SOUTH);
	}

	private void addAnimationFX(AnimationFX fx) {
		final AnimationFXLine box = new AnimationFXLine(fx);
		box.setActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lines.remove(box);
				panel.remove(box);
				revalidate();
				repaint();
			}
		});
		lines.add(box);
		panel.add(box);
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
		lines.clear();
		panel.removeAll();
		if(animation.getFx()!=null)
		{
		for (AnimationFX fx : animation.getFx()) {
			addAnimationFX(fx);
		}
		}
		revalidate();
		repaint();
	}

	public void save() {
		List<AnimationFX> fxs = new ArrayList<AnimationFX>();
		for (AnimationFXLine line : lines) {
			fxs.add(line.getFx());
		}
		animation.setFx(fxs);
	}
}
