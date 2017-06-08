package com.broll.pokllib.script.syntax;

import java.util.ArrayList;
import java.util.List;

import com.broll.pokllib.script.ScriptIdentifiers;
import com.broll.pokllib.script.SyntaxError;

public class DoCommand
{

    private String commandName;
    private List<Parameter> parameter;

    public DoCommand(String name)
    {
        this.commandName = name;
        parameter = new ArrayList<Parameter>();
    }

    public void addParameter(Parameter object)
    {
        parameter.add(object);
    }

    public String getCommandName()
    {
        return commandName;
    }

    public List<Parameter> getParameter()
    {
        return parameter;
    }

    public static DoCommand build(String code) throws SyntaxError
    {
        // Command syntax:
        // do methodename
        // do methodename @ ...parameters..

        SyntaxString str = new SyntaxString(code);
        str.skip(ScriptIdentifiers.COMMANDO);// skip DO
        DoCommand command = null;
        // check if method has paramters
        if (str.hasMark(ScriptIdentifiers.PARAMETER))
        {
            String name = str.cutAttribute(ScriptIdentifiers.PARAMETER);
            if (name == null || name.length() == 0)
            {
                throw new SyntaxError();
            }
            command = new DoCommand(name);
            str.skip(ScriptIdentifiers.PARAMETER);
            // check number of params			
            while (str.hasMark(ScriptIdentifiers.SEPERATOR))
            {
                String para = str.cutAttribute(ScriptIdentifiers.SEPERATOR);
                command.addParameter(new Parameter(para));
                str.skip(ScriptIdentifiers.SEPERATOR);
            }
            command.addParameter(new Parameter(str.cutAttribute()));
        }
        else
        {
            // no params
            String name = str.cutAttribute();
            if (name == null || name.length() == 0)
            {
                throw new SyntaxError();
            }
            command = new DoCommand(name);
        }
        return command;

    }
}
