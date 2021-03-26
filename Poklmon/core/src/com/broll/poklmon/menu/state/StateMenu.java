package com.broll.poklmon.menu.state;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.menu.MenuPage;
import com.broll.poklmon.menu.PlayerMenu;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.save.PoklmonData;

import java.util.List;

public class StateMenu extends MenuPage {


    private PoklmonStateMenu poklmonStateMenu;
    private Player player;
    private int currentPoklmonNr;
    private boolean disableSwap;
    private List<PoklmonData> scrollList;

    public StateMenu(PlayerMenu menu, Player player, DataContainer data) {
        super(menu, player, data);
        this.player = player;
        poklmonStateMenu = new PoklmonStateMenu(data);
    }

    public void open(PoklmonData poklmon, List<PoklmonData> scrollList) {
        poklmonStateMenu.open(poklmon);
        this.scrollList = scrollList;
        this.currentPoklmonNr = scrollList.indexOf(poklmon);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {
    }


    @Override
    public void render(Graphics g) {
        poklmonStateMenu.render(g);
    }

    private void showPrevious() {
        currentPoklmonNr--;
        if (currentPoklmonNr < 0) {
            currentPoklmonNr = scrollList.size() - 1;
        }
        PoklmonData poklmon = scrollList.get(currentPoklmonNr);
        if (poklmon != null) {
            poklmonStateMenu.open(poklmon);
        } else {
            //empty spot, skip
            showPrevious();
        }
    }

    private void showNext() {
        currentPoklmonNr++;
        if (currentPoklmonNr >= scrollList.size()) {
            currentPoklmonNr = 0;
        }
        PoklmonData poklmon = scrollList.get(currentPoklmonNr);
        if (poklmon != null) {
            poklmonStateMenu.open(poklmon);
        } else {
            //empty spot, skip
            showNext();
        }
    }

    @Override
    public void update(float delta) {
        poklmonStateMenu.update();

        if (GUIUpdate.isMoveUp()) {
            showPrevious();
        }
        if (GUIUpdate.isMoveDown()) {
            showNext();
        }

        if (GUIUpdate.isCancel()) {
            close();
        }
    }


}
