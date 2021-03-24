package com.broll.pokllib.poklmon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TypeComperator
{

    private static TypeCompare[][] table;
    public static String file = "/TypeTable.txt";

    public static TypeCompare getTypeCompare(ElementType attack, ElementType target)
    {
        return table[attack.ordinal()][target.ordinal()];
    }
/*
    private static String readTable()
    {
        BufferedReader br = null;
        String read = null;
        try
        {
        	InputStream input = TypeComperator.class.getResourceAsStream(file);
            br = new BufferedReader(new InputStreamReader(input));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null)
            {
                sb.append(line);
                line = br.readLine();
            }
            read = sb.toString();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return read;
    }
*/
    public static void init(String tableSource)
    {
        int size = ElementType.values().length;
        table = new TypeCompare[size][size];

        String[] fields = tableSource.split("\\|");
        for (int i = 0; i < fields.length - 1; i++)
        {
            int row = i / size;
            int column = i % size;
            TypeCompare comp = TypeCompare.STANDARD;

            String s = fields[i];
            if (s.length() > 1)
            {
                s = s.replaceAll("\\n", "");
                s = s.replaceAll("\\s", "");
                s = s.replaceAll("\n", "");
                s = s.replaceAll("\r", "");
                s = s.replaceAll("\\r", "");
            }
            switch (s)
            {
                case "+":
                    comp = TypeCompare.EFFECTIVE;
                    break;
                case "-":
                    comp = TypeCompare.NOTEFFECTIVE;
                    break;
                case "o":
                    comp = TypeCompare.NOEFFECT;
                    break;
            }

            table[row][column] = comp;
        }
    
    }

}
