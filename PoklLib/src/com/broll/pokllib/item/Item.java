package com.broll.pokllib.item;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Item
{

    private int id;
    private String name;
    private String description;
    private int iconnr;
    private int price;
    private ItemType type;
    private String effect;

    
    /**
     * Returns the {@link #name}.
     * @return The name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the {@link #name}.
     * @param name The new name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the {@link #description}.
     * @return The description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the {@link #description}.
     * @param description The new description to set.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns the {@link #iconnr}.
     * @return The iconnr.
     */
    public int getIconnr()
    {
        return iconnr;
    }

    /**
     * Sets the {@link #iconnr}.
     * @param iconnr The new iconnr to set.
     */
    public void setIconnr(int iconnr)
    {
        this.iconnr = iconnr;
    }

    /**
     * Returns the {@link #price}.
     * @return The price.
     */
    public int getPrice()
    {
        return price;
    }

    /**
     * Sets the {@link #price}.
     * @param price The new price to set.
     */
    public void setPrice(int price)
    {
        this.price = price;
    }

    /**
     * Returns the {@link #type}.
     * @return The type.
     */
    public ItemType getType()
    {
        return type;
    }

    /**
     * Sets the {@link #type}.
     * @param type The new type to set.
     */
    public void setType(ItemType type)
    {
        this.type = type;
    }

    /**
     * Returns the {@link #effect}.
     * @return The effect.
     */
    public String getEffect()
    {
        return effect;
    }

    /**
     * Sets the {@link #effect}.
     * @param effect The new effect to set.
     */
    public void setEffect(String effect)
    {
        this.effect = effect;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
    
}
