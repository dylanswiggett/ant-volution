package com.dylanswiggett.antvolution.model;

import java.awt.Color;
import java.util.Set;

import com.dylanswiggett.antvolution.model.action.Action;
import com.dylanswiggett.antvolution.model.neural.NeuralNet;
import com.dylanswiggett.antvolution.model.vision.LookingDirection;
import com.dylanswiggett.antvolution.render.ColorSprite2D;
import com.dylanswiggett.antvolution.render.Drawable;
import com.dylanswiggett.antvolution.render.Sprite2D;
import com.dylanswiggett.antvolution.util.Vector;

public class Ant implements Drawable{
	private static Sprite2D antSprite;
	private static final int FOV = 5;
	private static final int[] NET_SIZES = new int[] {10, 10, Action.values().length};
	private static final int MAX_IDLE_TIME = 100;
	
	static {
		antSprite = new ColorSprite2D(new Vector(), new Vector(10, 10), 0, Color.GRAY);
	}
	
	private Vector position;
	private LookingDirection direction;
	private boolean dead;
	private NeuralNet brain;
	
	private int walkCount = 0;
	
	public Ant(Vector position, LookingDirection lookingDir) {
		this(position, lookingDir, NeuralNet.getRandomInstance((1 + 2 * FOV) * 3, NET_SIZES));
	}
	
	public Ant(Vector position, LookingDirection lookingDir, NeuralNet brain) {
		this.position = position;
		this.direction = lookingDir;
		dead = false;
		this.brain = brain;
	}
	
	public Vector getPosition(){
		return position;
	}
	
	public void walkForward(){
		position = position.add(direction.asVector());
		double[] vals = new double[33];
		for (int i = 0; i < vals.length; i++)
			vals[i] = i;
		brain.score(vals);
		walkCount = 0;
	}
	
	public void turn(int amount){
		direction = direction.rot(amount);
	}
	
	public void kill() {
		dead = true;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	private int bestIndex(double[] scores){
		int bestIndex = 0;
		double bestScore = Double.MIN_VALUE;
		for (int i = 0; i < scores.length; i++)
			if (scores[i] > bestScore) {
				bestScore = scores[i];
				bestIndex = i;
			}
		return bestIndex;
	}
	
	public void actOnSight(double[] view, Set<Action> supportedActions){
		walkCount++;
		if (supportedActions.isEmpty())
			throw new IllegalArgumentException();
		double[] scores = brain.score(view);
		Action action;
		int failedActions = -1;
		do {
			failedActions++;
			if (failedActions >= supportedActions.size())
				return;
			int bestIndex = bestIndex(scores);
			action = Action.values()[bestIndex];
			scores[bestIndex] = Double.MIN_VALUE;
		} while (!supportedActions.contains(action));
		action.act(this);
		if (walkCount >= MAX_IDLE_TIME)
			kill();
	}

	@Override
	public void draw() {
		antSprite.setPosition(position);
		antSprite.draw();
	}
	
	public LookingDirection[] getFOV() {
		LookingDirection[] dirs = new LookingDirection[FOV * 2 + 1];
		for (int rot = -FOV; rot <= FOV; rot ++)
			dirs[rot + FOV] = direction.rot(rot);
		return dirs;
	}
	
	public NeuralNet getNet() {
		return brain;
	}
	
//	public void look(GameGrid) {
//		
//	}
}
