package com.broll.poklmon.game.scene.script.commands;

import java.util.HashMap;
import java.util.Iterator;

import com.broll.pokllib.script.syntax.VariableException;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.poklmon.FightPokemonBuilder;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.battle.util.ScriptValue;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.script.CommandControl;
import com.broll.poklmon.game.scene.script.Invoke;
import com.broll.poklmon.network.NetworkClient;
import com.broll.poklmon.network.NetworkEndpoint;
import com.broll.poklmon.network.NetworkException;
import com.broll.poklmon.network.NetworkServer;
import com.broll.poklmon.network.transfer.BattleInitTransfer;
import com.broll.poklmon.network.transfer.PoklmonTransfer;
import com.broll.poklmon.network.transfer.TeamTransfer;
import com.broll.poklmon.save.PoklmonData;
import com.broll.poklmon.transition.WildBattleTransition;
import com.esotericsoftware.minlog.Log;

public class NetworkCommands extends CommandControl {

	static public final int LEVEL_TRACE = 1;

	private NetworkEndpoint endpoint;
	private String errorMessage;

	public NetworkCommands(GameManager game) {
		super(game);
		Log.DEBUG();

	}

	public String openServer() throws NetworkException {
		try {
			endpoint = new NetworkServer();
			((NetworkServer) endpoint).open();
			return ((NetworkServer) endpoint).getAddress();
		} catch (NetworkException e) {
			errorMessage = e.getMessage();
			throw e;
		}
	}

	public void waitForClient() throws NetworkException {
		try {
			((NetworkServer) endpoint).waitForClient();
		} catch (NetworkException e) {
			errorMessage = e.getMessage();
			throw e;
		}
	}

	public void openClient(String serverAddress) throws NetworkException {
		try {
			endpoint = new NetworkClient();
			((NetworkClient) endpoint).open(serverAddress);

		} catch (NetworkException e) {
			errorMessage = e.getMessage();
			throw e;
		}
	}

	public void closeConnection() {
		if (endpoint != null) {
			endpoint.close();
		}
	}

	public void sendTeam() throws NetworkException {
		try {
			game.getPlayer().healTeam();
			HashMap<Integer, PoklmonData> team = game.getPlayer().getPoklmonControl().getPoklmonsInTeam();
			PoklmonData[] teamarray = new PoklmonData[team.size()];
			Iterator<Integer> keyset = team.keySet().iterator();
			int nr = 0;
			while (keyset.hasNext()) {
				teamarray[nr] = team.get(keyset.next());
				nr++;
			}
			TeamTransfer transfer = new TeamTransfer();
			transfer.team = teamarray;
			transfer.name = game.getPlayer().getData().getPlayerData().getName();
			endpoint.send(transfer);
		} catch (NetworkException e) {
			errorMessage = e.getMessage();
			throw e;
		}
	}

	public TeamTransfer receiveTeam() throws NetworkException {
		try {
			return (TeamTransfer) endpoint.receive(TeamTransfer.class);
		} catch (NetworkException e) {
			errorMessage = e.getMessage();
			throw e;
		}
	}

	public void sendPoklmon(PoklmonData poklmon) throws NetworkException {
		try {
			PoklmonTransfer transfer = new PoklmonTransfer();
			transfer.data = poklmon;
			endpoint.send(transfer);
		} catch (NetworkException e) {
			errorMessage = e.getMessage();
			throw e;
		}
	}

	public PoklmonData receivePoklmon() throws NetworkException {
		try {
			PoklmonTransfer transfer = (PoklmonTransfer) endpoint.receive(PoklmonTransfer.class);
			return transfer.data;
		} catch (NetworkException e) {
			errorMessage = e.getMessage();
			throw e;
		}
	}

	public void send(Object object) throws NetworkException {
		try {
			endpoint.send(object);
		} catch (NetworkException e) {
			errorMessage = e.getMessage();
			throw e;
		}
	}

	public BattleInitTransfer createBattleInit() {
		BattleInitTransfer transfer = new BattleInitTransfer();
		transfer.seed = System.currentTimeMillis();
		return transfer;
	}

	public BattleInitTransfer receiveBattleInit() throws NetworkException {
		try {
			return (BattleInitTransfer) endpoint.receive(BattleInitTransfer.class);
		} catch (NetworkException e) {
			errorMessage = e.getMessage();
			throw e;
		}
	}

	public int startNetworkBattle(BattleInitTransfer init, TeamTransfer enemyTeam) {
		BattleParticipants participants = new BattleParticipants();
		long seed = init.seed;
		PoklmonData[] team = enemyTeam.team;
		for (int i = 0; i < team.length; i++) {
			FightPoklmon trainer = FightPokemonBuilder.createPlayerPoklmon(game.getData(), team[i]);
			participants.addEnemyPoklmon(trainer);
		}
		String enemyName = enemyTeam.name;
		return startNetworkBattle(participants, endpoint, seed, enemyName, 0);
	}

	private int startNetworkBattle(final BattleParticipants battleParticipants,final NetworkEndpoint endpoint, final long seed,
			String enemyName, int winMoney) {
		battleParticipants.setIntroText(null);
		battleParticipants.setOutroText(null);
		battleParticipants.setWinMoney(winMoney);
		battleParticipants.setTrainerFight(true);
		battleParticipants.setEnemyName(enemyName);
		final ScriptValue returner = new ScriptValue(0);
		invoke(new Invoke() {
			@Override
			public void invoke() throws VariableException {
				game.startNetworkBattle(battleParticipants, endpoint, seed, new WildBattleTransition(),
						new BattleEndListener() {
							@Override
							public void battleWon() {
								game.getPlayer().healTeam();
								returner.value = 0;
								resume();
							}

							@Override
							public void battleLost() {
								game.getPlayer().healTeam();
								returner.value = 1;
								resume();
							}

							@Override
							public void battleEnd() {
								game.getPlayer().healTeam();
								returner.value = 2;
								resume();
							}
						});
			}
		});
		return returner.value;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
