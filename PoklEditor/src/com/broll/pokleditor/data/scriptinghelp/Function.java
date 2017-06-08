package com.broll.pokleditor.data.scriptinghelp;

import java.util.ArrayList;
import java.util.List;

import com.broll.pokllib.script.SyntaxError;
import com.broll.pokllib.script.syntax.SyntaxString;

public class Function
{
    private String name;
    private String description;
    private List<Field> parameter = new ArrayList<Field>();
    private VarType returnType;

    public Function(String name, String description, VarType returnValue)
    {
        this.name = name;
        this.description = description;
        this.returnType = returnValue;
    }

    public VarType getReturnType()
    {
        return returnType;
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

    public static Function read(SyntaxString str) throws SyntaxError
    {
        String fs=str.cutAttribute("$");
        Field f = Field.read(new SyntaxString(fs));
        VarType returnType = f.getVarType();
        String name = f.getName();
        str.skip("$");
        String content = str.cutAttribute("-");
        str.skip("-");
        String description = str.cutAttribute();

        String[] fields = content.split(",");

        Function function = new Function(name, description, returnType);
        for (String field : fields)
        {
            if(field!=null&&!field.isEmpty())
            {
            Field ff = Field.read(new SyntaxString(field));
            function.addParameter(ff);
            }
        }
        return function;
    }

}
