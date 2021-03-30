package com.broll.pokleditor.map.dialog;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.IntBox;
import com.broll.pokleditor.window.VerticalLayout;

public class ResizeMapDialog extends JPanel
{
    private IntBox widthBox=new IntBox("Width", 5);
    private IntBox heightBox=new IntBox("Height", 5);
    
    
    public ResizeMapDialog(int w, int h){
        
        setLayout(new VerticalLayout());
 
        widthBox.setValue(w);
        heightBox.setValue(h);

        add(new JLabel("Resize Map:"));
        add(widthBox);
        add(heightBox);
        
    }
    
    public int getWidthValue()
    {
        return widthBox.getValue();
    }
    
    public int getHeightValue()
    {
        return heightBox.getValue();
    }
    

}
