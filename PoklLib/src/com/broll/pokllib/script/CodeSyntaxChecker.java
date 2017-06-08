package com.broll.pokllib.script;

import com.broll.pokllib.script.syntax.DoCommand;
import com.broll.pokllib.script.syntax.ElseFinder;
import com.broll.pokllib.script.syntax.IsCommand;
import com.broll.pokllib.script.syntax.JumpLabel;
import com.broll.pokllib.script.syntax.SetCommand;
import com.broll.pokllib.script.syntax.VarCommand;

public class CodeSyntaxChecker
{
    private int nestingLevel = 0;
 
    public CodeSyntaxChecker()
    {

    }

    public void checkCode(String code) throws ScriptException
    {
        String codelines[] = code.toLowerCase().split("\\r?\\n");
        for (int i = 0; i < codelines.length; i++)
        {
            checkLine(codelines, i);
        }
        
        if(nestingLevel<0||nestingLevel>1)
        {
           // throw new ScriptException("Nestinglevel invalid ("+nestingLevel+")! Check conditional braches.");
        }
    }

    public void checkLine(String[] lines, int currentLine) throws ScriptException
    {
        String code = lines[currentLine].trim();
        LineType type = LineCheck.getLineType(code);
        switch (type)
        {
            case GOTO:
                JumpLabel.readGotolName(code);
                break;
            case LABEL:
                JumpLabel.readLabelName(code);
                break;
            case COMMENT:
                break;
            case DO:
                DoCommand.build(code);
                break;
            case ELSE:
                ElseFinder.findEndLine(lines, currentLine);
                break;
            case END:
                nestingLevel--;
                break;
            case IS:
                IsCommand.build(code);
                nestingLevel++;
                int line=ElseFinder.findElseLine(lines, currentLine);
                if(line==lines.length)
                {
                    nestingLevel--;
                }
                break;
            case SET:
                SetCommand.build(code);
                break;
            case VAR:
                VarCommand.build(code);
                break;
            case STOP:
                
                break;
        }



    }

}
