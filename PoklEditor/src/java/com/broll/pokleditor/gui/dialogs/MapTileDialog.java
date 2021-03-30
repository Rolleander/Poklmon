package com.broll.pokleditor.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.broll.pokleditor.map.MapTileEditor;
import com.broll.pokleditor.window.VerticalLayout;

public class MapTileDialog extends JPanel{
	
	private TilesetPreview tilesetPreview;
	private Checkbox blocking=new Checkbox("Blocking", false);
	private JComboBox<String> renderLevel=new JComboBox<String>(new String[] {"Below","Eqal","Above"});
	public MapTileDialog(){
		renderLevel.setSelectedIndex(2);
		setLayout(new BorderLayout());
		tilesetPreview=new TilesetPreview();
		JScrollPane scroll=new  JScrollPane(tilesetPreview);
		scroll.setPreferredSize(new Dimension(MapTileEditor.TILE_SIZE*10+20,0));
		add(scroll,BorderLayout.WEST);
		JPanel panel=new JPanel(new VerticalLayout());
		panel.add(blocking);
		panel.add(new JLabel("Render-Level"));
		panel.add(renderLevel);
		setPreferredSize(new Dimension(550,800));
		add(panel,BorderLayout.CENTER);		
	}

	public String getTileCommand() {
		String cmd="";
		cmd+="init.setObjectTilesetSprite("+tilesetPreview.getSelx()+","+tilesetPreview.getSely()+")\n";
		cmd+="self.setTriggerActive(false)\n"; 
		cmd+="self.setRenderLevel("+(renderLevel.getSelectedIndex()-1)+")\n";
		cmd+="self.setVisible(true)\n";
		cmd+="self.setBlocking("+blocking.getState()+") \n";
		return cmd;
	}
	
	public static String showMapTileDialog() {
		MapTileDialog dialog = new MapTileDialog();
		JOptionPane.showMessageDialog(null, dialog, "Map Tile Event", JOptionPane.PLAIN_MESSAGE);
		return dialog.getTileCommand();
	}
	
	private class TilesetPreview extends JPanel{
		private int w,h;
		private int size=MapTileEditor.TILE_SIZE;
		private int selx,sely;
		public TilesetPreview() {
			w = 10;
			h = MapTileEditor.tiles.length / 10 ;	
			setPreferredSize(new Dimension(w*size,h*size));
			addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
				
				@Override
				public void mousePressed(MouseEvent m) {
					selx=m.getX()/size;
					sely=m.getY()/size;
					repaint();
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
				}
			});
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for(int x=0; x<w; x++) {
				for(int y=0; y<h; y++) {
					int id=y*w+x;
					g.drawImage(MapTileEditor.tiles[id],x*size,y*size,size,size,null);
					g.setColor(new Color(200,200,200));
					g.drawRect(x*size,y*size,size-1,size-1);
				}
			}
			g.setColor(new Color(0,0,255,100));
			int x=selx*size;
			int y=sely*size;
			g.fillRect(x,y,size,size);
		}
		
		public int getSelx() {
			return selx;
		}
		
		public int getSely() {
			return sely;
		}
	}
}
