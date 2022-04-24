package com.broll.poklmon.battle.poklmon;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.game.GameDifficulty;
import com.broll.poklmon.player.control.VariableControlInterface;
import com.broll.poklmon.player.control.impl.VariableControl;

public class TrainerPoklmon extends FightPoklmon {

    public TrainerPoklmon(VariableControl variableControl, Poklmon poklmon, int level) {
        super(poklmon, level);
        this.dv = GameDifficulty.getTrainerPoklmonDV(variableControl);
        this.fp = GameDifficulty.getTrainerPoklmonFP(variableControl);
        this.wesen = PoklmonWesen.ERNST;
        this.initStats();
    }
}
