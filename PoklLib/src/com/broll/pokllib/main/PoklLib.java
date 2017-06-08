package com.broll.pokllib.main;


public class PoklLib {

	
    private static DataControlInterface dataControlInterface;
    
    public static void init(DataControlInterface dataControlInterface)
    {
        PoklLib.dataControlInterface = dataControlInterface;
    }
    
    public static DataControlInterface data()
    {
        return dataControlInterface;
    }

}
