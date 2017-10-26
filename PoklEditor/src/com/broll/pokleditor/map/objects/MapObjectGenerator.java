package com.broll.pokleditor.map.objects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.broll.pokleditor.gui.dialogs.LedgeDialog;
import com.broll.pokleditor.gui.dialogs.TeleportLocationDialog;
import com.broll.pokleditor.gui.dialogs.TrainerObjectDialog;
import com.broll.pokllib.main.PoklLib;
import com.broll.pokllib.map.MapFile;
import com.broll.pokllib.object.MapObject;
import com.broll.pokllib.object.ObjectDirection;

public class MapObjectGenerator {

	private final static String knownTemplate = "if(object.isKnown()){\n//TODO\n}\nelse\n{\n//TODO\nobject.setKnown()\n}";

	public static MapObject openWizard(ObjectType type) {
		MapObject object;
		switch (type) {
		case PLAIN:
			object = new MapObject();
			object.setName("New Object");
			object.setDirection(ObjectDirection.DOWN);
			object.setTriggerScript(knownTemplate);
			return object;

		case TELEPORTER:
			object = new MapObject();
			object.setName("Teleporter");
			object.setDirection(ObjectDirection.DOWN);
			object.setAttributes("self.setBlocking(false) \nself.setTouchTrigger(true)");
			String script = TeleportLocationDialog.showTeleportDialog();
			object.setTriggerScript(script);

			return object;
		case TRAINER:
			TrainerObjectDialog dialog = TrainerObjectDialog.openDialog();
			object = new MapObject();
			object.setName(dialog.getObjectName());
			object.setDirection(ObjectDirection.DOWN);
			object.setTriggerScript(dialog.getTriggerScript());
			object.setAttributes(dialog.getInitScript());
			return object;
		case ITEM:
			object = new MapObject();
			object.setName("New Item");
			object.setGraphic("_items.png");
			object.setDirection(ObjectDirection.DOWN);
			object.setAttributes("init.destroyIfKnown() \ninit.setFixCharGraphic(0,0)");
			object.setTriggerScript("player.giveItem(0,1)\nobject.setKnown()\nobject.destroy()");
			return object;
		case CITIZEN:
			object = new MapObject();
			object.setName("New Citizen");
			object.setDirection(ObjectDirection.values()[(int) (Math.random() * 4)]);
			object.setAttributes("path.setSimplePath()\n");
			object.setTriggerScript(knownTemplate);
			return object;
		case LEDGE:
			ObjectDirection dir = LedgeDialog.showLedgeDialog();
			object = new MapObject();
			object.setName("Ledge");
			object.setDirection(ObjectDirection.DOWN);
			object.setGraphic("_ledges.png");
			int graphic = 0;
			switch (dir) {
			case LEFT:
				graphic = 1;
				break;
			case UP:
				graphic = 2;
				break;
			case RIGHT:
				graphic = 3;
				break;
			}
			object.setAttributes("self.setRenderLevel(-1) \nself.setBlocking(false) \nself.setTriggerActive(false)\nself.setLedge(ObjectDirection."
					+ dir.name() + ")\ninit.setFixCharGraphic(" + graphic + ",0)\n");
			object.setTriggerScript("");
			return object;
		case REMOTE:
			object = new MapObject();
			object.setDirection(ObjectDirection.DOWN);
			try {
				List<String> selections = new ArrayList<String>();
				MapFile map = PoklLib.data().readMap(11);
				map.getObjects().forEach(o -> selections.add(o.getName()));
				int id = JOptionPane.showOptionDialog(null, "Select Remote", "New Remote Object",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						selections.toArray(new String[0]), 0);
				if (id >= 0) {
					object.setAttributes("init.remoteInit(11," + id + ")");
					object.setTriggerScript("object.triggerRemoteScript(11," + id + ")");
					return object;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
