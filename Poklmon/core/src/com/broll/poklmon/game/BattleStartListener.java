package com.broll.poklmon.game;

import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.transition.ScreenTransition;

public interface BattleStartListener
{

    
    public void startBattle(BattleParticipants participants,ScreenTransition transition, BattleEndListener battleEndListener);
    
}
