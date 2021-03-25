package com.broll.poklmon.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.TouchIconsRender;

public class TouchInputReceiver extends InputAdapter {

    private Viewport viewport;

    public TouchInputReceiver(Viewport viewport) {
        this.viewport = viewport;
    }

    private final static int[] WALK_KEYS = new int[]{Input.Keys.S, Input.Keys.D, Input.Keys.A, Input.Keys.W};

    private void checkWalking(Vector2 location, boolean down) {
        int code = TouchIconsRender.checkCrossPressed(location);
        if (code != -1 && down) {
            for (int walkKey : WALK_KEYS) {
                if (walkKey == code) {
                    GUIUpdate.keyDown(code);
                } else {
                    GUIUpdate.keyReleaesed(walkKey);
                }
            }
        }
        if (!down) {
            for (int walkKey : WALK_KEYS) {
                GUIUpdate.keyReleaesed(walkKey);
            }
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (PoklmonGame.TOUCH_MODE) {
            Vector2 newPoints = new Vector2(screenX, screenY);
            Vector2 location = viewport.unproject(newPoints);
            checkWalking(location, true);
            return true;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (PoklmonGame.TOUCH_MODE) {
            Vector2 newPoints = new Vector2(screenX, screenY);
            Vector2 location = viewport.unproject(newPoints);
            int code = TouchIconsRender.checkButtonPress(location);
            if (code != -1) {
                GUIUpdate.keyDown(code);
            } else {
                checkWalking(location, true);
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (PoklmonGame.TOUCH_MODE) {
            Vector2 newPoints = new Vector2(screenX, screenY);
            Vector2 location = viewport.unproject(newPoints);
            checkWalking(location, false);
        }
        return true;
    }

}
