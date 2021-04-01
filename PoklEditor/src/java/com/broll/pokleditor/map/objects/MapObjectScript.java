package com.broll.pokleditor.map.objects;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.broll.pokleditor.gui.components.JScriptHelpBox;
import com.broll.pokleditor.gui.components.ScriptBox;
import com.broll.pokllib.object.MapObject;
import com.broll.poklmon.script.ObjectInitEnvironment;
import com.broll.poklmon.script.ObjectRuntimeEnvironment;
import com.broll.poklmon.script.ScriptingEnvironment;

public class MapObjectScript {

	private ScriptBox attributes = new ScriptBox("Attributes", 60, 25, null);
	private ScriptBox trigger = new ScriptBox("Triggerscript", 60, 25, null);
	private MapObject object;
	private JPanel content = new JPanel();

	public MapObjectScript() {

		addScriptDictionary(new ObjectInitEnvironment(), attributes);
		addScriptDictionary(new ObjectRuntimeEnvironment(), trigger);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Init Script", attributes);
		tabs.addTab("Trigger Script", trigger);
		content.setLayout(new BorderLayout());
		content.add(tabs, BorderLayout.CENTER);

		// content.add(attributes, BorderLayout.NORTH);
		// content.add(trigger, BorderLayout.CENTER);
	}

	private void addScriptDictionary(ScriptingEnvironment env, ScriptBox box) {
		env.addController(null, new com.broll.poklmon.map.object.MapObject(null,null));
		JScriptHelpBox help = new JScriptHelpBox();
		for (int i = 0; i < env.getController().size(); i++) {
			help.addObject(env.getController().get(i).getClass(), env.getObjectNames().get(i));
		}
		box.addDictonary(help);
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
