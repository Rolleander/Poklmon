package com.broll.pokleditor.map.objects;

import com.broll.pokleditor.gui.dialogs.TeleportLocationDialog;
import com.broll.pokleditor.gui.dialogs.TrainerObjectDialog;
import com.broll.pokllib.object.MapObject;
import com.broll.pokllib.object.ObjectDirection;

public class MapObjectGenerator {

	private final static String knownTemplate = "if(object.isKnown()){\n//TODO\n}\nelse\n{\n//TODO\n}";

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
		}
		return null;
	}

}
