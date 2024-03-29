package com.broll.poklmon.game.items;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.attack.AttackAttributePlus;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.EffectStatus;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.process.CustomScriptCall;
import com.broll.poklmon.script.commands.VariableCommands;

public interface WearableItemScript {

	BattleManager getBattleManager();

	BattleProcessCore getBattleProcessCore();
	
	public void showText(String text);

	public FightPoklmon getActivePoklmon();

	public FightPoklmon getEnemyPoklmon();

	public FightPoklmon getCarrierPoklmon();
	
	public double random();

	public boolean isCarrierActive();

	public void addCallback(CustomScriptCall call);

	void removeCallbacks();

	public boolean isBool();

	public int getInt();

	public float getFloat();

	public void setBool(boolean bool);

	public void setInt(int value);

	public void setFloat(float value);
	
	public void changePoklmonAttributes(FightPoklmon target ,AttackAttributePlus type, int strength);
	
	public void doDamage(FightPoklmon target,String text, int damage);
	
	public void doHeal(FightPoklmon target,String text, int heal);
	
	public void addEffectStatus(FightPoklmon target, EffectStatus status);
	
	public void setStatusChange(FightPoklmon target, MainFightStatus status);
	
	public boolean canSetStatusChange(FightPoklmon target, MainFightStatus status);

	VariableCommands getVariableCmd();
}
