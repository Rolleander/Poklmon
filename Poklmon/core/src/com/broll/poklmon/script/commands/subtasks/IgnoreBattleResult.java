package com.broll.poklmon.script.commands.subtasks;

import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.script.CommandControl;

public class IgnoreBattleResult implements BattleEndListener {

    private CommandControl commandControl;

    public IgnoreBattleResult(CommandControl commandControl) {
        this.commandControl = commandControl;
    }

    @Override
    public void battleWon() {
        commandControl.resume();
    }

    @Override
    public void battleLost() {
        commandControl.resume();
    }

    @Override
    public void battleEnd() {
        commandControl.resume();
    }
}
