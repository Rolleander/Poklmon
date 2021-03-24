package com.broll.pokllib.animation;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Animation {
	
	private String name;
	private List<AnimationStep> animation;
	private List<AnimationFX> fx;
	private int id;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AnimationStep> getAnimation() {
		return animation;
	}
	public void setAnimation(List<AnimationStep> animation) {
		this.animation = animation;
	}
	public List<AnimationFX> getFx() {
		return fx;
	}
	public void setFx(List<AnimationFX> fx) {
		this.fx = fx;
	}
	
	
	public int getId()
    {
        return id;
    }
	
	public void setId(int id)
    {
        this.id = id;
    }
	
}
