package com.broll.pokleditor.map.history;


public class ChangeTilesOperation extends MapEditOperation {

    public static class TileChange {
        private int x, y, l;
        private int tile;
        private int previousTile = -1;

        public TileChange(int x, int y, int l, int tile) {
            this.x = x;
            this.y = y;
            this.l = l;
            this.tile = tile;
        }
    }

    private TileChange[] changes;

    public ChangeTilesOperation(TileChange... changes) {
        this.changes = changes;
    }

    @Override
    public void undo() {
        for (TileChange change : changes) {
            map.getTiles()[change.x][change.y][change.l] = change.previousTile;
        }
    }

    @Override
    public boolean redo() {
        boolean changed = false;
        for (TileChange change : changes) {
            if (change.previousTile == -1) {
                change.previousTile = map.getTiles()[change.x][change.y][change.l];
            }
            if (map.getTiles()[change.x][change.y][change.l] != change.tile) {
                changed = true;
                map.getTiles()[change.x][change.y][change.l] = change.tile;
            }
        }
        return changed;
    }
}
