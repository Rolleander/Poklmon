package com.broll.poklmon;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.broll.pokllib.game.StartInformation;
import com.broll.poklmon.data.DataLoader;
import com.broll.poklmon.data.MusicContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.TouchIconsRender;
import com.broll.poklmon.input.InputReceiver;
import com.broll.poklmon.input.TouchInputReceiver;
import com.broll.poklmon.main.GameStateManager;
import com.broll.poklmon.main.SystemClock;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.resource.ResourceUtils;
import com.broll.poklmon.script.TimerUtils;

import java.io.File;
import java.util.Timer;

public class PoklmonGame extends Game {

    public final static int WIDTH = 800;
    public final static int HEIGHT = 600;
    public final static int FPS = 80;
    
    public static boolean DEBUG_MODE = false;
    public static boolean TOUCH_MODE = false;
    private final static Logger logger = new Logger(PoklmonGame.class.getName());
    private GameStateManager stateManager;
    private StartInformation startInformation;
    private Graphics graphics;
    private OrthographicCamera camera;
    private InputReceiver input;
    private Viewport viewport;
    private Viewport touchViewport;
    private SpriteBatch touchSpriteBatch;

    public PoklmonGame(StartInformation startInformation) {
        this.startInformation = startInformation;
    }

    @Override
    public void create() {
        logger.setLevel(Gdx.app.getLogLevel());
        logger.info("Init Game...");
        File dataPath = startInformation.getDataPath();
        if (dataPath == null) {
            dataPath = new File("data");
        }
        if (startInformation.isDebugGame()) {
            Gdx.app.setLogLevel(Application.LOG_INFO);
        }
        TOUCH_MODE = startInformation.isTouchControling();
        DEBUG_MODE = startInformation.isDebugGame();

        ResourceUtils.setDataPath(dataPath);
        logger.info("Start Poklmon Game on Path: " + dataPath.getAbsolutePath());
        stateManager = new GameStateManager(this);
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(PoklmonGame.WIDTH, PoklmonGame.HEIGHT, camera);
        camera.position.set(PoklmonGame.WIDTH / 2, PoklmonGame.HEIGHT / 2, 0);
        graphics = new Graphics();
        touchSpriteBatch = new SpriteBatch();
        input = new InputReceiver(viewport);
        if (TOUCH_MODE) {
            touchViewport = new ScreenViewport();
            TouchInputReceiver touchInputReceiver = new TouchInputReceiver(touchViewport);
            Gdx.input.setInputProcessor(new InputMultiplexer(touchInputReceiver, input));
            TouchIconsRender.init(touchSpriteBatch, touchViewport);
        } else {
            Gdx.input.setInputProcessor(input);
        }
        stateManager.init(startInformation, graphics);
        // camera.translate(-PoklmonGame.WIDTH/2, PoklmonGame.HEIGHT/2);
    }

    @Override
    public void render() {
        viewport.apply();
        graphics.prepareRender(camera);
        graphics.sb.begin();
        TimerUtils.update();
        super.render();
        graphics.sb.end();
        if (PoklmonGame.TOUCH_MODE) {
            touchViewport.apply();
            TouchIconsRender.render();
        }
    }

    @Override
    public void dispose() {
        logger.info("Dispose Game");
        stateManager.dispose();
        MusicContainer.dispose();
        DataLoader.dispose();
        GUIFonts.dispose();
        graphics.dispose();
        touchSpriteBatch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        if (touchViewport != null) {
            touchViewport.update(width, height);
        }
    }

    public InputReceiver getInput() {
        return input;
    }

    public Viewport getViewport() {
        return viewport;
    }
}
