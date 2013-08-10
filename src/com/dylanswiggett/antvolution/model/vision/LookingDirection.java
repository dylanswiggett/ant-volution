package com.dylanswiggett.antvolution.model.vision;

import com.dylanswiggett.antvolution.util.IntVector;
import com.dylanswiggett.antvolution.util.Vector;

public class LookingDirection {
	public static final int NUM_DIRECTIONS = 36;
	private static final double DIRECTION_STEP = 2 * Math.PI / NUM_DIRECTIONS;
	private static final int MAX_VIEW_DISTANCE = 30;

	private static final IntVector[][] views = new IntVector[NUM_DIRECTIONS][MAX_VIEW_DISTANCE];
	
	private int direction;

	public LookingDirection(int direction) {
		assertDirectionExists(direction);
		
		this.direction = direction;
		
		if (views[direction][0] == null){
			// Calculate displacement vectors from the current position to check in order for line of sight.
			int count = 0;
			Vector dist = new Vector();
			IntVector viewDisp = new IntVector();
			while (count < MAX_VIEW_DISTANCE){
				Vector dirVec = directionToVector(direction);
				dist = new Vector(dist.x % 1, dist.y % 1);
				double xRatio = (1 - dist.x) / dirVec.x;
				double yRatio = (1 - dist.y) / dirVec.y; 
			}
		}
	}
	
	private void assertDirectionExists(int direction){
		if (direction < 0 || direction >= NUM_DIRECTIONS)
			throw new IndexOutOfBoundsException(
					"Can't make a direction not in the range from 0 to " + (NUM_DIRECTIONS - 1));
	}
	
	private Vector directionToVector(int direction){
		double angle = direction * DIRECTION_STEP;
		return new Vector(Math.cos(angle), Math.sin(angle));
	}
	
	public Vector asVector() {
		return directionToVector(direction);
	}
	
	public void setDirection(int direction) {
		assertDirectionExists(direction);
	}
}
