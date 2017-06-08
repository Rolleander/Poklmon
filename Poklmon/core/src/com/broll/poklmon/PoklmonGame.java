package com.broll.poklmon;

import java.io.File;
import java.util.Timer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.TouchIconsRender;
import com.broll.poklmon.input.InputReceiver;
import com.broll.poklmon.main.GameStateManager;
import com.broll.poklmon.main.StartInformation;
import com.broll.poklmon.main.SystemClock;
import com.broll.poklmon.resource.ResourceUtils;

public class PoklmonGame extends Game {

	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	public final static int FPS = 60;

	public static boolean DEBUG_MODE = false;
	public static boolean TOUCH_MODE=false;
	private Timer timer;
	private GameStateManager stateManager;
	private StartInformation startInformation;
	private Graphics graphics;
	private OrthographicCamera camera;
	private InputReceiver input;
	private Viewport viewport;

	public PoklmonGame(StartInformation startInformation) {
		this.startInformation = startInformation;
	}

	@Override
	public void create() {
		System.out.println("Init Game...");
		timer = new Timer();
		timer.schedule(new SystemClock(), 0, 1000);
		File dataPath = startInformation.getDataPath();
		if (dataPath == null) {
			dataPath = new File("data");
		}
		TOUCH_MODE=startInformation.isTouchControling();
		DEBUG_MODE=startInformation.isDebugGame();
		TouchIconsRender.init();
		ResourceUtils.setDataPath(dataPath);
		System.out.println("Start Poklmon Game on Path: " + dataPath.getAbsolutePath());
		stateManager = new GameStateManager(this);
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport =new StretchViewport(PoklmonGame.WIDTH,PoklmonGame.HEIGHT,camera);
		camera.position.set(PoklmonGame.WIDTH/2,PoklmonGame.HEIGHT/2,0);
		graphics = new Graphics();
		input = new InputReceiver(viewport);
		Gdx.input.setInputProcessor(input);
		stateManager.init(startInformation, graphics);
		// camera.translate(-PoklmonGame.WIDTH/2, PoklmonGame.HEIGHT/2);
	}

	@Override
	public void render() {
		graphics.prepareRender(camera);
		graphics.sb.begin();
		super.render();
		graphics.sb.end();
	}

	@Override
	public void dispose() {
		timer.cancel();
		timer = null;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width,height);
	}

	public InputReceiver getInput() {
		return input;
	}

}
