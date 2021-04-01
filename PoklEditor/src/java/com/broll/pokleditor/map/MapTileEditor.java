package com.broll.pokleditor.map;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.map.control.MapControlImpl;
import com.broll.pokleditor.map.control.PopupMenu;
import com.broll.pokleditor.map.dialog.ObjectEditDialog;
import com.broll.pokleditor.map.history.ChangeAreasOperation;
import com.broll.pokleditor.map.history.ChangeTilesOperation;
import com.broll.pokleditor.map.history.MapEditControl;
import com.broll.pokleditor.map.objects.MapObjectGenerator;
import com.broll.pokleditor.map.objects.ObjectType;
import com.broll.pokleditor.map.objects.ObjectUtil;
import com.broll.pokleditor.map.tileset.AreaColors;
import com.broll.pokleditor.map.tileset.AreaSetPanel;
import com.broll.pokleditor.map.tileset.TileSetPanel;
import com.broll.pokleditor.map.tools.CurrentTool;
import com.broll.pokleditor.map.tools.MapPaintTools;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.map.MapData;
import com.broll.pokllib.object.MapObject;

public class MapTileEditor extends JPanel {

    private MapData map;
    public final static int TILE_SIZE = 32;
    public static boolean showGrid = false;
    public static boolean showEvents = true;

