package com.broll.poklmon.battle;

import java.util.ArrayList;
import java.util.List;

import com.broll.pokllib.poklmon.TypeComperator;
import com.broll.poklmon.battle.enemy.EnemyMoveSelection;
import com.broll.poklmon.battle.field.FieldEffects;
import com.broll.poklmon.battle.player.PlayerMoveSelection;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.process.BattleProcessCore;
import com.broll.poklmon.battle.process.CustomScriptCall;
import com.broll.poklmon.battle.render.BattleRender;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.battle.util.BattleRandom;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.main.SystemClock;
import com.broll.poklmon.network.NetworkEndpoint;
import com.broll.poklmon.player.Player;
import com.esotericsoftware.minlog.Log;

public class BattleManager {

	private BattleEndListener endListener;
	private PlayerMoveSelection playerMoveSelection;
	private EnemyMoveSelection enemyMoveSelection;
	private BattleRender battleRender;
	private BattleParticipants participants;
	private DataContainer data;
	private FieldEffects fieldEffects;
	private Thread battleThread;
	private Player player;
	private List<CustomScriptCall> scriptCalls;
	private boolean networkBattle=false;

	public BattleManager(DataContainer data, Player player) {
		TypeComperator.init(data.getMisc().getTypeTable());
		this.data = data;
		this.player = player;
		battleRender = new BattleRender(this);
		playerMoveSelection = new PlayerMoveSelection(this);
		enemyMoveSelection = new EnemyMoveSelection(this);
	}

	public void debugInit(BattleParticipants participants) {
		fieldEffects = new FieldEffects(participants);
		this.participants = participants;
		battleRender.init();
	}

	private void initBattle(BattleParticipants participants, BattleEndListener end) {
		this.participants = participants;
		scriptCalls = new ArrayList<CustomScriptCall>();
		fieldEffects = new FieldEffects(participants);
		battleRender.getPoklmonRender().setEnemyPoklmonVisible(false);
		battleRender.getPoklmonRender().setPlayerPoklmonVisible(false);
		playerMoveSelection.reset();
		// check player poklmons defeated
		if (participants.getPlayer().isFainted()) {
			// find next poklmon in list, that isnt fainted
			for (int i = 1; i < participants.getPlayerTeam().size(); i++) {
				if (!participants.getPlayerTeam().get(i).isFainted()) {
					participants.changePlayerPoklmon(participants.getPlayerTeam().get(i));
					break;
				}
			}
		}
	}

	private void startCore(BattleProcessCore core, BattleEndListener end) {
		// init ki
		enemyMoveSelection.initKI(core, participants);
		this.endListener = end;
		battleRender.init();
		battleThread = new Thread(core);
		battleThread.start();
	}

	public void startNetworkBattle(NetworkEndpoint networkEndpoint, long seed, BattleParticipants participants,
			BattleEndListener end) {
		networkBattle=true;
		BattleRandom.init(seed);
		initBattle(participants, end);
		BattleProcessCore core = new BattleProcessCore(this);
		core.initNetwork(networkEndpoint);
		startCore(core, end);
	}

	public void startBattle(BattleParticipants participants, BattleEndListener end) {
		networkBattle=false;
		BattleRandom.init();
		initBattle(participants, end);
		BattleProcessCore core = new BattleProcessCore(this);
		startCore(core, end);
	}

	public void update(float delta) {
		if (SystemClock.isTick()) {
			player.getData().getGameVariables().setPlayTime(player.getData().getGameVariables().getPlayTime() + 1);
		}
		battleRender.update(delta);
		playerMoveSelection.update(delta);
	}

	public void render(Graphics g) {
		battleRender.render(g);
		playerMoveSelection.render(g);
	}

	public EnemyMoveSelection getEnemyMoveSelection() {
		return enemyMoveSelection;
	}

	public PlayerMoveSelection getPlayerMoveSelection() {
		return playerMoveSelection;
	}

	public BattleRender getBattleRender() {
		return battleRender;
	}

	public BattleEndListener getEndListener() {
		return endListener;
	}

	public BattleParticipants getParticipants() {
		return participants;
	}

	public DataContainer getData() {
		return data;
	}

	public FieldEffects getFieldEffects() {
		return fieldEffects;
	}

	public Player getPlayer() {
		return player;
	}

	public List<CustomScriptCall> getScriptCalls() {
		return scriptCalls;
	}
	
	public boolean isNetworkBattle() {
		return networkBattle;
	}

}
