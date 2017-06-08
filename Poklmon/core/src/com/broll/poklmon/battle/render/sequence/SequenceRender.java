package com.broll.poklmon.battle.render.sequence;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.util.ProcessThreadHandler;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;

public abstract class SequenceRender
{

    protected DataContainer data;
    protected BattleManager battle;
    protected ProcessThreadHandler exitListener;
    protected boolean isRunning=false;
    
    public SequenceRender(BattleManager battle)
    {
        this.battle=battle;
        this.data=battle.getData();
    }
    
    public void invoke(ProcessThreadHandler listener)
    {
        this.exitListener=listener;
        start();
        isRunning=true;
    }
    
    protected abstract void start();
    
    protected void stop()
    {
        isRunning=false;
        if(exitListener!=null)
        {
        exitListener.resume();
        }
    }
    
    public abstract void render(Graphics g);
   
    public abstract void update(float delta);
    
    public boolean isRunning()
    {
        return isRunning;
    }
}
