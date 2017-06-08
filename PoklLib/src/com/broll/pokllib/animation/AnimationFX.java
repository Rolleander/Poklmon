package com.broll.pokllib.animation;

public class AnimationFX {

	private int atFrame;
	private int value;
	private int r, g, b, a;
	private int length;
	private AnimationFXType type;
	private AnimationFXTarget target;

	public AnimationFXTarget getTarget() {
		return target;
	}

	public void setTarget(AnimationFXTarget target) {
		this.target = target;
	}

	public int getAtFrame() {
		return atFrame;
	}

	public void setAtFrame(int atFrame) {
		this.atFrame = atFrame;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public AnimationFXType getType() {
		return type;
	}

	public void setType(AnimationFXType type) {
		this.type = type;
	}

}
