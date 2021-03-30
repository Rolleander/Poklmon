package com.broll.pokleditor.main;

import com.broll.pokllib.main.DataConvertor;

public class ConvertData
{

    public static void main(String[] args)
    {
        try
        {
            DataConvertor.xmlToDb4o();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
