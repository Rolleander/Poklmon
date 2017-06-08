package com.broll.poklmon.menu.inventar;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.broll.pokllib.item.Item;
import com.broll.pokllib.item.ItemType;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.game.items.execute.ItemExecuteCallback;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.selection.ScrollableSelectionContext;
import com.broll.poklmon.menu.MenuPage;
import com.broll.poklmon.menu.MenuUtils;
import com.broll.poklmon.menu.PlayerMenu;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.resource.GUIFonts;

public class InventarMenu extends MenuPage {

	private int bagSelection;
	private List<InventarItem> items = new ArrayList<InventarItem>();
	private ItemType[] bags = new ItemType[] { ItemType.POKLBALL, ItemType.MEDICIN, ItemType.ATTACK, ItemType.WEARABLE,
			ItemType.BASIS_ITEM, ItemType.OTHER };
	private ScrollableSelectionContext[] itemSelection;
	private final static int ITEM_COUNT = 12;

	public InventarMenu(PlayerMenu menu, Player player, DataContainer data) {
		super(menu, player, data);
	}

	@Override
	public void onEnter() {
		bagSelection = 0;
		itemSelection = new ScrollableSelectionContext[bags.length];
		for (int i = 0; i < bags.length; i++) {
			int count = 0;
			for (int h = 0; h < data.getItems().getNumberOfItems(); h++) {
				Item item = data.getItems().getItem(h);
				if (item.getType() == bags[i] && player.getInventarControl().getItemCount(item.getId()) > 0) {
					count++;
				}
			}
			itemSelection[i] = new ScrollableSelectionContext(data, count, ITEM_COUNT);
		}
		updateBag();
	}

	private void updateBag() {
		items.clear();
		ItemType type = bags[bagSelection];
		for (int i = 0; i < data.getItems().getNumberOfItems(); i++) {
			Item item = data.getItems().getItem(i);
			if (item.getType() == type) {
				int count = player.getInventarControl().getItemCount(item.getId());
				if (count > 0) {
					items.add(new InventarItem(item, count));
				}
			}
		}
	}

	@Override
	public void onExit() {
	}

	@Override
	public void render(Graphics g) {
		// render background
		//TODO
	//	GradientFill gradient = new GradientFill(0, 0, ColorUtil.newColor(200, 200, 250), 0, 600, ColorUtil.newColor(50, 50, 150));
	//	g.fill(new Rectangle(0, 0, 800, 600), gradient);

		// render bag selection
		int x = 10;
		int y = 20;
		for (int i = 0; i < bags.length; i++) {
			ItemType type = bags[i];
			String text = type.getName();
			x += MenuUtils.drawButton(g, text, x, y, i == bagSelection);
		}

		// render items
		x = 20;
		y = 80;
		int w = 400;
		int h = 40;
		int start = itemSelection[bagSelection].getStartPos();
		int pos = itemSelection[bagSelection].getSelectPos();
		g.setColor(ColorUtil.newColor(130, 130, 150));
		g.fillRect(x - 6, y - 6, w + 12, h * ITEM_COUNT + 12);
		g.setColor(ColorUtil.newColor(30, 30, 30));
		g.drawRect(x - 6, y - 6, w + 12, h * ITEM_COUNT + 12);

		for (int i = 0; i < ITEM_COUNT; i++) {
			int nr = start + i;
			if (nr < items.size()) {
				InventarItem item = items.get(nr);
				g.setColor(ColorUtil.newColor(70, 70, 70));
				g.fillRect(x - 2, y - 2, w + 4, h + 2);
				if (pos == i) {
					g.setColor(ColorUtil.newColor(150, 150, 250));
				} else {
					g.setColor(ColorUtil.newColor(100, 100, 100));
				}
				g.fillRect(x, y, w, h - 2);
				g.setColor(ColorUtil.newColor(250, 250, 250));
				g.setFont(GUIFonts.hudText);
				MenuUtils.drawFancyString(g, "" + item.getItem().getName(), x + 10, y);
				int c = item.getCount();
				if (c > 1) {
					g.setColor(ColorUtil.newColor(30, 30, 30));
					g.setFont(GUIFonts.smallText);
					String cs = "" + c;
					g.drawString(cs, x + w - 10 - MenuUtils.getTextWidth(g, cs), y + 5);
				}
				if (item.getItem().getType() == ItemType.BASIS_ITEM) {
					String shortcut = null;
					int id = item.getItem().getId();
					int shortcutA = player.getVariableControl().getInt(Player.SHORTCUT + "A");
					int shortcutB = player.getVariableControl().getInt(Player.SHORTCUT + "B");
					if (shortcutA == id) {
						shortcut=GUIUpdate.getKeyShortcutA();
						g.setColor(ColorUtil.newColor(30, 30, 200));
					}
					else if (shortcutB == id) {
						shortcut=GUIUpdate.getKeyShortcutB();
						g.setColor(ColorUtil.newColor(30, 30, 200));
					}
					if(shortcut!=null)
					{
						g.setFont(GUIFonts.smallText);
						g.drawString(shortcut,x+345,y+5);						
					}
				}
				y += h;
			}
		}
		y = 80;
		x = 450;
		w = 316;
		h = 250;

		g.setColor(ColorUtil.newColor(130, 130, 150));
		g.fillRect(x - 6, y - 6, w + 12, h + 12);
		g.setColor(ColorUtil.newColor(30, 30, 30));
		g.drawRect(x - 6, y - 6, w + 12, h + 12);
		g.setColor(ColorUtil.newColor(100, 100, 100));
		g.fillRect(x, y, w, h);

		InventarItem item = null;
		int nr = itemSelection[bagSelection].getSelectedIndex();
		if (nr < items.size()) {
			item = items.get(nr);
		}
		if (item != null) {

			g.setColor(ColorUtil.newColor(50, 50, 50, 100));
			g.fillRect(x, y, w, 38);
			x += 10;

			g.setColor(ColorUtil.newColor(250, 250, 250));
			g.setFont(GUIFonts.hudText);
			MenuUtils.drawFancyString(g, item.getItem().getName(), x, y);

			String text = item.getItem().getDescription();
			g.setFont(GUIFonts.smallText);
			g.setColor(ColorUtil.newColor(30, 30, 30));
			y += 40;
			MenuUtils.drawBoxString(g, text, x, y, w + 150, 3);
		}
	}

