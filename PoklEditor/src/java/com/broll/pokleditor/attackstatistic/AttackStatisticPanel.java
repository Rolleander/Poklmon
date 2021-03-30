package com.broll.pokleditor.attackstatistic;

import java.awt.Dimension;

import javax.swing.JDialog;

import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.window.EditorWindow;

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
