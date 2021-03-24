package com.broll.poklmon.menu.pokldex;

import com.badlogic.gdx.graphics.Color;
import com.broll.pokllib.poklmon.ElementType;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.dialog.MessageLineCutter;
import com.broll.poklmon.gui.selection.ScrollableSelectionContext;
import com.broll.poklmon.menu.MenuPage;
import com.broll.poklmon.menu.PlayerMenu;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.resource.GUIFonts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokldexMenu extends MenuPage {

	private final static int ENTRY_HEIGHT = 25;
	private final static int ENTRY_SPACE = 5;
	private final static int ENTRIES = 16;
	private final static int HAS_SEEN = 0;
	private final static int UNKNOWN = -1;
	private final static Color COLOR_CACHED = ColorUtil.newColor(50, 50, 50);
	private final static Color COLOR_SEEN = ColorUtil.newColor(70, 70, 70);
	private final static Color COLOR_UNKNOWN = ColorUtil.newColor(100, 100, 100);
	private int maxNr;
	// private int selection = 0;
	private ScrollableSelectionContext selection;
	private List<Poklmon> poklmons;

	public PokldexMenu(PlayerMenu menu, Player player, DataContainer data) {
		super(menu, player, data);
	}

	@Override
	public void onEnter() {
		poklmons = new ArrayList<Poklmon>();
		for (int i = 0; i < data.getPoklmons().getNumberOfPoklmons(); i++) {
			poklmons.add(data.getPoklmons().getPoklmon(i));
		}
		Collections.sort(poklmons, new Comparator<Poklmon>() {
			@Override
			public int compare(Poklmon o1, Poklmon o2) {
				int nr1 = o1.getPokldexNumber();
				int nr2 = o2.getPokldexNumber();
				return nr1 < nr2 ? -1 : nr1 == nr2 ? 0 : 1;
			}
		});
		maxNr = poklmons.size() - 1;
		selection = new ScrollableSelectionContext(data,poklmons.size(), ENTRIES);
	}

	@Override
	public void onExit() {
	}

	private void renderPoklInfo(Graphics g, int x, int y) {
		g.setColor(ColorUtil.newColor(250, 250, 250));
		g.setFont(GUIFonts.hudText);
		g.drawString("PoklDex - Eintrag", x, y);
		y += 40;

		String name = "???";
		String description = "";
		ElementType e1 = null, e2 = null;
		int catchCount = 0;
		String firstSeen = "-";
		boolean hasEvolution = false;
		String evolveLevel = "?";
		String evolveTo = "???";
		boolean catched = false;
		Image image = data.getGraphics().getMenuGraphicsContainer().getMissingPoklmon();
		int state = getPoklmonState(selection.getSelectedIndex());
		if (state > UNKNOWN) {
			Poklmon pokl = poklmons.get(selection.getSelectedIndex());
			name = pokl.getName();
			description = pokl.getDescription();
			catchCount = state;
			firstSeen = player.getPokldexControl().getSeenTimestamp(pokl.getId());
			if (firstSeen == null) {
				firstSeen = "WAT";
			}
			if (state > HAS_SEEN) {
				catched = true;
				e1 = pokl.getBaseType();
				e2 = pokl.getSecondaryType();
				int evolve = pokl.getEvolveIntoPoklmon();
				if (evolve != -1) {
					hasEvolution = true;
					evolveTo = data.getPoklmons().getPoklmon(evolve).getName();
					if (player.getPokldexControl().hasCachedPoklmon(evolve)) {
						evolveLevel = "" + pokl.getEvolveLevel();
					}
				}
			}
			else{
				//not catched
				description="";
				
			}
			image = data.getGraphics().getPoklmonImage(pokl.getGraphicName());
		}
		x += 3;
		y += 5;
		g.setColor(ColorUtil.newColor(0f, 0f, 0f, 0.4f));
		g.fillRect(x - 2, y - 2, 100, 100);
		image.draw(x, y);
		if (catched) {
			data.getGraphics().getMenuGraphicsContainer().getPokldexIcons().getSprite(2, 0).draw(x, y + 74);
		}
		Color c1 = ColorUtil.newColor(50, 50, 50);
		Color c2 = ColorUtil.newColor(50, 50, 150);
		g.setFont(GUIFonts.smallText);
		y -= 5;
		x += 104;
		int s = 110;
		int yd = 26;
		g.setColor(c1);
		g.drawString("Poklmon:", x, y);
		g.setColor(c2);
		g.drawString(name, x + s, y);
		y += yd;
		g.setColor(c1);
		g.drawString("Typ:", x, y);
		g.setColor(c2);
		if(e1==null&&e2==null){
			g.drawString("???", x+s, y);
		}
		if (e1 != null) {
			data.getGraphics().getMenuGraphicsContainer().getElements().getSprite(0, e1.ordinal()).draw(x + s, y + 2);
		}
		if (e2 != null) {
			data.getGraphics().getMenuGraphicsContainer().getElements().getSprite(0, e2.ordinal())
					.draw(x + s + 65, y + 2);
		}
		y += yd;
		if (hasEvolution) {
			g.setColor(c1);
			g.drawString("Lv." + evolveLevel + " entw. ", x, y);
			g.setColor(c2);
			g.drawString(evolveTo, x + s, y);

		}
		y += yd;

		g.setColor(c1);
		g.drawString("Gefangen:", x, y);
		g.setColor(c2);
		g.drawString("" + catchCount, x + s, y);
		y += yd;
		x -= 104;
		g.setColor(c1);
		s += 60;
		g.drawString("Gesehen:", x, y);
		g.setColor(c2);
		g.drawString(firstSeen, x + s, y);
		y += yd;

		g.setColor(ColorUtil.newColor(220, 220, 220, 150));
		g.fillRect(x - 2, y + 2, 380, 80);
		g.setColor(ColorUtil.newColor(20, 20, 20));
		g.setFont(GUIFonts.tinyText);
		String[] lines = MessageLineCutter.cutMessage(description,fontUtils, GUIFonts.tinyText, 375, 15);
		for (String st : lines) {
			g.drawString(st, x, y);
			y += 18;
		}
	}

	private void renderDexInfo(Graphics g, int x, int y) {
		// draw ?bersicht
		g.setFont(GUIFonts.hudText);
		g.drawString("Übersicht", x, y);
		g.setFont(GUIFonts.smallText);
		y += 40;
		x += 4;
		int s = 200;
		Color c1 = ColorUtil.newColor(50, 50, 50);
		Color c2 = ColorUtil.newColor(50, 50, 150);
		g.setColor(c1);
		g.drawString("Gesamt:", x, y);
		g.setColor(c2);
		g.drawString("" + poklmons.size(), x + s, y);
		y += 30;
		g.setColor(c1);
		g.drawString("Gefangen:", x, y);
		g.setColor(c2);
		g.drawString("" + player.getPokldexControl().getDifferentPoklmonCached(), x + s, y);
		y += 30;
		g.setColor(c1);
		g.drawString("Gesehen:", x, y);
		g.setColor(c2);
		g.drawString("" + player.getPokldexControl().getDifferentPoklmonSeen(), x + s, y);

	}

	@Override
	public void render(Graphics g) {
		Image background = data.getGraphics().getMenuGraphicsContainer().getPokldexBackground();
		background.draw();
		// draw title
		g.setFont(GUIFonts.titleText);
		g.setColor(ColorUtil.newColor(250, 250, 250));
		g.drawString("PoklDex", 20, 5);

		int x = 390;
		int y = 100;

		renderDexInfo(g, x, y);

		// draw poklmon entry
		y = 325;
		x = 390;
		renderPoklInfo(g, x, y);

		// draw pokldex entries
		x = 23 + 4;
		y = 100 + 3;

		g.setFont(GUIFonts.smallText);
		int w = 340 - 8;
		for (int i = 0; i < ENTRIES; i++) {

			int id = selection.getStartPos() + i;
			if (id > maxNr || id < 0) {
				continue;
			}
			int state = getPoklmonState(id);
			boolean known = state != UNKNOWN;

			if (id == selection.getSelectedIndex()) {
				g.setColor(ColorUtil.newColor(10, 10, 10));
				g.fillRect(x - 3, y - 3, w + 6, ENTRY_HEIGHT + 6);
				g.setColor(ColorUtil.newColor(250, 250, 250));
				g.fillRect(x - 2, y - 2, w + 4, ENTRY_HEIGHT + 4);
			}
			if (state > HAS_SEEN) {
				g.setColor(ColorUtil.newColor(200, 200, 230));
			} else if (known) {
				g.setColor(ColorUtil.newColor(200, 200, 200));
			} else {
				g.setColor(ColorUtil.newColor(120, 120, 120));
			}
			g.fillRect(x, y, w, ENTRY_HEIGHT);
			g.setColor(ColorUtil.newColor(50, 50, 50));
			int nr = poklmons.get(id).getPokldexNumber();
			g.drawString((nr) + ".", x + 30, y);
			int icon = 2;
			if (state > HAS_SEEN) {
				g.setColor(COLOR_CACHED);
			} else if (state == HAS_SEEN) {
				g.setColor(COLOR_SEEN);
				icon = 1;
			} else {
				g.setColor(COLOR_UNKNOWN);
				icon = 0;
			}
			data.getGraphics().getMenuGraphicsContainer().getPokldexIcons().getSprite(icon, 0).draw(x + 4, y);
			String name = "???";
			if (known) {
				name = poklmons.get(id).getName();
			}
			g.drawString(name, x + 60, y);
			y += ENTRY_HEIGHT + ENTRY_SPACE;
		}
	}

	public int getPoklmonState(int id) {
		int poklId = poklmons.get(id).getId();
		if (player.getPokldexControl().hasCachedPoklmon(poklId)) {
			return player.getPokldexControl().getCachedCount(poklId);
		} else if (player.getPokldexControl().hasSeenPoklmon(poklId)) {
			return HAS_SEEN;
		}
		return UNKNOWN;
	}

	@Override
	public void update(float delta) {
		selection.update();

		if (GUIUpdate.isCancel()) {
			close();
		}

	}

}
