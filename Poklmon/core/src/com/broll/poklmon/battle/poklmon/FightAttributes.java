package com.broll.poklmon.battle.poklmon;

public class FightAttributes {

    //attribute values
    private int health, maxhealth;
    private int attack;
    private int defence;
    private int init;
    private int special_attack;
    private int special_defence;


    //attribute plus
    private int attackPlus = 0;
    private int defencePlus = 0;
    private int initPlus = 0;
    private int special_attackPlus = 0;
    private int special_defencePlus = 0;
    private int fluchtwertPlus = 0;
    private int genauigkeitsPlus = 0;


    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getInit() {
        return init;
    }

    public int getSpecial_attack() {
        return special_attack;
    }

    public int getSpecial_defence() {
        return special_defence;
    }


    public int getFluchtwertPlus() {
        return fluchtwertPlus;
    }

    public int getGenauigkeitsPlus() {
        return genauigkeitsPlus;
    }


    public float getHealthPercent() {
        float p = (float) health / (float) maxhealth;
        if (p < 0) {
            p = 0;
        } else if (p > 1) {
            p = 1;
        }
        return p;
    }

    public void fullHealh(){
        setHealth(getMaxhealth());
    }

    //default

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxhealth() {
        return maxhealth;
    }

    public void setMaxhealth(int maxhealth) {
        this.maxhealth = maxhealth;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public void setSpecial_attack(int special_attack) {
        this.special_attack = special_attack;
    }

    public void setSpecial_defence(int special_defence) {
        this.special_defence = special_defence;
    }

    public int getAttackPlus() {
        return attackPlus;
    }

    public void setAttackPlus(int attackPlus) {
        this.attackPlus = attackPlus;
    }

    public int getDefencePlus() {
        return defencePlus;
    }

    public void setDefencePlus(int defencePlus) {
        this.defencePlus = defencePlus;
    }

    public int getInitPlus() {
        return initPlus;
    }

    public void setInitPlus(int initPlus) {
        this.initPlus = initPlus;
    }

    public int getSpecial_attackPlus() {
        return special_attackPlus;
    }

    public void setSpecial_attackPlus(int special_attackPlus) {
        this.special_attackPlus = special_attackPlus;
    }

    public int getSpecial_defencePlus() {
        return special_defencePlus;
    }

    public void setSpecial_defencePlus(int special_defencePlus) {
        this.special_defencePlus = special_defencePlus;
    }

    public void setFluchtwertPlus(int fluchtwertPlus) {
        this.fluchtwertPlus = fluchtwertPlus;
    }

    public void setGenauigkeitsPlus(int genauigkeitsPlus) {
        this.genauigkeitsPlus = genauigkeitsPlus;
    }


}
