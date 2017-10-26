package com.broll.poklmon.save.manage;

import com.broll.poklmon.save.GameData;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Roland on 08.06.2017.
 */
public interface DeviceSaveOperations {

    public InputStream readFile(String file) throws Exception;

    public OutputStream writeFile(String file) throws Exception;

}
