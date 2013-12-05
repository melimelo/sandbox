package com.melimelo.wordninja.model;

import java.util.ArrayList;

public class WordsGrid {

	private int xCount;
	private int yCount;
	private ArrayList<Character> mContent = null;
	
	public WordsGrid(int xCount, int yCount){
		this.xCount = xCount;
		this.yCount = yCount;
		fillGrid();
	}
	private void fillGrid() {
		mContent = new ArrayList<Character>();
		for (int i =0; i<xCount*yCount; i++){
			mContent.add((char) ('a' +  (int)(Math.random() * 26)));
		}
	}
	public int getYCount() {
		return yCount;
	}
	public void setYCount(int yCount) {
		this.yCount = yCount;
	}
	public int getXCount() {
		return xCount;
	}
	public void setXCount(int xCount) {
		this.xCount = xCount;
	}
	public Character getItem(int position) {
		return mContent.get(position);
	}
}
