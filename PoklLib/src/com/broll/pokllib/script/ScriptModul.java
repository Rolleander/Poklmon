package com.broll.pokllib.script;

import java.util.Random;

public class ScriptModul
{

    private ScriptInterface scriptInterface;
    private CodeRunner runner = new CodeRunner();
    private VariableStorage variableStorage;
   
    public ScriptModul()
    {
        initStorage();
    }

    public void initStorage()
    {
        variableStorage = new VariableStorage();
        runner.setVariableStorage(variableStorage);
    }

    public VariableStorage getVariableStorage()
    {
        return variableStorage;
    }

    public void setScriptInterface(ScriptInterface scriptInterface)
    {
        this.scriptInterface = scriptInterface;
        scriptInterface.init(this);
    }
    
    public void setRandom(Random random)
    {
       runner.setRandom(random);
    }

    public void runScript(String script) throws ScriptException
    {
        if (scriptInterface != null)
        {
            String codelines[] = script.split("\\r?\\n");
            runner.run(codelines, scriptInterface);
        }
        else
        {
            throw new ScriptException("No script interface set!");
        }
    }

    public void runFullScript(String script) throws ScriptException
    {
        runScript(script);
        while (!nextStep())
        {}
    }

    public boolean nextStep() throws ScriptException
    {
        return runner.nextStep();
    }
}
