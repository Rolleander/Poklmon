package com.broll.pokleditor.gui.script;

import com.broll.pokleditor.debug.GameDebugger;

import org.fife.rsta.ac.java.JarManager;

import java.io.IOException;

public class ScriptCompletionSetup {
    public static RhinoJavaScriptLanguageSupport languageSupport;

    public static void setup(){
        languageSupport = new RhinoJavaScriptLanguageSupport();
        JarManager jarManager = languageSupport.getJarManager();
      /*  try {
            jarManager.addClassFileSource(GameDebugger.debugPath);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}
