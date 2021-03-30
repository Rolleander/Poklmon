package com.broll.pokleditor.resource;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.broll.pokleditor.animationdex.AnimationSpriteset;
import com.broll.pokleditor.gui.components.preview.PoklmonPreview;

public class ImageLoader {

    private static String graphicsPath;

    private static Map<String, ImageIcon> imageCache = new HashMap<>();

    public static void initPath(File file) {
        graphicsPath = file.getPath() + "/graphics/";
    }

    public static void createCache() {
        listPoklmonImages().forEach(it -> imageCache.put("poklmon_" + it, initPoklmonImage(it)));
        listCharacterImages().forEach(it -> imageCache.put("char_" + it, initCharacterImage(it)));
        PoklmonPreview.init();
    }

    public static ImageIcon loadPoklmonImage(String graphics) {
        return imageCache.get("poklmon_" + graphics);
    }

    public static ImageIcon loadCharacterImage(String graphics) {
        return imageCache.get("char_" + graphics);
    }

    public static ArrayList<String> listPoklmonImages() {
        File file = new File(graphicsPath + "poklmon");
        return list(file);
    }

    public static ArrayList<String> listCharacterImages() {
        File file = new File(graphicsPath + "chars");
        return list(file);
    }

    private static ArrayList<String> list(File file) {
        ArrayList<String> names = new ArrayList<String>();
        for (File f : file.listFiles()) {
            names.add(f.getName());
        }
        return names;
    }

    private static ImageIcon initPoklmonImage(String graphics) {
        try {
            Image image = ImageIO.read(new File(graphicsPath + "poklmon/" + graphics));
            return new ImageIcon(image.getScaledInstance(96, 96, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ImageIcon initCharacterImage(String graphics) {
        try {
            Image image = ImageIO.read(new File(graphicsPath + "chars/" + graphics));
            return new ImageIcon(image);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }


    public final static int TILE_SIZE = 16;

    public static Image[] loadTileset() {
        int count = 0;
        boolean searching = true;
        List<Image> sets = new ArrayList<Image>();
        int width = 0;
        int height = 0;
        do {
            try {
                Image image = ImageIO.read(new File(graphicsPath + "tileset" + count + ".png"));
                sets.add(image);
                int w = image.getWidth(null) / TILE_SIZE;
                int h = image.getHeight(null) / TILE_SIZE;
                if (w > width) {
                    width = w;
                }
                height += h;
                count++;
            } catch (Exception e) {
                searching = false;
            }
        } while (searching);
        Image[] tiles = new Image[width * height];
        int deltay = 0;
        for (Image image : sets) {
            int w = image.getWidth(null) / TILE_SIZE;
            int h = image.getHeight(null) / TILE_SIZE;
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    tiles[(y + deltay) * w + x] = cutImage(image, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
            deltay += h;
        }
        return tiles;
    }

    public static Image[] loadAnimationSprites() {
        try {
            Image image = ImageIO.read(new File(graphicsPath + "animations.png"));
            int w = AnimationSpriteset.WIDTH;
            int h = image.getHeight(null) / 100;
            Image[] tiles = new Image[w * h];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    tiles[y * w + x] = cutImage(image, x * 100, y * 100, 100, 100);
                }
            }
            return tiles;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

    public static Image cutImage(Image source, int x, int y, int w, int h) {
        BufferedImage bf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        bf.getGraphics().drawImage(source, -x, -y, null);
        return bf;
    }
}
