package com.joker.colormixer;

public class RGBColor {
	
	private int r = 0;
	private int g = 0;
	private int b = 0;
	
	private int Min = 0;
	private int Max = 255;
	
	public RGBColor(){
		r = Min + (int)(Math.random() * ((Max - Min) + 1));
		g = Min + (int)(Math.random() * ((Max - Min) + 1));
		b = Min + (int)(Math.random() * ((Max - Min) + 1));
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	
	public int compare(RGBColor currentColor) {
		return ((Math.abs(r - currentColor.getR()) + Math.abs(g-currentColor.getG()) + Math.abs(b-currentColor.getB()))*100/(Max*3));
	}

}
