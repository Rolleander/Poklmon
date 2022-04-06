package com.broll.poklmon.script;

import com.badlogic.gdx.Gdx;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.map.object.MapObject;

import java.util.Timer;
import java.util.TimerTask;

public abstract class CommandControl {
    protected GameManager game;
    protected MapObject object;
    private ScriptProcessInteraction interaction;
    private final static float MINIMUM_DELAY = 0.1f;

    public CommandControl(GameManager game) {
        this.game = game;
    }

    public void init(MapObject object, ScriptProcessInteraction interaction) {
        this.object = object;
        this.interaction = interaction;
    }

    public void waitForResume() {
        interaction.waitForResume();
    }

    public void resume() {
        interaction.resume();
    }

    public void invoke(final Invoke invoke) {
        TimerUtils.timerTask(MINIMUM_DELAY, new Runnable() {
            @Override
            public void run() {
                try {
                    invoke.invoke();
                } catch (Exception e) {
                    Gdx.app.error("CommandControl","InvokeExcpetion",e);
                }
            }
        });
        waitForResume();
    }
}
