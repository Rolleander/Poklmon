package com.broll.pokleditor.gui.script;

import com.broll.pokleditor.debug.GameDebugger;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.ShorthandCompletionCache;
import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.js.JavaScriptCompletionProvider;
import org.fife.rsta.ac.js.JavaScriptLanguageSupport;
import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.CodeBlock;
import org.fife.rsta.ac.js.ast.JavaScriptVariableDeclaration;
import org.fife.rsta.ac.js.ast.jsType.RhinoJavaScriptTypesFactory;
import org.fife.rsta.ac.js.ast.type.TypeDeclaration;
import org.fife.rsta.ac.js.ast.type.ecma.v5.TypeDeclarationsECMAv5;
import org.fife.rsta.ac.js.completion.JSVariableCompletion;
import org.fife.rsta.ac.js.engine.RhinoJavaScriptEngine;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.modes.JavaScriptTokenMaker;
import org.mozilla.javascript.Context;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.JTextComponent;

public class RhinoJavaScriptLanguageSupport extends JavaScriptLanguageSupport {

    private ScriptingCompletionProvider provider;


    public RhinoJavaScriptLanguageSupport(ScriptEnvironments.Type type) {
        JavaScriptTokenMaker.setJavaScriptVersion("1.7");
        setECMAVersion("1.7",getJarManager());
        try {
            getJarManager().addClassFileSource(GameDebugger.debugPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        provider.setJarManager(getJarManager());
        provider.initSystemVariables(type);
    }

    @Override
    protected JavaScriptCompletionProvider createJavaScriptCompletionProvider() {
        provider = new ScriptingCompletionProvider();
        return new JavaScriptCompletionProvider(provider, getJarManager(), this);
    }

    public void install(RSyntaxTextArea textArea) {
        //remove javascript support and replace with our custom rhino support
        LanguageSupport support = (LanguageSupport) textArea.getClientProperty("org.fife.rsta.ac.LanguageSupport");
        if (support != null) {
            support.uninstall(textArea);
        }
        super.install(textArea);
    }

    private static class ScriptingCompletionProvider extends SourceCompletionProvider {
        private ShorthandCompletionCache shorthandCache;

        private final static String GLOBAL_SCOPE_DETECTION = "Infinity";
        private final List<Completion> globalVariableCompletions = new ArrayList<>();

        public ScriptingCompletionProvider() {
            super(RhinoJavaScriptEngine.RHINO_ENGINE, false);
        }

        public void initSystemVariables(ScriptEnvironments.Type type) {
            try {
                Field field = SourceCompletionProvider.class.getDeclaredField("shorthandCache");
                field.setAccessible(true);
                shorthandCache = (ShorthandCompletionCache) field.get(this);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            for (GlobalScriptVariable variable : type.globalVariables) {
                addSystemVariable(variable, true);
            }
            for(GlobalScriptVariable variable : ScriptEnvironments.ENUM_TYPES){
                addSystemVariable(variable, false);
            }
            for(String standardImport : ScriptEnvironments.STANDARD_IMPORT_PACKAGES){
                RhinoJavaScriptTypesFactory typeFactory = (RhinoJavaScriptTypesFactory) getJavaScriptTypesFactory();
                typeFactory.addImportPackage(standardImport);
            }
        }

        public void addSystemVariable(GlobalScriptVariable variable, boolean emptyCompletion) {
            RhinoJavaScriptTypesFactory typeFactory = (RhinoJavaScriptTypesFactory) getJavaScriptTypesFactory();
            typeFactory.addImportPackage(variable.getPackagePath());
            JavaScriptVariableDeclaration variableDeclaration = new JavaScriptVariableDeclaration(variable.getName(),
                    Integer.MAX_VALUE, this, new GlobalCodeBlock());
            variableDeclaration.setTypeDeclaration(getTypeDeclaration(variable));
            getVariableResolver().addSystemVariable(variableDeclaration);
            JSVariableCompletion completion = new JSVariableCompletion(this, variableDeclaration);
            if(emptyCompletion){
                globalVariableCompletions.add(completion);
            }
            shorthandCache.addShorthandCompletion(completion);
        }


        private TypeDeclaration getTypeDeclaration(GlobalScriptVariable variable) {
            ClassFile classFile = getJarManager().getClassEntry(variable.getQualifiedClassName());
            return getJavaScriptTypesFactory().createNewTypeDeclaration(classFile, false, true);
        }

        @Override
        protected List<Completion> getCompletionsImpl(JTextComponent comp) {
            List<Completion> completions = super.getCompletionsImpl(comp);
            if (completions.stream().anyMatch(it -> GLOBAL_SCOPE_DETECTION.equals(it.getReplacementText()))) {
                completions.addAll(globalVariableCompletions);
            }
            return completions;
        }
    }

    private static class GlobalCodeBlock extends CodeBlock {

        public GlobalCodeBlock() {
            super(0);
        }

        @Override
        public boolean contains(int offset) {
            return true;
        }
    }
}