package com.broll.poklmon.script;

import com.badlogic.gdx.Gdx;

public abstract class ScriptProcessingRunnable implements Runnable{

    private boolean cancelled = false;

    public abstract  void runProcess();

    @Override
    public final void run() {
        try {
            runProcess();
        } catch (CancelProcessingException e) {
            //cancelled processing
            Gdx.app.error("ScriptProcessingRunnable", "Script Process "+this+" was cancelled!");
        }
    }

    public synchronized void waitForResume() {
        try {
            wait();
        } catch (InterruptedException e) {
            if (cancelled) {
                Gdx.app.error("ScriptProcessingRunnable", "Try cancel Script Process "+this+"...");
                throw new CancelProcessingException();
            } else {
                Gdx.app.error("ScriptProcessingRunnable", "Interrupted", e);
            }
        }
    }

    void cancel() {
        this.cancelled = true;
    }

    public synchronized void resume() {
        notify();
    }

}
