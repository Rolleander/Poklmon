package com.broll.poklmon.battle.player;

import com.broll.pokllib.item.Item;
import com.broll.pokllib.item.ItemType;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.item.InventarItemInstance;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.dialog.DialogBox;
import com.broll.poklmon.gui.selection.ScrollableSelectionBox;
import com.broll.poklmon.gui.selection.SelectionBox;
import com.broll.poklmon.gui.selection.SelectionBoxListener;
import com.broll.poklmon.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SelectItemDialog {

	private UseItemListener listener;
	private SelectionBox typeSelection;
	private final static ItemType[] itemTypes = new ItemType[] { ItemType.POKLBALL, ItemType.MEDICIN };
	private ItemType selectedType = null;
	private BattleManager manager;
	private DialogBox text;
	private ScrollableSelectionBox itemSelect;
	private boolean isVisible = false;
	private List<InventarItemInstance> items = new ArrayList<InventarItemInstance>();
	private GiveItemDialog giveItemDialog;

	public SelectItemDialog(BattleManager manager) {
		this.manager = manager;
		text = new DialogBox(manager.getData());
		text.setStyle(DialogBox.STYLE_BATTLE);
		giveItemDialog = new GiveItemDialog(manager.getData(), manager);
	}

	public void open(UseItemListener list) {
		this.listener = list;
		isVisible = true;
		selectedType = null;
		int x = 800;
		int y = (600 - 157);
		String[] names = new String[itemTypes.length];
		for (int i = 0; i < itemTypes.length; i++) {
			names[i] = itemTypes[i].getName();
		}

		typeSelection = new SelectionBox(manager.getData(), names, x, y, false);
		text.showInfo("Öffne Beutel...", "");
		typeSelection.blockItem(0, manager.getParticipants().isTrainerFight());

		if (!manager.getPlayer().getInventarControl().hasItemsOfType(ItemType.POKLBALL)) {
			typeSelection.blockItem(0, true);
		}
		if (!manager.getPlayer().getInventarControl().hasItemsOfType(ItemType.MEDICIN)) {
			typeSelection.blockItem(1, true);
		}

		typeSelection.setListener(new SelectionBoxListener() {
			@Override
			public void select(int item) {
				selectedType = itemTypes[item];
				showItemSelection();
			}

			@Override
			public void cancelSelection() {
				close();
			}
		});
	}

	private void close() {
		if (listener != null) {
			// cancel item selection
			listener.useItem(-1, null);
			listener = null;

		}
		isVisible = false;
	}

	private void showItemSelection() {
		Player player = manager.getPlayer();
		items.clear();
		List<InventarItemInstance> its = player.getInventarControl().getAllItems();
		List<String> selectionText = new ArrayList<String>();
		for (InventarItemInstance i : its) {
			Item item = manager.getData().getItems().getItem(i.getId());
			if (item.getType() == selectedType) {
				items.add(i);
				String t = i.getCount() + "x " + item.getName();
				selectionText.add(t);
			}
		}
		int w = 350;
		itemSelect = new ScrollableSelectionBox(manager.getData(), selectionText.toArray(new String[0]), 800 - w, 50,
				w, 7, false);
		itemSelect.setListener(new SelectionBoxListener() {
			@Override
			public void select(int item) {
				if (selectedType == ItemType.POKLBALL) {
					// finish
					isVisible = false;
					listener.useItem(items.get(item).getId(), null);
				} else if (selectedType == ItemType.MEDICIN) {
					// now select target poklmon
					isVisible = false;
					giveItemDialog.open(items.get(item).getId(), listener);
				}
			}

			@Override
			public void cancelSelection() {
				close();
			}
		});
	}

	public void render(Graphics g) {
		if (isVisible) {
			text.render(g);
			if (selectedType == null) {
				typeSelection.render(g);
			} else {
				itemSelect.render(g);
			}
		}
		giveItemDialog.render(g);
	}

	public void update() {
		giveItemDialog.update();
		if (isVisible) {

			if (selectedType == null) {
				typeSelection.update();
			} else {
				itemSelect.update();
				if (GUIUpdate.isCancel()) {
					close();
				}
			}

		}

	}
}
