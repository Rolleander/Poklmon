package com.broll.poklmon.battle.poklmon.extra;


import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;

public class RarePoklmonCalc {
    public static final double powerfulPercent = 0.51;

    public static double TARGET = 1;
    public static  int MAX = 10;

	/**
	 *
	 *    mehr würfel
	 *    	- > schwieriger wenn   > 0.5 to reach of max
	 *    - >  einfacher wenn < 0.5	to reach of max
	 *
	 *    augenzahl
	 *    - > mehr wird immer schwieriger
	 *
	 *  anzahl der würfel in der potenz
	 * 	log kurve
	 *
	 * 	  Anzahl Würfel   W
	 * 	  Augenzahl       A
	 * 	  Wurfereignis    E  : zufällig von 1 - A
	 * 	  Summe    S   =  summe über W  ereignise  E
	 *
	 *	  Wahrscheinlichkeit      S  >=   X
	 *
	 *
	 */

	public static void main(String[] args) {
		//32
		//24 / 31

		System.out.println("limit: "+(MAX*powerfulPercent));

		int W = 1000;
		MAX = 10;

		int steps = 20;
		for(int s = 0; s<steps; s++) {
			TARGET=   (MAX / (double)steps ) * s  ;
			int count = 1000000;
			int success = 0;
			for (int i = 0; i < count; i++) {
				double[] dv = new double[W];
				for (int k = 0; k < dv.length; k++)
				{
					dv[k] = (Math.random() * MAX +1);
				}
				if (RarePoklmonCalc.hasPowerfulGenes(dv)) {
					success++;
				}
			}
			double percent = (((double)success / (double)count) * 100);
			System.out.println( percent );
//			System.out.println(h+" "+success + " / " + count + " => " + percent + "%" + "  " + (100 / percent));
		}
	//	System.out.println(CaughtPoklmonMeasurement.generateRandomDVs().length * 31 * powerfulPercent);
      //  System.out.println(hasPowerfulGenes(new short[]{27,27,27,27,27,25}));
	}

    public static boolean hasPowerfulGenes(double[] dv) {
		double maxValue = dv.length * MAX;
		double isValue = 0;
        for (double s : dv) {
            isValue += s;
        }

        return isValue >= TARGET * dv.length;
    }

}
