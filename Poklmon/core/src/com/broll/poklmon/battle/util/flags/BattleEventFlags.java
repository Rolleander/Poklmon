package com.broll.poklmon.battle.util.flags;

import com.broll.pokllib.attack.Attack;
import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.data.DataContainer;

public class BattleEventFlags
{
    public final static int NONE = -1;

    private FightPoklmonFlags playerFlags = new FightPoklmonFlags();
    private FightPoklmonFlags enemyFlags = new FightPoklmonFlags();

    private DataContainer data;
    private BattleManager battle;

    public BattleEventFlags(BattleManager battle)
    {
        this.data = battle.getData();
        this.battle = battle;
    }

    public void poklmonUsedAttack(FightPoklmon fightPoklmon, FightAttack attack)
    {
        Attack atk = attack.getAttack();
        int id = data.getAttacks().getAttackID(atk);
        if (id != -1)
        {
            getPoklmonFlags(fightPoklmon).setLastAttackID(id);
        }
    }

    public void poklmonTookDamage(FightPoklmon poklmon, DamageTaken damageTaken)
    {
        getPoklmonFlags(poklmon).setLastDamageTaken(damageTaken);
    }

    public FightPoklmonFlags getPoklmonFlags(FightPoklmon fightPoklmon)
    {
        if (fightPoklmon == battle.getParticipants().getPlayer())
        {
            return playerFlags;
        }
        else
        {
            return enemyFlags;
        }
    }


}
