package com.broll.pokleditor.map.history;

import com.broll.pokleditor.map.MapTileEditor;
import com.broll.pokllib.map.MapData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapEditControl {

    private final static int MAX_HISTORY = 20;
    private int index;
    private Map<Integer, MapEditOperation> edits = new HashMap<>();
    private MapData map;
    private MapTileEditor editor;

    public MapEditControl(MapTileEditor editor) {
        this.editor = editor;
    }

    public void init(MapData map) {
        this.map = map;
        index = 0;
        edits.clear();
    }

    public void edit(MapEditOperation edit) {
        edit.setMap(map);
        boolean changed = edit.redo();
        if (changed) { //only add if it actually changed something in the map
            index++;
            edits.put(index, edit);
            edits.entrySet().removeIf(it -> it.getKey() <= index - MAX_HISTORY);
            editor.repaint();
        }
    }

    public void undo() {
        MapEditOperation edit = edits.get(index);
        if (edit == null) {
            return;
        }
        edit.undo();
        index--;
        editor.repaint();
    }

    public void redo() {
        index++;
        MapEditOperation edit = edits.get(index);
        if (edit == null) {
            index--;
            return;
        }
        edit.redo();
        editor.repaint();
    }

}
