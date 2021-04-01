package com.broll.poklmon.script.commands;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.script.CommandControl;
import com.broll.poklmon.script.Invoke;
import com.broll.poklmon.save.PoklmonData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogCommands extends CommandControl {

	public DialogCommands(GameManager game) {
		super(game);
	}

	public void text(final String text) {
		invoke(new Invoke() {
			@Override
			public void invoke() {
				game.getMessageGuiControl().showText(text);
			}
		});
	}

	public int selection(final String... options) {
		invoke(new Invoke() {
			@Override
			public void invoke()  {
				game.getMessageGuiControl().showSelection(options);
			}
		});
		return game.getMessageGuiControl().getSelectedOption();
	}

	public PoklmonData selectionPoklmon(List<PoklmonData> poklmons) {
		final List<String> selection = new ArrayList<String>();
		final List<Image> icons = new ArrayList<Image>();
		for (int i = 0; i < poklmons.size(); i++) {
			PoklmonData pokl = poklmons.get(i);
			Poklmon poklData = game.getData().getPoklmons().getPoklmon(pokl.getPoklmon());
			String name = pokl.getName();
			if (name == null) {
				name = poklData.getName();
			}
			Image graphic = game.getData().getGraphics().getPoklmonImage(poklData.getGraphicName());
			// graphic.setFilter(Image.FILTER_LINEAR);
			icons.add(graphic.getScaledCopy(50, 50));
			int level = pokl.getLevel();
			selection.add(name + "   Lv." + level);
		}
		invoke(new Invoke() {
			@Override
			public void invoke()  {
				game.getMessageGuiControl().showSelection(selection.toArray(new String[0]),
						new boolean[selection.size()], false, true);
				game.getMessageGuiControl().setSelectionIcons(icons.toArray(new Image[0]));
			}
		});
		int select = game.getMessageGuiControl().getSelectedOption();
		return poklmons.get(select);
	}

	public PoklmonData selectionPoklmon() {
		List<PoklmonData> poklmons = new ArrayList<PoklmonData>();
		HashMap<Integer, PoklmonData> team = game.getPlayer().getPoklmonControl().getPoklmonsInTeam();
		for (int i = 0; i < 6; i++) {
			PoklmonData pokl = team.get(i);
			if (pokl != null) {
				poklmons.add(pokl);
			}
		}
		return selectionPoklmon(poklmons);
	}

	public String input(final String defaultText) {
		invoke(new Invoke() {
			@Override
			public void invoke()  {
				game.getMessageGuiControl().openNameInput(defaultText);
			}
		});
		return game.getMessageGuiControl().getInputName();
	}

	public String customInput(final String defaultText, final int length) {
		invoke(new Invoke() {
			@Override
			public void invoke()  {
				game.getMessageGuiControl().openTextInput(defaultText, length);
			}
		});
		return game.getMessageGuiControl().getInputName();
	}

	public int numberInput(final String info, final int value, final int min, final int max) {
		invoke(new Invoke() {
			@Override
			public void invoke() {
				game.getMessageGuiControl().openNumberInput(info, value, min, max, false);
			}
		});
		return game.getMessageGuiControl().getInputNumber();
	}

	public void info(String text) {
		if (text == null) {
			text = " ";
		}
		game.getMessageGuiControl().showInfoBox(text);
	}

	public void hideInfo() {
		game.getMessageGuiControl().hideInfoBox();
	}
}
