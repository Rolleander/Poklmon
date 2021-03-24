package com.broll.poklmon.data;

import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;

public class BattleGraphicsContainer extends ResourceContainer {

	private final static String battleGraphicPath = "battle/";

	private SpriteSheet animationSet;
	private SpriteSheet pokeballs;
	private Image battleBackground, battleSky;
	private Image caveForeground, caveBackground;

	private Image fog, trainerBar;
	private SpriteSheet healthbars, trainerIcons;
	private SpriteSheet attributeArrows;
	private Image teamBox, fightboxPlayer, fightboxEnemy;
	private Image evolutionBackground, evolutionAnimation;

	public BattleGraphicsContainer() {
		setPath(battleGraphicPath);
	}

	public void load() throws DataException {

		animationSet = DataLoader.loadAnimationSet();

		caveBackground = loadImage("cave.png");
		caveForeground = loadImage("foregroundCave.png");

		battleBackground = loadImage("backgroundGrass.png");
		evolutionBackground = loadImage("evolution_background.png");
		evolutionAnimation = loadImage("evolution_animation.png");
		battleSky = loadImage("sky.png");

		fog = loadImage("fog.jpg");
		teamBox = loadImage("teambox.png");
		fightboxPlayer = loadImage("fightboxPlayer.png");
		fightboxEnemy = loadImage("fightboxEnemy.png");
		trainerBar = loadImage("trainerBar.png");

		pokeballs = loadSprites("ball.png", 24, 24);
		attributeArrows = loadSprites("arrows.png", 64, 64);
	
		healthbars = loadSprites("kpBar.png", 5, 10);
		trainerIcons = loadSprites("trainerIcons.png", 14, 14);

	}

	public Image getTrainerBar() {
		return trainerBar;
	}

	public SpriteSheet getTrainerIcons() {
		return trainerIcons;
	}

	public Image getCaveBackground() {
		return caveBackground;
	}

	public Image getCaveForeground() {
		return caveForeground;
	}

	public Image getEvolutionAnimation() {
		return evolutionAnimation;
	}

	public Image getEvolutionBackground() {
		return evolutionBackground;
	}

	public Image getFightboxEnemy() {
		return fightboxEnemy;
	}

	public Image getFightboxPlayer() {
		return fightboxPlayer;
	}

	public SpriteSheet getAnimationSet() {
		return animationSet;
	}

	public SpriteSheet getPokeballs() {
		return pokeballs;
	}

	public Image getBattleBackground() {
		return battleBackground;
	}

	public Image getBattleSky() {
		return battleSky;
	}

	public Image getFog() {
		return fog;
	}

	public SpriteSheet getHealthbars() {
		return healthbars;
	}

	public SpriteSheet getAttributeArrows() {
		return attributeArrows;
	}

	public Image getTeamBox() {
		return teamBox;
	}

}
