package com.broll.pokleditor.test;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import de.sciss.syntaxpane.syntaxkits.JavaScriptSyntaxKit;

public class SyntaxTester {

//    public static void main(String[] args) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//               SyntaxTester st = new SyntaxTester();
//            }
//        });
//    }

    public SyntaxTester() {
        JFrame f = new JFrame(SyntaxTester.class.getName());
        final Container c = f.getContentPane();
        c.setLayout(new BorderLayout());

        //DefaultSyntaxKit.initKit();
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