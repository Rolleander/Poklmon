package com.broll.poklmon.battle.poklmon;

import com.broll.pokllib.poklmon.ElementType;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.poklmon.extra.RarePoklmonCalc;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;
import com.broll.poklmon.data.basics.Image;

public abstract class FightPoklmon {

    protected String name;
    protected Image image;
    protected int level;
    protected PoklmonStatusChanges statusChanges = new PoklmonStatusChanges();
    protected FightAttributes attributes;
    protected Poklmon poklmon;
    protected FightAttack[] attacks;
    protected boolean isFlying;
    protected boolean hasPowerfulGenes;
    protected boolean isFighting = false;
    protected int carryItem = -1;
    protected int poklball = 0;
    private boolean participating = false;
    private boolean enemy = false;
    protected short[] dv, fp;
    protected PoklmonWesen wesen;

    public FightPoklmon(Poklmon poklmon, int level) {
        this.poklmon = poklmon;
        this.level = level;
        // check for flying type
        if (poklmon.getBaseType() == ElementType.FLYING || poklmon.getSecondaryType() == ElementType.FLYING) {
            isFlying = true;
        }
    }

    protected void initStats() {
        this.attributes = FightPokemonBuilder.createAttributes(poklmon, level, dv, fp, wesen);
        this.hasPowerfulGenes = RarePoklmonCalc.hasPowerfulGenes(dv);
    }

    public void initTeam(boolean enemy) {
        this.enemy = enemy;
    }

    public short[] getDv() {
        return dv;
    }

    public short[] getFp() {
        return fp;
    }

    public PoklmonWesen getWesen() {
        return wesen;
    }

    public boolean isInPlayerTeam() {
        return !enemy;
    }

    public boolean isInEnemyTeam() {
        return enemy;
    }

    public int getPoklball() {
        return poklball;
    }

    public void setPoklball(int poklball) {
        this.poklball = poklball;
    }

    public void setCarryItem(int carryItem) {
        this.carryItem = carryItem;
    }

    public int getCarryItem() {
        return carryItem;
    }

    public boolean isFighting() {
        return isFighting;
    }

    public void setFighting(boolean isFighting) {
        this.isFighting = isFighting;
        if (isFighting) {
            participating = true;
        }
    }

    public boolean isPoklmonType(ElementType type) {
        if (poklmon.getBaseType() == type) {
            return true;
        }
        if (poklmon.getSecondaryType() == type) {
            return true;
        }
        return false;
    }

    public void setMainStatus(MainFightStatus mainStatus) {
        statusChanges.setStatus(mainStatus);
    }

    public void healMainStatus() {
        statusChanges.healStatus();
    }

    public boolean noApLeft() {
        for (int i = 0; i < 4; i++) {
            if (attacks[i] != null) {
                if (attacks[i].getAp() > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void useAttack(FightAttack attack) {
        for (int i = 0; i < 4; i++) {
            if (attacks[i] == attack) {
                useAttack(i);
            }
        }
    }

    protected void useAttack(int nr) {
        attacks[nr].use();
    }

    protected void updateHealth(int kp) {
        attributes.setHealth(kp);
    }

    public void doDamage(int damage) {
        int health = attributes.getHealth();
        health -= damage;
        if (health <= 0) {
            health = 0;
            setMainStatus(MainFightStatus.FAINTED);

        }
        updateHealth(health);
    }

    public boolean isFullHealth() {
        return attributes.getHealthPercent() >= 1;
    }

    public boolean isFainted() {
        return attributes.getHealth() == 0;
    }

    public void doHeal(int heal) {
        int health = attributes.getHealth();
        if (health <= 0 && heal > 0) {
            // remove fainted status
            setMainStatus(null);
        }
        health += heal;
        if (health >= attributes.getMaxhealth()) {
            health = attributes.getMaxhealth();
        }
        updateHealth(health);
    }

    public Poklmon getPoklmon() {
        return poklmon;
    }

    public FightAttack[] getAttacks() {
        return attacks;
    }

    public FightAttributes getAttributes() {
        return attributes;
    }

    public Image getImage() {
        return image;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public void setAttacks(FightAttack[] attacks) {
        this.attacks = attacks;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public PoklmonStatusChanges getStatusChanges() {
        return statusChanges;
    }

    public boolean isHasPowerfulGenes() {
        return hasPowerfulGenes;
    }

    public boolean isFullKP() {
        return attributes.getHealthPercent() >= 1;
    }

    public boolean isParticipating() {
        return participating;
    }

}
