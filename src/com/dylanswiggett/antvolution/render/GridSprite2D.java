package com.dylanswiggett.antvolution.render;

import org.lwjgl.opengl.GL11;

import com.dylanswiggett.antvolution.util.Vector;


/**
 * 
 * Draws a square grid in black lines. Used for visual clarity and testing.
 * 
 * @author dylan
 * 
 */
public class GridSprite2D extends Sprite2D {
	private int hGrids, vGrids;

	public GridSprite2D(Vector position, Vector dimension, int horizontalGrids, int verticalGrids,
			double verticalOffset) {
		super(position, dimension, verticalOffset);

		hGrids = horizontalGrids;
		vGrids = verticalGrids;
	}

	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslated(position.x, position.y, verticalOffset);
		
		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		
		GL11.glNormal3d(0, 0, 1);
		
		double xScale = dimension.x / hGrids;
		double yScale = dimension.y / vGrids;
		
		// Inner lines
		for (int x = 0; x <= hGrids; x++){
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x * xScale, 0, 0);
			GL11.glVertex3d(x * xScale, dimension.y, 0);
			GL11.glEnd();
		}
		for (int y = 0; y <= vGrids; y++){
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(0, y * yScale, 0);
			GL11.glVertex3d(dimension.x, y * yScale, 0);
			GL11.glEnd();
		}

		GL11.glPopMatrix();

	}
}
