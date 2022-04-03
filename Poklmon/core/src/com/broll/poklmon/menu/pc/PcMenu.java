package com.broll.poklmon.menu.pc;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.gui.selection.SelectionBox;
import com.broll.poklmon.gui.selection.SelectionBoxListener;
import com.broll.poklmon.menu.MenuPage;
import com.broll.poklmon.menu.MenuUtils;
import com.broll.poklmon.menu.PlayerMenu;
import com.broll.poklmon.menu.state.StateMenu;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.PoklmonData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PcMenu extends MenuPage {


    private ArrayList<PoklmonData> pc;
    private HashMap<Integer, PoklmonData> team;
    public static int ROW_ITEMS = 3;
    public static int MAX_ROWS = 12;
    private int size;
    private PcBlockRender blockRender;
    private PcSelection selection;
    private SelectionBox selectionBox;
    private boolean enterFromStatus = false;

    public PcMenu(PlayerMenu menu, Player player, DataContainer data) {
        super(menu, player, data);
        selection = new PcSelection(this);
        blockRender = new PcBlockRender(data);
    }

    @Override
    public void onEnter() {
        if (enterFromStatus == false) {
            selection.init();

            pc = player.getPoklmonControl().getPoklmonsInPC();

            team = player.getPoklmonControl().getPoklmonsInTeam();

            size = pc.size();


            if (size % ROW_ITEMS == 0) {
                size += ROW_ITEMS * 2;
            } else {
                size = (size / ROW_ITEMS + 3) * ROW_ITEMS;
            }


            //immer mindestens 2 leere reihen f�r die team poklmons
        }
        enterFromStatus = false;
    }

    @Override
    public void onExit() {

    }

    public void openStatus(int poklmon, boolean inTeam) {
        StateMenu state = (StateMenu) menu.getPage(StateMenu.class);
        PoklmonData data = null;
        List<PoklmonData> scrollList;
        if (inTeam) {
            data = team.get(poklmon);
            scrollList = Arrays.asList(team.values().toArray(new PoklmonData[0]));
        } else {
            if (poklmon < pc.size()) {
                data = pc.get(poklmon);
            }
            scrollList = player.getPoklmonControl().getPoklmonsInPC();
        }
        if (data != null) {
            enterFromStatus = true;
            state.open(data, scrollList);
            menu.openPage(StateMenu.class);
        }
    }

    public void swap(int boxID, int teamID) {

        player.getPoklmonControl().takePoklmonIntoTeam(boxID, teamID);
        //refreshs
        pc = player.getPoklmonControl().getPoklmonsInPC();
        team = player.getPoklmonControl().getPoklmonsInTeam();
    }


    @Override
    public void render(Graphics g) {

        Image background = data.getGraphics().getMenuGraphicsContainer().getBoxBackground();
        background.draw();

        g.setFont(GUIFonts.hudText);
        g.setColor(ColorUtil.newColor(250, 250, 250));
        g.drawString("Abgelegte Poklmon (" + pc.size() + ")", 60, 58);
        g.drawString("Team", 635, 58);

        int boxSelection = selection.getBoxSelection();
        int selectMode = selection.getSelectMode();
        int teamSelection = selection.getTeamSelection();
        int optionSelection = selection.getOptionSelection();


        int anz = MAX_ROWS * ROW_ITEMS;
        int id = boxSelection / anz;

        g.setFont(GUIFonts.smallText);
        int maxs = size / anz;
        if (size % anz != 0) {
            maxs++;
        }
        String text = "Seite " + (id + 1) + " / " + maxs;
        g.drawString(text, 558 - MenuUtils.getTextWidth(g, fontUtils, text), 73);

        //draw box
        for (int i = 0; i < anz; i++) {
            int cid = i + id * anz;
            float x = calcBoxX(i);
            float y = calcBoxY(i);
            PoklmonData pokl = null;
            if (cid < pc.size()) {
                pokl = pc.get(cid);
            }
            if (cid < size) {
                boolean selected = cid == boxSelection && selectMode == PcSelection.EDIT_BOX;
                blockRender.renderBox(g, pokl, x, y, selected);
            }
        }

        //draw item

        for (int i = 0; i < 6; i++) {
            boolean selected = i == teamSelection && selectMode == PcSelection.EDIT_TEAM;
            PoklmonData pokl = team.get(i);
            float x = calcTeamX();
            float y = calcTeamY(i);
            blockRender.renderBox(g, pokl, x, y, selected);
        }

        float x = 10;
        for (int i = 0; i < PcSelection.options.length; i++) {
            boolean selected = optionSelection == i && selectMode == PcSelection.SELECT_OPTION;
            x += MenuUtils.drawButton(g, PcSelection.options[i], fontUtils, x, 10, selected);
            x += 10;
        }

        if (selectionBox != null) {
            selectionBox.render(g);
        }
    }

    private float calcBoxX(int select) {
        float x = 15;
        return x + PcBlockRender.WIDTH * (select % ROW_ITEMS);
    }

    private float calcBoxY(int select) {
        float y = 102;
        return y + PcBlockRender.HEIGHT * (select / ROW_ITEMS);
    }

    private float calcTeamX() {
        return 600;
    }

    private float calcTeamY(int select) {
        return 102 + select * 50;
    }

    @Override
    public void update(float delta) {
        if (selectionBox != null) {
            selectionBox.update();
        }
        selection.update();
    }

    public void openSelection(String[] options, int pos, boolean inTeam, final SelectionBoxListener listener) {
        int x = 0;
        int y = 0;
        if (inTeam) {
            x = (int) calcTeamX();
            y = (int) calcTeamY(pos);
        } else {
            pos = pos % (MAX_ROWS * ROW_ITEMS);
            x = (int) calcBoxX(pos);
            y = (int) calcBoxY(pos);
        }

        x += 205;
        y += 20;

        selectionBox = new SelectionBox(data, options, x, y, false);
        selectionBox.setListener(new SelectionBoxListener() {
            public void select(int item) {
                selectionBox = null;
                listener.select(item);
            }

            public void cancelSelection() {
                selectionBox = null;
                listener.cancelSelection();
            }

        });
        if (y < 500) {
            selectionBox.shiftDown();
        }
    }

    public int getHeight() {
        return size / ROW_ITEMS;
    }


}
