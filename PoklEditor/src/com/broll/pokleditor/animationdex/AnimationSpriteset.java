package com.broll.pokleditor.animationdex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.broll.pokleditor.resource.ImageLoader;

public class AnimationSpriteset extends JPanel {

	public final static int WIDTH = 9;
	public static Image[] sprites ;
	public static int selection;
	private int height;

	public AnimationSpriteset() {
		sprites = ImageLoader.loadAnimationSprites();
		height = sprites.length / WIDTH;
		setPreferredSize(new Dimension(0, height * 100));
		setBackground(new Color(100,100,100));
		addMouseListener(new Mouse());
	}

	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		for(int y=0; y<height; y++)
		{
			for(int x=0; x<WIDTH; x++)
			{
				int id=y*WIDTH+x;	
				int xp=x*100;
				int yp=y*100;
				g.drawImage(sprites[id],xp,yp,100,100,null);
				if(id==selection)
				{
					g.setColor(Color.WHITE);
					g.drawRect(xp,yp,99,99);
				}
			}
		}
	}
	
	private class Mouse implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			int y=e.getY()/100;
			int x=e.getX()/100;
			selection=y*WIDTH+x;	
			repaint();
		}
		
	}
}
