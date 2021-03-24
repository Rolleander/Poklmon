package com.broll.poklmon.battle.render.sequence;

import com.broll.poklmon.battle.BattleManager;
import com.broll.poklmon.battle.poklmon.PlayerPoklmon;
import com.broll.poklmon.battle.render.poklball.PoklballThrow;
import com.broll.poklmon.battle.util.AnimationEndListener;
import com.broll.poklmon.battle.util.SelectionListener;
import com.broll.poklmon.battle.util.message.BattleMessages;
import com.broll.poklmon.data.TextContainer;
import com.broll.poklmon.data.basics.Animation;
import com.broll.poklmon.data.basics.Graphics;

public class PlayerPoklmonIntro extends SequenceRender
{

    private PoklballThrow poklball;
    private Animation playerAnimation;
    public static int playerSize = 240;
    private float playerX;
    private boolean start = false;

    public PlayerPoklmonIntro(BattleManager battle)
    {
        super(battle);

    }


    @Override
    protected void start()
    {
        poklball = new PoklballThrow(data);
        playerX = 750;
        playerSize = 240;
        playerAnimation = new Animation(battle.getPlayer().getPlayerGraphics().getThrowAnimation(), 130);
        playerAnimation.setLooping(false);
        playerAnimation.stop();
        start = false;

        //show text
        String poklmon = battle.getParticipants().getPlayer().getName();
        String text =  TextContainer.get("playerPoklmonIntro",poklmon);
        battle.getBattleRender().getHudRender().showText(text, new SelectionListener() {
            @Override
            public void selectionDone()
            {
                if (start == false)
                {
                    battle.getBattleRender().getHudRender().setShowText(false);
                    start = true;
                    playerAnimation.start();
                }
            }
        });
    }

    @Override
    public void render(Graphics g)
    {
        poklball.render(g);
        playerAnimation.draw(playerX, 600 - playerSize, -playerSize, playerSize);
    }

    @Override
    public void update(float delta)
    {
        poklball.update(delta);
        playerAnimation.update(delta);
        if (start)
        {
            if (!poklball.isFlying())
            {
                if (playerX < 600)
                {
                    poklball.setStartPos((int)playerX - 70, 450);
                    poklball.setSpeed(-5f);
                    // throw ball
                    int ballType=battle.getParticipants().getPlayer().getPoklball();
                    poklball.throwPoklball(ballType, new AnimationEndListener() {

                        @Override
                        public void animationEnd()
                        {
                            battle.getBattleRender().getPoklmonRender().setPlayerPoklmonVisible(true);
                            battle.getBattleRender().getHudRender().setShowText(true);

                            stop();
                        }
                    });
                }
                else
                {

                    playerX -= 7.5f;
                }
            }
        }

    }

}