	private void useItem(Item item) {
		if (item.getType() == ItemType.OTHER) {
			menu.getItemExecutor().useOtherItem(item, new ItemExecuteCallback() {
				@Override
				public void itemExecuted(Item item, boolean itemUsed) {
					if (itemUsed) {
						player.getInventarControl().useItem(item.getId());
						updateBag();
					}
				}
			});
		} else if (item.getType() == ItemType.BASIS_ITEM) {
			menu.getItemExecutor().useBasisItem(item);
		} else if (item.getType() == ItemType.ATTACK) {
			menu.getItemExecutor().useAttackItem(item, new ItemExecuteCallback() {
				@Override
				public void itemExecuted(Item item, boolean itemUsed) {
					if (itemUsed) {
						player.getInventarControl().useItem(item.getId());
						updateBag();
					}
				}
			});
		}
	}

	private void shortcutItem(Item item, boolean shortcutA) {
		if (item.getType() == ItemType.BASIS_ITEM) {
			String shortcut = "A";
			String shortcut2 = "B";
			if (!shortcutA) {
				shortcut = "B";
				shortcut2 = "A";
			}
			player.getVariableControl().setInt(Player.SHORTCUT + shortcut, item.getId());
			//reset other, so you cant point both to the same item
			if (player.getVariableControl().getInt(Player.SHORTCUT + shortcut2) == item.getId()) {
				player.getVariableControl().setInt(Player.SHORTCUT + shortcut2, 0);
			}
		}
	}

	@Override
	public void update(float delta) {
		int before = bagSelection;
		bagSelection = MenuUtils.updateSelection(bagSelection, bags.length, 1);
		if (bagSelection != before) {
			updateBag();
		}
		itemSelection[bagSelection].update();
		InventarItem item = null;
		int nr = itemSelection[bagSelection].getSelectedIndex();
		if (nr < items.size()) {
			item = items.get(nr);
		}
		if (item != null) {
			if (GUIUpdate.isClick()) {

				String effect = item.getItem().getEffect();
				if (!effect.isEmpty()) {
					data.getSounds().playSound(GUIDesign.CLICK_SOUND);
					useItem(item.getItem());
				}

			} else if (GUIUpdate.isShortcutA()) {
				shortcutItem(item.getItem(), true);
			} else if (GUIUpdate.isShortcutB()) {
				shortcutItem(item.getItem(), false);
			}
		}
		if (GUIUpdate.isCancel()) {
			close();
		}
	}

}
