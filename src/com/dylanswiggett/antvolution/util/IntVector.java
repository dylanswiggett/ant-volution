package com.dylanswiggett.antvolution.util;

public class IntVector {
	public int x,y;
	
	public IntVector(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public IntVector(){
		this(0, 0);
	}
	
	public IntVector(double x, double y){
		this((int) Math.round(x), (int) Math.round(y));
	}
	
	public IntVector add(IntVector v){
		return new IntVector(x + v.x, y + v.y);
	}
	
	public IntVector sub(IntVector v){
		return new IntVector(x - v.x, y - v.y);
	}
}
