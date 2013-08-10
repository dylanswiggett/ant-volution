package com.dylanswiggett.antvolution.render;

import com.dylanswiggett.antvolution.util.Positioned;
import com.dylanswiggett.antvolution.util.Vector;

public abstract class Sprite2D implements Drawable, Positioned {
	protected Vector position, dimension;
	protected double verticalOffset; // Amount to offset from z = 0 when drawing.
									 // Use to avoid clipping when drawing
									 // overlapping objects.

	public Sprite2D(Vector position, Vector dimension, double verticalOffset) {
		this.position = position;
		this.dimension = dimension;
		this.verticalOffset = verticalOffset;
	}

	public abstract void draw();
	
	public Vector getPosition() {
		return position;
	}
	
	public void setPosition(Vector newPosition){
		position = newPosition;
	}
	
	public void setDimension(Vector newDimension){
		dimension = newDimension;
	}
}
