package com.broll.poklmon.gui.selection;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.resource.GUIFonts;

public class ScrollableSelectionBox extends SelectionBox {

	private ScrollableSelectionContext model;
	private int window;
	
	public ScrollableSelectionBox(DataContainer data,String[] items, int x, int y, int w, int window, boolean iconized) {

		super(data, new String[Math.min(items.length, window)], x, y, w, iconized);
		this.window = window;
		model = new ScrollableSelectionContext(data,items.length, window);
		selection = new SelectionWrapper(items);
	}

	private static String[] findLongest(String[] items, int window) {
		String[] results = new String[Math.min(items.length, window)];
		int width = 0;
		String itemr = "";
		for (String item : items) {
			int l = FontUtils.getWidth(GUIFonts.dialogText,item);
			if (l > width) {
				width = l;
				itemr = item;
			}
		}
		for (int i = 0; i < results.length; i++) {
			results[i] = "A";
		}
		results[0] = itemr;
		return results;
	}

	public ScrollableSelectionBox(DataContainer data, String[] items, int x, int y, int window, boolean iconized) {

		super(data, findLongest(items, window), x, y, iconized);
		this.window = window;
		model = new ScrollableSelectionContext(data,items.length, window);
		selection = new SelectionWrapper(items);
	}

	@Override
	public void update() {
		model.update();
		if (GUIUpdate.isClick()) {
			if(!selection.isSelectionBlocked()){
				data.getSounds().playSound(GUIDesign.CLICK_SOUND);
				listener.select(getSelectedIndex());				
			}
		}
		if (GUIUpdate.isCancel()) {
			listener.cancelSelection();
		}
	}

	@Override
	public int getSelectedIndex() {
		return model.getSelectedIndex();
	}

	private class SelectionWrapper extends SelectionModel {

		public SelectionWrapper(String[] items) {
			super(items);
		}

		@Override
		public boolean isBlocked(int id) {
			int p = model.getStartPos() + id;
			if (p >= items.length) {
				return false;
			}
			return blocked[p];
		}

		@Override
		public String getItem(int id) {
			int p = model.getStartPos() + id;
			if (p >= items.length) {
				return "";
			}
			return items[p];
		}

		@Override
		public Image getIcon(int id) {
			if (icons == null) {
				return null;
			}
			int p = model.getStartPos() + id;
			if (p >= icons.length) {
				return null;
			}
			return icons[id];
		}

		@Override
		public int getSelectedItem() {
			return model.getSelectPos();
		}

		@Override
		public int getSize() {
			return window;
		}
	}
}
