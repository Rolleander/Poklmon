package com.broll.poklmon.gui.selection;

import com.broll.poklmon.data.basics.Image;

public class SelectionModel
{
    
    protected int selectedItem;
    protected boolean[] blocked;
    protected String[] items;
	protected Image[] icons;
	
    public SelectionModel(String[] items)
    {
        blocked=new boolean[items.length];       
        this.items=items;
    }
    
    public void setIcons(Image[] icons) {
		this.icons = icons;
	}
    
    public void blockItem(int id, boolean block)
    {
        blocked[id]=block;
    }
    
    public void add(){
        selectedItem++;
        if(selectedItem>=blocked.length)
        {
            selectedItem=0;
        }
    }
    
    public void sub()
    {
        selectedItem--;
        if(selectedItem<0)
        {
            selectedItem=blocked.length-1;
        }
    }
    
    public void setSelectedItem(int id)
    {
        selectedItem=id;
    }
    
    public String getItem(int id)
    {
        return items[id];
    }
    
    public Image getIcon(int id)
    {
    	if (icons != null) {
			if (id >= 0 && id < icons.length) {
				return icons[id];
			}
		}
    	return null;
    }
    
    public boolean isSelectionBlocked()
    {
        return blocked[getSelectedItem()];
    }
    
    public boolean isBlocked(int id)
    {
        return blocked[id];
    }
    
    public int getSelectedItem()
    {
        return selectedItem;
    }
    
    public int getSize()
    {
        return items.length;
    }
}
