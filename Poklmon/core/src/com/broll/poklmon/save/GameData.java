package com.broll.poklmon.save;

import java.util.ArrayList;

public class GameData  {

	/**
     * 
     */
    private ArrayList<PoklmonData> poklmons = new ArrayList<PoklmonData>();
	private PlayerData playerData;
	private GameVariables variables;

	public void setPoklmons(ArrayList<PoklmonData> poklmons) {
		this.poklmons = poklmons;
	}

	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
	}

	public void setVariables(GameVariables variables) {
		this.variables = variables;
	}

	public ArrayList<PoklmonData> getPoklmons() {
		return poklmons;
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	public GameVariables getVariables() {
		return variables;
	}

}
