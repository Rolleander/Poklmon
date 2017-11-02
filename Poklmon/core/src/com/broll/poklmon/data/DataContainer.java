package com.broll.poklmon.data;

public class DataContainer
{

    private GraphicsContainer graphics;
    private MusicContainer musics;
    private SoundContainer sounds;
    private PoklmonContainer poklmons;
    private AttackContainer attacks;
    private AnimationContainer animations;
    private ItemContainer items;
    private MiscContainer misc;

    public DataContainer()
    {
        
    }

    public void setMisc(MiscContainer misc) {
        this.misc = misc;
    }

    public void setItems(ItemContainer items) {
		this.items = items;
	}
    
    public void setAnimations(AnimationContainer animations)
    {
        this.animations = animations;
    }
    
    public void setAttacks(AttackContainer attacks)
    {
        this.attacks = attacks;
    }
    
    public void setGraphics(GraphicsContainer graphics)
    {
        this.graphics = graphics;
    }
    
    public void setMusics(MusicContainer musics)
    {
        this.musics = musics;
    }
    
    public void setPoklmons(PoklmonContainer poklmons)
    {
        this.poklmons = poklmons;
    }
    
    public void setSounds(SoundContainer sounds)
    {
        this.sounds = sounds;
    }
        
    public GraphicsContainer getGraphics()
    {
        return graphics;
    }
    
    public MusicContainer getMusics()
    {
        return musics;
    }
    
    public SoundContainer getSounds()
    {
        return sounds;
    }
    
    public AnimationContainer getAnimations()
    {
        return animations;
    }
    
    public AttackContainer getAttacks()
    {
        return attacks;
    }
    
    public PoklmonContainer getPoklmons()
    {
        return poklmons;
    }
    
    public ItemContainer getItems() {
		return items;
	}

    public MiscContainer getMisc() {
        return misc;
    }

}
