package com.broll.poklmon.menu.player;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.menu.MenuPage;
import com.broll.poklmon.menu.PlayerMenu;
import com.broll.poklmon.player.Player;

public class TrainerMenu extends MenuPage
{

    public TrainerMenu(PlayerMenu menu, Player player, DataContainer data)
    {
        super(menu, player, data);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onEnter()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onExit()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(Graphics g)
    {
        // render background
        //TODO
    }

    @Override
    public void update(float delta)
    {
        // TODO Auto-generated method stub
        
        if(GUIUpdate.isCancel())
        {
            close();
        }
        
    }

}
