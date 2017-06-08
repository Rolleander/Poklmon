package com.broll.pokleditor.animationdex;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.animation.AnimationSprite;
import com.broll.pokllib.animation.AnimationStep;

public class AnimationEditBox extends JPanel{

	private Animation animation;
	private Image background;
	public static int selectionSize=30;
	private AnimationEditBoxMouse mouse=new AnimationEditBoxMouse();
	
	public AnimationEditBox()
	{
		background=GraphicLoader.loadImage("backgroundGrass.png");
	
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addMouseWheelListener(mouse);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawImage(background,0,0,null);
		
		g.setColor(Color.RED);
		g.fillRect(0,200,800,2);
		g.fillRect(160,0,2,600);
		g.fillRect(640,0,2,600);
        
		
		AnimationStep step=animation.getAnimation().get(AnimationEditList.selectedSheet);
		Graphics2D g2d=(Graphics2D)g;
		if(step.getSprites()!=null)
		{
		for(AnimationSprite sprite:step.getSprites())
		{
			paintSprite(g2d, sprite);
		}
		}
		
		//draw mouse 
		if(mouse.isShowMouse())
		{
		    int x=mouse.getMousex();
		    int y=mouse.getMousey();
		    AnimationSprite sprite=new AnimationSprite();
		    sprite.setX(x);
	        sprite.setY(y);
	        sprite.setAngle(AnimationEditList.angle);
	        sprite.setSpriteID(AnimationSpriteset.selection);
	        sprite.setSize(AnimationEditList.size);
	        sprite.setTransparency(AnimationEditList.transparency);
	        paintSprite(g2d, sprite);
		}
		
	}
	
	private void paintSprite(Graphics2D g2d, AnimationSprite sprite)
	{
	    int x=sprite.getX();
        int y=sprite.getY();
        float size=sprite.getSize();
        int w=(int) (100*size);
        int h=(int) (100*size);
        int id=sprite.getSpriteID();
        float transparency=sprite.getTransparency();
        float angle=sprite.getAngle();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency);
        g2d.setComposite(ac);
        
        AffineTransform affineTransform = new AffineTransform();
         affineTransform.setToTranslation(x-w/2,y-h/2);
         affineTransform.rotate(angle,w / 2, h/ 2);
         affineTransform.scale(size, size);
    //  g2d.drawImage(AnimationSpriteset.sprites[id],x-w/2,y-h/2,w,h,null);
         g2d.drawImage(AnimationSpriteset.sprites[id],affineTransform,null);
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
        g2d.setComposite(ac);
        
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x-selectionSize/2,y-selectionSize/2,selectionSize,selectionSize);
	}
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
		mouse.setAnimation(animation);
	}
	
	
	
}
