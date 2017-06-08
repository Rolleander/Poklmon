package com.broll.pokllib.script;

import java.util.List;

import com.broll.pokllib.script.syntax.Value;
import com.broll.pokllib.script.syntax.VariableException;

public abstract class ScriptInterface {
	
    private ScriptModul scriptModul;
    
    public void init(ScriptModul scriptModul)
    {
        this.scriptModul=scriptModul;
    }
    
    protected void clearVariableStorage()
    {
        scriptModul.initStorage();
    }
    
    protected void addField(String identifier, Object value)
    {
        try
        {
            scriptModul.getVariableStorage().addVariable(identifier,new Value( value));
        }
        catch (VariableException e)
        {
            e.printStackTrace();
        }
    }
    
    protected Value getField(String identifier) throws VariableException
    {
        return scriptModul.getVariableStorage().getVariable(identifier);
    }
    
	public void noCommand(){
		
	}

	public abstract void command(String cmd, List<Value> parameter)  throws VariableException;
	
	public abstract Object function(String fct, List<Value> parameter)  throws VariableException;
    
	

}
