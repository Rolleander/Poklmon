package com.broll.pokllib.script.syntax;

import com.broll.pokllib.script.LineCheck;
import com.broll.pokllib.script.LineType;
import com.broll.pokllib.script.ScriptException;
import com.broll.pokllib.script.SyntaxError;

public class ElseFinder
{

    public static int findElseLine(String[] codelines, int currentLine) throws ScriptException
    {
        int anzIfs = 0;
        boolean found = false;
        do
        {
            currentLine++;

            String code = null;
            try
            {
                code = codelines[currentLine].trim();
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                //no else or end found => its the end of the script
                return currentLine;
            }

            LineType type = LineCheck.getLineType(code);
            if (type == LineType.ELSE || type == LineType.END)
            {
                if (anzIfs == 0)
                {
                    found = true;
                }
            }
            if (type == LineType.IS)
            {
                anzIfs++;
            }
            if (type == LineType.END)
            {
                anzIfs--;
            }
        }
        while (found == false);
        return currentLine + 1;
    }

    public static int findEndLine(String[] codelines, int currentLine) throws ScriptException
    {
        int anzIfs = 0;
        boolean found = false;
        do
        {
            currentLine++;
            String code = null;
            try
            {
                code = codelines[currentLine].trim();
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                //no end found => its the end of the script
                return currentLine;
            }
            LineType type = LineCheck.getLineType(code);
            if (type == LineType.END)
            {
                if (anzIfs == 0)
                {
                    found = true;
                }
            }
            if (type == LineType.IS)
            {
                anzIfs++;
            }
            if (type == LineType.END)
            {
                anzIfs--;
            }
        }
        while (found == false);
        return currentLine + 1;
    }
}
