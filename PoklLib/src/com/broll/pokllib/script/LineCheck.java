package com.broll.pokllib.script;

public class LineCheck
{


    /**
     * syntax
     * 
     * 
     * set hans = 12 do retten do spawn @ 8 , 7 do text @
     * "Hey du kleiner Lümmel" is vari.12 equal 100 .. .. end
     * 
     * is known equal true .. .. .. else .. .. .. end
     * 
     * 
     */

    public static LineType getLineType(String line) throws ScriptException
    {
        if (line == null || line.isEmpty())
        {
            return LineType.COMMENT;
        }

        for (LineType lineType : LineType.values())
        {
            if (line.toLowerCase().startsWith(lineType.getLineName()))
            {
                return lineType;
            }
        }

        throw new ScriptException("Unknown Line Command! (" + line + ")");
    }

}
