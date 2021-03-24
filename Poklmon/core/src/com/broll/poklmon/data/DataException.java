package com.broll.poklmon.data;

public class DataException extends Exception
{

    public DataException(String text)
    {
        super("Data Exception: "+text);
    }
}
