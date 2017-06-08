package com.broll.poklmon.player.control.impl;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.player.OverworldPlayer;
import com.broll.poklmon.player.control.PlayerControlInterface;
import com.broll.poklmon.save.PlayerData;

public class PlayerControl implements PlayerControlInterface
{

    private OverworldPlayer player;
 
    private PlayerData playerData;


    public PlayerControl(OverworldPlayer player, PlayerData data)
    {
        this.player = player;
        this.playerData = data;
        initOverworldPlayer();
    }


    private void initOverworldPlayer()
    {
        // init overworld player
        float x = playerData.getXpos();
        float y = playerData.getYpos();
        ObjectDirection direction = ObjectDirection.values()[playerData.getView()];
        // set direction and position
        player.setDirection(direction);
        player.teleport(x, y);
    }

    public void update(float delta)
    {
        player.update(delta);
    }

    @Override
    public void move(ObjectDirection direction)
    {
       player.move(direction);
    }

    @Override
    public void teleportTo(float x, float y)
    {
        player.teleport(x, y);
    }


    @Override
    public void saveCurrentLocation(int mapNr)
    {
        int x = (int)player.getXpos();
        int y = (int)player.getYpos();
        int dir = player.getDirection().ordinal();
        playerData.setMapNr(mapNr);
        playerData.setView(dir);
        playerData.setXpos(x);
        playerData.setYpos(y);
    }

    @Override
    public void look(ObjectDirection direction)
    {
        player.setDirection(direction);
    }
}
