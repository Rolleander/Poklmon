package com.broll.poklmon.menu.poklmon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.broll.pokllib.item.Item;
import com.broll.pokllib.item.ItemType;
import com.broll.poklmon.battle.item.InventarItemInstance;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.selection.ScrollableSelectionBox;
import com.broll.poklmon.gui.selection.SelectionBox;
import com.broll.poklmon.gui.selection.SelectionBoxListener;
import com.broll.poklmon.menu.MenuPage;
import com.broll.poklmon.menu.PlayerMenu;
import com.broll.poklmon.menu.state.StateMenu;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.save.PoklmonData;

public class PoklmonMenu extends MenuPage {

	private SelectionBox selectionBox = null;
	private PoklmonBlockRender poklmonBlockRender;
	private HashMap<Integer, PoklmonData> team;
	private int selection = 0;
	private final static int NUM_POKLMONS = 6;
	private final static String[] STANDARD_OPTIONS = { "Bericht", "Tausch", "Medizin geben" };
	private boolean isSwitching = false;
	private int swapPosition;
	private ScrollableSelectionBox itemSelect;
	private List<InventarItemInstance> itemList;
	

	public PoklmonMenu(PlayerMenu menu, Player player, DataContainer data) {
		super(menu, player, data);
		poklmonBlockRender = new PoklmonBlockRender(data);
	}

	@Override
	public void onEnter() {
		isSwitching = false;
		team = player.getPoklmonControl().getPoklmonsInTeam();
	}

	@Override
	public void onExit() {

	}

	private void openPoklmonStatus() {
		StateMenu state = (StateMenu) menu.getPage(StateMenu.class);
		menu.openPage(StateMenu.class);
		state.open(player.getPoklmonControl().getPoklmonsInTeam().get(selection), selection, false);
	}

	private void openPoklmonOptions() {
		final PoklmonData poklmon = player.getPoklmonControl().getPoklmonsInTeam().get(selection);
		List<String> options = new ArrayList<String>();
		options.addAll(Arrays.asList(STANDARD_OPTIONS));
		final boolean carriesItem = poklmon.getCarryItem() > -1;
		if (carriesItem) {
			options.add("Item nehmen");
		} else {
			options.add("Item anlegen");
		}
		openSelection(options.toArray(new String[0]), new SelectionBoxListener() {
			public void select(int item) {
				selectionBox = null;
				switch (item) {
				case 0:
					// bericht
					openPoklmonStatus();
					break;
				case 1:
					// tausch
					isSwitching = true;
					swapPosition = selection;
					break;
				case 2:
					// medizin geben
					givePoklmonMedicine(poklmon);
					break;
				case 3:
					if (carriesItem) {
						// remove item
						int itemId = poklmon.getCarryItem();
						player.getInventarControl().addItem(itemId);
						poklmon.setCarryItem(-1);
					} else {
						// give item
						givePoklmonWearableItem(poklmon);
					}
					break;
				}

			}

			public void cancelSelection() {
				selectionBox = null;
			}
		});
		selectionBox.blockItem(2, getItemList(ItemType.MEDICIN).length == 0);
		selectionBox.blockItem(3, getItemList(ItemType.WEARABLE).length==0&&carriesItem==false);
	}

	private String[] getItemList(ItemType type) {
		List<InventarItemInstance> its = player.getInventarControl().getAllItems();
		List<String> selectionText = new ArrayList<String>();
		itemList = new ArrayList<InventarItemInstance>();
		for (InventarItemInstance i : its) {
			Item item = data.getItems().getItem(i.getId());
			if (item.getType() == type) {
				itemList.add(i);
				String t = i.getCount() + "x " + item.getName();
				selectionText.add(t);
			}
		}
		return selectionText.toArray(new String[0]);
	}

