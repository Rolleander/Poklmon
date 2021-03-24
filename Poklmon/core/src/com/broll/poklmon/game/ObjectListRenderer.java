package com.broll.poklmon.game;

import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.map.Viewport;
import com.broll.poklmon.map.object.MapObject;
import com.broll.poklmon.model.OverworldCharacter;
import com.broll.poklmon.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ObjectListRenderer {

	private OverworldComparator comp = new OverworldComparator();

	public ObjectListRenderer() {
	}

	public void render(Graphics g, Viewport view, Player player, List<MapObject> objects) {
		List<OverworldCharacter> chars = new ArrayList<OverworldCharacter>(objects.size() + 1);
		chars.add(player.getOverworld());
		chars.addAll(objects);
		Collections.sort(chars, comp);
		for(OverworldCharacter chara: chars){
			chara.render(g, view);
		}
	}

	private class OverworldComparator implements Comparator<OverworldCharacter> {

		@Override
		public int compare(OverworldCharacter o1, OverworldCharacter o2) {
			if(o1.getRenderLevel()!=o2.getRenderLevel()){
				return o1.getRenderLevel()-o2.getRenderLevel();
			}
			if (o1.getYpos() == o2.getYpos()) {
				return (int) Math.floor((o1.getXpos() - o2.getXpos()));
			}
			return (int) Math.floor((o1.getYpos() - o2.getYpos()));
		}

	}
}
