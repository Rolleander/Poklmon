package com.broll.poklmon.battle.util;

import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.poklmon.FightPoklmon;

public class BattleMove {

	private BattleMoveType moveType;

	private FightAttack attack;
	private FightPoklmon switchTo;
	private FightPoklmon itemTarget;
	private int itemId;

	public BattleMove(FightAttack attack) {
		this.attack = attack;
		this.moveType = BattleMoveType.ATTACK;
	}

	public BattleMove(FightPoklmon swawpPoklmon) {
		this.switchTo = swawpPoklmon;
		this.moveType = BattleMoveType.CHANGE_POKLMON;
	}

	public BattleMove(FightPoklmon target, int itemId) {
		this.moveType = BattleMoveType.USE_ITEMS;
		this.itemTarget = target;
		this.itemId=itemId;
	}

	public BattleMove() {
		this.moveType = BattleMoveType.TRY_ESCAPE;
	}

	public int getItemId() {
		return itemId;
	}

	public FightPoklmon getItemTarget() {
		return itemTarget;
	}

	public BattleMoveType getMoveType() {
		return moveType;
	}

	public FightAttack getAttack() {
		return attack;
	}

	public FightPoklmon getSwitchTo() {
		return switchTo;
	}
}
