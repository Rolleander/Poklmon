package com.broll.poklmon.save.manage;

/**
 * Created by Roland on 31.10.2017.
 */

public class SaveFileUtils {

    public static String getPlayTime(int time){
        int hours = (int) time / 3600;
        int remainder = (int) time - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;
        String m = "" + mins;
        if (m.length() == 1) {
            m = "0" + m;
        }
        String s = "" + secs;
        if (s.length() == 1) {
            s = "0" + s;
        }
        return hours + ":" + m + ":" + s;
    }
}
