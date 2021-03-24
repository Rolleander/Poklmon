package com.broll.pokllib.script.syntax;

import com.broll.pokllib.script.ScriptIdentifiers;
import com.broll.pokllib.script.SyntaxError;

public class JumpLabel
{


    public static String readLabelName(String code) throws SyntaxError
    {
        // Command syntax:
        // label name
        SyntaxString str = new SyntaxString(code);
        str.skip(ScriptIdentifiers.LABEL);// skip label
        return str.cutAttribute();
    }

    public static String readGotolName(String code) throws SyntaxError
    {
        // Command syntax:
        // label name
        SyntaxString str = new SyntaxString(code);
        str.skip(ScriptIdentifiers.GOTO);// skip label
        return str.cutAttribute();
    }

}
