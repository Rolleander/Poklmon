package com.broll.poklmon.menu;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.menu.inventar.InventarMenu;
import com.broll.poklmon.menu.pc.PcMenu;
import com.broll.poklmon.menu.player.TrainerMenu;
import com.broll.poklmon.menu.pokldex.PokldexMenu;
import com.broll.poklmon.menu.poklmon.PoklmonMenu;
import com.broll.poklmon.menu.state.StateMenu;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.resource.GUIDesign;

public class MenuPageContainer {
	private MenuPage currentPage;
	private PlayerMenu playerMenu;
	private ArrayList<MenuPage> pageContainer = new ArrayList<MenuPage>();
	private Stack<MenuPage> pageStack = new Stack<MenuPage>();
	private DataContainer data;
	
	public MenuPageContainer(PlayerMenu menu, Player player, DataContainer data) {
		this.playerMenu = menu;
		this.data=data;
		// init pages
		pageContainer.add(new PoklmonMenu(menu, player, data));
		pageContainer.add(new TrainerMenu(menu, player, data));
		pageContainer.add(new StateMenu(menu, player, data));
		pageContainer.add(new PcMenu(menu, player, data));
		pageContainer.add(new PokldexMenu(menu, player, data));
		pageContainer.add(new InventarMenu(menu, player, data));
		

	}

	public void init() {
		pageStack.clear();
		currentPage = null;
	}

	public void closePage() {
		data.getSounds().playSound(GUIDesign.CANCEL_SOUND);
		
		MenuPage last = null;
		try {
			last = pageStack.pop();
		} catch (EmptyStackException e) {
		}
		if (last != null) {
			// open last page
			currentPage.onExit();
			currentPage = last;
			currentPage.onEnter();
		} else {
			
			// close menu
		
			playerMenu.closeMenu();
		}
	}

	public MenuPage getPage(Class<? extends MenuPage> pageClass) {
		for (MenuPage page : pageContainer) {
			if (page.getClass().equals(pageClass)) {
				return page;
			}
		}
		return null;
	}

	public void openPage(Class<? extends MenuPage> pageClass) {
		for (MenuPage page : pageContainer) {
			if (page.getClass().equals(pageClass)) {
				if (currentPage != null) {
					data.getSounds().playSound(GUIDesign.CLICK_SOUND);
					currentPage.onExit();
					pageStack.add(currentPage);	
				}
				currentPage = page;
				currentPage.onEnter();
				return;
			}
		}
	}

	public void render(Graphics g) {
		if (currentPage != null) {
			currentPage.render(g);
		}
	}

	public void update(float delta) {
		if (currentPage != null) {
			currentPage.update(delta);
		}
	}
}
