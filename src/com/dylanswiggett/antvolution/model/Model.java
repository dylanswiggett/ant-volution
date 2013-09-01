package com.dylanswiggett.antvolution.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.dylanswiggett.antvolution.model.action.Action;
import com.dylanswiggett.antvolution.model.vision.LookingDirection;
import com.dylanswiggett.antvolution.render.Drawable;
import com.dylanswiggett.antvolution.util.IntVector;
import com.dylanswiggett.antvolution.util.Vector;

public class Model implements Runnable {
	private boolean gameRunning = true;

	protected List<Drawable> drawableObjects;

	public GameGrid world;

	private static final Random r = new Random();

	public LookingDirection dir = new LookingDirection(0);

	public List<Ant> ants;

	public Model() {
		drawableObjects = Collections.synchronizedList(new ArrayList<Drawable>());
		// GridSprite2D grid = new GridSprite2D(new Vector(-200, -200), new
		// Vector(400, 400), 20, 20, 1);
		// drawableObjects.add(grid);

		world = new GameGrid(300, 300, new Vector(75, 75));

		setup();

		drawableObjects.add(world.getColorGrid());
	}
	
	public void setup() {
		PerlinNoise wallNoise = new PerlinNoise(.7, .05, 50, Math.random() * 1000, 3);

		Wall wall = new Wall();

		for (int x = 0; x < world.getW(); x++) {
			for (int y = 0; y < world.getH(); y++) {
				if (wallNoise.getHeight(x, y) > 10)
					world.setElement(wall, x, y);
				else
					world.setElement(null, x, y);
			}
		}

		ants = Collections.synchronizedList(new ArrayList<Ant>());
		synchronized (ants) {
			for (int i = 0; i < 100; i++) {
				ants.add(new Ant(new Vector(world.getW() / 2, world.getH() / 2), new LookingDirection(r.nextInt(60))));
			}
		}
	}

	public void run() {
		while (gameRunning) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized (ants) {
				Iterator<Ant> antIter = ants.iterator();
				while (antIter.hasNext()) {
					Ant a = antIter.next();
					Set<Action> actions = world.getSupportedActions(a.getPosition());
					LookingDirection[] dirs = a.getFOV();
					IntVector pos = new IntVector(a.getPosition());
					double[] visible = new double[dirs.length * 3];
					for (int i = 0; i < dirs.length; i++){
						Color c = world.getVisibleColor(pos, dirs[i]);
						visible[i * 3] = c.getRed();
						visible[i * 3 + 1] = c.getGreen();
						visible[i * 3 + 2] = c.getBlue();
					}
					a.actOnSight(visible, actions);
					if (a.isDead())
						antIter.remove();
				}
			}
			
			dir = dir.rot(1);
		}

		/*
		 * Clean up
		 */
	}

	public List<Drawable> getDrawableObjects() {
		return drawableObjects;
	}

	public void endGame() {
		gameRunning = false;
	}
}
