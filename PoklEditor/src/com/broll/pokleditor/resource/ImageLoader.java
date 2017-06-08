package com.broll.pokleditor.resource;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.broll.pokleditor.animationdex.AnimationSpriteset;
import com.broll.pokllib.animation.AnimationSprite;

public class ImageLoader {

	private static String graphicsPath;
	
	public static void initPath(File file)
	{
		graphicsPath=file.getPath()+"/graphics/";
	}
	
	public static ArrayList<String> listPoklmonImages()
	{
		File file=new File(graphicsPath+"poklmon");
		return list(file);
	}
	
	public static ArrayList<String> listCharacterImages()
	{
		File file=new File(graphicsPath+"chars");
		return list(file);
	}
	
	private static 	ArrayList<String> list(File file)
	{
		ArrayList<String> names=new ArrayList<String>();		
		for(File f:file.listFiles())
		{
			names.add(f.getName());
		}	
		return names;
	}
	
	public static ImageIcon loadPoklmonImage(String graphics)
	{
		try {
			Image image=ImageIO.read(new File(graphicsPath+"poklmon/"+graphics));
			return new ImageIcon(image.getScaledInstance(96, 96, Image.SCALE_SMOOTH));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public static ImageIcon loadCharacterImage(String graphics)
	{
		try {
			Image image=ImageIO.read(new File(graphicsPath+"chars/"+graphics));
			return new ImageIcon(image);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	
	public final static int TILE_SIZE=16;
	public static Image[] loadTileset()
	{
		try {
			Image image=ImageIO.read(new File(graphicsPath+"tileset.png"));
			int w=image.getWidth(null)/TILE_SIZE;
			int h=image.getHeight(null)/TILE_SIZE;
			Image[] tiles=new Image[w*h]; 
			for(int y=0; y<h; y++)
			{
			for(int x=0; x<w; x++)
			{
				tiles[y*w+x]=cutImage(image, x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
			}
			return tiles;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public static Image[] loadAnimationSprites()
	{
		try {
			Image image=ImageIO.read(new File(graphicsPath+"animations.png"));
			int w=AnimationSpriteset.WIDTH;
			int h=image.getHeight(null)/100;
			Image[] tiles=new Image[w*h]; 
			for(int y=0; y<h; y++)
			{
			for(int x=0; x<w; x++)
			{
				tiles[y*w+x]=cutImage(image, x*100, y*100, 100, 100);
			}
			}
			return tiles;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public static Image cutImage(Image source, int x, int y, int w, int h)
	{
		BufferedImage bf=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		bf.getGraphics().drawImage(source,-x,-y,null);
		return bf;
	}
}
