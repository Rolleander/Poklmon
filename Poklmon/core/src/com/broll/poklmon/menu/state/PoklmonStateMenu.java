package com.broll.poklmon.menu.state;

import com.broll.pokllib.poklmon.ElementType;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.render.hud.HudRenderUtils;
import com.broll.poklmon.battle.render.hud.StateBar;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.menu.MenuUtils;
import com.broll.poklmon.menu.state.sites.AttackSite;
import com.broll.poklmon.menu.state.sites.AttributesSite;
import com.broll.poklmon.menu.state.sites.FameSite;
import com.broll.poklmon.menu.state.sites.GenomSite;
import com.broll.poklmon.menu.state.sites.InfoSite;
import com.broll.poklmon.menu.state.sites.StatisticSite;
import com.broll.poklmon.menu.state.sites.TrainingSite;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.PoklmonData;

import java.util.ArrayList;
import java.util.List;

public class PoklmonStateMenu {

	private boolean isVisible = false;
	private DataContainer data;
	private PoklmonData poklmon;
	private Poklmon poklmonInfo;
	private Image poklmonImage;
	private float angle;
	private List<StateSite> stateSites = new ArrayList<StateSite>();
	private int currentSite = 0;
	private int maxKp;
	private FontUtils fontUtils=new FontUtils();

	public PoklmonStateMenu(DataContainer data) {
		this.data = data;
		currentSite = 0;
	}

	public void open(PoklmonData poklmon) {
		this.poklmon = poklmon;
		stateSites.clear();
		int id = poklmon.getPoklmon();
		poklmonInfo = data.getPoklmons().getPoklmon(id);
		String src = poklmonInfo.getGraphicName();
		poklmonImage = data.getGraphics().getPoklmonImage(src).getScaledCopy(3);

		// init sites
		stateSites.add(new InfoSite(poklmonInfo, poklmon, data));
		AttributesSite asite = new AttributesSite(poklmonInfo, poklmon, data);

		stateSites.add(asite);
		stateSites.add(new AttackSite(poklmonInfo, poklmon, data));
		stateSites.add(new GenomSite(poklmonInfo, poklmon, data));
		stateSites.add(new TrainingSite(poklmonInfo, poklmon, data));
		stateSites.add(new StatisticSite(poklmonInfo, poklmon, data));
		stateSites.add(new FameSite(poklmonInfo, poklmon, data));

		maxKp = asite.getKp();
		isVisible = true;
	}

	public void close() {
		isVisible = false;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void update() {
		if (isVisible) {
			if (GUIUpdate.isMoveLeft()) {
				data.getSounds().playSound(GUIDesign.MOVE_SOUND);
				currentSite--;
				if (currentSite < 0) {
					currentSite = stateSites.size() - 1;
				}
			}

			if (GUIUpdate.isMoveRight()) {
				data.getSounds().playSound(GUIDesign.MOVE_SOUND);
				currentSite++;
				if (currentSite >= stateSites.size()) {
					currentSite = 0;
				}
			}
		}
	}

	public void render(Graphics g) {
		if (isVisible) {

			Image background = data.getGraphics().getMenuGraphicsContainer().getStateBackground();
			background.draw();

			String name = poklmon.getName();
			if (name == null) {
				name = poklmonInfo.getName();
			}

			// draw title
			g.setFont(GUIFonts.titleText);
			g.setColor(ColorUtil.newColor(250, 250, 250));
			g.drawString(name, 20, 5);

			// draw status
			MainFightStatus status = poklmon.getStatus();
			if (status != null) {
				HudRenderUtils.renderMainStatus(g,fontUtils, status, 20,70);
			}

			// draw types
			ElementType base = poklmonInfo.getBaseType();
			ElementType second = poklmonInfo.getSecondaryType();

			float x = 50;
			float y = 465;

			SpriteSheet elementGraphics = data.getGraphics().getMenuGraphicsContainer().getElements();
			Image elGraphic = elementGraphics.getSprite(0, base.ordinal());
			elGraphic.draw(x, y);
			if (second != null) {
				x += 65;
				elGraphic = elementGraphics.getSprite(0, second.ordinal());
				elGraphic.draw(x, y);

			}

			// draw level
			g.setFont(GUIFonts.dialogText);

			int level = poklmon.getLevel();
			String levelText = "Lv." + level;
			int w = MenuUtils.getTextWidth(g,fontUtils, levelText);
			MenuUtils.drawFancyString(g, levelText, 780 - w, 20);

			// draw image
			poklmonImage.setCenterOfRotation();
			poklmonImage.drawCentered(160, 270);
			poklmonImage.rotate((float) (Math.cos(angle) * 0.3));
			angle += 0.05;

			// draw kp box
			renderKPBar(g, 91, 505);

			// draw current site
			stateSites.get(currentSite).render(g, 350, 170);

			// draw rider

			float xp = 352;
			float yp = 120;
			SpriteSheet back = data.getGraphics().getMenuGraphicsContainer().getRiderBackground();
			SpriteSheet menus = data.getGraphics().getMenuGraphicsContainer().getMenuRiders();

			for (int i = 0; i < stateSites.size(); i++) {
				Image rider;
				if (i == currentSite) {
					rider = back.getSprite(1, 0);
				} else {
					rider = back.getSprite(0, 0);
				}
				rider.draw(xp, yp);
				menus.getSprite(i, 0).draw(xp, yp);
				xp += 40;

			}
		}
	}

	private void renderKPBar(Graphics g, float xpos, float ypos) {
		int kp = poklmon.getKp();
		float percent = (float) kp / (float) maxKp;
		if (percent < 0) {
			percent = 0;
		}
		if (percent > 1) {
			percent = 1;
		}
		float width = 204;
		float kpw = width * percent;
		if (kpw < 1 && percent > 0) {
			kpw = 1;
		}

		Image bar = StateBar
				.getHealtBarSprite(data.getGraphics().getBattleGraphicsContainer().getHealthbars(), percent);
		bar.draw(xpos, ypos, kpw, 10);

		g.setColor(ColorUtil.newColor(250, 250, 250));
		g.drawString("KP: " + kp + " / " + maxKp, xpos - 45, ypos + 15);
	}

	public PoklmonData getPoklmon() {
		return poklmon;
	}
}
