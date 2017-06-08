package com.broll.pokleditor.map.tools;

import java.awt.Rectangle;
import java.util.Stack;

public class FloodFillAlgorithm
{

    public static void floodFill(int[][] map, int sx, int sy, int newtile)
    {
        Stack<Pixel> stack = new Stack<Pixel>();
        stack.push(new Pixel(sx, sy));
        int oldtile = map[sx][sy];

        if (oldtile == newtile)
        {
            return;
        }

        while (!stack.isEmpty())
        {

            Pixel p = stack.pop();
            int x = p.x;
            int y = p.y;
            if (x > -1 && y > -1 && x < map.length && y < map[0].length)
            {

                int t = map[x][y];
                if (t == oldtile)
                {
                    map[x][y] = newtile;
                    stack.push(new Pixel(x, y + 1));
                    stack.push(new Pixel(x, y - 1));
                    stack.push(new Pixel(x + 1, y));
                    stack.push(new Pixel(x - 1, y));
                }
            }
        }

    }

    private static class Pixel
    {
        public int x, y;

        public Pixel(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
