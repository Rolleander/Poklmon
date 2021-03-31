package com.broll.pokleditor.map.history;


public class ChangeAreasOperation extends MapEditOperation {

    public static class AreaChange {
        private int x, y;
        private int area;
        private int previousArea = -1;

        public AreaChange(int x, int y, int area) {
            this.x = x;
            this.y = y;
            this.area = area;
        }
    }

    private AreaChange[] changes;

    public ChangeAreasOperation(AreaChange... changes) {
        this.changes = changes;
    }

    @Override
    public void undo() {
        for (AreaChange change : changes) {
            map.getAreas()[change.x][change.y] = change.previousArea;
        }
    }

    @Override
    public boolean redo() {
        boolean changed = false;
        for (AreaChange change : changes) {
            if (change.previousArea == -1) {
                change.previousArea = map.getAreas()[change.x][change.y];
            }
            if (map.getAreas()[change.x][change.y] != change.area) {
                changed = true;
                map.getAreas()[change.x][change.y] = change.area;
            }
        }
        return changed;
    }
}
