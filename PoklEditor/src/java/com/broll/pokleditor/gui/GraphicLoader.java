package com.broll.pokleditor.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GraphicLoader {

	public final static String path="/graphics/";
	
	public static ImageIcon loadIcon(String name)
	{
		return new ImageIcon(GraphicLoader.class.getResource(path+name));
	}
	
	public static Image loadImage(String name)
	{
		try {
			Image image=ImageIO.read(GraphicLoader.class.getResource(path+name));
			return image;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public static JButton newIconButton(String icon)
	{
		ImageIcon ico=loadIcon(icon);
		JButton but=new JButton(ico);
		but.setBorderPainted(false);
		but.setContentAreaFilled(false);
		but.setPreferredSize(new Dimension(ico.getIconWidth(),ico.getIconHeight()));
		return but;
	}
}
