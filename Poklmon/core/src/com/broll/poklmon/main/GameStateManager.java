package com.broll.poklmon.main;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Logger;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.DataLoader;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.main.states.AnimationDebugState;
import com.broll.poklmon.main.states.BattleDebugState;
import com.broll.poklmon.main.states.BattleState;
import com.broll.poklmon.main.states.CreditState;
import com.broll.poklmon.main.states.CustomBattleState;
import com.broll.poklmon.main.states.ExceptionState;
import com.broll.poklmon.main.states.IntroState;
import com.broll.poklmon.main.states.LoadingState;
import com.broll.poklmon.main.states.MapState;
import com.broll.poklmon.main.states.MapTransitionState;
import com.broll.poklmon.main.states.MenuDebugState;
import com.broll.poklmon.main.states.NetworkDebugState;
import com.broll.poklmon.main.states.NewGameState;
import com.broll.poklmon.main.states.TitleMenuState;
import com.broll.poklmon.transition.DefaultTransition;
import com.broll.poklmon.transition.ScreenTransition;
import com.broll.poklmon.transition.TransitionListener;
import com.broll.pokllib.game.StartInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameStateManager {

    private List<GameState> states = new ArrayList<GameState>();
    private PoklmonGame game;
    private Graphics graphics;
    private static Logger logger = new Logger(GameStateManager.class.getName());
    private Stack<WaitingTranstion> transitionStack = new Stack<WaitingTranstion>();

    public GameStateManager(PoklmonGame game) {
        this.game = game;
    }

    public void init(StartInformation startInformation, Graphics graphics) {
        this.graphics = graphics;
        DataLoader.setGraphics(graphics);
        LoadingState loadingState = new LoadingState(startInformation);
        DataContainer data = loadingState.getData();
        addState(loadingState);
        addState(new ExceptionState(game.getViewport()));
        addState(new AnimationDebugState(data));
        addState(new BattleDebugState(data));
        addState(new BattleState(data));
        addState(new CustomBattleState(data));
        addState(new CreditState(data));
        addState(new IntroState(data, TitleMenuState.class));
        addState(new MapState(data));
        addState(new MapTransitionState());
        addState(new MenuDebugState(data));
        addState(new NetworkDebugState(data));
        addState(new NewGameState(data));
        addState(new TitleMenuState(data));
        for (GameState state : states) {
            try {
                state.init(game, this, graphics, data);
            } catch (Exception e) {
                gameException(e);
            }
        }
        game.setScreen(loadingState);
    }

    public void gameException(Exception e) {
        logger.error("Exception occured in Game!", e);
        ExceptionState state = (ExceptionState) getState(ExceptionState.class);
        String currentState = game.getScreen().getClass().getSimpleName();
        state.setException(currentState, e);
        state.onEnter();
        game.setScreen(state);
    }

    private void addState(GameState state) {
        states.add(state);
    }

    public GameState getState(Class<? extends GameState> state) {
        for (GameState s : states) {
            if (s.getClass().equals(state)) {
                return s;
            }
        }
        return null;
    }

    public void transition(Class<? extends GameState> newState) {
        transition(newState, new DefaultTransition());
    }

    public void transition(Class<? extends GameState> newState, ScreenTransition transition) {
        try {
            for (GameState state : states) {
                if (state.getClass().equals(newState)) {
                    final GameState nextState = state;
                    Screen currentState = game.getScreen();
                    if (currentState instanceof GameState) {
                        GameState currentGameState = (GameState) currentState;
                        currentGameState.onExit();
                        transition.start(graphics, currentGameState, nextState, new TransitionListener() {
                            @Override
                            public void transitionFinished() {
                                game.setScreen(nextState);
                                try {
                                    nextState.onEnter();
                                    // check stack for waiting transitions
                                    if (!transitionStack.isEmpty()) {
                                        WaitingTranstion wt = transitionStack.pop();
                                        transition(wt.newState, wt.transition);
                                    }
                                } catch (Exception e) {
                                    gameException(e);
                                }
                            }
                        });
                        game.setScreen(transition);
                    } else {
                        //allready a transition running, add to transition stack
                        WaitingTranstion wt = new WaitingTranstion();
                        wt.newState = newState;
                        wt.transition = transition;
                        transitionStack.push(wt);
                    }

                }
            }
        } catch (Exception e) {
            gameException(e);
        }
    }

    private class WaitingTranstion {
        public ScreenTransition transition;
        public Class<? extends GameState> newState;
    }
}
