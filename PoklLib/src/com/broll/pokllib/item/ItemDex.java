package com.broll.pokllib.item;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ItemDex {

	
	private List<ItemID> items;

	public void setItems(List<ItemID> items)
    {
        this.items = items;
    }
	
	public List<ItemID> getItems()
    {
        return items;
    }
}
