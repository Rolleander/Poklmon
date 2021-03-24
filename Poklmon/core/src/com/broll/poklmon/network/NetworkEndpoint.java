package com.broll.poklmon.network;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackDamage;
import com.broll.pokllib.attack.AttackPriority;
import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.poklmon.EXPLearnTypes;
import com.broll.pokllib.poklmon.ElementType;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.pokllib.poklmon.PoklmonAttributes;
import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.battle.attack.FightAttack;
import com.broll.poklmon.battle.poklmon.FightAttributes;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.battle.poklmon.states.PoklmonStatusChanges;
import com.broll.poklmon.battle.util.BattleMove;
import com.broll.poklmon.battle.util.BattleMoveType;
import com.broll.poklmon.network.transfer.BattleInitTransfer;
import com.broll.poklmon.network.transfer.BattleMoveTransfer;
import com.broll.poklmon.network.transfer.PoklmonTransfer;
import com.broll.poklmon.network.transfer.TeamTransfer;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.PoklmonData;
import com.broll.poklmon.save.PoklmonStatistic;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class NetworkEndpoint {

	protected final static int PORT = 54555;
	protected Object receivedMessage;
	protected boolean received = false;
	protected boolean disconnect = false;

	public abstract void send(Object object) throws NetworkException;

	protected void register(Kryo kryo) {
		kryo.register(BattleInitTransfer.class);

		kryo.register(BattleMoveTransfer.class);
		kryo.register(BattleMove.class);
		kryo.register(BattleMoveType.class);
		kryo.register(FightAttack.class);
		kryo.register(Attack.class);
		kryo.register(AttackType.class);
		kryo.register(ElementType.class);
		kryo.register(AttackDamage.class);
		kryo.register(AttackPriority.class);
		kryo.register(FightPoklmon.class);
		kryo.register(PlayerPoklmon.class);
		kryo.register(PoklmonStatusChanges.class);
		kryo.register(MainFightStatus.class);
		kryo.register(HashMap.class);
		kryo.register(FightAttributes.class);
		kryo.register(Poklmon.class);
		kryo.register(PoklmonAttributes.class);
		kryo.register(ElementType.class);
		kryo.register(EXPLearnTypes.class);
		kryo.register(ElementType.class);
		kryo.register(ArrayList.class);
		kryo.register(FightAttack[].class);
		kryo.register(PoklmonTransfer.class);
		kryo.register(PoklmonData.class);
		kryo.register(AttackData[].class);
		kryo.register(AttackData.class);
		kryo.register(MainFightStatus.class);
		kryo.register(PoklmonWesen.class);
		kryo.register(PoklmonStatistic.class);
		kryo.register(short[].class);

		kryo.register(TeamTransfer.class);
		kryo.register(PoklmonData[].class);
		
	}

	public Object receive(Class wanted) throws NetworkException {
		while (true) {
			if (disconnect) {
				disconnect = false;
				throw new NetworkException("Disconnected while waiting for Response!");
			}
			if (received) {
				received = false;
				if (wanted.isInstance(receivedMessage)) {
					return receivedMessage;
				} else {
					throw new NetworkException("Received wrong Package: [" + receivedMessage.getClass().getSimpleName()
							+ "]");
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void onReceive(Object receive) {
		if (!KeepAlive.class.isInstance(receive)) {
			this.receivedMessage = receive;
			received = true;
		}
	}

	public abstract void close();
}
