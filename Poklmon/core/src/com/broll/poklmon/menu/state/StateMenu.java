package com.broll.poklmon.menu.state;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.menu.MenuPage;
import com.broll.poklmon.menu.PlayerMenu;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.save.PoklmonData;

public class StateMenu extends MenuPage
{


    private PoklmonStateMenu poklmonStateMenu;
    private Player player;
    private int currentPoklmonNr;
    private boolean disableSwap;

    public StateMenu(PlayerMenu menu, Player player, DataContainer data)
    {
        super(menu, player, data);
        this.player = player;
        poklmonStateMenu = new PoklmonStateMenu(data);
    }

    public void open(PoklmonData poklmon, int nr, boolean noSwap)
    {
        disableSwap = noSwap;
        poklmonStateMenu.open(poklmon);
        this.currentPoklmonNr = nr;
    }

    @Override
    public void onEnter()
    {

    }

    @Override
    public void onExit()
    {
    }



    @Override
    public void render(Graphics g)
    {
        poklmonStateMenu.render(g);
    }

    @Override
    public void update(float delta)
    {
        poklmonStateMenu.update();

        if (!disableSwap)
        {
            if (GUIUpdate.isMoveUp())
            {
                currentPoklmonNr--;
                if (currentPoklmonNr < 0)
                {
                    currentPoklmonNr = player.getPoklmonControl().getPoklmonsInTeam().size() - 1;
                }
                open(player.getPoklmonControl().getPoklmonsInTeam().get(currentPoklmonNr), currentPoklmonNr, false);
            }
            if (GUIUpdate.isMoveDown())
            {
                currentPoklmonNr++;
                if (currentPoklmonNr >= player.getPoklmonControl().getPoklmonsInTeam().size())
                {
                    currentPoklmonNr = 0;
                }
                open(player.getPoklmonControl().getPoklmonsInTeam().get(currentPoklmonNr), currentPoklmonNr, false);
            }
        }
        if (GUIUpdate.isCancel())
        {
            close();
        }
    }



}
