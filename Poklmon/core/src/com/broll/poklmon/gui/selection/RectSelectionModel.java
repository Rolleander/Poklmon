package com.broll.poklmon.gui.selection;

public class RectSelectionModel extends SelectionModel
{

    public RectSelectionModel(String[] items)
    {
        super(items);
        
    }

    
    @Override
    public void add()
    {
        if(selectedItem<2)
        {
        selectedItem+=2;
        }
    }
    
    @Override
    public void sub()
    {
        if(selectedItem>1)
        {
       selectedItem-=2;
        }
    }
    
    public void right(){
      
        if(selectedItem<blocked.length-1)
        {
            selectedItem++;
        }
    }
    
    public void left()
    {       
        if(selectedItem>0)
        {
            selectedItem--;
        }
    }
}
