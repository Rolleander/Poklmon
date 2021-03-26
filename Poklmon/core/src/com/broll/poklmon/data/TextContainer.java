package com.broll.poklmon.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.broll.poklmon.resource.ResourceUtils;

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Created by Roland on 29.10.2017.
 */

public class TextContainer {

    private static I18NBundle i18NBundle;

    public static void load(){
        String path=ResourceUtils.DATA_PATH+"i18n/Game";
        FileHandle baseFileHandle = Gdx.files.internal(path);
        Locale locale = new Locale("de", "ger", "VAR1");
        i18NBundle= I18NBundle.createBundle(baseFileHandle,locale,"ISO-8859-1");
    }

    public static synchronized String get(String key, Object... values){
        try{
            return i18NBundle.format(key,values);
        }catch (MissingResourceException e){
            return "[!]";
        }
    }
}
