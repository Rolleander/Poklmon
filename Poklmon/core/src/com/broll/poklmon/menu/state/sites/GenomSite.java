package com.broll.poklmon.menu.state.sites;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.menu.state.StateSite;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.PoklmonData;

public class GenomSite extends SpikesStateSite {

    // public static Color[] attributeColors={ColorUtil.newColor(20,200,75),new
    // Color(220,70,70),ColorUtil.newColor(40,90,240),ColorUtil.newColor(220,80,240),new
    // Color(80,220,250),ColorUtil.newColor(255,255,165)};

    public GenomSite(Poklmon poklmonInfo, PoklmonData poklmon, DataContainer data) {
        super(poklmonInfo, poklmon, data);

    }

    private int sum;
    private short[] dv;

    @Override
    protected void initData() {
        this.lineWidth = 300;
        dv = poklmon.getDv();

        sum = 0;

        for (short s : dv) {
            sum += s;
        }
        initGraphes(dv, (short) 31);
    }

    @Override
    public void render(Graphics g, float x, float y) {

        updateGraphes();
        this.x = x;
        this.y = y;

        g.setFont(GUIFonts.hudText);

        lines = 0;

        renderLine(g, "Genomstärke", "" + sum);

        this.x += 40;
        this.y += 50;

        g.setColor(ColorUtil.newColor(0, 0, 0, 50));
        float w = (GRAPH_WIDTH + GRAPH_SPACING) * dv.length - GRAPH_SPACING;

        g.fillRect(this.x - 10, this.y, w + 20, GRAPH_HEIGHT);

        for (int i = 0; i < dv.length; i++) {
            renderGraph(g, i);
        }
    }


}
