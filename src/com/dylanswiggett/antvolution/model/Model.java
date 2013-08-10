package com.dylanswiggett.antvolution.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dylanswiggett.antvolution.render.Drawable;
import com.dylanswiggett.antvolution.render.GridSprite2D;
import com.dylanswiggett.antvolution.util.Vector;


public class Model implements Runnable{
	private boolean gameRunning = true;
	
	protected List<Drawable> drawableObjects;
	
	public Model() {
		drawableObjects = Collections.synchronizedList(new ArrayList<Drawable>());
		GridSprite2D grid = new GridSprite2D(new Vector(-200, -200), new Vector(400, 400), 20, 20, 1);
		drawableObjects.add(grid);

	}
	
	public void run() {
		while (gameRunning){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/*
		 * Clean up
		 */
	}
	
	public List<Drawable> getDrawableObjects() {
		return drawableObjects;
	}
	
	public void endGame(){
		gameRunning = false;
	}
}
