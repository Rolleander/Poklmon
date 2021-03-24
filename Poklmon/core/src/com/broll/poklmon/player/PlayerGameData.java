package com.broll.poklmon.player;

import com.broll.poklmon.save.GameData;
import com.broll.poklmon.save.GameVariables;
import com.broll.poklmon.save.PlayerData;
import com.broll.poklmon.save.PoklmonData;

import java.util.ArrayList;

public class PlayerGameData
{
    private PlayerData playerData;
    private GameVariables gameVariables;
    private ArrayList<PoklmonData> poklmons;
 
    
    public PlayerGameData(GameData data)
    {
        //data from save file
        playerData=data.getPlayerData();
        gameVariables=data.getVariables();
        poklmons=data.getPoklmons();       
    }
    
    public GameVariables getGameVariables()
    {
        return gameVariables;
    }
    
    public PlayerData getPlayerData()
    {
        return playerData;
    }
    
    public ArrayList<PoklmonData> getPoklmons()
    {
        return poklmons;
    }   
    
    public GameData getSaveFile()
    {
        GameData data=new GameData();
        data.setPlayerData(playerData);
        data.setPoklmons(poklmons);
        data.setVariables(gameVariables);
        return data;
    }
}
