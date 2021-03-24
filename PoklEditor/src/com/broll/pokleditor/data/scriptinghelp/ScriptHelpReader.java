package com.broll.pokleditor.data.scriptinghelp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.broll.pokllib.script.SyntaxError;
import com.broll.pokllib.script.syntax.SyntaxString;

public class ScriptHelpReader
{
    

    public static ScriptDictionary read(String file)
    {
        file="/com/broll/pokleditor/data/scriptinghelp/dicts/"+file;
        ScriptDictionary scriptDictionary = new ScriptDictionary();
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(new File(ScriptHelpReader.class.getResource(file).getFile())));
            String line = null;
            while ((line = br.readLine()) != null)
            {
                analyse(line, scriptDictionary);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return scriptDictionary;
    }

    private final static int READ_FIELDS = 0;
    private final static int READ_COMMANDS = 1;
    private final static int READ_FUNCTIONS = 2;
    private static int readMode;

    private static void analyse(String line, ScriptDictionary scriptDictionary)
    {
        if (line.startsWith("fields>"))
        {
            readMode = READ_FIELDS;
        }
        else if (line.startsWith("commands>"))
        {
            readMode = READ_COMMANDS;
        }
        else if (line.startsWith("functions>"))
        {
            readMode = READ_FUNCTIONS;
        }
        else
        {
            if (line == null || line.isEmpty())
            {
                return;
            }
            //read
            SyntaxString syntaxString = new SyntaxString(line.trim());
            switch (readMode)
            {
                case READ_FIELDS:
                    try
                    {
                        Field field = Field.read(syntaxString);
                       syntaxString.skip("-");
                        field.setDescription(syntaxString.cutAttribute());
                        scriptDictionary.addField(field);
                    }
                    catch (SyntaxError e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case READ_COMMANDS:
                    try
                    {
                        Command command = Command.read(syntaxString);
                        scriptDictionary.addCommand(command);
                    }
                    catch (SyntaxError e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case READ_FUNCTIONS:
                    try
                    {
                        Function function = Function.read(syntaxString);
                        scriptDictionary.addFunction(function);
                    }
                    catch (SyntaxError e)
                    {
                        System.err.println(line);
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

}
