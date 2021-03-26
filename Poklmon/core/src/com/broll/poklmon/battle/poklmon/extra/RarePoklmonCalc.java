package com.broll.poklmon.battle.poklmon.extra;


import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;

public class RarePoklmonCalc {
    public static final float powerfulPercent = 0.86f;

    /*
	public static void main(String[] args) {

		int count = 10000000;
		int success = 0;
		for (int i = 0; i < count; i++) {

			short[] dv = CaughtPoklmonMeasurement.generateRandomDVs();
			if (RarePoklmonCalc.hasPowerfulGenes(dv)) {
				success++;
			}
		}
		float percent = (((float)success/(float)count)*100);
		System.out.println(success + " / " + count+" => "+percent+"%"+ "  "+(100/percent));
		System.out.println(CaughtPoklmonMeasurement.generateRandomDVs().length * 31 * powerfulPercent);
        System.out.println(hasPowerfulGenes(new short[]{27,27,27,27,27,25}));
	}
*/
    public static boolean hasPowerfulGenes(short[] dv) {
        float maxValue = dv.length * 31;
        float isValue = 0;
        for (short s : dv) {
            isValue += s;
        }

        float perc = isValue / maxValue;
        return perc >= powerfulPercent;
    }

}
