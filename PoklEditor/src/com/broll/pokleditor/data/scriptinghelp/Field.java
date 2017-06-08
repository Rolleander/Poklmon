package com.broll.pokleditor.data.scriptinghelp;

import com.broll.pokllib.script.SyntaxError;
import com.broll.pokllib.script.syntax.SyntaxString;

public class Field
{

    private VarType varType;
    private String name;
    private String description;

    public Field(VarType varType, String name)
    {
        this.varType = varType;
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
    }

    public VarType getVarType()
    {
        return varType;
    }

    public static Field read(SyntaxString str) throws SyntaxError
    {
         for (VarType varType : VarType.values())
        {
            if (str.hasMark(varType.getTxt()))
            {
                str.skip(varType.getTxt());
                String name = "";
              
                 if (str.hasMark("-"))
                {
                    name = str.cutAttribute("-");
                }
                 else   if (str.hasMark(","))
                 {
                     name = str.cutAttribute(",");
                 }
                else if (str.hasMark("$"))
                {
                    name = str.cutAttribute("$");
                }
                
                else
                {
                    name = str.cutAttribute();
                }
                return new Field(varType, name);
            }
        }
     

        throw new SyntaxError();
    }
}
