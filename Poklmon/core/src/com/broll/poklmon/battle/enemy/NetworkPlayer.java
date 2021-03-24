package com.broll.poklmon.battle.enemy;

import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.process.NetworkUtil;
import com.broll.poklmon.battle.util.BattleMove;
import com.broll.poklmon.network.NetworkException;
import com.broll.poklmon.network.transfer.BattleMoveTransfer;

public class NetworkPlayer extends EnemyKIProcess {

	public NetworkPlayer(BattleProcessCore core, BattleParticipants participants) {
		super(core, participants);
	}

	@Override
	public BattleMove processMove() throws NetworkException {
		// receive next move
		return NetworkUtil.convert(participants,
				(BattleMoveTransfer) core.getNetworkEndpoint().receive(BattleMoveTransfer.class));
	}

	@Override
	protected BattleMove process() {
		return null;
	}

	@Override
	public FightPoklmon sendNextPoklmon() throws NetworkException {
		// receive next move (new poklmon)
		BattleMove move = NetworkUtil.convert(participants,
				(BattleMoveTransfer) core.getNetworkEndpoint().receive(BattleMoveTransfer.class));
		return move.getSwitchTo();
	}

}
