package com.broll.poklmon.menu.pc;

import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.selection.SelectionBoxListener;
import com.broll.poklmon.menu.MenuUtils;

public class PcSelection
{
    public final static int EDIT_BOX = 1;
    public final static int EDIT_TEAM = 2;
    public final static int CLOSE=4;
    public final static int SELECT_OPTION = 0;

    private int boxSelection = 0;
    private int teamSelection = 0;
    private int optionSelection = 0;
    private int selectMode = SELECT_OPTION;

    public final static String[] options = {"Box", "Team", "Sortieren", "Zurück"};
    public final static String[] poklmonOptions = {"Bericht", "Tausch"};

    private PcMenu menu;
    private boolean swaping = false;

    public PcSelection(PcMenu pcMenu)
    {
        this.menu = pcMenu;
        init();
    }

    public void init()
    {
        selectMode = SELECT_OPTION;
        boxSelection = 0;
        teamSelection = 0;
        optionSelection = 0;
    }
    
    public void setBoxSelection(int boxSelection)
    {
        this.boxSelection = boxSelection;
    }

    public void update()
    {

        if (GUIUpdate.isClick())
        {
            if (swaping)
            {
                swap();
            }
            else
            {
                switch (selectMode)
                {
                    case SELECT_OPTION:
                        doOption();
                        break;
                    case EDIT_BOX:
                        doBox();
                        break;
                    case EDIT_TEAM:
                        doTeam();
                        break;
                }
            }
        }
        if (GUIUpdate.isCancel())
        {
            swaping = false;
            if (selectMode == SELECT_OPTION)
            {
                menu.close();
            }
            else
            {
                selectMode = SELECT_OPTION;
            }
        }
        updateSelection();

    }

    private void swap()
    {
        swaping = false;
        //swap team with box
        menu.swap(boxSelection, teamSelection);
        if (selectMode == EDIT_BOX)
        {
            selectMode = EDIT_TEAM;
        }
        else
        {
            selectMode = EDIT_BOX;
        }
    }

    private void openReport()
    {
        if (selectMode == EDIT_TEAM)
        {
            menu.openStatus(teamSelection, true);
        }
        else
        {
            menu.openStatus(boxSelection, false);
        }
    }

    private void doBox()
    {
        menu.openSelection(poklmonOptions, boxSelection, false, new SelectionBoxListener() {
            @Override
            public void select(int item)
            {
                if (item == 0)
                {
                    openReport();
                }
                else
                {
                    swaping = true;
                    selectMode = EDIT_TEAM;
                }
            }

            @Override
            public void cancelSelection()
            {

            }
        });
    }

    private void doTeam()
    {
        menu.openSelection(poklmonOptions, teamSelection, true, new SelectionBoxListener() {
            @Override
            public void select(int item)
            {
                if (item == 0)
                {
                    openReport();
                }
                else
                {
                    swaping = true;
                    selectMode = EDIT_BOX;
                }
            }

            @Override
            public void cancelSelection()
            {

            }
        });
    }

    private void doOption()
    {
        switch (optionSelection)
        {
            case 0:
                //edit box
                selectMode = EDIT_BOX;
                break;
            case 1:
                //edit team
                selectMode = EDIT_TEAM;
                break;
            case 2:
                //sort
                break;
            case 3:
                //close
                menu.close();
                break;
        }
    }

    private void updateSelection()
    {
        switch (selectMode)
        {
            case SELECT_OPTION:
                optionSelection = MenuUtils.updateSelection(optionSelection, options.length, 1);
                break;
            case EDIT_BOX:
                boxSelection = MenuUtils.updateSelection(boxSelection, PcMenu.ROW_ITEMS, menu.getHeight());
                break;
            case EDIT_TEAM:
                teamSelection = MenuUtils.updateSelection(teamSelection, 1, 6);
                break;
        }
    }

    public int getBoxSelection()
    {
        return boxSelection;
    }

    public int getOptionSelection()
    {
        return optionSelection;
    }

    public int getSelectMode()
    {
        return selectMode;
    }

    public int getTeamSelection()
    {
        return teamSelection;
    }

}
