package com.broll.poklmon.menu;

import com.broll.poklmon.game.control.MenuControlInterface;
import com.broll.poklmon.menu.inventar.InventarMenu;
import com.broll.poklmon.menu.pc.PcMenu;
import com.broll.poklmon.menu.player.TrainerMenu;
import com.broll.poklmon.menu.pokldex.PokldexMenu;
import com.broll.poklmon.menu.poklmon.PoklmonMenu;

public class MenuControl implements MenuControlInterface {

	private PlayerMenu menu;

	public MenuControl(PlayerMenu menu) {
		this.menu = menu;
	}

	@Override
	public void closeMenu() {
		menu.closeMenu();
	}

	@Override
	public void openPokldex() {
		menu.showMenu();
		menu.openPage(PokldexMenu.class);
	}

	@Override
	public void openTeam() {
		menu.showMenu();
		menu.openPage(PoklmonMenu.class);
	}

	@Override
	public void openInventar() {
		menu.showMenu();
		menu.openPage(InventarMenu.class);
	}

	@Override
	public void openPlayer() {
		menu.showMenu();
		menu.openPage(TrainerMenu.class);
	}

	@Override
	public void openSettings() {

	}

	@Override
	public void openPc() {
		menu.showMenu();
		menu.openPage(PcMenu.class);
	}

}
