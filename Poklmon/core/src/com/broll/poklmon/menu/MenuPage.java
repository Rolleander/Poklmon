package com.broll.poklmon.menu;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.resource.FontUtils;

public abstract class MenuPage
{

    protected Player player;
    protected PlayerMenu menu;
    protected DataContainer data;
    protected FontUtils fontUtils=new FontUtils();

    public MenuPage(PlayerMenu menu, Player player, DataContainer data)
    {
        this.menu = menu;
        this.player = player;
        this.data=data;
    }

    public void close()
    {
        menu.closePage();
    }

    public abstract void onEnter();

    public abstract void onExit();

    public abstract void render(Graphics g);

    public abstract void update(float delta);



}
