package com.broll.pokleditor.map;

import com.broll.pokleditor.map.control.MapControlImpl;
import com.broll.pokleditor.map.history.MapEditControl;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class MapKeyActions extends KeyAdapter {

    private final MapControlImpl control;
    private final MapEditControl edit;

    public MapKeyActions(MapEditControl edit, MapControlImpl control) {
        this.edit = edit;
        this.control = control;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            if (KeyEvent.VK_Z == e.getKeyCode()) {
                edit.undo();
            } else if (KeyEvent.VK_Y == e.getKeyCode()) {
                edit.redo();
            } else if (KeyEvent.VK_C == e.getKeyCode()) {
                control.copyObject();
            } else if (KeyEvent.VK_X == e.getKeyCode()) {
                control.cutObject();
            }
        } else {
            if (KeyEvent.VK_DELETE == e.getKeyCode()) {
                control.deleteSelectedObject();
            }
        }
    }

}
