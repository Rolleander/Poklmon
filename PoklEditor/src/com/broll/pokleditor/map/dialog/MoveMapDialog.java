package com.broll.pokleditor.map.dialog;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.IntBox;
import com.broll.pokleditor.window.VerticalLayout;

public class MoveMapDialog extends JPanel
{
    private IntBox movex=new IntBox("Move X", 3);
    private IntBox movey=new IntBox("Move Y", 3);
    
    
    public MoveMapDialog(){
        
        setLayout(new VerticalLayout());
        movex.setValue(0);
        movey.setValue(0);
        add(new JLabel("Move Map:"));
        add(movex);
        add(movey);
      
    }
    
    public int getMoveX()
    {
        return movex.getValue();
    }
    
    public int getMoveY()
    {
        return movey.getValue();
    }
    

}
