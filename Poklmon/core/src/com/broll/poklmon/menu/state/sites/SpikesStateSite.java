package com.broll.poklmon.menu.state.sites;

import com.badlogic.gdx.graphics.Color;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.menu.state.StateSite;
import com.broll.poklmon.save.PoklmonData;

/**
 * Created by Roland on 31.10.2017.
 */

public abstract class SpikesStateSite extends StateSite {

    protected final static int GRAPH_WIDTH = 40;
    protected final static int GRAPH_SPACING = 25;
    protected final static int GRAPH_HEIGHT = 300;
    protected float[] currentValues;
    protected short[] targetValues;


    public SpikesStateSite(Poklmon poklmonInfo, PoklmonData poklmon, DataContainer data) {
        super(poklmonInfo, poklmon, data);
    }

    protected void initGraphes(short[] targetValues){
        this.targetValues=targetValues;
        this.currentValues=new float[targetValues.length];
    }

    protected void updateGraphes(){
        for(int i=0; i<targetValues.length; i++){
            if(currentValues[i]<targetValues[i]) {
                currentValues[i]+=1f;
            }
        }
    }


    protected void renderGraph(Graphics g, int nr) {
        float h = GRAPH_HEIGHT * ( currentValues[nr]/ 31f);
        float ypos = y + GRAPH_HEIGHT - h;

        SpriteSheet sprite= data.getGraphics().getMenuGraphicsContainer().getDnaspikes();
        Image image = sprite.getSprite(nr, 0).getSubImage(40*nr, 0, 40, (int) h);
        float perc=currentValues[nr]/(float)targetValues[nr];
        if(perc<1){
            image.draw(x, ypos,new Color(1,1,1,perc));
        }
        else{
            image.draw(x, ypos);
        }
        x += GRAPH_WIDTH + GRAPH_SPACING;
    }
}
