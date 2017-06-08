package com.broll.poklmon.data;

import com.badlogic.gdx.Gdx;
import com.broll.poklmon.resource.ResourceUtils;

/**
 * Created by Roland on 08.06.2017.
 */
public class MiscContainer extends ResourceContainer{

    private String typeTable;

    public void load()
    {
        typeTable=Gdx.files.internal(ResourceUtils.DATA_PATH+"TypeTable.txt").readString();

    }

    public String getTypeTable() {
        return typeTable;
    }
}
