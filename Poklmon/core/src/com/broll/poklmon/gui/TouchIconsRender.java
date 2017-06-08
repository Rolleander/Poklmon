package com.broll.poklmon.gui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.resource.MenuGraphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roland on 08.06.2017.
 */
public class TouchIconsRender {

    private static List<TouchButton> buttons=new ArrayList<TouchButton>();

    public static void init(){
        float border=5;
        float size=64;
        buttons.add(new TouchButton(size,0, Input.Keys.O,new Vector2(PoklmonGame.WIDTH-border-size/2,PoklmonGame.HEIGHT-border-size/2)));
        buttons.add(new TouchButton(size,1, Input.Keys.P,new Vector2(border+size/2,PoklmonGame.HEIGHT-border-size/2)));
        buttons.add(new TouchButton(size,2, Input.Keys.K,new Vector2(PoklmonGame.WIDTH-border-size/2,border+size/2)));
        buttons.add(new TouchButton(size,3, Input.Keys.L,new Vector2(border+size/2,border+size/2)));

    }

    public static int checkButtonPress(Vector2 pos){
        for(TouchButton button: buttons){
            Vector2 buttonPos=button.getPosition();
            if(pos.dst(buttonPos)<=button.getSize()/2) {
                return button.getKeyCode();
            }
        }
        return -1;
    }

    public static void render(Graphics g){
        for(TouchButton button: buttons){
            int nr=button.getImageNr();
            Vector2 pos=button.getPosition();
            MenuGraphics.touchIcons.getSprite(nr,0).drawCentered(pos.x, pos.y, button.getSize(), button.getSize());
        }
    }


    private static class TouchButton {
        private int keyCode;
        private float size=64;
        private Vector2 position;
        private int imageNr;

        public TouchButton(float size,int nr,int code, Vector2 pos){
            this.keyCode=code;
            this.size=size;
            this.imageNr=nr;
            this.position=pos;
        }

        public float getSize() {
            return size;
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
