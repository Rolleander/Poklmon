package com.broll.pokleditor.gui.script;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.js.JavaScriptCompletionProvider;
import org.fife.rsta.ac.js.JavaScriptLanguageSupport;
import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.type.ecma.v5.TypeDeclarationsECMAv5;
import org.fife.rsta.ac.js.engine.RhinoJavaScriptEngine;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.modes.JavaScriptTokenMaker;

import java.io.IOException;

public class RhinoJavaScriptLanguageSupport extends JavaScriptLanguageSupport {
    private static final String ENGINE = RhinoJavaScriptEngine.RHINO_ENGINE;

    public RhinoJavaScriptLanguageSupport() {
        JavaScriptTokenMaker.setJavaScriptVersion("1.7");
        setECMAVersion(TypeDeclarationsECMAv5.class.getName(), getJarManager());
        try {
            getJarManager().addCurrentJreClassFileSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JavaScriptCompletionProvider createJavaScriptCompletionProvider() {
        return new JavaScriptCompletionProvider(new MySourceCompletionProvider(), getJarManager(), this);
    }

    public void install(RSyntaxTextArea textArea) {
        //remove javascript support and replace with Rhino support
        LanguageSupport support = (LanguageSupport) textArea.getClientProperty("org.fife.rsta.ac.LanguageSupport");
        if (support != null) {
            support.uninstall(textArea);
        }
        super.install(textArea);
    }

    private class MySourceCompletionProvider extends SourceCompletionProvider {
        public MySourceCompletionProvider() {
            super(ENGINE, false);
        }
    }
}