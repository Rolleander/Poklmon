package com.broll.poklmon.game;

import java.util.Iterator;
import java.util.List;

import com.broll.pokllib.item.Item;
import com.broll.pokllib.item.ItemType;
import com.broll.pokllib.map.MapFile;
import com.broll.poklmon.battle.BattleParticipants;
import com.broll.poklmon.battle.util.BattleEndListener;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.game.control.MessageGuiControl;
import com.broll.poklmon.game.scene.ObjectTriggerCheck;
import com.broll.poklmon.game.scene.SceneProcessManager;
import com.broll.poklmon.game.scene.ScriptInstance;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.gui.TouchIconsRender;
import com.broll.poklmon.main.GameStateManager;
import com.broll.poklmon.main.SystemClock;
import com.broll.poklmon.main.states.MapState;
import com.broll.poklmon.main.states.MapTransitionState;
import com.broll.poklmon.map.MapContainer;
import com.broll.poklmon.map.Viewport;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.map.object.MapObjectLoader;
import com.broll.poklmon.network.NetworkEndpoint;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.player.TeleportDestination;
import com.broll.poklmon.save.GameData;
import com.broll.poklmon.transition.ScreenTransition;

public class GameManager {

	private DataContainer data;
	private MapContainer map;
	private Player player;
	private List<MapObject> objects;
	private Viewport viewport;
	private SceneProcessManager sceneProcessManager;
	private MessageGuiControl messageGuiControl;
	private ObjectListRenderer objectRenderer;
	private boolean menuOpen = false, gameOver = false;
	private GameStateManager gameStateManager;
	private MapState mapState;
	private PlayerMovementTrigger playerMovementTrigger;
	public static long TRIGGER_AGAIN_DELAY_TIMER = 0;
	public static long TRIGGER_AGAIN_DELAY = 200; // delay in ms before event
													// can be triggered again

	public GameManager(DataContainer data, GameStateManager gameStateManager, MapState mapState) {
		this.data = data;
		this.gameStateManager = gameStateManager;
		this.mapState=mapState;
		objectRenderer = new ObjectListRenderer();
		viewport = new Viewport();
		map = new MapContainer(this);
		player = new Player(this);
		sceneProcessManager = new SceneProcessManager(this);
		messageGuiControl = new MessageGuiControl(this, sceneProcessManager);
		playerMovementTrigger = new PlayerMovementTrigger(this);
		player.getOverworld().setMovementListener(playerMovementTrigger);
	}


	public void startGame(GameData data) {
		player.init(data);
		messageGuiControl.initMenu();
		// open current map
		int mapID = data.getPlayerData().getMapNr();
		int mapX = data.getPlayerData().getXpos();
		int mapY = data.getPlayerData().getYpos();
		enterMap(mapID, mapX, mapY);
	}

	public void enterMap(int mapID, int mapX, int mapY) {
		// load map
		MapFile mapFile = map.openMap(mapID, mapX, mapY);
		// load objects
		objects = MapObjectLoader.loadObjects(mapFile, data, map);
		// start init scripts
		Iterator<MapObject> iterator = objects.iterator();
		while (iterator.hasNext()) {
			MapObject object = iterator.next();
			boolean destroyObject = sceneProcessManager.runInitScript(object, object.getInitScript());
			if (destroyObject) {
				object.setBlocking(false);
				iterator.remove();
			} else {
				object.doneInit();
			}
		}
		player.getOverworld().setBlocking(true);
	}

	public void teleportPlayer(TeleportDestination teleportDestination) {
		data.getSounds().playSound("teleport");
		if (teleportDestination.getDir() != null && teleportDestination.getMap() != map.getMapId()) {
			player.getOverworld().setDirection(teleportDestination.getDir());
		}
		MapTransitionState state = (MapTransitionState) gameStateManager.getState(MapTransitionState.class);
		gameStateManager.transition(MapTransitionState.class);
		state.teleportPlayer(this, teleportDestination);
	}

	public void gameOver() {
		gameOver = true;
		player.getData().getPlayerData().playerDied();
		MapTransitionState state = (MapTransitionState) gameStateManager.getState(MapTransitionState.class);
		state.gameOverTransition();
		gameStateManager.transition(MapTransitionState.class);
		state.teleportPlayer(this, player.getRecoveryLocation());
	}

	public void recoverPlayer() {
		gameOver = false;
		player.healTeam();
		messageGuiControl.showRecoverDialog();
	}

