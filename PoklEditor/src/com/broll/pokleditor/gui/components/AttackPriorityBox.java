package com.broll.pokleditor.gui.components;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokllib.attack.AttackPriority;
import com.broll.pokllib.attack.AttackType;

public class AttackPriorityBox extends JPanel {

	
	 private JComboBox<String> disp;

    public AttackPriorityBox(String text)
    {
        AttackPriority[] values = AttackPriority.values();
        String[] names=new String[values.length];
        for(int i=0; i<values.length; i++)
        {
            names[i]=values[i].getName();
        }
        disp=new JComboBox<String>(names);      
        
        add(new JLabel(text));
        add(disp);
    }
    
    public AttackPriority getPriority() {
        int index=disp.getSelectedIndex();
        return AttackPriority.values()[index];
    }
    
    public void setPriority(AttackPriority prio) {
        disp.setSelectedIndex(prio.ordinal());  
    }
}