    public static Image[] tiles;
    private Image collisionTile, eventTile;
    private MousePlacementListener mouse;
    private JPopupMenu menu;
    private MapControlImpl control;
    private ObjectEditDialog editObject = new ObjectEditDialog();
    private MapEditControl mapEdit = new MapEditControl(this);
    private AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
    private AlphaComposite previewComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
    private AlphaComposite areaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f);
    private AlphaComposite defaultComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
    private int mouseX, mouseY;
    private boolean preview;

    public MapTileEditor() {
        collisionTile = GraphicLoader.loadImage("collisionTile.png");
        eventTile = GraphicLoader.loadImage("eventTile.png");
        control = new MapControlImpl(this, mapEdit);
        mouse = new MousePlacementListener(this, mapEdit);
        this.setBackground(MapEditorPanel.background);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        setFocusable(true);
        addKeyListener(new MapKeyActions(mapEdit, control));
        menu = PopupMenu.createPopupmenu(control);
    }

    public void setMap(MapData map) {
        this.map = map;
        mouse.setMap(map);
        control.setMap(map);
        mapEdit.init(map);
        updateSize();
        this.requestFocusInWindow();
    }

    public void mouseLocation(int x, int y) {
        this.mouseX = x;
        this.mouseY = y;
    }

    public void openEditDialog(MapObject object) {
        editObject.open(object);
    }

    public void fastEditEventTile(int x, int y) {
        control.setSelectedTile(x, y);
        MapObject select = ObjectUtil.findObjectAt(map.getFile().getObjects(), x, y);
        if (select == null) {
            // new event wizard
            String[] newTypes = new String[ObjectType.values().length];
            int nr = 0;
            for (ObjectType type : ObjectType.values()) {
                newTypes[nr] = type.name();
                nr++;
            }
            int selected = JOptionPane.showOptionDialog(EditorWindow.frame, "Create new Event", "New Event",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, newTypes, 0);
            ObjectType create = ObjectType.values()[selected];
            MapObject newobject = MapObjectGenerator.openWizard(create);
            control.addObject(newobject);
            if (create != ObjectType.TELEPORTER && create != ObjectType.LEDGE && create != ObjectType.REMOTE && create != ObjectType.MAPTILE) {
                control.editObject();
            }
        } else {
            // edit event
            control.editObject();
        }
    }

    public void focusTile(int x, int y) {
        control.setSelectedTile(x, y);
        repaint();
    }

    public void openMenu(int x, int y) {
        int xpos = x / TILE_SIZE;
        int ypos = y / TILE_SIZE;
        focusTile(xpos, ypos);
        MapObject select = ObjectUtil.findObjectAt(map.getFile().getObjects(), xpos, ypos);
        PopupMenu.activateObjectOptions(select != null);
        menu.show(this, x, y);
    }

    public void updateSize() {
        int w = map.getWidth() * TILE_SIZE;
        int h = map.getHeight() * TILE_SIZE;
        this.setPreferredSize(new Dimension(w, h));
        revalidate();
    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        int width = map.getWidth();
        int height = map.getHeight();
        g.setColor(new Color(100, 100, 100));
        g.fillRect(0, 0, width * TILE_SIZE, height * TILE_SIZE);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(defaultComposite);
        for (int i = 0; i < MapData.LAYERS; i++) {
            if (MapPaintTools.hideOtherLayers && !MapPanel.isAreaPanelSelected()) {
                if (i == MapPaintTools.SELECTED_LAYER) {
                    g2d.setComposite(defaultComposite);
                } else {
                    g2d.setComposite(alphaComposite);
                }
            }
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int xp = x * TILE_SIZE;
                    int yp = y * TILE_SIZE;
                    int tile = map.getTiles()[x][y][i];
                    if (tile > 0) {
                        g.drawImage(tiles[tile - 1], xp, yp, TILE_SIZE, TILE_SIZE, null);
                    }
                    if (showGrid) {
                        g.setColor(new Color(50, 50, 50));
                        g.drawRect(xp, yp, TILE_SIZE - 1, TILE_SIZE - 1);
                    }
                }
            }
        }
        if(MapPanel.isAreaPanelSelected()){
            g2d.setComposite(areaComposite);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int xp = x * TILE_SIZE;
                    int yp = y * TILE_SIZE;
                    int area = map.getAreas()[x][y];
                    if (area == 1) {
                        // collision
                        g.drawImage(collisionTile, xp, yp, null);
                    } else if (area > 1) {
                        // area
                        g.setColor(AreaColors.getAreaColor(area - 2));
                        g.fillRect(xp, yp, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }
        g2d.setComposite(defaultComposite);
        FontMetrics fm = g.getFontMetrics();

        if (map.getFile().getObjects() != null) {
            for (MapObject object : map.getFile().getObjects()) {
                int xp = object.getXpos() * TILE_SIZE;
                int yp = object.getYpos() * TILE_SIZE;
                g.drawImage(eventTile, xp, yp, null);
                int id = object.getObjectID();
                String txt = "" + id;

                int tx = xp + TILE_SIZE / 2 - fm.stringWidth(txt) / 2;
                int ty = yp + 20;
                g.setColor(new Color(0, 0, 0));
                int b = 1;
                g.drawString(txt, tx - b, ty - b);
                g.drawString(txt, tx - b, ty);
                g.drawString(txt, tx + b, ty);
                g.drawString(txt, tx, ty - b);
                g.drawString(txt, tx, ty + b);
                g.drawString(txt, tx + b, ty + b);

                g.setColor(new Color(250, 250, 250));
                g.drawString(txt, tx, ty);

            }
        }
        if (MapPaintTools.selectedTool == CurrentTool.SELECTOR) {
            int x = control.getSelectionx() * TILE_SIZE;
            int y = control.getSelectiony() * TILE_SIZE;
            g.setColor(new Color(0, 0, 0));
            g.drawRect(x, y, TILE_SIZE - 1, TILE_SIZE - 1);
            g.setColor(new Color(1f, 1f, 1f));
            g.drawRect(x + 1, y + 1, TILE_SIZE - 3, TILE_SIZE - 3);
        } else if (MapPaintTools.selectedTool == CurrentTool.PENCIL && !MapPanel.isAreaPanelSelected() && preview) {
            //placement preview
            int x = mouseX;
            int y = mouseY;
            g2d.setComposite(previewComposite);
            for (int sx = 0; sx < TileSetPanel.selection.getWidth(); sx++) {
                for (int sy = 0; sy < TileSetPanel.selection.getHeight(); sy++) {
                    int idx = TileSetPanel.selection.getTilex() + sx;
                    int idy = TileSetPanel.selection.getTiley() + sy;
                    int xpos = x + sx;
                    int ypos = y + sy;
                    if (xpos > -1 && ypos > -1) {
                        if (xpos < map.getWidth() && ypos < map.getHeight()) {
                            int id = idy * 10 + idx + 1;
                            g.drawImage(tiles[id - 1], xpos * TILE_SIZE, ypos * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        }
                    }
                }
            }
        }
    }

    public void save() {
        // write tile string into map file
        String tiles = map.getTileData();
        map.getFile().setMapTiles(tiles);
    }


    public void showPreview(boolean b) {
        this.preview = b;
        repaint();
    }
}