	private void prepareBattle(BattleParticipants participants) {
		data.getMusics().stop();
		data.getSounds().playSound("battleintro");
		player.getOverworld().setMovementAllowed(false);
		player.addTeamToFight(participants);
		participants.setPlayerName(player.getData().getPlayerData().getName());
	}

	public void startBattle(BattleParticipants participants, ScreenTransition transition, BattleEndListener endListener) {
		prepareBattle(participants);
		mapState.startPoklmonBattle(participants, transition, endListener);
	}

	public void startNetworkBattle(BattleParticipants participants, NetworkEndpoint enpoint, long seed,
			ScreenTransition transition, BattleEndListener endListener) {
		prepareBattle(participants);
		mapState.startNetworkPoklmonBattle(enpoint, seed, participants, transition, endListener);
	}

	public void renderGame(Graphics g) {
		map.render(g);
		objectRenderer.render(g, viewport, player, objects);
		map.renderOverlay(g);
		messageGuiControl.render(g);
	}

	public void updateGame(long longDelta, float delta) {
		if (SystemClock.isTick()) {
			player.getData().getGameVariables().setPlayTime(player.getData().getGameVariables().getPlayTime() + 1);
		}
		player.update(delta);
		for (MapObject object : objects) {
			object.update(delta);
		}
		map.update(delta);
		messageGuiControl.update(delta);
		// check player actions
		checkPlayerActions();
		if (TRIGGER_AGAIN_DELAY_TIMER > 0) {
			TRIGGER_AGAIN_DELAY_TIMER -= longDelta;
		}
		GUIUpdate.consume();
	}

	private void checkPlayerActions() {
		if (!sceneProcessManager.isScriptRunning()) {
			if (!menuOpen) {
				if (!player.getOverworld().isMoving()) {
					playerMovementTrigger.update();
					if (!playerMovementTrigger.isTriggered()) {
						if (TRIGGER_AGAIN_DELAY_TIMER <= 0) {
							if (GUIUpdate.isClick()) {
								// check interactions
								MapObject interaction = ObjectTriggerCheck.checkClickTrigger(player, objects, map);
								if (interaction != null) {
									// start trigger script
									sceneProcessManager.runScript(new ScriptInstance(interaction));
								}
							} else if (GUIUpdate.isCancel()) {
								// menu
								changeMenuState(true);
								messageGuiControl.openMenu();
							} else if (GUIUpdate.isShortcutA()) {
								runBasicItem(player.getVariableControl().getInt(Player.SHORTCUT + "A"));
							} else if (GUIUpdate.isShortcutB()) {
								runBasicItem(player.getVariableControl().getInt(Player.SHORTCUT + "B"));
							}
						}
					}
				}
			}
		}
	}

	private void runBasicItem(int id) {
		if (id > 0) {
			Item item = data.getItems().getItem(id);
			if (item.getType() == ItemType.BASIS_ITEM) {
				String script = item.getEffect();
				if (script != null && !script.isEmpty()) {
					messageGuiControl.getPlayerMenu().getItemExecutor().useBasisItem(item);
				}
			}
		}
	}

	public void setMovementAllowed(boolean allowed) {
		if (!allowed) {
			for(MapObject object: objects){
				object.stopMoving();
			}
		}
		for(MapObject object: objects){
			object.setMovementAllowed(allowed);
		}
		player.getOverworld().setMovementAllowed(allowed);
	}

	public void changeMenuState(boolean open) {
		menuOpen = open;
		setMovementAllowed(!open);
		if(open==false){
			checkFastItemUse();
		}
	}

	public void checkFastItemUse(){
		boolean showA=player.getVariableControl().getInt(Player.SHORTCUT + "A")>0;
		boolean showB=player.getVariableControl().getInt(Player.SHORTCUT + "B")>0;
		TouchIconsRender.hideButton(2, !showA);
		TouchIconsRender.hideButton(3, !showB);
	}

	public SceneProcessManager getSceneProcessManager() {
		return sceneProcessManager;
	}

	public List<MapObject> getObjects() {
		return objects;
	}

	public MapObject getObject(int oid) {
		for (MapObject object : objects) {
			if (object.getObjectId() == oid) {
				return object;
			}
		}
		throw new RuntimeException("Object for id " + oid + " doesnt exist!");
	}

	public DataContainer getData() {
		return data;
	}

	public MapContainer getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public MessageGuiControl getMessageGuiControl() {
		return messageGuiControl;
	}

	public void keyPressed(int key, char c) {
		messageGuiControl.keyPressed(key, c);
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public boolean isMenuOpen() {
		return menuOpen;
	}
	
	
}
