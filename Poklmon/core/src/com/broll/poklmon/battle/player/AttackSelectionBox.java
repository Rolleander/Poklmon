package com.broll.poklmon.battle.player;

import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.DialogBoxRender;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.selection.RectSelectionBox;
import com.broll.poklmon.resource.GUIDesign;

public class AttackSelectionBox {
	private boolean open;
	private FightPoklmon poklmon;
	private RectSelectionBox attacks;
	private String[] attackNames = new String[4];
	private DialogBoxRender infoBox;
	private int infoBoxX, infoBoxY;
	private AttackSelectionListener listener;
	private DataContainer data;
	public AttackSelectionBox(DataContainer data) {
		this.data=data;
		int h = 157;
		infoBoxY = 600 - h;
		infoBoxX = 600;
		attacks = new RectSelectionBox(data,attackNames, 0, infoBoxY, 600, h);
		infoBox = new DialogBoxRender(GUIDesign.selectionBoxBorder, GUIDesign.selectionBoxBorder2,
				GUIDesign.selectionBoxCorner, ColorUtil.newColor(248, 248, 248));
		infoBox.setSize(infoBoxX, infoBoxY, 200, h);
	}

	public FightAttack getSelectedAttack() {
		int id = attacks.getSelectedIndex();
		return poklmon.getAttacks()[id];
	}

	public void open(FightPoklmon poklmon, AttackSelectionListener listener) {
		open = true;
		this.listener=listener;
		this.poklmon = poklmon;
		for (int i = 0; i < 4; i++) {
			attacks.blockItem(i, false);
			if (poklmon.getAttacks()[i] != null) {
				attackNames[i] = poklmon.getAttacks()[i].getAttack().getName();
				if (poklmon.getAttacks()[i].getAp() <= 0) {
					attacks.blockItem(i, true);
				}
			} else {
				attackNames[i] = "-";
				attacks.blockItem(i, true);
			}
		}
	}


	public void render(Graphics g) {
		if (open) {
			attacks.render(g);
			infoBox.render(g);

			// info box
			int x = infoBoxX;
			int y = infoBoxY;
			g.setColor(ColorUtil.newColor(50, 50, 50));

			FightAttack selected = poklmon.getAttacks()[attacks.getSelectedIndex()];
			if (selected != null) {
				int curAp = selected.getAp();
				int maxAp = selected.getAttack().getDamage().getAp();
				if (((float) curAp) / ((float) maxAp) <= 0.2) {
					g.setColor(ColorUtil.newColor(150, 20, 20));
				}
				g.drawString("AP " + curAp + "/" + maxAp, x + 25, y + 20);
				g.setColor(ColorUtil.newColor(50, 50, 50));
				g.drawString(selected.getAttack().getElementType().getName(), x + 25, y + 80);
			}
		}
	}

	public void update() {
		if (open) {
			if (GUIUpdate.isClick()) {
				data.getSounds().playSound(GUIDesign.CLICK_SOUND);

				// attack not null?
				if (!attackNames[attacks.getSelectedIndex()].equals("-")) {
					// ap für attacke übrig?
					if (poklmon.getAttacks()[attacks.getSelectedIndex()].getAp() > 0) {
						// select attack
						open=false;
						listener.select(poklmon.getAttacks()[attacks.getSelectedIndex()]);
					}
				}
			}
			if(GUIUpdate.isCancel()){
				data.getSounds().playSound(GUIDesign.CANCEL_SOUND);

				open=false;
				listener.select(null);
			}
			attacks.update();

		}
	}
}
