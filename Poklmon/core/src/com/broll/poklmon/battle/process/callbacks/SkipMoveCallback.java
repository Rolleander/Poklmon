package com.broll.poklmon.battle.process.callbacks;

import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.CustomScriptCall;

public interface SkipMoveCallback extends CustomScriptCall {
    boolean call(FightPoklmon poklmon);
}
