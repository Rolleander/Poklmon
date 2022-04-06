package com.broll.poklmon.battle.process.callbacks;

import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.CustomScriptCall;

public interface DamageReceivedCallback extends CustomScriptCall {

    void call(FightPoklmon poklmon, int damage);
}
