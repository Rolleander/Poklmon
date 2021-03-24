package com.broll.pokllib.script.syntax;

import com.broll.pokllib.script.ScriptIdentifiers;
import com.broll.pokllib.script.SyntaxError;

public class Parameter
{

    private Object value;
    private ParameterType type;

    public Parameter(String param) throws SyntaxError
    {
        // check for type and parse

        if (param.startsWith(ScriptIdentifiers.STRINGID))
        {
            // string
            type = ParameterType.STRING;
            value = param.substring(1, param.length());
        }
        else if (param.equals(ScriptIdentifiers.BOOLTRUE))
        {
            type = ParameterType.BOOLEAN;
            value = new Boolean(true);
        }
        else if (param.equals(ScriptIdentifiers.BOOLFALSE))
        {
            type = ParameterType.BOOLEAN;
            value = new Boolean(false);
        }
        else
        {
            // number
            type = ParameterType.NUMBER;
            try
            {
                value = Double.parseDouble(param);
            }
            catch (Exception e)
            {
                if (param.length() > 0)
                {
                    if (param.startsWith(ScriptIdentifiers.FUNCTION))
                    {
                        //its a function
                        type = ParameterType.FUNCTION;
                        value = param.substring(ScriptIdentifiers.FUNCTION.length());
                    }
                    else
                    {
                        //its a variable
                        type = ParameterType.VARIABLE;
                        value = param;
                    }
                }
                else
                {
                    throw new SyntaxError();
                }
            }
        }
    }

    public ParameterType getType()
    {
        return type;
    }

    public Object getValue()
    {
        return value;
    }

}
