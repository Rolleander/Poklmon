package com.broll.pokllib.script;

import java.util.HashMap;

import com.broll.pokllib.script.syntax.Value;
import com.broll.pokllib.script.syntax.VariableException;

public class VariableStorage
{

    private HashMap<String, Value> variables = new HashMap<String, Value>();

    public VariableStorage()
    {

    }

    public void addVariable(String identifier, Value value) throws VariableException
    {
        if (!variables.containsKey(identifier))
        {
            variables.put(identifier, value);
        }
        else
        {
            throw new VariableException();
        }
    }

    public boolean existsVariable(String identifier)
    {
        return variables.containsKey(identifier);
    }

    public Value getVariable(String identifier) throws VariableException
    {
       if (existsVariable(identifier))
        {
            return variables.get(identifier);
        }
        throw new VariableException();
    }

    public HashMap<String, Value> getVariables()
    {
        return variables;
    }
}
