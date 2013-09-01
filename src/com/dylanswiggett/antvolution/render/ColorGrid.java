package com.dylanswiggett.antvolution.render;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Stack;

import com.dylanswiggett.antvolution.util.Vector;

public class ColorGrid extends Sprite2D{
	private static final Color DEFAULT_COLOR = Color.DARK_GRAY;
	
	private ColorSprite2D drawSprite;
	
	private int w, h;
	
	private double wScale, hScale;
	private Vector dimension;
	
	private Color[][] colors;
	
	private Stack<BufferedColor> bufferedColors;

	private class BufferedColor {
		private int x, y;
		private Color color;
		
		private BufferedColor(int x, int y, Color color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
	}
	
	public ColorGrid(Vector position, Vector dimension, double verticalOffset, int w, int h) {
		super(position, dimension, verticalOffset);
		
		this.dimension = dimension;
		
		bufferedColors = new Stack<BufferedColor>();
		
		this.w = w;
		this.h = h;
		
		wScale = dimension.x / w;
		hScale = dimension.y / h;
		
		drawSprite = new ColorSprite2D(new Vector(), new Vector(wScale, hScale), verticalOffset, Color.BLACK);
		
		colors = new Color[w][h];
	}
	
	public void setColor(int x, int y, Color color) {
		if (x >= 0 && x < w && y >= 0 && y < w)
			colors[x][y] = color;
	}
	
	private void setColor(BufferedColor c) {
		setColor(c.x, c.y, c.color);
	}
	
	public void setColorForOneFrame(int x, int y, Color color) {
		if (x >= 0 && x < w && y >= 0 && y < w) {
			bufferedColors.push(new BufferedColor(x, y, colors[x][y]));
			setColor(x, y, color);
		}
	}

	@Override
	public void draw() {
		drawSprite.setPosition(position);
		drawSprite.setColor(DEFAULT_COLOR);
		drawSprite.setDimension(dimension);
		drawSprite.draw();
		
		drawSprite.setDimension(new Vector(wScale, hScale));
		for (int x = 0; x < w; x++){
			for (int y =0; y < h; y++){
				if (colors[x][y] != null) {
					drawSprite.setColor(colors[x][y]);
					drawSprite.setPosition(new Vector(x * wScale, y * hScale).add(position));
					drawSprite.draw();
				}
			}
		}
		while(!bufferedColors.isEmpty())
			setColor(bufferedColors.pop());
	}

}
