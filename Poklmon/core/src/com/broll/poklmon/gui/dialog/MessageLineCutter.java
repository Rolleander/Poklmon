package com.broll.poklmon.gui.dialog;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIFonts;

public class MessageLineCutter
{
    private static int maxWidth = 725;
    private static int minCutChars = 25;
    private static char blankChar = ' ';

    public static String[] cutMessage(String message)
    {
        return cutMessage(message,GUIFonts.dialogText,maxWidth,minCutChars);
    }
    
    public static String[] cutMessage(String message,BitmapFont font, int maxWidth, int minCutChars)
    {
        ArrayList<String> lines = new ArrayList<String>();
        while (message.length() >= minCutChars)
        {

            int cut = -1;
            for (int i = minCutChars; i < message.length(); i++)
            {
                String s = message.substring(0, i);
                if (FontUtils.getWidth(font,s) > maxWidth)
                {
                    cut = i - 1;
                    break;
                }
            }

            if (cut == -1)
            {
                break;
            }
            else
            {
                //check auf wort
                while (message.charAt(cut) != blankChar)
                {
                    cut--;
                }
                String part1 = message.substring(0, cut + 1);
                String part2 = message.substring(cut + 1);
                message = part2;
                lines.add(part1);
            }

        }
        //add rest
        lines.add(message);
        return lines.toArray(new String[0]);
    }


}
