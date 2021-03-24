package com.broll.pokleditor.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.dialogs.TeleportLocationDialog;
import com.broll.pokleditor.gui.graphics.GraphicLoader;

import de.sciss.syntaxpane.syntaxkits.JavaScriptSyntaxKit;

public class ScriptBox extends JPanel {

	private JEditorPane script;

	private final static Color background = new Color(0, 0, 0);
	private final static Color text = new Color(150, 250, 150);
	private JLabel compileInfo = new JLabel("");
	private JSplitPane content = new JSplitPane();

	public ScriptBox(String name, int w, int h, final ScriptTest scriptTest) {
		setLayout(new BorderLayout());
		JPanel infoLine = new JPanel(new BorderLayout());
		FontMetrics fm = this.getFontMetrics((new JLabel().getFont()));
		JavaScriptSyntaxKit.initKit();
		script = new JEditorPane();

		JLabel title = new JLabel(name);
		title.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		infoLine.add(title, BorderLayout.WEST);
		infoLine.add(compileInfo, BorderLayout.CENTER);
		add(infoLine, BorderLayout.NORTH);

		// script.setCaretColor(Color.WHITE);
		// script.setBackground(background);
		// script.setForeground(text);

		script.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		JScrollPane scroll = new JScrollPane(script);
		scroll.setMinimumSize(new Dimension(100, 0));
		scroll.setPreferredSize(new Dimension(w * fm.charWidth('m'), h * fm.getHeight()));
		content.setLeftComponent(scroll);
		content.setRightComponent(null);
		content.setOneTouchExpandable(true);

		add(content, BorderLayout.CENTER);

		script.setContentType("text/javascript");
		if (scriptTest != null) {
			JButton debug = new JButton("Debug", GraphicLoader.loadIcon("control_play_blue.png"));
			debug.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					scriptTest.debugScript(getScript());
				}
			});
			add(debug, BorderLayout.SOUTH);
		}

		JPanel topLane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton button = new JButton("Poklmons", GraphicLoader.loadIcon("poklball.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard("" + ScriptEntityList.showList(PoklDataUtil.getAllPoklmonNames()));

			}
		});
		topLane.add(button);
		button = new JButton("Attacks", GraphicLoader.loadIcon("fire.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard("" + ScriptEntityList.showList(PoklDataUtil.getAllAttackNames()));
			}
		});
		topLane.add(button);
		button = new JButton("Items", GraphicLoader.loadIcon("key.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard("" + ScriptEntityList.showList(PoklDataUtil.getAllItemNames()));
			}
		});
		topLane.add(button);
		button = new JButton("Animations", GraphicLoader.loadIcon("rainbow.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard("" + ScriptEntityList.showList(PoklDataUtil.getAllAnimationNames()));
			}
		});
		topLane.add(button);
		button = new JButton("Maps", GraphicLoader.loadIcon("map.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard("" + ScriptEntityList.showList(PoklDataUtil.getAllMapNames()));
			}
		});
		topLane.add(button);
		button = new JButton("Teleport", GraphicLoader.loadIcon("label.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard(TeleportLocationDialog.showTeleportDialog());
			}
		});
		topLane.add(button);
		add(topLane, BorderLayout.NORTH);
	}

	private void copyToClipboard(String s) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s), null);
	}

	public void addDictonary(JPanel scriptDictionary) {
		JScrollPane scroll = new JScrollPane(scriptDictionary);
		scroll.getVerticalScrollBar().setUnitIncrement(20);
		scroll.setMinimumSize(new Dimension(100, 0));
		scroll.setPreferredSize(new Dimension(200, 0));
		content.setRightComponent(scroll);
		content.setOneTouchExpandable(true);
	}

	public String getScript() {
		return script.getText();
	}

	public void setScript(String scr) {
		script.setText(scr);
	}

	public void appendText(String t) {
		script.setText(script.getText() + "\n" + t);
	}

}
