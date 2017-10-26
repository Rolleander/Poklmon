package com.broll.poklmon;

import com.badlogic.gdx.Gdx;
import com.broll.poklmon.save.manage.DeviceSaveOperations;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Roland on 08.06.2017.
 */
public class AndroidSaveFolder implements DeviceSaveOperations {
    @Override
    public InputStream readFile(String file) throws Exception {
        return Gdx.files.local(file).read();
    }

    @Override
    public OutputStream writeFile(String file) throws Exception {
        return Gdx.files.local(file).write(false);
    }
}
