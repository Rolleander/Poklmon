package com.broll.pokleditor.itemdex;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.broll.pokleditor.debug.CallbackList;
import com.broll.pokleditor.gui.components.JScriptHelpBox;
import com.broll.pokleditor.gui.components.ScriptBox;
import com.broll.pokleditor.gui.components.StringBox;
import com.broll.pokllib.item.Item;

public class ItemEditScript extends JPanel {

	private Item item;
	private ScriptBox effectscript = new ScriptBox("Effectscript", 50, 5, null);

	private StringBox description = new StringBox("Beschreibung", 40);

	public ItemEditScript() {
		setLayout(new BorderLayout());
		JScriptHelpBox help = new JScriptHelpBox();
		help.addObject("com.broll.poklmon.game.items.MedicineItemScript", "medicine");
		help.addObject("com.broll.poklmon.game.items.PoklballItemScript", "poklball");
		help.addObject("com.broll.poklmon.game.items.WearableItemScript", "wearable");
		help.addObject("com.broll.poklmon.game.items.OtherItemScript", "other");
		help.addObject("com.broll.poklmon.game.items.BasisItemScript", "basis");
		help.addObject("com.broll.poklmon.game.items.AttackItemScript", "attack");
		for(Class c: CallbackList.getCallbacks()){
			help.addClass(c, null);
		}

		effectscript.addDictonary(help);
		add(effectscript, BorderLayout.CENTER);
		JPanel north = new JPanel();
		north.setLayout(new FlowLayout(FlowLayout.LEFT));
		north.add(description);

		add(north, BorderLayout.NORTH);
	}

	public void setItem(Item item) {
		this.item = item;
		effectscript.setScript(item.getEffect());
		description.setText(item.getDescription());
	}

	public void save() {
		item.setEffect(effectscript.getScript());
		item.setDescription(description.getText());

	}

}
