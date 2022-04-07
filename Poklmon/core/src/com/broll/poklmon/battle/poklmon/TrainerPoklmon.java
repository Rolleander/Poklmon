package com.broll.poklmon.battle.poklmon;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonWesen;

public class TrainerPoklmon extends FightPoklmon {

    public TrainerPoklmon(Poklmon poklmon, int level) {
        super(poklmon, level);
        short[] DV = {10, 10, 10, 10, 10, 10};
        short[] FP = {50, 50, 50, 50, 50, 50};
        this.dv = DV;
        this.fp = FP;
        this.wesen = PoklmonWesen.ERNST;
        this.initStats();
    }
}
