package com.broll.pokleditor.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import de.sciss.syntaxpane.syntaxkits.JavaScriptSyntaxKit;
import de.sciss.syntaxpane.util.Configuration;
import de.sciss.syntaxpane.util.JarServiceProvider;

public class SyntaxPaneTest {
//	 public static void main(String[] args) {
//	        java.awt.EventQueue.invokeLater(new Runnable() {
//
//	            @Override
//	            public void run() {
//	              test();
//	            }
//	        });
//	    }

	public static void test() {
		JFrame f = new JFrame(SyntaxTester.class.getName());
		final Container c = f.getContentPane();
		c.setLayout(new BorderLayout());
		
		Configuration config=JavaScriptSyntaxKit.getConfig(JavaScriptSyntaxKit.class);
		
		/**
		 * Action.combo-completion = de.sciss.syntaxpane.actions.ComboCompletionAction, control SPACE
			Action.combo-completion.MenuText = Completions
			Action.combo-completion.ItemsURL=${class_path}/combocompletions.txt
		 */
		
		
		
		config.put("Action.combo-completion", "de.sciss.syntaxpane.actions.ComboCompletionAction, control SPACE");
		config.put("Action.combo-completion.MenuText","Completions");
		String url="${class_path}/completions.txt";
		config.put("Action.combo-completion.ItemsURL",url);
		config.put("Action.combo-completion.ItemsURL",url);
		
		config.put("Action.execute-script.MenuText", "Arsch");	
		List<String> lines = JarServiceProvider.readLines(url);
		System.out.println(lines.size());
		
		for(String s:config.keySet())
		{
			System.out.println(s);
		}
		JavaScriptSyntaxKit.initKit();
			
		final JEditorPane codeEditor = new JEditorPane();
		JScrollPane scrPane = new JScrollPane(codeEditor);
		c.add(scrPane, BorderLayout.CENTER);
		c.doLayout();
		codeEditor.setContentType("text/javascript");
		codeEditor.setText("public static void main(String[] args) {\n}");

		f.setSize(800, 600);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

}
