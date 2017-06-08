package com.broll.pokleditor.data.scriptinghelp;

import java.util.ArrayList;
import java.util.List;

import com.broll.pokllib.script.SyntaxError;
import com.broll.pokllib.script.syntax.SyntaxString;

public class Command
{
    private String name;
    private String description;
    private List<Field> parameter = new ArrayList<Field>();

    public Command(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public void addParameter(Field field)
    {
        parameter.add(field);
    }

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
    }

    public List<Field> getParameter()
    {
        return parameter;
    }

    public static Command read(SyntaxString str) throws SyntaxError
    {
        String name = str.cutAttribute("@");
        str.skip("@");
        String content = str.cutAttribute("-");
        str.skip("-");
        String description = str.cutAttribute();
        String[] fields = content.split(",");

        Command command = new Command(name, description);
        for (String field : fields)
        {
        	if(field!=null &&!field.isEmpty())
        	{
            Field f = Field.read(new SyntaxString(field));
            command.addParameter(f);
        	}
        }
        return command;
    }
}
