package com.broll.poklmon.desktop;

import com.broll.poklmon.save.manage.DeviceSaveOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Roland on 08.06.2017.
 */
public class DesktopSaveFolder implements DeviceSaveOperations {

    private File path;
    private final static String dir="/BRollGames/Poklmon/";

    public DesktopSaveFolder()
    {
        path=new File(System.getProperty("user.home")+dir);
        if(!path.exists()){
            path.mkdirs();
        }
        System.out.println("PATH: "+path);
    }


    @Override
    public InputStream readFile(String file) throws FileNotFoundException {
            return new FileInputStream(path.getAbsolutePath()+"/"+file);
    }

    @Override
    public OutputStream writeFile(String file) throws FileNotFoundException {
        return new FileOutputStream(path.getAbsolutePath()+"/"+file);
    }
}
