package com.broll.poklmon.game.scene.script;

import com.badlogic.gdx.Gdx;
import com.broll.pokllib.script.syntax.VariableException;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.map.object.MapObject;

import java.util.Timer;
import java.util.TimerTask;

public abstract class CommandControl {
    protected GameManager game;
    protected MapObject object;
    private ScriptProcessInteraction interaction;
    private final static int MINIMUM_DELAY = 100;

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
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            invoke.invoke();
                        } catch (VariableException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, MINIMUM_DELAY);
        waitForResume();
    }
}
