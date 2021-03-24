package com.broll.poklmon.battle.util;

import java.awt.event.ActionListener;


public class DebugProcessThreadHandler extends ProcessThreadHandler
{
    private ActionListener action;

    public DebugProcessThreadHandler(ActionListener listener)
    {

        super(null);
        action = listener;
    }

    @Override
    public synchronized void resume()
    {
        //invoke listener
        action.actionPerformed(null);
    }
}
