package com.broll.pokleditor.map.areas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.JScriptHelpBox;
import com.broll.pokleditor.gui.components.ScriptBox;
import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.gui.script.ScriptEnvironments;
import com.broll.pokleditor.map.control.MapControlImpl;

public class MapAreaScriptBox extends JPanel {

	private ScriptBox script = new ScriptBox("AreaScript", 30, 30, ScriptEnvironments.Type.AREA_INIT,null);

	public MapAreaScriptBox() {
		setLayout(new BorderLayout());
		JPanel tools = new JPanel();
		tools.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JScriptHelpBox help = new JScriptHelpBox();
		help.addObject("com.broll.poklmon.map.areas.AreaScriptActions", "area");
		script.addDictonary(help);

		JButton poklwizard = new JButton("Add Wild Poklmons", GraphicLoader.loadIcon("grass.png"));
		poklwizard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WildPoklmonWizard wizard = new WildPoklmonWizard(false);
				if (MapControlImpl.acceptDialog(wizard, "Wild Poklmon Wizard")) {
					script.appendText(wizard.getScript());
				}
			}
		});
		tools.add(poklwizard);
		poklwizard = new JButton("Add Fishing Poklmons", GraphicLoader.loadIcon("grass.png"));
		poklwizard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WildPoklmonWizard wizard = new WildPoklmonWizard(true);
				if (MapControlImpl.acceptDialog(wizard, "Fishing Poklmon Wizard")) {
					script.appendText(wizard.getScript());
				}
			}
		});
		tools.add(poklwizard);
		add(tools, BorderLayout.NORTH);
		add(script, BorderLayout.CENTER);
	}

	public void setScript(String string) {
		script.setScript(string);

	}

	public String getScript() {
		return script.getScript();
	}

}
