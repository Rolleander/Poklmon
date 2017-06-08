package com.broll.poklmon.gui.dialog;

import java.util.Timer;
import java.util.TimerTask;

public class DialogTimer
{
    private Timer timer;
    private TimerListener listener;
    private int time;

    public DialogTimer(TimerListener listener)
    {
        this.listener = listener;
    }

    public void cancel()
    {
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }
    }

    public void start(float seconds)
    {
        time = (int)(seconds * 1000);
        timer = new Timer();
        timer.schedule(new EndInvoke(), time);

    }

    public void startAgain()
    {
        timer = new Timer();
        timer.schedule(new EndInvoke(), time);
    }

    private class EndInvoke extends TimerTask
    {

        @Override
        public void run()
        {

            listener.timerStopped();
        }
    }

}
