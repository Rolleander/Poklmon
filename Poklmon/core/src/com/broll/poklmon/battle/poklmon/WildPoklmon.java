package com.broll.poklmon.battle.poklmon;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;

public class WildPoklmon extends FightPoklmon {

    public WildPoklmon(Poklmon poklmon, int level) {
        super(poklmon, level);
        short[] FP = {0, 0, 0, 0, 0, 0};
        this.dv = CaughtPoklmonMeasurement.generateRandomDVs();
        this.fp = FP;
        this.wesen = CaughtPoklmonMeasurement.getRandomWesen();
        this.initStats();
    }
}
