package com.broll.poklmon.game.scene.script.commands;

import java.util.ArrayList;
import java.util.List;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.item.Item;
import com.broll.pokllib.item.ItemType;
import com.broll.pokllib.script.syntax.VariableException;
import com.broll.poklmon.battle.item.InventarItemInstance;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.control.MessageGuiControl;
import com.broll.poklmon.game.scene.script.CommandControl;
import com.broll.poklmon.game.scene.script.Invoke;
import com.broll.poklmon.gui.input.NumberInputField;
import com.broll.poklmon.menu.pc.PcMenu;
import com.broll.poklmon.model.shop.ShopArticle;
import com.broll.poklmon.model.shop.ShopInstance;
import com.broll.poklmon.player.control.impl.InventarControl;
import com.broll.poklmon.save.PoklmonData;

public class MenuCommands extends CommandControl {

	private ShopInstance shop;
	private DialogCommands dialog;
	private PlayerCommands playerCommands;

	public MenuCommands(GameManager game, DialogCommands dialog, PlayerCommands playerCommands) {
		super(game);
		this.dialog = dialog;
		this.playerCommands = playerCommands;
	}

	public void openPc() {
		game.getData().getSounds().playSound("computer");
		invoke(new Invoke() {
			@Override
			public void invoke() throws VariableException {
				game.getMessageGuiControl().showMenu(PcMenu.class);
			}
		});
	}

	public void initShop() {
		shop = new ShopInstance(game.getData(), true, true);
	}

	public void initShop(boolean buy, boolean sell) {
		shop = new ShopInstance(game.getData(), buy, sell);
	}

	public void addShopItem(int item) {
		shop.addArticle(new ShopArticle(item));
	}

	public void addShopItem(int item, int price) {
		shop.addArticle(new ShopArticle(item, price));
	}

	public void addShopItemSellValue(int item, int value) {
		shop.addCustomSellValue(item, value);
	}

	public void setShopSellFactor(float factor) {
		shop.setSellFactor(factor);
	}

	public boolean openAttackLearnig(int atkNr) {
		final MessageGuiControl gui = game.getMessageGuiControl();
		Attack atk = game.getData().getAttacks().getAttack(atkNr);
		final String atkName = atk.getName();
		List<PoklmonData> targets = game.getPlayer().getPoklmonControl().getAttackLearningTargets(atkNr);
		if (targets.size() > 0) {
			invoke(new Invoke() {
				public void invoke() throws VariableException {
					gui.showText("Welches deiner Poklmon soll " + atkName + " lernen?");
				}
			});
			PoklmonData target = dialog.selectionPoklmon(targets);
			playerCommands.getLevelUpHandler().initPoklmon(target);
			return playerCommands.getLevelUpHandler().canLearnAttack(atkNr);
		} else {
			invoke(new Invoke() {
				public void invoke() throws VariableException {
					gui.showText("Keines deiner Poklmon kann " + atkName + " lernen!");
				}
			});
			return false;
		}
	}

