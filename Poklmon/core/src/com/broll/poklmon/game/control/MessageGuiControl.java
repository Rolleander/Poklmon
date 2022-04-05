package com.broll.poklmon.game.control;

/**
 * Created by Roland on 28.10.2017.
 */

import com.badlogic.gdx.Gdx;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.TextContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.SceneProcessManager;
import com.broll.poklmon.game.scene.ScriptInstance;
import com.broll.poklmon.gui.dialog.DialogBox;
import com.broll.poklmon.gui.info.InfoBox;
import com.broll.poklmon.gui.input.NameInputField;
import com.broll.poklmon.gui.input.NameInputListener;
import com.broll.poklmon.gui.input.NumberInputField;
import com.broll.poklmon.gui.input.NumberInputListener;
import com.broll.poklmon.gui.selection.ScrollableSelectionBox;
import com.broll.poklmon.gui.selection.SelectionBox;
import com.broll.poklmon.gui.selection.SelectionBoxListener;
import com.broll.poklmon.menu.MenuCloseListener;
import com.broll.poklmon.menu.MenuPage;
import com.broll.poklmon.menu.PlayerMenu;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.resource.FontUtils;

public class MessageGuiControl {

    private GameManager game;
    private DataContainer data;
    private DialogBox dialogBox;
    private ScrollableSelectionBox selectionBox;
    private SelectionBox menuBox;
    private InfoBox infoBox;
    private NumberInputField numberInput;
    private NameInputField nameInput;
    private SceneProcessManager process;
    private int selectedOption;
    private int inputNumber;
    private String inputName;
    private boolean guiVisible = false;
    private String[] menuItems = new String[8];
    private PlayerMenu playerMenu;
    private final static int SELECTION_MAX = 10;

    public MessageGuiControl(GameManager game, SceneProcessManager sceneProcessManager) {
        this.game = game;
        this.data = game.getData();
        dialogBox = new DialogBox(data);
        dialogBox.setTextSpeed(DialogBox.TEXT_FASTEST);
        nameInput = new NameInputField(data);
        numberInput = new NumberInputField(data);
        process = sceneProcessManager;
        dialogBox.setStyle(DialogBox.STYLE_PLAIN);
        infoBox = new InfoBox(0, 0, false);

    }

    public void initMenu() {
        menuItems[0] = TextContainer.get("playerMenu_PoklDex");
        menuItems[1] = TextContainer.get("playerMenu_Team");
        menuItems[2] = TextContainer.get("playerMenu_Inventar");
        menuItems[3] = TextContainer.get("playerMenu_Player",game.getPlayer().getData().getPlayerData().getName());
        menuItems[4] = TextContainer.get("playerMenu_Save");
        menuItems[5] = TextContainer.get("playerMenu_Options");
        menuItems[6] = TextContainer.get("playerMenu_Exit");
        menuItems[7] = TextContainer.get("playerMenu_Close");
        playerMenu = new PlayerMenu(game.getPlayer(), game.getData(), game);
    }

    private boolean acceptedText = false;

    public void hideGui() {
        guiVisible = false;
    }

    public void openMenu() {
        int x = 700;
        int y = 435;
        menuBox = new SelectionBox(data, menuItems, x, y, false);
        menuBox.blockItem(4, !Player.SAVING_ALLOWED);
        data.getSounds().playSound("bagopen");
        menuBox.setListener(new SelectionBoxListener() {
            @Override
            public void select(int item) {
                if (item == 7) {
                    // close
                    game.changeMenuState(false);
                    menuBox = null;
                    data.getSounds().playSound("bagopen");
                } else {
                    // reset listener
                    playerMenu.setMenuCloseListener(null);
                    // open menus
                    switch (item) {
                        case 0:
                            playerMenu.getControl().openPokldex();
                            break;
                        case 1:
                            playerMenu.getControl().openTeam();
                            break;
                        case 2:
                            playerMenu.getControl().openInventar();
                            break;
                        case 3:
                            playerMenu.getControl().openPlayer();
                            break;
                        case 4:
                            menuBox = null;
                            showSaveDialog();
                            break;
                        case 5:
                            playerMenu.getControl().openSettings();
                            break;
                        case 6:
                            menuBox = null;
                            showExitDialog();
                            break;
                    }
                }
            }

            @Override
            public void cancelSelection() {
                menuBox = null;
                game.changeMenuState(false);
                data.getSounds().playSound("bagopen");
            }
        });
    }

    public void showCustomDialog(final String text) {
        process.runScript(new ScriptInstance(new Runnable() {
            @Override
            public void run() {
                showText(text);
                process.waitForResume();
                guiVisible = false;
            }
        }));
    }

    public void showRecoverDialog() {
        process.runScript(new ScriptInstance(new Runnable() {
            @Override
            public void run() {
                showText(game.getPlayer().getData().getPlayerData().getName() + " hat sich wieder erholt!");
                process.waitForResume();
                guiVisible = false;
            }
        }));
    }

