package com.broll.poklmon.gui.selection;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.resource.GUIDesign;

public class ScrollableSelectionContext {

    private int startPos, selectPos;
    private int items, window;
    private int border;
    private DataContainer data;
    private boolean allowJumping = false;

    public ScrollableSelectionContext(DataContainer data, int items, int window) {
        this.items = items;
        this.data = data;
        this.window = window;
        this.startPos = 0;
        this.selectPos = 0;
        this.border = Math.min(window, items);
    }

    public void setAllowJumping(boolean allowJumping) {
        this.allowJumping = allowJumping;
    }

    public boolean isWindowTop() {
        return startPos == 0;
    }

    public boolean isWindowBot() {
        if (items > window) {
            if (startPos == items - window) {
                return true;
            }
            return false;
        }
        return true;
    }

    public void moveUp() {
        data.getSounds().playSound(GUIDesign.MOVE_SOUND);
        selectPos--;
        if (selectPos < 0) {
            selectPos = 0;
            moveWindowUp();
        }
    }

    public void jumpUp() {
        data.getSounds().playSound(GUIDesign.MOVE_SOUND);
        for (int i = 0; i < window; i++) {
            moveWindowUp();
        }
    }

    private void moveWindowUp() {
        startPos--;
        if (startPos < 0) {
            startPos = items - window;
            if (items <= window) {
                startPos = 0;
                selectPos = items - 1;
            } else {
                selectPos = window - 1;
            }
        }
    }

    public void moveDown() {
        data.getSounds().playSound(GUIDesign.MOVE_SOUND);
        selectPos++;
        if (selectPos > border - 1) {
            selectPos = border - 1;
            moveWindowDown();
        }
    }

    public void jumpDown() {
        data.getSounds().playSound(GUIDesign.MOVE_SOUND);
        for (int i = 0; i < window; i++) {
            moveWindowDown();
        }
    }

    private void moveWindowDown() {
        startPos++;
        if (startPos + border > items) {
            startPos = 0;
            selectPos = 0;
        }
    }

    public void update() {
        if (GUIUpdate.isMoveDown()) {
            moveDown();
        } else if (GUIUpdate.isMoveUp()) {
            moveUp();
        }
        if (allowJumping) {
            if (GUIUpdate.isMoveLeft()) {
                jumpUp();
            } else if (GUIUpdate.isMoveRight()) {
                jumpDown();
            }
        }
    }

    public int getSelectedIndex() {
        return startPos + selectPos;
    }

    public int getSelectPos() {
        return selectPos;
    }

    public int getStartPos() {
        return startPos;
    }
}
