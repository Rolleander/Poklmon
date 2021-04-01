package com.broll.poklmon.script.commands;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.item.Item;
import com.broll.pokllib.object.ObjectDirection;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.save.CustomObjectSerializer;
import com.broll.poklmon.script.CommandControl;
import com.broll.poklmon.script.ScriptProcessInteraction;
import com.broll.poklmon.map.areas.MapArea;
import com.broll.poklmon.map.areas.util.RandomBattle;
import com.broll.poklmon.map.areas.util.WildPoklmonEntry;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.model.CharacterWorldState;
import com.broll.poklmon.poklmon.CaughtPoklmonMeasurement;
import com.broll.poklmon.poklmon.PoklmonAttributeCalculator;
import com.broll.poklmon.save.PoklmonData;
import com.broll.poklmon.save.manage.SaveFileManager;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SystemCommands extends CommandControl {

    private com.broll.poklmon.script.commands.CustomScripts customScripts;

    public SystemCommands(GameManager game) {
        super(game);
        customScripts = new CustomScripts(game);
    }

    @Override
    public void init(MapObject object, ScriptProcessInteraction sceneProcess) {
        super.init(object, sceneProcess);
        customScripts.init(object, sceneProcess);
    }

    public void pause(double seconds) {
        int ms = (int) (1000 * seconds);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void saveGame() {
        game.getPlayer().getPlayerControl().saveCurrentLocation(game.getMap().getMapId());
        SaveFileManager.saveGame(game.getPlayer().getData().getSaveFile());
    }

    public void gameOver() {
        game.gameOver();
    }

    public int getPoklmonKP(PoklmonData poklmon) {
        return PoklmonAttributeCalculator.getKP(game.getData(), poklmon);
    }

    public int getPoklmonAttack(PoklmonData poklmon) {
        return PoklmonAttributeCalculator.getAttack(game.getData(), poklmon);
    }

    public int getPoklmonDefence(PoklmonData poklmon) {
        return PoklmonAttributeCalculator.getDefence(game.getData(), poklmon);
    }

    public int getPoklmonSpecialAttack(PoklmonData poklmon) {
        return PoklmonAttributeCalculator.getSpecialAttack(game.getData(), poklmon);
    }

    public int getPoklmonSpecialDefence(PoklmonData poklmon) {
        return PoklmonAttributeCalculator.getSpecialDefence(game.getData(), poklmon);
    }

    public int getPoklmonInitiative(PoklmonData poklmon) {
        return PoklmonAttributeCalculator.getInitiative(game.getData(), poklmon);
    }

    public String getItemName(int id) {
        return getItemData(id).getName();
    }

    public String getAttackName(int id) {
        return getAttackData(id).getName();
    }

    public String getPoklmonName(int id) {
        return getPoklmonData(id).getName();
    }

    public Item getItemData(int id) {
        return game.getData().getItems().getItem(id);
    }

    public Attack getAttackData(int id) {
        return game.getData().getAttacks().getAttack(id);
    }

    public Poklmon getPoklmonData(int id) {
        return game.getData().getPoklmons().getPoklmon(id);
    }

    public int getItemsCount() {
        return game.getData().getItems().getNumberOfItems();
    }

    public int getAttacksCount() {
        return game.getData().getAttacks().getNumberOfAttacks();
    }

    public int getPoklmonsCount() {
        return game.getData().getPoklmons().getNumberOfPoklmons();
    }

    public void callScript(String script) {
        customScripts.call(script);
    }

    public void playMusic(String file) {
        game.getData().getMusics().playMusic(file, true);
    }

    public void playSound(String sound) {
        game.getData().getSounds().playSound(sound);
    }

    public String getCurrentTime() {
        return CaughtPoklmonMeasurement.getCaughtDayInfo();
    }

    public String getCurrentTimeVar() {
        Date d = new Date();
        return "" + d.getTime();
    }

    public int getTimeDifferenceInMinutes(String oldTimeVar, String newTimeVar2) {
        Date d1 = new Date(Long.parseLong(oldTimeVar));
        Date d2 = new Date(Long.parseLong(newTimeVar2));
        long diff = d2.getTime() - d1.getTime();
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(diff);
        return minutes;
    }

    public int getMapId() {
        return game.getMap().getMapId();
    }

    public int getPlaytime() {
        return game.getPlayer().getData().getGameVariables().getPlayTime();
    }

    public boolean isMapPassable(int x, int y) {
        return game.getMap().isPassable(x, y, null, CharacterWorldState.STANDARD);
    }

    public MapArea getMapArea(int x, int y) {
        return game.getMap().getMap().getArea(x, y);
    }

    public boolean isInMapBounds(int x, int y) {
        return game.getMap().isInsideBounds(x, y);
    }

    public int forwardX(int x, ObjectDirection dir) {
        if (dir == ObjectDirection.LEFT) {
            return x - 1;
        } else if (dir == ObjectDirection.RIGHT) {
            return x + 1;
        }
        return x;
    }

    public int forwardY(int y, ObjectDirection dir) {
        if (dir == ObjectDirection.UP) {
            return y - 1;
        } else if (dir == ObjectDirection.DOWN) {
            return y + 1;
        }
        return y;
    }

    public RandomBattle calcRandomBattle(List<WildPoklmonEntry> list) {
        return game.getMap().getMapAreaController().calcRandomBattle(list);
    }

    public void debug(String s) {
        System.out.println("========>EVENT DEBUG:  " + s);
    }

    public String writeObject(Object object) {
        return CustomObjectSerializer.write(object);
    }

    public <T> T readObject(String data, String clazzName) {
        try {
            return CustomObjectSerializer.read(data, (Class<T>) Class.forName(clazzName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
