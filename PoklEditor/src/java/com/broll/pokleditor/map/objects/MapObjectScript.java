package com.broll.pokleditor.map.objects;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.broll.pokleditor.gui.components.JScriptHelpBox;
import com.broll.pokleditor.gui.components.ScriptBox;
import com.broll.pokllib.object.MapObject;

public class MapObjectScript {

	private ScriptBox attributes = new ScriptBox("Attributes", 60, 25, null);
	private ScriptBox trigger = new ScriptBox("Triggerscript", 60, 25, null);
	private MapObject object;
	private JPanel content = new JPanel();

	public MapObjectScript() {

		addInitScriptEnv(attributes);
		addRuntimeScriptEnv(trigger);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Init Script", attributes);
		tabs.addTab("Trigger Script", trigger);
		content.setLayout(new BorderLayout());
		content.add(tabs, BorderLayout.CENTER);

		// content.add(attributes, BorderLayout.NORTH);
		// content.add(trigger, BorderLayout.CENTER);
	}

	private void addInitScriptEnv(ScriptBox box){
		//has to match com.broll.poklmon.script.ObjectInitEnvironment
		JScriptHelpBox help = new JScriptHelpBox();
		help.addObject("com.broll.poklmon.map.object.MapObject", "self");
		help.addObject("com.broll.poklmon.script.commands.InitCommands","init");
		help.addObject("com.broll.poklmon.script.commands.VariableCommands","game");
		help.addObject("com.broll.poklmon.script.commands.PathingCommands","path");
		help.addObject("com.broll.poklmon.script.commands.SystemCommands","system");
		box.addDictonary(help);
	}

	private void addRuntimeScriptEnv(ScriptBox box){
		//has to match com.broll.poklmon.script.ObjectInitEnvironment
		JScriptHelpBox help = new JScriptHelpBox();
		help.addObject("com.broll.poklmon.map.object.MapObject", "self");
		help.addObject("com.broll.poklmon.script.commands.PlayerCommands","player");
		help.addObject("com.broll.poklmon.script.commands.BattleCommands","battle");
		help.addObject("com.broll.poklmon.script.commands.DialogCommands","dialog");
		help.addObject("com.broll.poklmon.script.commands.MenuCommands","menu");
		help.addObject("com.broll.poklmon.script.commands.ObjectCommands","object");
		help.addObject("com.broll.poklmon.script.commands.SystemCommands","system");
		help.addObject("com.broll.poklmon.script.commands.VariableCommands","game");
		help.addObject("com.broll.poklmon.script.commands.PathingCommands","path");
		help.addObject("com.broll.poklmon.script.commands.NetworkCommands","network");
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
