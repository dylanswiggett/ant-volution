package com.dylanswiggett.antvolution.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.dylanswiggett.antvolution.util.Vector;


public class ColorSprite2D extends Sprite2D{
	float red, green, blue;
	
	public ColorSprite2D(Vector position, Vector dimension, double verticalOffset, Color color) {
		super(position, dimension, verticalOffset);
		setColor(color);
	}

	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslated(position.x, position.y, verticalOffset);
		
		GL11.glColor3f(red, green, blue);
		
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glNormal3d(0, 0, 1);
			GL11.glVertex3d(0, 0, 0);
			GL11.glVertex3d(dimension.x, 0, 0);
			GL11.glVertex3d(dimension.x, dimension.y, 0);
			GL11.glVertex3d(0, dimension.y, 0);
		}
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}
	
	public void setColor(Color color) {
		red =   ((float) color.getRed())   / 255;
		green = ((float) color.getGreen()) / 255;
		blue =  ((float) color.getBlue())  / 255;
	}
}
