package com.broll.pokllib.script.syntax;

import com.broll.pokllib.script.ScriptIdentifiers;
import com.broll.pokllib.script.SyntaxError;

public class VarCommand
{

    private String variableName;
    private Parameter parameter;

    public VarCommand(String name)
    {
        this.variableName = name;

    }

    public void setParameter(Parameter parameter)
    {
        this.parameter = parameter;
    }


    public String getVariableName()
    {
        return variableName;
    }

    public Parameter getParameter()
    {
        return parameter;
    }

    public static VarCommand build(String code) throws SyntaxError
    {
        // Command syntax:
        // set variablenname to
        SyntaxString str = new SyntaxString(code);
        str.skip(ScriptIdentifiers.VAR);// skip set

        VarCommand command = null;
        String var = str.cutAttribute(ScriptIdentifiers.INIT);
        if(var==null || var.length()==0)
        {
            throw new SyntaxError();
        }
        command = new VarCommand(var);
        str.skip(ScriptIdentifiers.INIT);

        command.setParameter(new Parameter(str.cutAttribute()));
        return command;

    }
}
