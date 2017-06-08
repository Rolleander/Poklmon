package com.broll.pokleditor.attackstatistic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.attack.Attack;

public class AttackStatisticPanel extends JDialog {

    private StatisticOverview overview=new StatisticOverview();

	public AttackStatisticPanel() {

		setModal(true);
		setTitle("Attack Statistic");
		  setIconImage(GraphicLoader.loadImage("fire.png"));
		setSize(new Dimension(1000, 600));
		setMinimumSize(new Dimension(1000, 600));
		setLocationRelativeTo(EditorWindow.frame);

		
		setContentPane(overview);
	}

	public void open() {
	    overview.calcStatistic();
		this.setVisible(true);
	}

	
}
