package com.broll.poklmon.poklmon.util;

import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.save.PoklmonData;

public class FpCalculator {

	private DataContainer data;
	public final static int MAX_SUM = 510;
	public final static int MAX_STAT = 255;

	public FpCalculator(DataContainer data) {
		this.data = data;
	}

	private int getEvolveLevel(int pokl) {
		Poklmon poklmon = data.getPoklmons().getPoklmon(pokl);
		int ev = poklmon.getEvolveIntoPoklmon();
		if (ev != -1) {
			if (data.getPoklmons().getPoklmon(ev).getEvolveIntoPoklmon() != -1) {
				return 1;
			} else {
				for (Poklmon p : data.getPoklmons().getPoklmons()) {
					if (p.getEvolveIntoPoklmon() == pokl) {
						return 2;
					}
				}
				return 1;
			}
		} else {
			for (Poklmon p : data.getPoklmons().getPoklmons()) {
				if (p.getEvolveIntoPoklmon() == pokl) {
					for (Poklmon p2 : data.getPoklmons().getPoklmons()) {
						if (p2.getEvolveIntoPoklmon() == p.getId()) {
							return 3;
						}
					}
					return 2;
				}
			}
			return 1;
		}
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
