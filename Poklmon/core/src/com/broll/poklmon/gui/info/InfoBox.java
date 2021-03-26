package com.broll.poklmon.gui.info;

import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.DialogBoxRender;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.resource.GUIFonts;
import com.esotericsoftware.minlog.Log;

public class InfoBox {

    private DialogBoxRender render;
    private String text;
    private final static float X_OFFSET = 20;
    private final static float Y_OFFSET = 15;
    private float x, y;
    private boolean extendLeft;
    private float textX, textY;
    private boolean visible;
    private boolean extendUp = false;
    private FontUtils fontUtils = new FontUtils();


    public InfoBox(float x, float y, boolean extendLeft) {
        this.x = x;
        this.y = y;
        this.extendLeft = extendLeft;
        visible = false;
        render = new DialogBoxRender(GUIDesign.selectionBoxBorder, GUIDesign.selectionBoxBorder2,
                GUIDesign.selectionBoxCorner, ColorUtil.newColor(248, 248, 248));
    }

    public void setExtendUp(boolean extendUp) {
        this.extendUp = extendUp;
    }

    public void showText(String text) {
        this.text = text;
        updateSize();
        visible = true;
    }

    private void updateSize() {
        float tw = fontUtils.getWidth(GUIFonts.dialogText, text);
        float th = fontUtils.getHeight(GUIFonts.dialogText, text) * 2;
        float w = X_OFFSET * 2 + tw;
        float h = Y_OFFSET * 2 + th;
        float ax = 0;
        float ay = y;
        if (extendLeft) {
            ax = x - w;
        } else {
            ax = x;
        }
        if (extendUp) {
            ay -= h;
        }
        textX = ax + X_OFFSET;
        textY = ay + Y_OFFSET;
        render.setSize((int) ax, (int) ay, (int) w, (int) h);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void render(Graphics g) {
        if (visible) {
            render.render(g);
            g.setColor(ColorUtil.newColor(50, 50, 50));
            g.setFont(GUIFonts.dialogText);
            g.drawString(text, textX, textY);
        }
    }

    public boolean isVisible() {
        return visible;
    }
}
