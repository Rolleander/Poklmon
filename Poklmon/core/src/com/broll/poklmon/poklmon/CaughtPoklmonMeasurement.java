package com.broll.poklmon.poklmon;

import com.badlogic.gdx.math.MathUtils;
import com.broll.pokllib.poklmon.PoklmonWesen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CaughtPoklmonMeasurement
{

    public static String getCaughtDayInfo()
    {
        Date date=new Date();
        SimpleDateFormat formatDateJava = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        return formatDateJava.format(date);      
    }
    
    public static short[] generateRandomDVs()
    {
        short[] dv = new short[6];
        for (int i = 0; i < dv.length; i++)
        {
            dv[i] = (short) MathUtils.random(0, 31);
        }
        return dv;
    }
    
    public static PoklmonWesen getRandomWesen()
    {
        int randomWesen = (int)(Math.random() * PoklmonWesen.values().length);
        PoklmonWesen wesen = PoklmonWesen.values()[randomWesen];
        return wesen;
    }
}
