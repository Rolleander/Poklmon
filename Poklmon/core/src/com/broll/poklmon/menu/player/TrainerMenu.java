package com.broll.poklmon.menu.player;

import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.menu.MenuPage;
import com.broll.poklmon.menu.PlayerMenu;
import com.broll.poklmon.player.Player;
import com.broll.poklmon.resource.FontUtils;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.manage.SaveFileUtils;

public class TrainerMenu extends MenuPage
{
    public TrainerMenu(PlayerMenu menu, Player player, DataContainer data)
    {
        super(menu, player, data);
    }

    @Override
    public void onEnter()
    {

    }

    @Override
    public void onExit()
    {

    }

    private float y=0;
    private float x=0;
    @Override
    public void render(Graphics g)
    {
        Image picture=player.getPlayerGraphics().getThrowAnimation().getSprite(0,0);
        picture.draw(0, PoklmonGame.HEIGHT-picture.getHeight()*4,4);
        g.setFont(GUIFonts.titleText);
        g.setColor(ColorUtil.newColor(250, 250, 250));
        g.drawString(player.getData().getPlayerData().getName(), 30, 5);
        String playTime= SaveFileUtils.getPlayTime(player.getData().getGameVariables().getPlayTime());
        int steps=player.getVariableControl().getInt(Player.STEP_ID);
        int money=player.getInventarControl().getMoney();
        int defeats=player.getData().getPlayerData().getDeaths();
        boolean lavendiaLabor=player.getVariableControl().getBoolean("LABOR_0_DONE");
        x=360;
        y=5;
        g.setFont(GUIFonts.hudText);
        renderLine(g,"--------------------------------------","");
        renderLine(g,"Spielzeit",playTime);
        renderLine(g,"Geld",money+"$");
        renderLine(g,"Schritte",""+steps);
        renderLine(g,"Niederlagen",""+defeats);
        renderLine(g,"--------------------------------------","");
        renderLine(g,"Labor-Essenzen gesammelt:","");
        if(lavendiaLabor){
            renderLine(g,"Lavendia","Pflanzenessenz");
        }
        else{
            renderLine(g,"","Keine");
        }
        renderLine(g,"","");
        renderLine(g,"PoklLiga","-");
    }

    private void renderLine(Graphics g, String text, String value){
        g.setColor(ColorUtil.newColor(250, 250, 250));
        g.drawString(text,x,y);
        int space=340;
        g.setColor(ColorUtil.newColor(30, 30, 30));
        g.drawString(value,x+space- FontUtils.getWidth(GUIFonts.hudText,value),y);
        y+=30;
    }



    @Override
    public void update(float delta)
    {
        if(GUIUpdate.isCancel())
        {
            close();
        }
    }

}
