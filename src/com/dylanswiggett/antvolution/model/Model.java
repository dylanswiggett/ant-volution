package com.dylanswiggett.antvolution.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import com.dylanswiggett.antvolution.model.action.Action;
import com.dylanswiggett.antvolution.model.neural.NeuralNet;
import com.dylanswiggett.antvolution.model.vision.LookingDirection;
import com.dylanswiggett.antvolution.render.Drawable;
import com.dylanswiggett.antvolution.util.IntVector;
import com.dylanswiggett.antvolution.util.Vector;

public class Model implements Runnable {
	private static final int NUM_SURVIVORS = 5;
	private static final int NUM_ANTS = 100;
	private static final double MUTATION_CHANCE = 0;

	private boolean gameRunning = true;

	protected List<Drawable> drawableObjects;

	public GameGrid world;

	private static final Random r = new Random();

	public LookingDirection dir = new LookingDirection(0);

	public List<Ant> ants;

	private PriorityQueue<SurvivalNet> survivors;

	private long runTime;

	public Model() {
		drawableObjects = Collections.synchronizedList(new ArrayList<Drawable>());
		// GridSprite2D grid = new GridSprite2D(new Vector(-200, -200), new
		// Vector(400, 400), 20, 20, 1);
		// drawableObjects.add(grid);

		world = new GameGrid(300, 300, new Vector(75, 75));

		setup();

		drawableObjects.add(world.getColorGrid());
	}

	private Vector getStartingPosition() {
		Vector position;
		do {
			position = new Vector(r.nextInt(world.getW()), r.nextInt(world.getH()));
		} while (!world.isEmpty(position));
		return position;
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

		if (survivors != null) {
			System.out.println("Spawning from survivors");
			if (ants.size() != 0) {
				synchronized (ants) {
					for (Ant ant : ants) {
						survivors.add(new SurvivalNet(runTime, ant.getNet()));
					}
				}
			}
			NeuralNet[] baseNets = new NeuralNet[NUM_SURVIVORS];
			ants = Collections.synchronizedList(new ArrayList<Ant>());
			for (int i = 0; i < NUM_SURVIVORS; i++) {
				baseNets[i] = survivors.remove().net;
				Ant a = new Ant(getStartingPosition(), new LookingDirection(r.nextInt(60)),
						baseNets[i]);
				synchronized (ants) {
					ants.add(a);
				}
			}
			synchronized (ants) {
				for (int i = 0; i < NUM_ANTS - NUM_SURVIVORS; i++) {
					NeuralNet net = baseNets[r.nextInt(NUM_SURVIVORS)].duplicate();
					net.mutate(MUTATION_CHANCE);
					Ant a = new Ant(getStartingPosition(), new LookingDirection(r.nextInt(60)), net);
					synchronized (ants) {
						ants.add(a);
					}
				}
			}
		} else {
			if (ants == null) {
				ants = Collections.synchronizedList(new ArrayList<Ant>());
				synchronized (ants) {
					for (int i = 0; i < NUM_ANTS; i++) {
						ants.add(new Ant(getStartingPosition(), new LookingDirection(r.nextInt(60))));
					}
				}
			}
		}
		survivors = new PriorityQueue<SurvivalNet>();
		runTime = 0;
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
					for (int i = 0; i < dirs.length; i++) {
						Color c = world.getVisibleColor(pos, dirs[i]);
						visible[i * 3] = c.getRed();
						visible[i * 3 + 1] = c.getGreen();
						visible[i * 3 + 2] = c.getBlue();
					}
					a.actOnSight(visible, actions);
					if (a.isDead()) {
						antIter.remove();
						survivors.add(new SurvivalNet(runTime, a.getNet()));
					}
				}
			}
			runTime++;
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