    public void showExitDialog() {
        process.runScript(new ScriptInstance(new Runnable() {
            @Override
            public void run() {
                showText(TextContainer.get("dialog_ExitGame"));
                process.waitForResume();
                showSelection(new String[]{TextContainer.get("option_Yes"), TextContainer.get("option_No")});
                process.waitForResume();
                if (getSelectedOption() == 0) {
                    Gdx.app.exit();
                }
                game.changeMenuState(false);
                guiVisible = false;
            }
        }));
    }

    public void showSaveDialog() {
        process.runScript(new ScriptInstance(new Runnable() {
            @Override
            public void run() {
                showText(TextContainer.get("dialog_SaveGame"));
                process.waitForResume();
                showSelection(new String[]{TextContainer.get("option_Yes"), TextContainer.get("option_No")});
                process.waitForResume();
                if (getSelectedOption() == 0) {
                    dialogBox.showInfo(TextContainer.get("dialog_SaveGame_Saving"), "");
                    game.getPlayer().saveGame();
                    showText(TextContainer.get("dialog_SaveGame_Finished"));
                    process.waitForResume();
                }
                game.changeMenuState(false);
                guiVisible = false;
            }
        }));
    }

    public void showText(String text) {
        acceptedText = false;
        guiVisible = true;
        dialogBox.showMessage(text, new SelectionListener() {
            @Override
            public void selectionDone() {
                if (acceptedText == false) {
                    acceptedText = true;
                    process.resume();
                }
            }
        });
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public String getInputName() {
        return inputName;
    }

    public void showSelection(String[] items) {
        int x = 800;
        int y = (600 - 157);
        selectionBox = new ScrollableSelectionBox(data,items, x, y, SELECTION_MAX, false);
        selectionBox.setListener(new SelectionBoxListener() {
            @Override
            public void select(int item) {
                selectedOption = item;
                process.resume();
                selectionBox = null;
            }

            @Override
            public void cancelSelection() {

            }
        });
    }

    public void showInfoBox(String text) {
        infoBox.showText(text);
    }

    public void hideInfoBox() {
        infoBox.setVisible(false);
    }

    public void showSelection(String[] items, boolean[] locked, final boolean cancelable, boolean iconized) {
        int x = 800;
        int y = (600 - 157);
        selectionBox = new ScrollableSelectionBox(data, items, x, y, SELECTION_MAX, iconized);
        for (int i = 0; i < locked.length; i++) {
            selectionBox.blockItem(i, locked[i]);
        }
        selectionBox.setListener(new SelectionBoxListener() {
            @Override
            public void select(int item) {
                selectedOption = item;
                process.resume();
                selectionBox = null;
            }

            @Override
            public void cancelSelection() {
                if (cancelable) {
                    selectedOption = -1;
                    process.resume();
                    selectionBox = null;
                }
            }
        });
    }

    public void setSelectionIcons(Image[] icons) {
        selectionBox.setIcons(icons);
    }

    public void render(Graphics g) {
        if (playerMenu.isVisible()) {
            playerMenu.render(g);
        } else {
            if (menuBox != null) {
                menuBox.render(g);
            }
        }
        if (guiVisible) {
            dialogBox.render(g);
            if (selectionBox != null) {
                selectionBox.render(g);
            }
            numberInput.render(g);
            nameInput.render(g);
        }
        infoBox.render(g);
    }

    public void update(float delta) {
        if (guiVisible) {
            dialogBox.update();
            if (selectionBox != null) {
                selectionBox.update();
            }
            numberInput.update();
        } else {
            if (playerMenu.isVisible()) {
                playerMenu.update(delta);
            } else {
                if (menuBox != null) {
                    menuBox.update();
                }
            }
        }
    }

    public void openNameInput(String t) {
        guiVisible = true;
        nameInput.openNameInput(t, new NameInputListener() {
            public void inputName(String name) {
                inputName = name;
                process.resume();
            }
        });
    }

    public void openTextInput(String t, int max) {
        guiVisible = true;
        nameInput.openCustomInput(t, max, new NameInputListener() {
            public void inputName(String name) {
                inputName = name;
                process.resume();
            }
        });
    }

    public void openNumberInput(String info, int value, int min, int max, boolean cancelable) {
        guiVisible = true;
        numberInput.show(info, value, min, max, cancelable, new NumberInputListener() {
            @Override
            public void input(int number) {
                inputNumber = number;
                process.resume();
            }

            @Override
            public void cancel() {
                inputNumber = NumberInputField.CANCEL_VALUE;
                process.resume();
            }
        });
    }

    public int getInputNumber() {
        return inputNumber;
    }

    public void keyPressed(int key, char c) {
        nameInput.keyPressed(key, c);
    }

    public void showInfo(String text) {
        guiVisible = true;
        dialogBox.showMessage(text, null);
        dialogBox.setMaxLetters();
    }

    public void setTextEnd() {
        dialogBox.setMaxLetters();
    }

    public void showMenu(Class<? extends MenuPage> page) {
        guiVisible = false;
        playerMenu.setMenuCloseListener(new MenuCloseListener() {
            @Override
            public void menuClosed() {
                process.resume();
            }
        });
        playerMenu.showMenu();
        playerMenu.openPage(page);
    }

    public MenuPage getPage(Class<? extends MenuPage> page) {
        return playerMenu.getPage(page);
    }

    public PlayerMenu getPlayerMenu() {
        return playerMenu;
    }

}
