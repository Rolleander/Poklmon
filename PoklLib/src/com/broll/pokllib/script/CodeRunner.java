package com.broll.pokllib.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.broll.pokllib.script.syntax.DoCommand;
import com.broll.pokllib.script.syntax.ElseFinder;
import com.broll.pokllib.script.syntax.IsCommand;
import com.broll.pokllib.script.syntax.JumpLabel;
import com.broll.pokllib.script.syntax.Parameter;
import com.broll.pokllib.script.syntax.SetCommand;
import com.broll.pokllib.script.syntax.Value;
import com.broll.pokllib.script.syntax.VarCommand;
import com.broll.pokllib.script.syntax.VariableException;

public class CodeRunner
{
    private CommandExecutor commandExecutor=new CommandExecutor();
    private String[] lines;
    private int currentLine;
    private ScriptInterface scriptInterface;
    private static Random random=new Random();


    public void setVariableStorage(VariableStorage variableStorage)
    {
        commandExecutor.setVariableStorage(variableStorage);
    }



    public void run(String[] codelines, ScriptInterface scriptInterface) throws ScriptException
    {

        currentLine = 0;
        lines = codelines;
        this.scriptInterface = scriptInterface;
        commandExecutor.setScriptInterface(scriptInterface);
        runLine(currentLine);
    }

    private void runLine(int line) throws ScriptException
    {
        currentLine = line;
        String code = line();
        LineType type = LineCheck.getLineType(code);
        switch (type)
        {
            case GOTO:
                String gotoLabel = JumpLabel.readGotolName(code);
                //find line of label
                for (int i = 0; i < lines.length; i++)
                {
                    String jumpLine = lines[i].trim();
                    //check for label cmd
                    LineType checkType = LineCheck.getLineType(jumpLine);
                    if (checkType == LineType.LABEL)
                    {
                        //get label name
                        String labelName = JumpLabel.readLabelName(jumpLine);
                        if (labelName.equals(gotoLabel))
                        {
                            //found label, jump
                            currentLine = i;
                            break;
                        }
                    }
                }
                //overread goto if labelname not found
                currentLine++;
                break;
            case LABEL:
                //falltrough to comment
            case COMMENT:
                scriptInterface.noCommand();
                currentLine++;
                break; // do nothing
            case DO:
                DoCommand doCommand=DoCommand.build(code);
                String cmd=doCommand.getCommandName();
                List<Value> parameter=new ArrayList<Value>();
                try
                {
                for(Parameter param:doCommand.getParameter())
                {                  
                        parameter.add(commandExecutor.getValue(param));            
                }
                //call interface
                scriptInterface.command(cmd.toLowerCase(), parameter);
                }
                catch (VariableException e)
                {
                    e.printStackTrace();
                    throw new ScriptException("Failed to read parameter variable values!");
                }
                currentLine++;
                break;
            case ELSE:
                // jump to correct end
                currentLine = ElseFinder.findEndLine(lines, currentLine);
                break;
            case END:
                scriptInterface.noCommand();
                currentLine++;
                break;
            case IS:
                IsCommand isCommand = IsCommand.build(code);
                try
                {
                    if (commandExecutor.executeIs(isCommand))
                    {
                        // go normal way
                        currentLine++;
                    }
                    else
                    {
                        // jump to correct else/end
                        currentLine = ElseFinder.findElseLine(lines, currentLine);
                    }
                }
                catch (VariableException e2)
                {
                    e2.printStackTrace();
                    throw new ScriptException("Is-command failed, encountered invalid variables!");
                }
                break;
            case SET:
                SetCommand setCommand = SetCommand.build(code);
                try
                {
                    commandExecutor.executeSet(setCommand);
                }
                catch (VariableException e1)
                {
                    e1.printStackTrace();
                    throw new ScriptException("Variable execution failed in set command!");
                }
                currentLine++;
                break;
            case VAR:
                VarCommand varCommand = VarCommand.build(code);
                try
                {
                    commandExecutor.executeVar(varCommand);
                }
                catch (VariableException e)
                {
                    e.printStackTrace();
                    throw new ScriptException("Could not init variable \"" + varCommand.getVariableName() + "\"");
                }
                currentLine++;
                break;
            case STOP:
                currentLine = Integer.MAX_VALUE - 1;
                scriptInterface.noCommand();
                break; // stop script in next step
        }
    }

  

    public boolean nextStep() throws ScriptException
    {
        if (currentLine >= lines.length)
        {
            return true;
        }
        runLine(currentLine);
        return false;
    }

    private String line()
    {
        return lines[currentLine].trim();
    }



    public void setRandom(Random random)
    {
        CodeRunner.random=random;
    }

    public static double getRandom()
    {
        return random.nextDouble();
    }
    
}
