package com.broll.pokleditor.map.objects;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.broll.pokleditor.gui.components.JScriptHelpBox;
import com.broll.pokleditor.gui.components.ScriptBox;
import com.broll.pokleditor.gui.script.ScriptEnvironments;
import com.broll.pokllib.object.MapObject;

public class MapObjectScript {

	private ScriptBox attributes = new ScriptBox("Attributes", 60, 25, ScriptEnvironments.Type.OBJECT_INIT,null);
	private ScriptBox trigger = new ScriptBox("Triggerscript", 60, 25, ScriptEnvironments.Type.OBJECT_RUNTIME, null);
	private MapObject object;
	private JPanel content = new JPanel();

	public MapObjectScript() {

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Init Script", attributes);
		tabs.addTab("Trigger Script", trigger);
		content.setLayout(new BorderLayout());
		content.add(tabs, BorderLayout.CENTER);

		// content.add(attributes, BorderLayout.NORTH);
		// content.add(trigger, BorderLayout.CENTER);
	}


	public void setMapObject(MapObject object) {
		this.object = object;
		attributes.setScript(object.getAttributes());
		trigger.setScript(object.getTriggerScript());
	}

	public void save() {
		object.setAttributes(attributes.getScript());
		object.setTriggerScript(trigger.getScript());
	}

	public JPanel getContent() {
		return content;
	}
}
