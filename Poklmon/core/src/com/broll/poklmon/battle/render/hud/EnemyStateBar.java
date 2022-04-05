package com.broll.poklmon.battle.render.hud;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.FightPoklmon;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.resource.FontUtils;

import java.util.ArrayList;

public class EnemyStateBar extends StateBar {
    private DataContainer data;
    private FightPoklmon[] team;
    private BattleManager battleManager;

    public EnemyStateBar(DataContainer data, BattleManager battleManager) {
        this.data = data;
        this.battleManager = battleManager;
        initGraphics(false, data);
    }

    public void init(ArrayList<FightPoklmon> enemyTeam) {
        team = new FightPoklmon[enemyTeam.size()];
        for (int i = 0; i < team.length; i++) {
            team[i] = enemyTeam.get(i);
        }
    }

    public void render(Graphics g, FightPoklmon poklmon) {
        int x = 10;
        int y = 10;

        box.draw(x, y);

        // draw name
        drawName(g, x + 20, y + 10, poklmon.getName());

        // draw level
        drawLevel(g, x + 205, y + 10, poklmon.getLevel());

        // draw kp
        drawHealthBar(x + 124, y + 54, poklmon.getAttributes().getHealthPercent());

        // draw status
        MainFightStatus status = poklmon.getStatusChanges().getMainStatus();
        if (status != null) {
            HudRenderUtils.renderMainStatus(g, status, x + 12, y + 49);
        }

        //draw poklball if its caught
        if (!battleManager.getParticipants().isTrainerFight()) {
            boolean caught = battleManager.getPlayer().getPokldexControl().hasCachedPoklmon(poklmon.getPoklmon().getId());
            if (caught) {
                Image ball = data.getGraphics().getBattleGraphicsContainer().getPokeballs().getSprite(0, 0);
                ball.draw(x - 5, y - 5);
            }
        }

        if (team.length > 1) {
            Image bar = data.getGraphics().getBattleGraphicsContainer().getTrainerBar();
            SpriteSheet icons = data.getGraphics().getBattleGraphicsContainer().getTrainerIcons();
            int bx = x + 192;
            int by = y + 82;
            bar.draw(bx, by);
            bx += 4;
            by += 1;
            for (int i = 0; i < team.length; i++) {
                FightPoklmon pokl = team[i];
                int icon = 1;
                if (pokl.isFainted()) {
                    icon = 2;
                } else if (pokl == poklmon) {
                    icon = 0;
                }
                icons.getSprite(icon, 0).draw(bx, by);
                bx += 15;
            }
        }
    }

}