	private void givePoklmonMedicine(final PoklmonData poklmon) {
		int x = (int) calcBoxX(selection);
		int y = (int) calcBoxY(selection);
		x += 20;
		y -= 10;
		itemSelect = new ScrollableSelectionBox(data,getItemList(ItemType.MEDICIN), x, y, 350, 7,false);
		itemSelect.setListener(new SelectionBoxListener() {
			@Override
			public void select(int id) {
				InventarItemInstance item = itemList.get(id);
				int itemId = item.getId();
				// run medicine script
				player.getInventarControl().useItem(itemId);
				menu.getItemExecutor().useMedicine(poklmon, data.getItems().getItem(item.getId()));
				
				itemSelect = null;
			}

			@Override
			public void cancelSelection() {
				itemSelect = null;
			}
		});
	}

	private void givePoklmonWearableItem(final PoklmonData poklmon) {
		int x = (int) calcBoxX(selection);
		int y = (int) calcBoxY(selection);
		x += 20;
		y -= 10;
		itemSelect = new ScrollableSelectionBox(data,getItemList(ItemType.WEARABLE), x, y, 350, 7,false);
		itemSelect.setListener(new SelectionBoxListener() {
			@Override
			public void select(int id) {
				InventarItemInstance item = itemList.get(id);
				int itemId = item.getId();
				// give poklmon item
				poklmon.setCarryItem(itemId);
				player.getInventarControl().useItem(itemId);
				itemSelect = null;
			}

			@Override
			public void cancelSelection() {
				itemSelect = null;
			}
		});
	}

	private void openSelection(String[] selections, SelectionBoxListener listener) {
		int x = (int) calcBoxX(selection);
		int y = (int) calcBoxY(selection);
		x += 400;
		y += 10;
		selectionBox = new SelectionBox(data,selections, x, y,false);
		selectionBox.setListener(listener);
		selectionBox.shiftDown();
	}

	private float calcBoxX(int select) {
		float x = 400 - PoklmonBlockRender.WIDTH;
		return x + PoklmonBlockRender.WIDTH * (select % 2);
	}

	private float calcBoxY(int select) {
		float y = 25;
		return y + PoklmonBlockRender.HEIGHT * (select / 2);
	}

	@Override
	public void render(Graphics g) {

		for (int i = 0; i < NUM_POKLMONS; i++) {
			float xpos = calcBoxX(i);
			float ypos = calcBoxY(i);
			poklmonBlockRender.render(g, xpos, ypos, team.get(i), i == selection);

		}

		if (selectionBox != null) {
			selectionBox.render(g);
		}

		if (itemSelect != null) {
			itemSelect.render(g);
		}
	}

	@Override
	public void update(float delta) {
		if (itemSelect != null) {
			itemSelect.update();
		} else if (selectionBox != null) {
			selectionBox.update();
		} else {
			updateSelection();
			if (GUIUpdate.isClick()) {
				data.getSounds().playSound(GUIDesign.CLICK_SOUND);
				if (isSwitching) {
					if (swapPosition != selection) {
						if (swapPosition == 0 && team.get(selection) == null) {
							// cant swap first position to nothing
						} else {
							// swap poklmons
							team = player.getPoklmonControl().switchTeamPositions(swapPosition, selection);
						}
					}
					isSwitching = false;
					return;
				}
				// open selectionbox
				if (team.get(selection) != null) {
					openPoklmonOptions();
				}
			}

			if (GUIUpdate.isCancel()) {
				close();
			}

		}
	}

	private void updateSelection() {
		int lastSel = selection;
		if (GUIUpdate.isMoveRight()) {
			data.getSounds().playSound(GUIDesign.MOVE_SOUND);
			selection++;
		} else if (GUIUpdate.isMoveLeft()) {
			data.getSounds().playSound(GUIDesign.MOVE_SOUND);
			selection--;
		}
		if (GUIUpdate.isMoveDown()) {
			data.getSounds().playSound(GUIDesign.MOVE_SOUND);
			selection += 2;
		} else if (GUIUpdate.isMoveUp()) {
			data.getSounds().playSound(GUIDesign.MOVE_SOUND);
			selection -= 2;
		}

		if (selection < 0) {
			selection += NUM_POKLMONS;
		} else if (selection >= NUM_POKLMONS) {
			selection -= NUM_POKLMONS;
		}

	}

}
