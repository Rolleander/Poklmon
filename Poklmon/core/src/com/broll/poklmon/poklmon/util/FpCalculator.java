package com.broll.poklmon.poklmon.util;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.save.PoklmonData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FpCalculator {

    private DataContainer data;
    public final static int MAX_SUM = 510;
    public final static int MAX_STAT = 255;

    private final Map<Integer, Integer> evolveStage = new HashMap<Integer, Integer>();

    public FpCalculator(DataContainer data) {
        this.data = data;
        initStages();
    }

    private void initStages() {
        initNextStages(1, data.getPoklmons().getPoklmons());
    }

    private void initNextStages(int stage, List<Poklmon> poklmons) {
        List<Poklmon> nextStage = new ArrayList<Poklmon>();
        for (Poklmon poklmon : poklmons) {
            evolveStage.put(poklmon.getId(), stage);
            if (poklmon.getEvolveIntoPoklmon() != -1) {
                //has development
                nextStage.add(data.getPoklmons().getPoklmon(poklmon.getEvolveIntoPoklmon()));
            }
        }
        if (!nextStage.isEmpty()) {
            initNextStages(stage + 1, nextStage);
        }
    }

    private int getEvolveLevel(int pokl) {
        return evolveStage.get(pokl);
    }

    private int getFpSum(PoklmonData pokl) {
        int s = 0;
        for (int i = 0; i < pokl.getFp().length; i++) {
            s += pokl.getFp()[i];
        }
        return s;
    }

    public void addFps(PoklmonData pokl, int fromPoklmonId) {
        int v = getEvolveLevel(fromPoklmonId);
        int stat = fromPoklmonId % 6;
        int sum = getFpSum(pokl);
        int diff = MAX_SUM - sum;
        if (diff < v) {
            v = diff;
        }
        int sv = pokl.getFp()[stat];
        int d2 = MAX_STAT - sv;
        if (d2 < v) {
            v = d2;
        }
        pokl.getFp()[stat] += v;
    }
}
