package com.dylanswiggett.antvolution.model;

import java.awt.Color;

import com.dylanswiggett.antvolution.render.ColorSprite2D;
import com.dylanswiggett.antvolution.render.Drawable;
import com.dylanswiggett.antvolution.render.Sprite2D;
import com.dylanswiggett.antvolution.util.Vector;

public class Ant implements Drawable{
	private static Sprite2D antSprite;
	
	static {
		antSprite = new ColorSprite2D(new Vector(), new Vector(10, 10), 0, Color.GRAY);
	}
	
	private Vector position;
	
	public Ant(Vector position) {
		
	}

	@Override
	public void draw() {
		antSprite.setPosition(position);
		antSprite.draw();
	}
}
