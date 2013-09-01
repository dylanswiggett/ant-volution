package com.dylanswiggett.antvolution.model.vision;

import com.dylanswiggett.antvolution.util.IntVector;
import com.dylanswiggett.antvolution.util.Vector;

public class LookingDirection {
	private static final int MAX_VIEW_DISTANCE = 30;
	public static final int NUM_DIRECTIONS = MAX_VIEW_DISTANCE * 2;
	private static final double DIRECTION_STEP = 2 * Math.PI / NUM_DIRECTIONS;
	
	private static Vector[] directionVectors = new Vector[NUM_DIRECTIONS];

	private static final IntVector[][] views = new IntVector[NUM_DIRECTIONS][MAX_VIEW_DISTANCE];
	
	private int direction;

	public LookingDirection(int direction) {
		assertDirectionExists(direction);
		
		this.direction = direction;
		
		if (directionVectors[direction] == null) {
			double angle = direction * DIRECTION_STEP;
			directionVectors[direction] = new Vector(Math.cos(angle), Math.sin(angle));
		}
		
		if (views[direction][0] == null){
			// Calculate displacement vectors from the current position to check in order for line of sight.
			int count = 0;
			Vector dist = new Vector();
			IntVector viewDisp = new IntVector();
			Vector dirVec = convertDirectionToVector(direction);
			Vector endVec = dirVec.norm().scale(MAX_VIEW_DISTANCE);
			IntVector end = new IntVector(endVec.x, endVec.y);
			int dx = end.x;
//			IntVector sx = new IntVector(Math.signum(end.x), 0);
			int dy = end.y;
//			IntVector sy = new IntVector(0, Math.signum(end.y));
			int err = dx + dy, e2 = 0;
			int steps = Math.abs(dx) == Math.abs(dy)
		            ? Math.abs(dx) : Math.abs(dx) + Math.abs(dy);
		    double xPos = 0;
		    double yPos = 0;
		    double incX = (dx + 0.0d) / steps;
		    double incY = (dy + 0.0d) / steps;
			while (count < MAX_VIEW_DISTANCE){
				views[direction][count] = new IntVector((int)Math.floor(xPos), (int)Math.floor(yPos));
				xPos += incX;
		        yPos += incY;
//				e2 = err * 2;
//				if (e2 > dy) { 
//		            err += dy;
//		            viewDisp = viewDisp.add(sx);
//		        } if (e2 < dx) {
//		            err += dx;
//		            viewDisp = viewDisp.add(sy);
//		        }
				count++;
//				double xRatio = Math.abs(dirVec.x / (1.0 - Math.abs(dist.x)));
//				double yRatio = Math.abs(dirVec.y / (1.0 - Math.abs(dist.y)));
//				IntVector viewDispIncrement = null;
//				Vector distIncrement = null;
//				if (xRatio > yRatio) {
//					viewDispIncrement = new IntVector(Math.signum(dirVec.x), 0);
//					distIncrement = dirVec.scale(1 / xRatio);
//				} else {
//					viewDispIncrement = new IntVector(0, Math.signum(dirVec.y));
//					distIncrement = dirVec.scale(1 / yRatio);
//				}
//				viewDisp = viewDisp.add(viewDispIncrement);
//				dist = dist.add(distIncrement);
//				dist = new Vector(dist.x % 1, dist.y % 1);
//				views[direction][count] = viewDisp;
//				count++;
			}
		}
	}
	
	private void assertDirectionExists(int direction){
		if (direction < 0 || direction >= NUM_DIRECTIONS)
			throw new IndexOutOfBoundsException(
					"Can't make a direction not in the range from 0 to " + (NUM_DIRECTIONS - 1));
	}
	
	private Vector convertDirectionToVector(int direction){
		return directionVectors[direction];
	}
	
	public Vector asVector() {
		return convertDirectionToVector(direction);
	}
	
	public void setDirection(int direction) {
		assertDirectionExists(direction);
	}
	
	public int getDirection() {
		return direction;
	}
	
	public IntVector[] getAllLookVectors() {
		return views[direction];
	}
	
	public IntVector getLookVector(int numSteps) {
		if (numSteps > MAX_VIEW_DISTANCE) { 
			throw new IllegalArgumentException("Tried to look too far!!!");
		}
		return views[direction][numSteps];
	}
	
	public LookingDirection rot(int num){
		return new LookingDirection((direction + num + NUM_DIRECTIONS) % NUM_DIRECTIONS);
	}
}
