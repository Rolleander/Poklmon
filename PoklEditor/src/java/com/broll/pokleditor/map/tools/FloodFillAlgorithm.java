package com.broll.pokleditor.map.tools;

import com.broll.pokleditor.map.history.ChangeAreasOperation;
import com.broll.pokleditor.map.history.ChangeTilesOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FloodFillAlgorithm {

    public static int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    public static int[][] cloneArray(int[][][] src, int level) {
        int length = src.length;
        int length2 = src[0].length;
        int[][] target = new int[length][length2];
        for (int i = 0; i < length; i++) {
            for (int h = 0; h < length2; h++) {
                target[i][h] = src[i][h][level];
            }
        }
        return target;
    }

    public static List<ChangeAreasOperation.AreaChange> floodFill(int[][] input, int sx, int sy, int newtile) {
        int[][] map = cloneArray(input);
        List<ChangeAreasOperation.AreaChange> edits = new ArrayList<>();
        Stack<Pixel> stack = new Stack<Pixel>();
        stack.push(new Pixel(sx, sy));
        int oldtile = map[sx][sy];
        if (oldtile == newtile) {
            return edits;
        }
        while (!stack.isEmpty()) {

            Pixel p = stack.pop();
            int x = p.x;
            int y = p.y;
            if (x > -1 && y > -1 && x < map.length && y < map[0].length) {
                int t = map[x][y];
                if (t == oldtile) {
                    map[x][y] = newtile;
                    edits.add(new ChangeAreasOperation.AreaChange(x, y, newtile));
                    stack.push(new Pixel(x, y + 1));
                    stack.push(new Pixel(x, y - 1));
                    stack.push(new Pixel(x + 1, y));
                    stack.push(new Pixel(x - 1, y));
                }
            }
        }
        return edits;
    }

    private static class Pixel {
        public int x, y;

        public Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static List<ChangeTilesOperation.TileChange> floodFill(int[][][] input, int layer, int sx, int sy, int newtile) {
        List<ChangeTilesOperation.TileChange> edits = new ArrayList<>();
        int[][] map = cloneArray(input, layer);
        Stack<Pixel> stack = new Stack<Pixel>();
        stack.push(new Pixel(sx, sy));
        int oldtile = map[sx][sy];

        if (oldtile == newtile) {
            return edits;
        }

        while (!stack.isEmpty()) {

            Pixel p = stack.pop();
            int x = p.x;
            int y = p.y;
            if (x > -1 && y > -1 && x < map.length && y < map[0].length) {
                int t = map[x][y];
                if (t == oldtile) {
                    map[x][y] = newtile;
                    edits.add(new ChangeTilesOperation.TileChange(x, y, layer, newtile));
                    stack.push(new Pixel(x, y + 1));
                    stack.push(new Pixel(x, y - 1));
                    stack.push(new Pixel(x + 1, y));
                    stack.push(new Pixel(x - 1, y));
                }
            }
        }
        return edits;
    }
}
