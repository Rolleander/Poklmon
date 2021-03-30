package com.broll.pokleditor.map.tileset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.broll.pokleditor.window.VerticalLayout;
import com.broll.pokllib.map.MapData;

public class AreaSetPanel extends JPanel
{

    private MapData map;
    public static int areaSelection;
    public static Color collisionColor=new Color(50,50,50,200);
    private JPanel areaPanel=new JPanel();
    
    public AreaSetPanel()
    {
        AreaColors.prepareColors();
        areaPanel.setLayout(new VerticalLayout(5,VerticalLayout.CENTER));
        setLayout(new BorderLayout());
        add(areaPanel,BorderLayout.CENTER);
    }
    
 
    public void setMap(MapData map)
    {
        this.map = map;
        updateAreas();
    }


    public void updateAreas()
    {
        areaSelection=1;
        areaPanel.removeAll();
        
        addArea(1);//add collisiom
        if(map.getFile().getAreaScripts()!=null)
        {
            int size=map.getFile().getAreaScripts().size();
            for(int i=0; i<size; i++)
            {
                addArea(i+2);
            }
        }
        
        revalidate();
        repaint();
    }
    
    private void addArea(int nr)
    {
        areaPanel.add(new AreaPanel(nr));
    }
    
    private void press(int id){
        areaSelection=id;
    }
    
    private class AreaPanel extends JToggleButton{
        
        private int id;
        private Color c;
        
        public AreaPanel(int i)
        {
            String name=null;
            this.id=i;
            if(id==1)
            {
                name="Player Collision Layer";
                c=collisionColor;
                setSelected(true);
            }
            else
            {
                name="AreaScript "+(id-2);
                c=AreaColors.getAreaColor(id-2);
            }
           
            setText(name);
            setPreferredSize(new Dimension(300,30));
            this.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e)
                {
                   JToggleButton bt = (JToggleButton) areaPanel.getComponent(areaSelection-1);
                   if(bt!=AreaPanel.this)
                   {
                    bt.setSelected(false);
                    press(id);           
                   }
                   else
                   {
                       bt.setSelected(true);
                   }
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g)
        {
            // TODO Auto-generated method stub
            super.paintComponent(g);
            g.setColor(c);
            g.fillRect(0,0,50,50);
        }
        
    }
}
