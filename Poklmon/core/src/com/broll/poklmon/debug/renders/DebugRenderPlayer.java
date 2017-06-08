package com.broll.poklmon.debug.renders;

import com.badlogic.gdx.Input.Keys;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.debug.DebugRenderSite;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.player.OverworldPlayer;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.player.TeleportDestination;

public class DebugRenderPlayer extends DebugRenderSite {

	public DebugRenderPlayer(GameManager game) {
		super(game);
	}

	@Override
	public void render(Graphics g) {
		initDrawing();
		drawTitle(g, "Player Variables");
		Player player = game.getPlayer();
		OverworldPlayer character = player.getOverworld();
		
		drawValuePair(g, "Xpos", character.getXpos());
		drawValuePair(g, "Ypos", character.getYpos());
		drawValuePair(g, "Map", game.getMap().getMapId());
		drawValuePair(g, "Dir", character.getDirection().name());
		drawValuePair(g, "State", character.getWorldState().name());
		drawValuePair(g, "Running", ""+character.isRunning());
		
		TeleportDestination recover = player.getRecoveryLocation();
		drawValuePair(g, "recover_x", recover.getX());
		drawValuePair(g, "recover_y", recover.getY());
		drawValuePair(g, "recover_map", recover.getMap());
		drawValuePair(g, "time",  player.getData().getGameVariables().getPlayTime());
		drawValuePair(g, "name", player.getData().getPlayerData().getName());
		drawValuePair(g, "poklmons", player.getData().getPoklmons().size());
		drawValuePair(g, "teamsize", player.getPoklmonControl().getPoklmonsInTeam().size());
		drawValuePair(g, "pcsize", player.getPoklmonControl().getPoklmonsInPC().size());
		
			
		
	}

	@Override
	public int getKeyCode() {
		return Keys.F1;
	}

}
