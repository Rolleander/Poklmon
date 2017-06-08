package com.broll.poklmon.battle.render.poklball;

import com.broll.poklmon.battle.util.AnimationEndListener;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;

public class PoklballThrow {

	private DataContainer data;
	private AnimationEndListener listener;
	private float x,y;
	private boolean flying;
	private int ballType;
	private float speed;
	private float ychange;
	private Image ball;
	
	public PoklballThrow(DataContainer data)
	{
		this.data=data;
	}
	
	public void throwPoklball(int ballType, AnimationEndListener listener )
	{
		this.listener=listener;
		this.ballType=ballType;
		 ball=data.getGraphics().getBattleGraphicsContainer().getPokeballs().getSprite(ballType, 0).getScaledCopy(1.5f);
		 ball.setRotation(0);
		ychange=-14;
		flying=true;
	}
	
	public void setYchange(float ychange) {
		this.ychange = ychange;
	}
	
	public void setStartPos(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public void setSpeed(float s)
	{
		speed=s;
	}
	
	public void render(Graphics g)
	{
		if(flying)
		{		
			ball.setCenterOfRotation();
			ball.rotate(-30);
			ball.drawCentered(x, y);
		}
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Image getBall() {
		return ball;
	}
	
	public void update(float delta)
	{
		if(flying)
		{
			x+=speed;
			y+=ychange;
			ychange+=0.3;
			if(ychange>0&&y>220)
			{
				listener.animationEnd();
			}
		}
	}
	
	public boolean isFlying() {
		return flying;
	}
}
