package com.broll.poklmon.gui.selection;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.gui.DialogBoxRender;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.resource.GUIFonts;

public class SelectionBox {

	protected DialogBoxRender render;
	protected SelectionBoxListener listener;
	protected int selectionWidth, selectionHeight;
	protected int selectionXP, selectionYP;
	protected int xpos, ypos;
	private final static int PADDING = 25;
	public final static int ICONSIZE = 30;

	protected SelectionModel selection;
	protected int height;
	private boolean iconized = false;
	protected DataContainer data;

	public SelectionBox(DataContainer data, String[] items, int x, int y, int w, boolean iconized) {
		int d = 10;
		this.data=data;
		selectionHeight = 30;
		selectionXP = 0;
		selectionYP = selectionHeight + d;
		selectionWidth = w - PADDING * 2;
		this.iconized = iconized;
		height = items.length * (selectionYP) + PADDING * 2 - d;
		int width = w;
		render = new DialogBoxRender(GUIDesign.selectionBoxBorder, GUIDesign.selectionBoxBorder2,
				GUIDesign.selectionBoxCorner, ColorUtil.newColor(248, 248, 248));
		render.setSize(x, y, w, height);
		xpos = x + PADDING + 10;
		ypos = y + PADDING;
		selection = new SelectionModel(items);
	}

	public SelectionBox(DataContainer data,String[] items, int x, int y, boolean iconized) {
		int d = 10;
		this.data=data;
		selectionHeight = 30;
		selectionXP = 0;
		selectionYP = selectionHeight + d;
		this.iconized = iconized;
		int width = 0;
		for (String item : items) {
			int l = FontUtils.getWidth(GUIFonts.dialogText,item) + PADDING * 2 + 10;
			if (l > width) {
				width = l;
			}
		}
		if (iconized) {
			width += ICONSIZE + 10;
		}
		selectionWidth = width - PADDING * 2;
		height = items.length * (selectionYP) + PADDING * 2 - d;
		render = new DialogBoxRender(GUIDesign.selectionBoxBorder, GUIDesign.selectionBoxBorder2,
				GUIDesign.selectionBoxCorner, ColorUtil.newColor(248, 248, 248));
		x -= width;
		y -= height;
		xpos = x + PADDING + 10;
		ypos = y + PADDING;
		render.setSize(x, y, width, height);
		selection = new SelectionModel(items);
	}

	public void setIcons(Image[] icons) {
		selection.setIcons(icons);
	}

	public void shiftDown() {
		ypos += height;
		render.move(0, height);
	}

	public int getSelectedIndex() {
		return selection.getSelectedItem();
	}

	public void setListener(SelectionBoxListener listener) {
		this.listener = listener;
	}

	public void blockItem(int id, boolean block) {
		selection.blockItem(id, block);
	}

	public void render(Graphics g) {
		render.render(g);
		// draw selection
		int selected = selection.getSelectedItem();
		int x = xpos;
		int y = ypos;
		for (int i = 0; i < selection.getSize(); i++) {
			int w = selectionWidth;
			int h = selectionHeight;
			if (selection.isBlocked(i)) {
				g.setColor(ColorUtil.newColor(150, 150, 150));
			} else {
				g.setColor(ColorUtil.newColor(50, 50, 50));
			}

			int xp = 5;
			if (iconized) {
				Image img = selection.getIcon(i);
				if (img != null) {
					img.drawCentered(x + xp+ICONSIZE/2, y+ICONSIZE/2);
				}
				xp += ICONSIZE + 5;
			}
			g.setFont(GUIFonts.dialogText);
			g.drawString(selection.getItem(i), x + xp, y - 8);

			if (selected == i) {
				drawSelection(g, x, y, w, h);
			}
			x += selectionXP;
			y += selectionYP;
		}
	}

	protected void drawSelection(Graphics g, float x, float y, float w, float h) {

		g.drawImage(GUIDesign.selector, x - 24, y);
	}

	public void update() {

		updateSelection();
		GUIUpdate.consume();
	}

	protected void updateSelection() {

		if (GUIUpdate.isMoveDown()) {
			data.getSounds().playSound(GUIDesign.MOVE_SOUND);
			selection.add();
		}
		if (GUIUpdate.isMoveUp()) {
			data.getSounds().playSound(GUIDesign.MOVE_SOUND);
			selection.sub();
		}
		if (listener != null) {
			if (GUIUpdate.isClick()) {
				if (!selection.isSelectionBlocked()) {
					data.getSounds().playSound(GUIDesign.CLICK_SOUND);
					listener.select(selection.getSelectedItem());
				} else {
					// item is blocked!

				}
			}
			if (GUIUpdate.isCancel()) {
				listener.cancelSelection();
			}
		}
	}

	public void setSelectedItem(int i) {
		selection.setSelectedItem(i);

	}

}
