package com.broll.poklmon.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.resource.MenuGraphics;
import com.esotericsoftware.minlog.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roland on 08.06.2017.
 */
public class TouchIconsRender {

    private static List<TouchButton> buttons = new ArrayList<TouchButton>();

    private static SpriteBatch sb;
    private static Viewport viewport;

    private static float buttonSize;

    public static void init(SpriteBatch sb, Viewport viewport) {
        TouchIconsRender.sb = sb;
        TouchIconsRender.viewport = viewport;
        float border = 5;
        buttons.add(new TouchButton(0, Input.Keys.O, new Vector2()));
        buttons.add(new TouchButton(1, Input.Keys.P, new Vector2()));
        buttons.add(new TouchButton(2, Input.Keys.K, new Vector2()));
        buttons.add(new TouchButton(3, Input.Keys.L, new Vector2()));
        buttons.add(new TouchButton(4, Input.Keys.W, new Vector2()));
        buttons.add(new TouchButton(5, Input.Keys.D, new Vector2()));
        buttons.add(new TouchButton(6, Input.Keys.A, new Vector2()));
        buttons.add(new TouchButton(7, Input.Keys.S, new Vector2()));

        //  hideButton(2, true);
        //   hideButton(3, true);
    }

    public static void hideButton(int nr, boolean hide) {
        buttons.get(nr).setHide(hide);
    }

    public static int checkButtonPress(Vector2 pos) {
        for (int i=0; i<4; i++) {
            TouchButton button = buttons.get(i);
            Vector2 buttonPos = button.getPosition();
            if (pos.dst(buttonPos) <= buttonSize / 2) {
                return button.getKeyCode();
            }
        }
        return -1;
    }

    public static int checkCrossPressed(Vector2 pos) {
        for (int i=4; i<8; i++) {
            TouchButton button = buttons.get(i);
            Vector2 buttonPos = button.getPosition();
            if (pos.dst(buttonPos) <= buttonSize / 2) {
                return button.getKeyCode();
            }
        }
        return -1;
    }

    public static void render() {
        if (MenuGraphics.touchIcons == null) {
            return;
        }
        buttonSize = viewport.getScreenHeight() / 8f;

        float w = viewport.getScreenWidth() / 2 - buttonSize * 0.5f;
        float h = -buttonSize * 0.5f;
        float d = buttonSize * 1.2f;
        buttons.get(0).getPosition().set(w, h + d / 1.5f);
        buttons.get(1).getPosition().set(w - d, h + d / 1.5f);
        buttons.get(2).getPosition().set(w, h - d / 1.5f);
        buttons.get(3).getPosition().set(w - d, h - d / 1.5f);

        d = buttonSize / 1.5f;
        w = -w + buttonSize * 0.7f;
        buttons.get(4).getPosition().set(w, h + d);
        buttons.get(5).getPosition().set(w + d, h);
        buttons.get(6).getPosition().set(w - d, h);
        buttons.get(7).getPosition().set(w, h - d);
        viewport.getCamera().update();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.begin();
        for (TouchButton button : buttons) {
            if (!button.isHide()) {
                int nr = button.getImageNr();
                Vector2 pos = button.getPosition();
                Texture texture = MenuGraphics.touchIcons.getSprite(0, 0).getTexture();
                TextureRegion region = new TextureRegion(texture, 64 * nr, 0, 64, 64);
                sb.draw(region, pos.x - buttonSize / 2, pos.y - buttonSize / 2, buttonSize, buttonSize);
                //   .drawCentered(pos.x, pos.y, button.getSize(), button.getSize());
            }
        }
        sb.end();
    }


    private static class TouchButton {
        private int keyCode;
        private float size = 64;
        private Vector2 position;
        private int imageNr;
        private boolean hide = false;

        public TouchButton(int nr, int code, Vector2 pos) {
            this.keyCode = code;
            this.size = size;
            this.imageNr = nr;
            this.position = pos;
        }

        public boolean isHide() {
            return hide;
        }

        public void setHide(boolean hide) {
            this.hide = hide;
        }


        public int getImageNr() {
            return imageNr;
        }

        public int getKeyCode() {
            return keyCode;
        }

        public Vector2 getPosition() {
            return position;
        }
    }
}
