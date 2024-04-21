package com.broll.pokleditor.gui.script;

public class GlobalScriptVariable {

    private final String name;
    private final String packagePath;
    private final String className;

    public GlobalScriptVariable(String packagePath, String className){
        this.name = className;
        this.packagePath = packagePath;
        this.className = className;
    }

    public GlobalScriptVariable(String name, String packagePath, String className){
        this.name = name;
        this.packagePath = packagePath;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public String getQualifiedClassName(){
        return packagePath+"."+className;
    }
}
