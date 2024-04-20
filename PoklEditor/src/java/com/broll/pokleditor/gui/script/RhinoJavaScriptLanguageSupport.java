package com.broll.pokleditor.gui.script;

import com.broll.pokleditor.debug.GameDebugger;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.js.JavaScriptCompletionProvider;
import org.fife.rsta.ac.js.JavaScriptLanguageSupport;
import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.CodeBlock;
import org.fife.rsta.ac.js.ast.JavaScriptVariableDeclaration;
import org.fife.rsta.ac.js.ast.VariableResolver;
import org.fife.rsta.ac.js.ast.type.ecma.v5.TypeDeclarationsECMAv5;
import org.fife.rsta.ac.js.engine.JavaScriptEngine;
import org.fife.rsta.ac.js.engine.JavaScriptEngineFactory;
import org.fife.rsta.ac.js.engine.RhinoJavaScriptEngine;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.modes.JavaScriptTokenMaker;

import java.io.IOException;

public class RhinoJavaScriptLanguageSupport extends JavaScriptLanguageSupport {

    public static ScriptEnvironments.Type SETUP_TYPE;


    public RhinoJavaScriptLanguageSupport() {
        JavaScriptTokenMaker.setJavaScriptVersion("1.7");
        setECMAVersion(TypeDeclarationsECMAv5.class.getName(), getJarManager());
        try {
            getJarManager().addClassFileSource(GameDebugger.debugPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JavaScriptCompletionProvider createJavaScriptCompletionProvider() {
        return  new JavaScriptCompletionProvider(new MySourceCompletionProvider(SETUP_TYPE), getJarManager(), this);
    }

    public void install(RSyntaxTextArea textArea) {
        //remove javascript support and replace with Rhino support
        LanguageSupport support = (LanguageSupport) textArea.getClientProperty("org.fife.rsta.ac.LanguageSupport");
        if (support != null) {
            support.uninstall(textArea);
        }
        super.install(textArea);
    }

    private static class MySourceCompletionProvider extends SourceCompletionProvider {

        public MySourceCompletionProvider(ScriptEnvironments.Type type) {
            super(type.getEngineName(), false);
        }

    }
}