	public void openShop(final String shopText, final String cancelText) {

		final MessageGuiControl gui = game.getMessageGuiControl();

		boolean inShop = true;
		boolean inList = true;
		invoke(new Invoke() {
			public void invoke() throws VariableException {
				gui.showText(shopText);
			}
		});

		final boolean[] locked = new boolean[ShopInstance.SHOP_ACTIONS.length];
		locked[0] = !shop.isBuy();
		locked[1] = !shop.isSell();
		do {
			gui.showInfoBox("$" + game.getPlayer().getInventarControl().getMoney());
			gui.showInfo(shopText);
			invoke(new Invoke() {
				public void invoke() throws VariableException {
					gui.showSelection(ShopInstance.SHOP_ACTIONS, locked, true, false);
				}
			});

			int selection = gui.getSelectedOption();

			if (selection == 0) {
				inList = true;
				do {
					gui.showInfoBox("$" + game.getPlayer().getInventarControl().getMoney());
					gui.showInfo(shopText);
					// buy
					final List<String> buyList = new ArrayList<String>();
					for (ShopArticle article : shop.getArticles()) {
						String name = game.getData().getItems().getItem(article.getItemId()).getName();
						String price = article.getPrice() + "$";
						buyList.add(name + " : " + price);
					}
					invoke(new Invoke() {
						public void invoke() throws VariableException {
							gui.showSelection(buyList.toArray(new String[0]), new boolean[buyList.size()], true, false);
						}
					});

					selection = gui.getSelectedOption();
					if (selection > -1) {
						// buy item selected
						ShopArticle item = shop.getArticles().get(selection);
						int money = game.getPlayer().getInventarControl().getMoney();
						final int max = money / item.getPrice();
						if (max == 0) {
							invoke(new Invoke() {
								public void invoke() throws VariableException {
									gui.showText("Das kannst du garnicht bezahlen!");
								}
							});
						} else {
							final String name = game.getData().getItems().getItem(item.getItemId()).getName();
							gui.showInfo(" >" + name + "< Wie viele sollen es denn sein?");
							invoke(new Invoke() {
								public void invoke() throws VariableException {
									gui.openNumberInput("", 1, 1, max, true);
								}
							});
							final int number = gui.getInputNumber();
							if (number != NumberInputField.CANCEL_VALUE) {
								final int price = item.getPrice() * number;
								invoke(new Invoke() {
									public void invoke() throws VariableException {
										gui.showText(number + " x " + name + ", das macht dann  " + price + "$!");
									}
								});
								InventarControl inventar = game.getPlayer().getInventarControl();
								inventar.addMoney(-price);
								for (int i = 0; i < number; i++) {
									inventar.addItem(item.getItemId());
								}
							}
						}
					} else {
						// cancel buying
						inList = false;
					}
				} while (inList);
			} else if (selection == 1) {
				// sell
				inList = true;
				do {
					gui.showInfoBox("$" + game.getPlayer().getInventarControl().getMoney());
					gui.showInfo("Was möchtest du verkaufen?");
					// sell items

					final List<String> sellList = new ArrayList<String>();
					List<Item> sellItems = new ArrayList<Item>();
					for (InventarItemInstance item : game.getPlayer().getInventarControl().getAllItems()) {
						Item i = game.getData().getItems().getItem(item.getId());
						// cant sell basis items!
						if (i.getType() != ItemType.BASIS_ITEM) {
							String name = i.getName();
							String price = shop.getSellWorth(item.getId()) + "$";
							sellList.add(item.getCount() + " x " + name + " [ " + price + " ]");
							sellItems.add(i);
						}

					}
					invoke(new Invoke() {
						public void invoke() throws VariableException {
							gui.showSelection(sellList.toArray(new String[0]), new boolean[sellList.size()], true,
									false);
						}
					});
					selection = gui.getSelectedOption();
					if (selection > -1) {
						// sell item selected
						InventarControl inventar = game.getPlayer().getInventarControl();
						Item item = sellItems.get(selection);
						final String name = game.getData().getItems().getItem(item.getId()).getName();
						int id = item.getId();
						int value = shop.getSellWorth(id);
						final int count = inventar.getItemCount(id);
						gui.showInfo("Verkaufen: " + name + " im Besitz: >" + count + "<  Wert: >" + value + "$<");
						invoke(new Invoke() {
							public void invoke() throws VariableException {
								gui.openNumberInput("", 1, 1, count, true);
							}
						});
						final int number = gui.getInputNumber();
						if (number != NumberInputField.CANCEL_VALUE) {
							final int price = value * number;
							invoke(new Invoke() {
								public void invoke() throws VariableException {
									gui.showText(number + " x " + name + ", dafür gebe ich dir " + price + "$!");
								}
							});
							inventar.addMoney(price);
							for (int i = 0; i < number; i++) {
								inventar.useItem(id);
							}
						}
					} else {
						// cancel buying
						inList = false;
					}

				} while (inList);
			} else {
				inShop = false;
				invoke(new Invoke() {
					public void invoke() throws VariableException {
						gui.showText(cancelText);
					}
				});

			}
		} while (inShop);
		gui.hideInfoBox();
	}
}
