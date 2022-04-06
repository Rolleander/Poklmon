package com.broll.poklmon.battle.process.callbacks;

import com.broll.poklmon.battle.attack.UseAttack;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.CustomScriptCall;

public interface SkipAttackCallback extends CustomScriptCall {
    boolean call(FightPoklmon poklmon, UseAttack attack);
}
