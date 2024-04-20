package com.broll.pokleditor.gui.script;

import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.js.JavaScriptHelper;
import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.CodeBlock;
import org.fife.rsta.ac.js.ast.TypeDeclarationOptions;
import org.fife.rsta.ac.js.ast.jsType.JavaScriptTypesFactory;
import org.fife.rsta.ac.js.ast.jsType.RhinoJavaScriptTypesFactory;
import org.fife.rsta.ac.js.ast.parser.JavaScriptParser;
import org.fife.rsta.ac.js.ast.parser.RhinoJavaScriptAstParser;
import org.fife.rsta.ac.js.ast.type.TypeDeclaration;
import org.fife.rsta.ac.js.engine.RhinoJavaScriptEngine;
import org.fife.rsta.ac.js.resolver.JSR223JavaScriptCompletionResolver;
import org.fife.rsta.ac.js.resolver.JavaScriptResolver;
import org.fife.ui.autocomplete.Completion;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomRhinoJavaScriptEngine extends RhinoJavaScriptEngine {

    private List<GlobalScriptVariable> globalVariables = new ArrayList<>();

    public CustomRhinoJavaScriptEngine(GlobalScriptVariable[] globalVariables) {
        this.globalVariables.addAll(Arrays.asList(globalVariables));
    }

    @Override
    public JavaScriptParser getParser(SourceCompletionProvider provider, int dot, TypeDeclarationOptions options) {
        CustomRhinoJavaScriptAstParser parser = new CustomRhinoJavaScriptAstParser(provider, dot, options);
        globalVariables.stream().map(GlobalScriptVariable::getPackagePath).distinct().forEach(parser::addPackage);
        return parser;
    }


    @Override
    public JavaScriptResolver getJavaScriptResolver(SourceCompletionProvider provider) {
        CustomJSR223JavaScriptCompletionResolver   resolver =  new CustomJSR223JavaScriptCompletionResolver(provider);
        globalVariables.forEach(it-> resolver.addGlobalVariable(it.getName(), it.getQualifiedClassName()));
        return resolver;
    }

    private static class CustomRhinoJavaScriptAstParser extends RhinoJavaScriptAstParser{

        private List<String> staticImports = new ArrayList<>();
        private LinkedHashSet<String> importPackages;

        public CustomRhinoJavaScriptAstParser(SourceCompletionProvider provider, int dot, TypeDeclarationOptions options) {
            super(provider, dot, options);
            try {
              Field imps =  RhinoJavaScriptAstParser.class.getDeclaredField("importPackages");
              imps.setAccessible(true);
              importPackages = (LinkedHashSet<String>) imps.get(this);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public void addPackage(String pkg){
            staticImports.add(pkg);
        }

        @Override
        public CodeBlock convertAstNodeToCodeBlock(AstRoot root, Set<Completion> set, String entered) {
            importPackages.addAll(staticImports);
           return super.convertAstNodeToCodeBlock(root, set, entered);
        }

    }

    private  static class CustomJSR223JavaScriptCompletionResolver extends JSR223JavaScriptCompletionResolver {

        private Map<String, TypeDeclaration> globals = new HashMap<>();

        public CustomJSR223JavaScriptCompletionResolver(SourceCompletionProvider provider) {
            super(provider);
        }

        public void addGlobalVariable(String name, String clazz){
            ClassFile classFile = provider.getJarManager().getClassEntry(clazz);
            TypeDeclaration type = provider.getJavaScriptTypesFactory().createNewTypeDeclaration(classFile, false, false);
            globals.put(name, type);
        }

        @Override
        protected TypeDeclaration findJavaStaticType(AstNode node) {
            TypeDeclaration type = super.findJavaStaticType(node);
            if(type == null){
                String name = node.toSource();
                return globals.get(name);
            }
            return type;
        }
    }
}


