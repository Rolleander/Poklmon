package com.broll.pokleditor.gui.script;

import java.io.InputStreamReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavascriptFormatter {

    // my javascript beautifier of choice
    private static final String BEAUTIFY_JS_RESOURCE = "/beautify.js";

    // name of beautifier function
    private static final String BEAUTIFY_METHOD_NAME = "js_beautify";

    private  static ScriptEngine engine;

    public static void init() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");
        // this is needed to make self invoking function modules work
        // otherwise you won't be able to invoke your function
        try {
            engine.eval("var global = this;");
            engine.eval(new InputStreamReader(JavascriptFormatter.class.getResourceAsStream(BEAUTIFY_JS_RESOURCE)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String beautify(String javascriptCode) {
        try {
            return (String) ((Invocable) engine).invokeFunction(BEAUTIFY_METHOD_NAME, javascriptCode);
        } catch (Exception e) {
            e.printStackTrace();
            return javascriptCode;
        }
    }

}
