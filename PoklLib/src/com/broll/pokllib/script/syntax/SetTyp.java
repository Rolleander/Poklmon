package com.broll.pokllib.script.syntax;

import com.broll.pokllib.script.ScriptIdentifiers;

public enum SetTyp
{

    TO(ScriptIdentifiers.ALLOCATION), ADD(ScriptIdentifiers.ADD), SUB(ScriptIdentifiers.SUBTRACT), MUL(
            ScriptIdentifiers.MULTIPLICATE), DIV(ScriptIdentifiers.DIVIDE);

    private String setName;

    private SetTyp(String name)
    {
        this.setName = name;
    }

    public String getSetName()
    {
        return setName;
    }

}
