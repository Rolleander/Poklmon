package com.broll.poklmon.script.commands;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.poklmon.WildPoklmon;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.script.CommandControl;
import com.broll.poklmon.script.Invoke;
import com.broll.poklmon.script.commands.subtasks.AutoLevelUpHandler;
import com.broll.poklmon.script.commands.subtasks.LevelUpHandler;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.model.CharacterWorldState;
import com.broll.poklmon.model.movement.MoveCommandListener;
import com.broll.poklmon.player.OverworldPlayer;
import com.broll.poklmon.player.TeleportDestination;
import com.broll.poklmon.poklmon.PoklmonDataFactory;
import com.broll.poklmon.poklmon.util.PoklmonLevelCalc;
import com.broll.poklmon.save.PoklmonData;
import com.broll.poklmon.script.ScriptProcessInteraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PlayerCommands extends CommandControl {

    private LevelUpHandler levelUpHandler;
    private AutoLevelUpHandler autoLevelUpHandler;
    private PoklmonLevelCalc levelCalc;

    public PlayerCommands(GameManager game) {
        super(game);
        this.levelUpHandler = new LevelUpHandler(game, this);
        this.autoLevelUpHandler = new AutoLevelUpHandler(game.getData());
        if (game != null) {
            levelCalc = new PoklmonLevelCalc(game.getData());
        }
    }

    @Override
    public void init(MapObject object, ScriptProcessInteraction interaction) {
        super.init(object, interaction);
        levelUpHandler.init(object, interaction);
    }

    public CharacterWorldState getWorldState() {
        return game.getPlayer().getOverworld().getWorldState();
    }

    public void setSwimmingState() {
        game.getPlayer().getOverworld().setWorldState(CharacterWorldState.SWIMMING);
    }

    public void setStandardState() {
        game.getPlayer().getOverworld().setWorldState(CharacterWorldState.STANDARD);
    }

    public boolean isSpeedModeAllowed() {
        return !game.getMap().isSpeedModeDisabled();
    }

    public boolean isInSpeedMode() {
        return game.getPlayer().getOverworld().isRunning();
    }

    public void startSpeedMode() {
        game.getPlayer().getOverworld().activateSpeedMode();
    }

    public void stopSpeedMode() {
        game.getPlayer().getOverworld().activateWalkMode();
    }

    public void teleportPlayerToLastRecover() {
        TeleportDestination tp = game.getPlayer().getRecoveryLocation();
        game.teleportPlayer(tp);
    }

    public void teleportPlayer(int mapid, int x, int y) {
        TeleportDestination tp = new TeleportDestination(mapid, x, y);
        game.teleportPlayer(tp);
    }

    public void teleportPlayer(int mapid, int x, int y, ObjectDirection dir) {
        TeleportDestination tp = new TeleportDestination(mapid, x, y, dir);
        game.teleportPlayer(tp);
    }

    public void teleportPlayerStep(int mapid, int x, int y, ObjectDirection dir) {
        TeleportDestination tp = new TeleportDestination(mapid, x, y, dir);
        tp.setDoStep(true);
        game.teleportPlayer(tp);
    }

    public void giveItem(int itemId, int count) {
        game.getData().getSounds().playSound("item_get");
        for (int i = 0; i < count; i++) {
            game.getPlayer().getInventarControl().addItem(itemId);
        }
        String item = game.getData().getItems().getItem(itemId).getName();
        String text = "";
        if (count > 1) {
            text = "x" + count;
        }
        game.getMessageGuiControl().showInfo(
                game.getPlayer().getData().getPlayerData().getName() + " erhält " + text + " " + item + "!");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void movePlayer(final ObjectDirection direction) {
        final OverworldPlayer op = game.getPlayer().getOverworld();
        invoke(new Invoke() {
            public void invoke() {
                op.move(direction, new MoveCommandListener() {

                    @Override
                    public void stoppedMoving(boolean success) {
                        resume();
                    }
                });
            }
        });
    }

    public void movePlayer(String dir) {
        String s = dir.trim().toUpperCase();
        if (s.equals("UP")) {
            movePlayer(ObjectDirection.UP);
        } else if (s.equals("DOWN")) {
            movePlayer(ObjectDirection.DOWN);
        } else if (s.equals("LEFT")) {
            movePlayer(ObjectDirection.LEFT);
        } else if (s.equals("RIGHT")) {
            movePlayer(ObjectDirection.RIGHT);
        }
    }

    public void turnPlayer(ObjectDirection direction) {
        game.getPlayer().getOverworld().setDirection(direction);
    }

    public void giveExp(PoklmonData poklmon, int exp) {
        levelUpHandler.initPoklmon(poklmon);
        levelCalc.setLevelCalcListener(levelUpHandler);
        levelCalc.addEXP(poklmon, exp);
    }

    public void giveLevel(PoklmonData poklmon, int level) {
        for (int i = 0; i < level; i++) {
            int missing = levelCalc.getExpToNextLevel(poklmon);
            giveExp(poklmon, missing);
        }
    }

    public void autoExp(PoklmonData poklmon, int exp) {
        autoLevelUpHandler.initPoklmon(poklmon);
        levelCalc.setLevelCalcListener(autoLevelUpHandler);
        levelCalc.addEXP(poklmon, exp);
    }

    public void giveMoney(final int money) {
        if (money != 0) {
            game.getPlayer().getInventarControl().addMoney(money);
            invoke(new Invoke() {
                @Override
                public void invoke() {
                    String what = "erhält";
                    if (money < 0) {
                        what = "bezahlt";
                    }
                    game.getMessageGuiControl().showText(
                            game.getPlayer().getData().getPlayerData().getName() + " " + what + " " + Math.abs(money)
                                    + " PoklDollar!");
                }
            });
        }
    }

    public void removeItem(int itemId, int count) {
        for (int i = 0; i < count; i++) {
            if (!game.getPlayer().getInventarControl().hasItem(itemId)) {
                return;
            }
            game.getPlayer().getInventarControl().useItem(itemId);
        }
    }

    public boolean hasItem(int itemId) {
        return game.getPlayer().getInventarControl().hasItem(itemId);
    }

    public int getItemCount(int itemId) {
        return game.getPlayer().getInventarControl().getItemCount(itemId);
    }

    public void addPoklmon(int nr, int level, String name) {
        Poklmon poklmon = game.getData().getPoklmons().getPoklmon(nr);
        WildPoklmon wildPoklmon = FightPokemonBuilder.createWildPoklmon(game.getData(), poklmon, level);
        PoklmonDataFactory poklmonDataFactory = new PoklmonDataFactory(game.getData());
        PoklmonData pokl = poklmonDataFactory.getPoklmon(wildPoklmon);
        pokl.setName(name);
        game.getPlayer().getPoklmonControl().addNewPoklmon(pokl);
    }

    public void addPoklmon(PoklmonData poklmon) {
        game.getPlayer().getPoklmonControl().addNewPoklmon(poklmon);
    }

    public void releasePoklmon(PoklmonData poklmon) {
        game.getPlayer().getPoklmonControl().releasePoklmon(poklmon);
    }

    public String getPoklmonName(PoklmonData poklmon) {
        String name = poklmon.getName();
        if (name == null) {
            name = game.getData().getPoklmons().getPoklmon(poklmon.getPoklmon()).getName();
        }
        return name;
    }

    public List<String> getTeamNames() {
        HashMap<Integer, PoklmonData> team = game.getPlayer().getPoklmonControl().getPoklmonsInTeam();
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < 6; i++) {
            PoklmonData pokl = team.get(i);
            if (pokl != null) {
                names.add(getPoklmonName(pokl));
            }
        }
        return names;
    }

    public List<PoklmonData> getTeamData() {
        HashMap<Integer, PoklmonData> team = game.getPlayer().getPoklmonControl().getPoklmonsInTeam();
        List<PoklmonData> data = new ArrayList<PoklmonData>();
        for (int i = 0; i < 6; i++) {
            PoklmonData pokl = team.get(i);
            if (pokl != null) {
                data.add(pokl);
            }
        }
        return data;
    }

    public void healTeam() {
        game.getPlayer().saveRecoveryPosition();
        game.getPlayer().healTeam();
    }

    public void healPoklmon(PoklmonData poklmon) {
        game.getPlayer().healPoklmon(poklmon);
    }

    public String getName() {
        return game.getPlayer().getData().getPlayerData().getName();
    }

    public int getPlayerX() {
        return (int) game.getPlayer().getOverworld().getXpos();
    }

    public int getPlayerY() {
        return (int) game.getPlayer().getOverworld().getYpos();
    }

    public ObjectDirection getPlayerDirection() {
        return game.getPlayer().getOverworld().getDirection();
    }

    public int getPoklmonCount(int id) {
        int c = getPoklmonCount(game.getPlayer().getPoklmonControl().getPoklmonsInPC().iterator(), id);
        c += getPoklmonCount(game.getPlayer().getPoklmonControl().getPoklmonsInTeam().values().iterator(), id);
        return c;
    }

    public int getTeamSize() {
        return game.getPlayer().getPoklmonControl().getPoklmonsInTeam().size();
    }

    public int getBoxSize() {
        return game.getPlayer().getPoklmonControl().getPoklmonsInPC().size();
    }

    public boolean hasSeenPoklmon(int id) {
        return game.getPlayer().getPokldexControl().hasSeenPoklmon(id);
    }

    public boolean hasCatchedPoklmon(int id) {
        return game.getPlayer().getPokldexControl().getCachedCount(id) > 0;
    }

    public int getPoklmonCatchCount(int id) {
        return game.getPlayer().getPokldexControl().getCachedCount(id);
    }

    public int getTotalSeenPoklmonsCount() {
        return game.getPlayer().getPokldexControl().getDifferentPoklmonSeen();
    }

    public int getTotalCatchedPoklmonsCount() {
        return game.getPlayer().getPokldexControl().getDifferentPoklmonCached();
    }

    public int getMoney() {
        return game.getPlayer().getInventarControl().getMoney();
    }

    public boolean hasEnoughMoney(int needed) {
        return game.getPlayer().getInventarControl().hasEnoughMoney(needed);
    }

    public boolean hasPoklmonInTeam(int id) {
        for (PoklmonData pokl : game.getPlayer().getPoklmonControl().getPoklmonsInTeam().values()) {
            if (id == pokl.getPoklmon()) {
                return true;
            }
        }
        return false;
    }

    public boolean isTeamNotFull() {
        return game.getPlayer().getPoklmonControl().getPoklmonsInTeam().size() < 6;
    }

    private int getPoklmonCount(Iterator<PoklmonData> iterator, int id) {
        int c = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getPoklmon() == id) {
                c++;
            }
        }
        return c;
    }

    public LevelUpHandler getLevelUpHandler() {
        return levelUpHandler;
    }
}
