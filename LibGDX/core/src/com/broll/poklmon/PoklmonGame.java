package com.broll.poklmon;

import java.io.File;
import java.util.Timer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.input.InputReceiver;
import com.broll.poklmon.main.GameStateManager;
import com.broll.poklmon.main.StartInformation;
import com.broll.poklmon.main.SystemClock;
import com.broll.poklmon.resource.ResourceUtils;

public class PoklmonGame extends Game {

	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	public final static int FPS = 60;

	public static boolean DEBUG_MODE = true;
	private Timer timer;
	private GameStateManager stateManager;
	private StartInformation startInformation;
	private Graphics graphics;
	private OrthographicCamera camera;
	private InputReceiver input;

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
			dataPath = new File(Gdx.files.getLocalStoragePath() + "/data");
			System.out.println(dataPath.getAbsolutePath());
		}
		ResourceUtils.setDataPath(dataPath);
		System.out.println("Start Poklmon Game on Path: " + dataPath.getAbsolutePath());
		stateManager = new GameStateManager(this);
		camera = new OrthographicCamera(800, 600);
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		graphics = new Graphics();
		input = new InputReceiver();
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

	public InputReceiver getInput() {
		return input;
	}

}
