package com.broll.poklmon.main.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.player.Player;

/**
 * Created by Roland on 28.10.2017.
 */

public class ExceptionState extends GameState {

    private Exception exception;
    private String exceptionState;
    private boolean canSave;
    private Player player;
    private Viewport viewport;
    BitmapFont font;

    public ExceptionState(Viewport viewport) {
        this.viewport=viewport;
    }

    public void setException(String exceptionState, Exception exception) {
        this.exception = exception;
        this.exceptionState=exceptionState;
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onEnter() {
        clickWait=160; // wait x updates
        font=new BitmapFont(true);
        Gdx.input.setInputProcessor(new InputAdapter());
        canSave=false;
        /*  try {
            MapState gameState= (MapState) states.getState(MapState.class);
            GameManager gameManager=gameState.getGameInstance();
            if(gameManager!=null){
               player= gameManager.getPlayer();
                if(player!=null){
                    canSave=true;
                }
            }
        }catch (Exception e){
        }*/
    }

    @Override
    public void onExit() {

    }

    public void closeGame(){
        font.dispose();
        Gdx.app.exit();
    }

    float clickWait;

    @Override
    public void update(float delta) {
        if(clickWait<=0) {
            if (Gdx.input.isTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.input.getY();
                Vector2 touch = viewport.unproject(new Vector2(x, y));
                if (touch.y > 560) {
                    if (touch.x < 350) {
                        closeGame();
                    } else {
                        if (canSave) {
                            player.saveGame();
                            closeGame();
                        }
                    }
                }
            }
        }
        else{
            clickWait--;
        }
    }

    @Override
    public void render(Graphics g) {
        SpriteBatch sb=g.getSpriteBatch();
        float y=15;
        font.setColor(Color.WHITE);
        font.draw(sb,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>[POKLMON GAME ERROR]<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",10,y);
        y+=25;
        font.setColor(Color.RED);
        font.draw(sb,"Exception occured in state ["+exceptionState+"]",10,y);
        y+=15;
        float x=30;
        font.draw(sb,exception.getClass().getName()+": "+exception.getMessage(),x,y);
        y+=15;
        for (int i = 0; i < Math.min(exception.getStackTrace().length,26); i++) {
            String text="  at "+exception.getStackTrace()[i].toString();
            if(i==25){
                text="...";
            }
            font.draw(sb,text,x,y);
            y+=15;
        }
        y=570;
        if(clickWait<=0) {
            font.setColor(Color.GREEN);
            font.draw(sb, "[EXIT GAME]", x, y);
            if (canSave) {
                x = 400;
                font.draw(sb, "[SAVE GAME AND EXIT]", x, y);
            }
        }
    }
}
