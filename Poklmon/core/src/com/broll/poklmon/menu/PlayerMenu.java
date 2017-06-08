package com.broll.poklmon.menu;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.items.execute.MenuItemExecutor;
import com.broll.poklmon.player.Player;

public class PlayerMenu
{

    private boolean visible;
    private MenuPageContainer menuPages;
    private MenuControl control;
    private MenuCloseListener menuCloseListener;
    private MenuItemExecutor itemExecutor;

    public PlayerMenu(Player player, DataContainer data, GameManager game)
    {
    	itemExecutor=new MenuItemExecutor(game);
        MenuUtils.init(data);
        menuPages = new MenuPageContainer(this, player, data);
        control = new MenuControl(this);
    }
    
    public void setMenuCloseListener(MenuCloseListener menuCloseListener) {
		this.menuCloseListener = menuCloseListener;
	}
    
    public void showMenu()
    {
        visible = true;
        menuPages.init();    	
    }

    public void openPage(Class<? extends MenuPage> page)
    {
        menuPages.openPage(page);
    }

    public MenuPage getPage(Class<? extends MenuPage> page)
    {
        return menuPages.getPage(page);
    }

    public void closePage()
    {
        menuPages.closePage();
    }

    public void closeMenu()
    {
        visible = false;
        if(menuCloseListener!=null)
        {
        	menuCloseListener.menuClosed();
        }
    }

    public void render(Graphics g)
    {
        if (visible)
        {
        	//TODO
          //  GradientFill gradient = new GradientFill(0, 0, ColorUtil.newColor(200, 200, 250), 0, 600, ColorUtil.newColor(50, 50, 150));
          //  g.fill(new Rectangle(0, 0, 800, 600), gradient);

            menuPages.render(g);
        }
    }

    public void update(float delta)
    {
        if (visible)
        {
            menuPages.update(delta);
        }
    }

    public boolean isVisible()
    {
        return visible;
    }

    public MenuControl getControl()
    {
        return control;
    }
    
    public MenuItemExecutor getItemExecutor() {
		return itemExecutor;
	}
}
