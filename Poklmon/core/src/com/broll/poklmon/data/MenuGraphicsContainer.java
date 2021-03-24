package com.broll.poklmon.data;

import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;

public class MenuGraphicsContainer extends ResourceContainer {

	private final static String menuGraphicPath = "menu/";

	private Image stateBackground, boxBackground, pokldexBackground, missingPoklmon,menuBackground, arrow;
	private SpriteSheet dnaspikes, elements, attacktypes;
	private SpriteSheet riderBackground, menuRiders, medals;
	private SpriteSheet teamBlock, boxBlock, designButton;
	private SpriteSheet pokldexIcons;

	public MenuGraphicsContainer() {
		setPath(menuGraphicPath);
	}

	public void load() throws DataException {
		stateBackground = loadImage("statemenu.png");
		menuBackground = loadImage("background.jpg");

		arrow = loadImage("arrow.png");
		pokldexBackground = loadImage("pokldex_background.png");
		pokldexIcons = loadSprites("pokldex_icons.png", 24, 24);
		missingPoklmon = loadImage("missing.png");
		boxBackground = loadImage("boxbackground.png");

		dnaspikes = loadSprites("dnaspikes.png", 40, 300);
		elements = loadSprites("elements.png", 60, 25);
		attacktypes = loadSprites("attacktypes.png", 41, 18);

		riderBackground = loadSprites("riderbackground.png", 40, 40);
		menuRiders = loadSprites("menurider.png", 40, 40);
		medals = loadSprites("medals.png", 32, 32);

		teamBlock = loadSprites("teamblock.png", 380, 130);
		boxBlock = loadSprites("boxblock.png", 180, 40);
		designButton = loadSprites("designbutton.png", 200, 40);
	}

	public Image getMenuBackground() {
		return menuBackground;
	}

	public Image getArrow() {
		return arrow;
	}

	public Image getMissingPoklmon() {
		return missingPoklmon;
	}

	public SpriteSheet getPokldexIcons() {
		return pokldexIcons;
	}

	public Image getPokldexBackground() {
		return pokldexBackground;
	}

	public Image getBoxBackground() {
		return boxBackground;
	}

	public SpriteSheet getDesignButton() {
		return designButton;
	}

	public SpriteSheet getBoxBlock() {
		return boxBlock;
	}

	public SpriteSheet getTeamBlock() {
		return teamBlock;
	}

	public SpriteSheet getMedals() {
		return medals;
	}

	public SpriteSheet getMenuRiders() {
		return menuRiders;
	}

	public SpriteSheet getRiderBackground() {
		return riderBackground;
	}

	public SpriteSheet getAttacktypes() {
		return attacktypes;
	}

	public SpriteSheet getElements() {
		return elements;
	}

	public SpriteSheet getDnaspikes() {
		return dnaspikes;
	}

	public Image getStateBackground() {
		return stateBackground;
	}

}
