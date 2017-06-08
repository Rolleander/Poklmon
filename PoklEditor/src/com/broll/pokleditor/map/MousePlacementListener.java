package com.broll.pokleditor.map;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPopupMenu;

import com.broll.pokleditor.map.control.PopupMenu;
import com.broll.pokleditor.map.tileset.AreaSetPanel;
import com.broll.pokleditor.map.tileset.TileSetPanel;
import com.broll.pokleditor.map.tools.CurrentTool;
import com.broll.pokleditor.map.tools.FloodFillAlgorithm;
import com.broll.pokleditor.map.tools.MapPaintTools;
import com.broll.pokleditor.panel.Toolbar;
import com.broll.pokllib.map.MapData;

public class MousePlacementListener implements MouseListener, MouseMotionListener
{

    private MapData map;
    private MapTileEditor tiles;

    public MousePlacementListener(MapTileEditor tiles)
    {
        this.tiles = tiles;
    }

    public void setMap(MapData map)
    {
        this.map = map;
    }

    private void place(int x, int y)
    {

        if (MapPaintTools.selectedTool == CurrentTool.PENCIL)
        {
            for (int sx = 0; sx < TileSetPanel.selection.getWidth(); sx++)
            {
                for (int sy = 0; sy < TileSetPanel.selection.getHeight(); sy++)
                {
                    int idx = TileSetPanel.selection.getTilex() + sx;
                    int idy = TileSetPanel.selection.getTiley() + sy;

                    int xpos = x + sx;
                    int ypos = y + sy;
                    if (xpos > -1 && ypos > -1)
                    {
                        if (xpos < map.getWidth() && ypos < map.getHeight())
                        {
                            if (MapPanel.isAreaPanelSelected())
                            {
                                map.getAreas()[xpos][ypos] = AreaSetPanel.areaSelection;
                            }
                            else
                            {
                                int id = idy * 10 + idx + 1;
                                map.getTiles()[xpos][ypos] = id;
                            }
                        }
                    }
                }
            }
        }
        else if (MapPaintTools.selectedTool == CurrentTool.ERASER)
        {
            if (MapPanel.isAreaPanelSelected())
            {
                map.getAreas()[x][y] = 0;
            }
            else
            {
                map.getTiles()[x][y] = 0;
            }
        }
        else if (MapPaintTools.selectedTool == CurrentTool.FLOODFILL)
        {
            if (MapPanel.isAreaPanelSelected())
            {
                FloodFillAlgorithm.floodFill(map.getAreas(), x, y, AreaSetPanel.areaSelection);
            }
            else
            {
                int idx = TileSetPanel.selection.getTilex();
                int idy = TileSetPanel.selection.getTiley();
                int id = idy * 10 + idx + 1;
                FloodFillAlgorithm.floodFill(map.getTiles(), x, y, id);
            }
        }

        tiles.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {

        if (MapPaintTools.selectedTool == CurrentTool.PENCIL || MapPaintTools.selectedTool == CurrentTool.ERASER)
        {
            place(e.getX() / MapTileEditor.TILE_SIZE, e.getY() / MapTileEditor.TILE_SIZE);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        int x = e.getX() / MapTileEditor.TILE_SIZE;
        int y = e.getY() / MapTileEditor.TILE_SIZE;
        if (x > -1 && y > -1 && x < map.getWidth() && y < map.getHeight())
        {
            MapPaintTools.setMapCoordinates(x, y);
        }
        else
        {
            MapPaintTools.resetMapCoordinates();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(e.getClickCount()==2)
        {
            //doppelklick um events zu editieren / anlegen
            if (MapPaintTools.selectedTool == CurrentTool.SELECTOR)
            {
                int x = e.getX() / MapTileEditor.TILE_SIZE;
                int y = e.getY() / MapTileEditor.TILE_SIZE;
                if (x > -1 && y > -1 && x < map.getWidth() && y < map.getHeight())
                {
                    tiles.fastEditEventTile(x,y);
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            if (MapPaintTools.selectedTool == CurrentTool.SELECTOR)
            {
                int x = e.getX() / MapTileEditor.TILE_SIZE;
                int y = e.getY() / MapTileEditor.TILE_SIZE;
                if (x > -1 && y > -1 && x < map.getWidth() && y < map.getHeight())
                {
                    tiles.focusTile(x,y);
                }
            }
            else
            {
                place(e.getX() / MapTileEditor.TILE_SIZE, e.getY() / MapTileEditor.TILE_SIZE);
            }
        }
        else if (e.getButton() == MouseEvent.BUTTON3)
        {
            if (MapPaintTools.selectedTool == CurrentTool.SELECTOR)
            {
                int x = e.getX() / MapTileEditor.TILE_SIZE;
                int y = e.getY() / MapTileEditor.TILE_SIZE;
                if (x > -1 && y > -1 && x < map.getWidth() && y < map.getHeight())
                {
                    tiles.openMenu(e.getX(), e.getY());
                }
            }
        }
    }

}
