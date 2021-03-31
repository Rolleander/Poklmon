package com.broll.pokleditor.map;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.broll.pokleditor.map.history.ChangeAreasOperation;
import com.broll.pokleditor.map.history.ChangeTilesOperation;
import com.broll.pokleditor.map.history.MapEditControl;
import com.broll.pokleditor.map.tileset.AreaSetPanel;
import com.broll.pokleditor.map.tileset.TileSetPanel;
import com.broll.pokleditor.map.tools.CurrentTool;
import com.broll.pokleditor.map.tools.FloodFillAlgorithm;
import com.broll.pokleditor.map.tools.MapPaintTools;
import com.broll.pokllib.map.MapData;

import org.apache.commons.lang3.ArrayUtils;

public class MousePlacementListener implements MouseListener, MouseMotionListener {

    private MapData map;
    private MapTileEditor tiles;
    private MapEditControl mapEdit;

    public MousePlacementListener(MapTileEditor tiles, MapEditControl editControl) {
        this.tiles = tiles;
        this.mapEdit = editControl;
    }

    public void setMap(MapData map) {
        this.map = map;
    }

    private void place(int x, int y) {
        List<ChangeTilesOperation.TileChange> tileChanges = new ArrayList<>();
        List<ChangeAreasOperation.AreaChange> areaChanges = new ArrayList<>();
        if (MapPaintTools.selectedTool == CurrentTool.PENCIL) {
            for (int sx = 0; sx < TileSetPanel.selection.getWidth(); sx++) {
                for (int sy = 0; sy < TileSetPanel.selection.getHeight(); sy++) {
                    int idx = TileSetPanel.selection.getTilex() + sx;
                    int idy = TileSetPanel.selection.getTiley() + sy;

                    int xpos = x + sx;
                    int ypos = y + sy;
                    if (xpos > -1 && ypos > -1) {
                        if (xpos < map.getWidth() && ypos < map.getHeight()) {
                            if (MapPanel.isAreaPanelSelected()) {
                                areaChanges.add(new ChangeAreasOperation.AreaChange(xpos, ypos, AreaSetPanel.areaSelection));
                            } else {
                                int id = idy * 10 + idx + 1;
                                tileChanges.add(new ChangeTilesOperation.TileChange(xpos, ypos, MapPaintTools.SELECTED_LAYER, id));
                            }
                        }
                    }
                }
            }
        } else if (MapPaintTools.selectedTool == CurrentTool.ERASER) {
            if (MapPanel.isAreaPanelSelected()) {
                areaChanges.add(new ChangeAreasOperation.AreaChange(x, y, 0));
            } else {
                tileChanges.add(new ChangeTilesOperation.TileChange(x, y, MapPaintTools.SELECTED_LAYER, 0));
            }
        } else if (MapPaintTools.selectedTool == CurrentTool.FLOODFILL) {
            if (MapPanel.isAreaPanelSelected()) {
                areaChanges = FloodFillAlgorithm.floodFill(map.getAreas(), x, y, AreaSetPanel.areaSelection);
            } else {
                int idx = TileSetPanel.selection.getTilex();
                int idy = TileSetPanel.selection.getTiley();
                int id = idy * 10 + idx + 1;
                tileChanges = FloodFillAlgorithm.floodFill(map.getTiles(), MapPaintTools.SELECTED_LAYER, x, y, id);
            }
        }
        if (!tileChanges.isEmpty()) {
            mapEdit.edit(new ChangeTilesOperation(tileChanges.toArray(new ChangeTilesOperation.TileChange[0])));
        }
        if (!areaChanges.isEmpty()) {
            mapEdit.edit(new ChangeAreasOperation(areaChanges.toArray(new ChangeAreasOperation.AreaChange[0])));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / MapTileEditor.TILE_SIZE;
        int y = e.getY() / MapTileEditor.TILE_SIZE;
        tiles.mouseLocation(x, y);
        if (MapPaintTools.selectedTool == CurrentTool.PENCIL || MapPaintTools.selectedTool == CurrentTool.ERASER) {
            place(e.getX() / MapTileEditor.TILE_SIZE, e.getY() / MapTileEditor.TILE_SIZE);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX() / MapTileEditor.TILE_SIZE;
        int y = e.getY() / MapTileEditor.TILE_SIZE;
        tiles.mouseLocation(x, y);
        if (x > -1 && y > -1 && x < map.getWidth() && y < map.getHeight()) {
            MapPaintTools.setMapCoordinates(x, y);
        } else {
            MapPaintTools.resetMapCoordinates();
        }
        tiles.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            //doppelklick um events zu editieren / anlegen
            if (MapPaintTools.selectedTool == CurrentTool.SELECTOR) {
                int x = e.getX() / MapTileEditor.TILE_SIZE;
                int y = e.getY() / MapTileEditor.TILE_SIZE;
                if (x > -1 && y > -1 && x < map.getWidth() && y < map.getHeight()) {
                    tiles.fastEditEventTile(x, y);
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        tiles.requestFocus();
        tiles.showPreview(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        tiles.showPreview(false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (MapPaintTools.selectedTool == CurrentTool.SELECTOR) {
                int x = e.getX() / MapTileEditor.TILE_SIZE;
                int y = e.getY() / MapTileEditor.TILE_SIZE;
                if (x > -1 && y > -1 && x < map.getWidth() && y < map.getHeight()) {
                    tiles.focusTile(x, y);
                }
            } else {
                place(e.getX() / MapTileEditor.TILE_SIZE, e.getY() / MapTileEditor.TILE_SIZE);
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            if (MapPaintTools.selectedTool == CurrentTool.SELECTOR) {
                int x = e.getX() / MapTileEditor.TILE_SIZE;
                int y = e.getY() / MapTileEditor.TILE_SIZE;
                if (x > -1 && y > -1 && x < map.getWidth() && y < map.getHeight()) {
                    tiles.openMenu(e.getX(), e.getY());
                }
            }
        }
    }

}
