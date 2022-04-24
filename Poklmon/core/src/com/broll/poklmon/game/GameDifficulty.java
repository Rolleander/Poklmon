package com.broll.poklmon.game;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.player.control.VariableControlInterface;
import com.broll.poklmon.player.control.impl.VariableControl;
import com.broll.poklmon.poklmon.util.FpCalculator;

public final class GameDifficulty {

    public static final String[] NAMES = new String[]{"Normal", "Einfach", "Schwer", "Brutal"};
    public final static int DIFFICULTY_NORMAL = 0;
    public final static int DIFFICULTY_EASY = 1;
    public final static int DIFFICULTY_HARD = 2;
    public final static int DIFFICULTY_BRUTAL = 3;

    private static int getDifficulty(VariableControl variableControl) {
        return variableControl.getInt(Player.DIFFICULTY_ID);
    }

    public static short[] getTrainerPoklmonDV(VariableControl variableControl) {
        short dv = 10;
        switch (getDifficulty(variableControl)) {
            case DIFFICULTY_EASY:
                dv = 0;
                break;
            case DIFFICULTY_HARD:
                dv = 15;
                break;
            case DIFFICULTY_BRUTAL:
                dv = 25;
                break;
        }
        return getStatArray(dv);
    }

    public static short[] getTrainerPoklmonFP(VariableControl variableControl) {
        short fp = 50;
        switch (getDifficulty(variableControl)) {
            case DIFFICULTY_EASY:
                fp = 0;
                break;
            case DIFFICULTY_HARD:
                fp = 100;
                break;
            case DIFFICULTY_BRUTAL:
                fp = FpCalculator.MAX_STAT;
                break;
        }
        return getStatArray(fp);
    }

    private static short[] getStatArray(short value) {
        short[] array = new short[6];
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
        return array;
    }

    public static float getExpMultiplicator(VariableControl variableControl) {
        switch (getDifficulty(variableControl)) {
            case DIFFICULTY_EASY:
                return 1.33f;
            case DIFFICULTY_BRUTAL:
                return 0.77f;
        }
        return 1;
    }
}
