package com.broll.poklmon.main.states;

import com.badlogic.gdx.Gdx;
import com.broll.pokllib.main.KryoDataControl;
import com.broll.pokllib.main.PoklLib;
import com.broll.poklmon.data.AnimationContainer;
import com.broll.poklmon.data.AttackContainer;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.DataException;
import com.broll.poklmon.data.DataLoader;
import com.broll.poklmon.data.GraphicsContainer;
import com.broll.poklmon.data.ItemContainer;
import com.broll.poklmon.data.MiscContainer;
import com.broll.poklmon.data.MusicContainer;
import com.broll.poklmon.data.PoklmonContainer;
import com.broll.poklmon.data.SoundContainer;
import com.broll.poklmon.data.TextContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.debug.DebugPlayerFactory;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.main.StartInformation;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.resource.MenuGraphics;
import com.broll.poklmon.resource.ResourceUtils;
import com.broll.poklmon.save.GameData;

import java.io.IOException;
import java.io.InputStream;

public class LoadingState extends GameState {

	private DataContainer data = new DataContainer();
	private int loadingStep = 0;
	private String loadingInfo = "Poklmons";
	private StartInformation startInformation;

	public LoadingState(StartInformation startInformation) {
		this.startInformation = startInformation;
	}

	private void finishedLoading() {
		Class nextState = IntroState.class;
		if (startInformation.isDebugGame()) {

			if (startInformation.isDebugMap()) {
				// test map
				Player.SAVING_ALLOWED = false;
				nextState = MapState.class;
				int mapNr = startInformation.getMapId();
				int mapX = startInformation.getMapX();
				int mapY = startInformation.getMapY();
				DebugPlayerFactory debugPlayerFactory = new DebugPlayerFactory(data);
				GameData gameData = debugPlayerFactory.createDebugPlayer(mapNr, mapX, mapY);
				MapState gameState = (MapState) states.getState(MapState.class);
				gameState.openGame(gameData);
			} else if (startInformation.isDebugAttack()) {
				// test attack
				nextState = BattleDebugState.class;
				BattleDebugState state = (BattleDebugState) states.getState(BattleDebugState.class);
				state.debugAttack(startInformation.getAttackId());
			} else if (startInformation.isDebugAnimation()) {
				nextState = AnimationDebugState.class;
				AnimationDebugState state = (AnimationDebugState) states.getState(AnimationDebugState.class);
				state.debugAnimation(startInformation.getAnimationId());
			} else {

				// test a scene
				nextState = startInformation.getDebugScene();
			}
		}
		states.transition(nextState);
	}

	public DataContainer getData() {
		return data;
	}

	@Override
	public void render(float delta) {
		update(delta);
		render(graphics);
	}

	@Override
	public void onInit() {
	}

	@Override
	public void onEnter() {
	}

	@Override
	public void onExit() {
	}

	@Override
	public void update(float delta) {
		try {
			switch (loadingStep) {
			case 0:
				loadingInfo = "Data";
				// load gamedata file
				KryoDataControl dataControl = new KryoDataControl();
				InputStream stream = Gdx.files.internal(ResourceUtils.DATA_PATH + "poklmon.data").read();
				try {
					dataControl.read(stream);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				PoklLib.init(dataControl);
				TextContainer.load();
				break;

			case 1:
				// Load poklmon data
				loadingInfo = "Items";
				PoklmonContainer pokldat = new PoklmonContainer(DataLoader.loadPoklmons());
				data.setPoklmons(pokldat);
				//load msic
				MiscContainer misc=new MiscContainer();
				misc.load();
				data.setMisc(misc);
				break;
			case 2:
				// Load item data
				loadingInfo = "Attacks";
				ItemContainer items = new ItemContainer(DataLoader.loadItems());
				data.setItems(items);
				break;

			case 3:
				// Load attack data
				loadingInfo = "Animations";
				AttackContainer atkdat = new AttackContainer(DataLoader.loadAttacks());
				data.setAttacks(atkdat);
				break;

			case 4:
				// Load animation data
				loadingInfo = "Graphics";
				AnimationContainer anidat = new AnimationContainer(DataLoader.loadAnimations());
				data.setAnimations(anidat);
				break;
			case 5:
				// load graphic data
				loadingInfo = "Sounds";
				GraphicsContainer graphics = new GraphicsContainer();
				graphics.load();
				data.setGraphics(graphics);
				break;

			case 6:
				// load sound data
				loadingInfo = "Design";
				SoundContainer sounds = new SoundContainer(DataLoader.loadSounds());
				data.setSounds(sounds);
				break;

			case 7:
				// load music data
				loadingInfo = "";
				MusicContainer musicdat = new MusicContainer();
				data.setMusics(musicdat);
				// load fonts
				GUIFonts.loadFonts();
				// load design
				GUIDesign.loadDesign();
				MenuGraphics.loadGraphics();
				break;

			case 8:
				// finished loading
				System.out.println("Finished Loading Game!");
				finishedLoading();
				break;
			}
			loadingStep++;
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	@Override
	public void render(Graphics g) {
		float width = 350;
		float height = 350;
		float x = 400 - width / 2;
		float y = 300 - height / 2;

		float start = 0;
		float end = ((float) loadingStep / 7) * 360;
		g.setColor(ColorUtil.newColor(50, 50, 50, 255));
		g.fillArc(x, y, height / 2, start, end);

		g.setColor(ColorUtil.newColor(200, 200, 200, 255));
		g.drawString("Loading " + loadingInfo + "...", 5, 580);
	}

}
