package com.broll.poklmon.main;

import java.util.ArrayList;
import java.util.List;

import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.DataLoader;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.main.states.AnimationDebugState;
import com.broll.poklmon.main.states.BattleDebugState;
import com.broll.poklmon.main.states.BattleState;
import com.broll.poklmon.main.states.CreditState;
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

public class GameStateManager {

	private List<GameState> states = new ArrayList<GameState>();
	private PoklmonGame game;
	private Graphics graphics;

	public GameStateManager(PoklmonGame game) {
		this.game = game;
	}

	public void init(StartInformation startInformation, Graphics graphics) {
		this.graphics = graphics;
		DataLoader.setGraphics(graphics);
		LoadingState loadingState = new LoadingState(startInformation);
		DataContainer data = loadingState.getData();
		addState(loadingState);
		addState(new AnimationDebugState(data));
		addState(new BattleDebugState(data));
		addState(new BattleState(data));
		addState(new CreditState(data));
		addState(new IntroState(data, TitleMenuState.class));
		addState(new MapState(data));
		addState(new MapTransitionState());
		addState(new MenuDebugState(data));
		addState(new NetworkDebugState(data));
		addState(new NewGameState(data));
		addState(new TitleMenuState(data));
		for (GameState state : states) {
			state.init(game, this, graphics, data);
		}
		game.setScreen(loadingState);
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
		for (GameState state : states) {
			if (state.getClass().equals(newState)) {
				final GameState nextState = state;
				GameState currentState = (GameState) game.getScreen();
				currentState.onExit();
				transition.start(graphics, currentState, nextState, new TransitionListener() {
					@Override
					public void transitionFinished() {
						game.setScreen(nextState);
						nextState.onEnter();
					}
				});
				game.setScreen(transition);
			}
		}

	}
}
