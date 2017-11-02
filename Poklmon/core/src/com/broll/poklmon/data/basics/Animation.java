package com.broll.poklmon.data.basics;

public class Animation {

	private SpriteSheet animation;
	private int time;
	private boolean looping=false;
	private int timer;
	private int step;
	private boolean running=false;
	private int limit;
	private boolean started=false;

	public Animation(SpriteSheet animation, int time) {
		this.animation=animation;
		this.time=time;
		limit=animation.getCountX();
	}

	public void setLooping(boolean b) {
		looping=b;
	}

	public void stop() {
		running=false;
	}

	public boolean isStopped() {
		return running==false&&started;
	}

	public void start() {
		running=true;
		started=true;
		timer=0;
		step=0;
	}

	public void update(float delta){
		if(running){
			timer+=delta*2000;
			if(timer>=time){
				step++;
				if(step>=limit){
					step=0;
					if(!looping){
						step=limit-1;
						stop();
					}
				}
				timer=0;
			}
		}
	}

	public void draw(float x, float y, float w, float h) {

			animation.getSprite(step, 0).draw(x, y, w, h);

	}

	public void draw(float x, float y) {
		draw(x,y,getWidth(),getHeight());
	}

	public float getWidth(){
		return animation.getSprite(0,0).getWidth();
	}
	
	public float getHeight()
	{
		return animation.getSprite(0,0).getHeight();
	}
}
