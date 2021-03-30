package com.broll.pokleditor.data.scriptinghelp;

import java.util.ArrayList;
import java.util.List;

public class ScriptDictionary
{
    private List<Field> fields = new ArrayList<Field>();
    private List<Command> commands = new ArrayList<Command>();
    private List<Function> functions = new ArrayList<Function>();


    public ScriptDictionary()
    {

    }

    public void addField(Field field)
    {
        fields.add(field);
    }

    public void addCommand(Command command)
    {
        commands.add(command);
    }

    public void addFunction(Function function)
    {
        functions.add(function);
    }

    public List<Command> getCommands()
    {
        return commands;
    }

    public List<Field> getFields()
    {
        return fields;
    }

    public List<Function> getFunctions()
    {
        return functions;
    }
}
