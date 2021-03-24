package com.broll.poklmon.input;

import com.badlogic.gdx.math.Vector2;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.PoklmonGame;

/**
 * Created by Roland on 08.06.2017.
 */
public class ScreenLocationCalc {

    private static float m= (float)PoklmonGame.HEIGHT/(float)PoklmonGame.WIDTH;
    public static ObjectDirection getTouchDir(Vector2 touch){

        boolean belowLine1=belowLine1(touch);
        boolean belowLine2=belowLine2(touch);
        if(!belowLine1&&belowLine2){
            return ObjectDirection.LEFT;
        }
        if(!belowLine1&&!belowLine2){
            return ObjectDirection.DOWN;
        }
        if(belowLine1&&belowLine2){
            return ObjectDirection.UP;
        }
        if(belowLine1&&!belowLine2){
            return ObjectDirection.RIGHT;
        }
        return null;
    }

    private static boolean belowLine1(Vector2 pos){
        float x=pos.x;
        float y=pos.y;
        float ly=m*x;
        return ly>y;
    }

    private static boolean belowLine2(Vector2 pos){
        float x=pos.x;
        float y=pos.y;
        float ly=-m*x+PoklmonGame.HEIGHT;
        return ly>y;
    }
}
