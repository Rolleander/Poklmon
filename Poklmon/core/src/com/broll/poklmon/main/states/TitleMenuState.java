package com.broll.poklmon.main.states;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.selection.ScrollableSelectionBox;
import com.broll.poklmon.gui.selection.SelectionBox;
import com.broll.poklmon.gui.selection.SelectionBoxListener;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.resource.MenuGraphics;
import com.broll.poklmon.save.GameData;
import com.broll.poklmon.save.manage.SaveFileInfo;
import com.broll.poklmon.save.manage.SaveFileManager;

public class TitleMenuState extends GameState {

	public static int STATE_ID = 2;
	private DataContainer data;

	private SelectionBox selection;
	private boolean saveSelection = false;
	private ScrollableSelectionBox saveBox;

	public TitleMenuState(DataContainer data) {

		this.data = data;
	}

	int cloudx = 0;
	int cloudy = 0;
	float a = 0;

	@Override
	public void onInit() {
	}

	@Override
	public void onEnter() {
		selection = new SelectionBox(data, new String[] { "Weiter", "Neu", "Credits", "Beenden" }, 275, 360, 250, false);
		saveSelection = false;
		SaveFileManager.readSaves();
		// check savefile
		if (!SaveFileManager.hasGameFile()) {
			// no save file, =>continue blocked and select new game
			selection.blockItem(0, true);
			selection.setSelectedItem(1);
		}

		selection.setListener(new SelectionBoxListener() {
			public void select(int item) {
				switch (item) {
				case 0:
					saveSelection = true;
					break;
				case 1:
					// new game
					states.transition(NewGameState.class);
					break;
				case 2:
					// credits
					states.transition(CreditState.class);
					break;
				case 3:
					// exit
					Gdx.app.exit();
					break;
				}
			}

			public void cancelSelection() {

			}
		});

		final List<SaveFileInfo> files = SaveFileManager.getSaveFiles();
		String[] items = new String[files.size()];
		for (int i = 0; i < files.size(); i++) {
			SaveFileInfo file = files.get(i);
			int time = file.getPlayTime();
			int hours = (int) time / 3600;
			int remainder = (int) time - hours * 3600;
			int mins = remainder / 60;
			remainder = remainder - mins * 60;
			int secs = remainder;
			String m = "" + mins;
			if (m.length() == 1) {
				m = "0" + m;
			}
			String s = "" + secs;
			if (s.length() == 1) {
				s = "0" + s;
			}
			items[i] = files.get(i).getPlayerName() + " - " + hours + ":" + m + ":" + s;
		}
		saveBox = new ScrollableSelectionBox(data, items, 225, 360, 350, 4, false);
		saveBox.setListener(new SelectionBoxListener() {
			@Override
			public void select(int item) {
				SaveFileInfo file = files.get(item);
				// continue
				GameData savefile = SaveFileManager.loadGame(file);
				MapState state = (MapState) states.getState(MapState.class);
				state.openGame(savefile);
				states.transition(MapState.class);
			}

			@Override
			public void cancelSelection() {
				saveSelection = false;
			}
		});
	}

	@Override
	public void onExit() {
	}

	@Override
	public void update(float delta) {
		if (saveSelection) {
			saveBox.update();
		} else {
			selection.update();
		}
		cloudx -= 3;
		cloudy = -600 + (int) (Math.sin(a) * 500);
		a += 0.003f;
		if (cloudx <= -2600) {
			cloudx = 0;
		}
		GUIUpdate.consume();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(ColorUtil.newColor(150, 150, 255));
		g.fillRect(0, 0, 800, 600);
		Color c = ColorUtil.newColor(1, 1, 1, 0.7f);
		MenuGraphics.clouds.draw(cloudx, cloudy, c);
		MenuGraphics.clouds.draw(cloudx + 2600, cloudy, c);
		MenuGraphics.logo.drawCentered(400, 100);
		if (saveSelection) {
			saveBox.render(g);
		} else {
			selection.render(g);
		}
	}

}
