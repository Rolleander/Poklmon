package com.broll.pokleditor.gui.components;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.gui.components.preview.AttackPreview;
import com.broll.pokleditor.gui.components.preview.ItemPreview;
import com.broll.pokleditor.gui.components.preview.PoklmonPreview;
import com.broll.pokleditor.gui.components.preview.TextSearchEntry;
import com.broll.pokleditor.gui.dialogs.TeleportLocationDialog;
import com.broll.pokleditor.gui.script.JavascriptFormatter;
import com.broll.pokleditor.gui.script.ScriptEnvironments;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class ScriptBox extends JPanel {

    private RSyntaxTextArea script;
    private JLabel compileInfo = new JLabel("");
    private JSplitPane content = new JSplitPane();
    private ScriptEnvironments.Type type;

    public ScriptBox(String name, int w, int h, ScriptEnvironments.Type type, final ScriptTest scriptTest) {
        setLayout(new BorderLayout());
        this.type = type;
        JPanel infoLine = new JPanel(new BorderLayout());
        FontMetrics fm = this.getFontMetrics((new JLabel().getFont()));

        script = new RSyntaxTextArea();
        changeScriptType(type);
        script.setCodeFoldingEnabled(false);
        script.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        ScriptEnvironments.getScriptLanguageSupport(type).install(script);
        JLabel title = new JLabel(name);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        infoLine.add(title, BorderLayout.WEST);
        infoLine.add(compileInfo, BorderLayout.CENTER);
        add(infoLine, BorderLayout.NORTH);


        script.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        RTextScrollPane scroll = new RTextScrollPane(script);
        scroll.setLineNumbersEnabled(true);
        scroll.setMinimumSize(new Dimension(100, 0));
        scroll.setPreferredSize(new Dimension(w * fm.charWidth('m'), h * fm.getHeight()));
        content.setLeftComponent(scroll);
        content.setRightComponent(null);
        content.setOneTouchExpandable(true);

        add(content, BorderLayout.CENTER);

        //  script.setContentType("text/javascript");
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
        JButton button = new JButton("", GraphicLoader.loadIcon("font.png"));
        button.addActionListener(action -> {
            setScript(JavascriptFormatter.beautify(getScript()));
        });
        topLane.add(button);
        button = new JButton("Poklmons", GraphicLoader.loadIcon("poklball.png"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchableList.showList("Poklmons", PoklmonPreview.all()).ifPresent(it -> copyToClipboard("" + it));
            }
        });
        topLane.add(button);
        button = new JButton("Attacks", GraphicLoader.loadIcon("fire.png"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchableList.showList("Attacks", AttackPreview.all()).ifPresent(it -> copyToClipboard("" + it));
            }
        });
        topLane.add(button);
        button = new JButton("Items", GraphicLoader.loadIcon("key.png"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchableList.showList("Items", ItemPreview.all()).ifPresent(it -> copyToClipboard("" + it));
            }
        });
        topLane.add(button);
        button = new JButton("Animations", GraphicLoader.loadIcon("rainbow.png"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchableList.showList("Animations", PoklDataUtil.getAllAnimations().stream().map(it -> new TextSearchEntry(it.getName(), it.getId())).collect(Collectors.toList()))
                        .ifPresent(it -> copyToClipboard("" + it));
            }
        });
        topLane.add(button);
        button = new JButton("Maps", GraphicLoader.loadIcon("map.png"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchableList.showList("Maps", PoklDataUtil.getAllMaps().stream().map(it -> new TextSearchEntry(it.getName(), it.getId())).collect(Collectors.toList()))
                        .ifPresent(it -> copyToClipboard("" + it));
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

    public void changeScriptType(ScriptEnvironments.Type type){
        if(type != this.type){
            ScriptEnvironments.getScriptLanguageSupport(this.type).uninstall(script);
            ScriptEnvironments.getScriptLanguageSupport(type).install(script);
        }
    }

    private void copyToClipboard(String s) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s), null);
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
