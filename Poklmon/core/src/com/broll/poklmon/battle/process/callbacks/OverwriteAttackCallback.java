package com.broll.poklmon.battle.process.callbacks;

import com.broll.pokllib.attack.Attack;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.CustomScriptCall;
import com.broll.poklmon.battle.process.callbacks.ojects.OverwriteAttackSettings;

public interface OverwriteAttackCallback extends CustomScriptCall {

    void call(FightPoklmon poklmon, OverwriteAttackSettings settings);

}
