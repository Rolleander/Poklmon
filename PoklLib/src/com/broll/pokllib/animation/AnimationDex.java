package com.broll.pokllib.animation;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnimationDex {

	
	private List<AnimationID> animations;
	
	public List<AnimationID> getAnimations() {
		return animations;
	}
	
	public void setAnimations(List<AnimationID> animations) {
		this.animations = animations;
	}
	
}
