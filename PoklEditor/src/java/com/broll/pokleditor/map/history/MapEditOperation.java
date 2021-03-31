package com.broll.pokleditor.map.history;

import com.broll.pokllib.map.MapData;

public abstract class MapEditOperation {

    protected MapData map;

    public void setMap(MapData map) {
        this.map = map;
    }

    public abstract void undo();

    public abstract boolean redo();

}
