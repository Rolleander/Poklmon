package com.broll.poklmon.map;

import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;

public class MapDisplay {
    public final static int TILE_SIZE = 32;
    private CurrentMap map;
    private SpriteSheet tileSet;

    private final static int HORIZONTAL_TILES = PoklmonGame.WIDTH / TILE_SIZE + 1;
    private final static int VERTICAL_TILES = PoklmonGame.WIDTH / TILE_SIZE + 1;

    public MapDisplay(DataContainer data) {
        tileSet = data.getGraphics().getTileSet();
    }

    public void setMap(CurrentMap map) {
        this.map = map;
    }

    public void renderMap(Graphics g, float x, float y, int fromLayer, int toLayer) {
        float xd = x % TILE_SIZE;
        float yd = y % TILE_SIZE;
        int xid = (int) (x / TILE_SIZE);
        int yid = (int) (y / TILE_SIZE);
        for (int i = -1; i < HORIZONTAL_TILES; i++) {
            for (int h = -1; h < VERTICAL_TILES; h++) {
                float tx = i * TILE_SIZE + xd ;
                float ty = h * TILE_SIZE + yd ;
                int tileX = -xid + i ;
                int tileY = -yid + h ;
                MapTile tile = map.getTile(tileX, tileY);
                if (tile == null) {
                    continue;
                }
                for (int l = fromLayer; l < toLayer; l++) {
                    int tileID = tile.getTileID()[l] - 1;
                    if (tileID >= 0) {
                        Image image = getTile(tileID);
                        image.draw(tx, ty, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }
    }

    private Image getTile(int tiled) {
        int x = tiled % 10;
        int y = tiled / 10;
        return tileSet.getSprite(x, y);
    }

}
