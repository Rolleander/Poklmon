package com.broll.poklmon.battle.process.callbacks.ojects;

import com.broll.pokllib.attack.Attack;

public class OverwriteAttackSettings {

    private Attack attack;
    private boolean allowOtherMoves = true;

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    public void setAllowOtherMoves(boolean allowOtherMoves) {
        this.allowOtherMoves = allowOtherMoves;
    }

    public Attack getAttack() {
        return attack;
    }

    public boolean isAllowOtherMoves() {
        return allowOtherMoves;
    }
}
