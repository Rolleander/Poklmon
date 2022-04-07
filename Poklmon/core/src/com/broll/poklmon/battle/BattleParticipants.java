package com.broll.poklmon.battle;

import com.broll.poklmon.battle.enemy.EnemyKIType;
import com.broll.poklmon.battle.item.TrainerItem;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.map.areas.AreaType;

import java.util.ArrayList;
import java.util.List;

public class BattleParticipants {

    private boolean isTrainerFight;
    private EnemyKIType enemyKIType = EnemyKIType.RANDOM;
    private String enemyName, playerName;
    private FightPoklmon player, enemy;
    private ArrayList<FightPoklmon> playerTeam = new ArrayList<FightPoklmon>();
    private ArrayList<FightPoklmon> enemyTeam = new ArrayList<FightPoklmon>();
    private List<TrainerItem> trainerItems = new ArrayList<TrainerItem>();
    private int winMoney;
    private String introText, outroText;
    private AreaType areaType = AreaType.GRASS;
    private String customMusic;

    public BattleParticipants() {
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    public void setCustomMusic(String customMusic) {
        this.customMusic = customMusic;
    }

    public String getCustomMusic() {
        return customMusic;
    }

    public void addTrainerItem(int id) {
        for (TrainerItem item : trainerItems) {
            if (item.getId() == id) {
                item.add(1);
                return;
            }
        }
        trainerItems.add(new TrainerItem(id));
    }

    public void addTrainerItem(int id, int count) {
        for (TrainerItem item : trainerItems) {
            if (item.getId() == id) {
                item.add(count);
                return;
            }
        }
        trainerItems.add(new TrainerItem(id, count));
    }

    public List<TrainerItem> getTrainerItems() {
        return trainerItems;
    }

    public void setIntroText(String introText) {
        this.introText = introText;
    }

    public void setOutroText(String outroText) {
        this.outroText = outroText;
    }

    public void setWinMoney(int winMoney) {
        this.winMoney = winMoney;
    }

    public BattleParticipants(boolean trainerFight) {
        this.isTrainerFight = trainerFight;
    }

    public void setTrainerFight(boolean isTrainerFight) {
        this.isTrainerFight = isTrainerFight;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void addPlayerPoklmon(FightPoklmon poklmon) {
        if (player == null) {
            player = poklmon;
        }
        poklmon.initTeam(false);
        playerTeam.add(poklmon);
    }

    public void addEnemyPoklmon(FightPoklmon poklmon) {
        if (enemy == null) {
            enemy = poklmon;
        }
        poklmon.initTeam(true);
        enemyTeam.add(poklmon);
    }

    public FightPoklmon getEnemy() {
        return enemy;
    }

    public FightPoklmon getPlayer() {
        return player;
    }

    public void changePlayerPoklmon(FightPoklmon pokl) {
        player = pokl;
    }

    public void changeEnemyPoklmon(FightPoklmon pokl) {
        enemy = pokl;
    }

    public boolean isTrainerFight() {
        return isTrainerFight;
    }

    public ArrayList<FightPoklmon> getEnemyTeam() {
        return enemyTeam;
    }

    public ArrayList<FightPoklmon> getPlayerTeam() {
        return playerTeam;
    }

    public void setEnemyKIType(EnemyKIType enemyKIType) {
        this.enemyKIType = enemyKIType;
    }

    public EnemyKIType getEnemyKI() {
        return enemyKIType;
    }

    public String getIntroText() {
        return introText;
    }

    public String getOutroText() {
        return outroText;
    }

    public int getWinMoney() {
        return winMoney;
    }

    public AreaType getAreaType() {
        return areaType;
    }
}
