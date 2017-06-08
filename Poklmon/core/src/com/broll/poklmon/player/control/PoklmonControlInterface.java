package com.broll.poklmon.player.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.broll.poklmon.save.PoklmonData;

public interface PoklmonControlInterface {

	
	public void addNewPoklmon(PoklmonData poklmon);
	
	public void releasePoklmon(PoklmonData poklmon);
	
	public HashMap<Integer,PoklmonData> getPoklmonsInTeam();
	
	public ArrayList<PoklmonData> getPoklmonsInPC();
	
	public HashMap<Integer, PoklmonData> switchTeamPositions(int pos1, int pos2);
	
	public void movePoklmonToPC(PoklmonData poklmon);
	
	public void takePoklmonIntoTeam(int boxPlace, int teamPlace);

    public List<PoklmonData> getAttackLearningTargets(int atk);
    
    public boolean canLearnAttack(PoklmonData poklmon, int atk);
}
