package com.broll.pokleditor.attackdex;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.broll.pokleditor.debug.GameDebugger;
import com.broll.pokleditor.gui.components.AnimationBox;
import com.broll.pokleditor.gui.components.JScriptHelpBox;
import com.broll.pokleditor.gui.components.SaveListener;
import com.broll.pokleditor.gui.components.ScriptBox;
import com.broll.pokleditor.gui.components.ScriptTest;
import com.broll.pokleditor.gui.components.StringBox;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.attack.Attack;
import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.attack.script.ScriptAttackActions;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.main.StartInformation;

public class AttackEditScript extends JPanel {

	private Attack attack;
	private ScriptBox effectscript = new ScriptBox("Effectscript", 50, 5, new CompilerHelp());
	private AnimationBox animation = new AnimationBox("Attack Animation");
	private StringBox description = new StringBox("Beschreibung", 40);
	private SaveListener saveListener;

	public AttackEditScript(SaveListener saveListener) {
		setLayout(new BorderLayout());
		this.saveListener = saveListener;
		JScriptHelpBox help = new JScriptHelpBox();
		help.addObject(UseAttack.class, "atk");
		help.addObject(ScriptAttackActions.class, "util");
		help.addObject(FightPoklmon.class, "user");
		help.addObject(FightPoklmon.class, "target");

		effectscript.addDictonary(help);
		add(effectscript, BorderLayout.CENTER);
		JPanel north = new JPanel();
		north.setLayout(new FlowLayout(FlowLayout.LEFT));
		north.add(description);
		north.add(animation);

		add(north, BorderLayout.NORTH);
	}

	public void setAttack(Attack attack) {
		this.attack = attack;
		effectscript.setScript(attack.getEffectCode());
		animation.setAnimation(attack.getAnimationID());
		description.setText(attack.getDescription());
	}

	public void save() {
		attack.setEffectCode(effectscript.getScript());
		attack.setAnimationID(animation.getAnimation());
		attack.setDescription(description.getText());
	}

	private class CompilerHelp implements ScriptTest {

		@Override
		public void debugScript(String script) {
			saveListener.saveChanges();
			// EditorWindow.createDebugData();
			EditorWindow.save();
			StartInformation startInformation = new StartInformation(null);
			startInformation.debugAttack(attack.getId());
			GameDebugger.debugGame(startInformation);
		}

	}
}
