package com.broll.pokllib.script.syntax;

import com.broll.pokllib.script.CodeRunner;
import com.broll.pokllib.script.ScriptIdentifiers;
import com.broll.pokllib.script.SyntaxError;

public class SetCommand
{

    private String variableName;
    private SetTyp typ;
    private Parameter parameter;

    public SetCommand(String name)
    {
        this.variableName = name;

    }

    public void setParameter(Parameter parameter)
    {
        this.parameter = parameter;
    }

    public void setTyp(SetTyp typ)
    {
        this.typ = typ;
    }

    public SetTyp getTyp()
    {
        return typ;
    }

    public String getVariableName()
    {
        return variableName;
    }

    public Parameter getParameter()
    {
        return parameter;
    }

    public static SetCommand build(String code) throws SyntaxError
    {
        // Command syntax:
        // set variablenname to
        SyntaxString str = new SyntaxString(code);
        str.skip(ScriptIdentifiers.SET);// skip set

        SetCommand command = null;
        for (SetTyp setTyp : SetTyp.values())
        {
            String set = setTyp.getSetName();
            if (str.hasMark(set))
            {
                String var = str.cutAttribute(set);
                if(var==null || var.length()==0)
                {
                    throw new SyntaxError();
                }
                command = new SetCommand(var);
                str.skip(set);
                command.setTyp(setTyp);
            }
        }

        if (command == null)
        {
            throw new SyntaxError();
        }

        command.setParameter(new Parameter(str.cutAttribute()));
        return command;

    }
}
