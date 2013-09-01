package com.dylanswiggett.antvolution.model;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import com.dylanswiggett.antvolution.model.action.Action;
import com.dylanswiggett.antvolution.model.vision.LookingDirection;
import com.dylanswiggett.antvolution.render.ColorGrid;
import com.dylanswiggett.antvolution.util.IntVector;
import com.dylanswiggett.antvolution.util.Vector;

public class GameGrid {
	private static final Set<Action> DEFAULT_ACTIONS = new HashSet<Action>();
	private static final Set<Action> DIE_DIE_DIE_DIE = new HashSet<Action>();

	private static final Color EMPTY_COLOR = Color.BLACK;

	static {
		DEFAULT_ACTIONS.add(Action.WALK);
		DEFAULT_ACTIONS.add(Action.LEFT);
		DEFAULT_ACTIONS.add(Action.RIGHT);
		DIE_DIE_DIE_DIE.add(Action.DIE);
	}

	private ColorGrid colorGrid;
	private GameGridElement[][] gridElements;
	private int w, h;

	public GameGrid(int w, int h, Vector drawDim) {
		colorGrid = new ColorGrid(new Vector(drawDim.scale(-.5)), drawDim, 0, w, h);
		gridElements = new GameGridElement[w][h];

		this.w = w;
		this.h = h;

		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
				setElement(null, x, y);
	}

	public void setElement(GameGridElement element, int x, int y) {
		if (x < 0 || x >= gridElements.length || y < 0 || y >= gridElements[0].length)
			throw new ArrayIndexOutOfBoundsException("Tried to place gridElement outside grid.");

		gridElements[x][y] = element;
		if (element == null)
			colorGrid.setColor(x, y, null);
		else
			colorGrid.setColor(x, y, element.getColor());
	}

	private GameGridElement getElement(IntVector pos) {
		if (pos.x >= 0 && pos.y >= 0 && pos.x < w && pos.y < h)
			return gridElements[pos.x][pos.y];
		else
			return new Wall();
	}

	public ColorGrid getColorGrid() {
		return colorGrid;
	}

	public Set<Action> getSupportedActions(int x, int y) {
		if (x < 0 || x >= w || y < 0 || y >= h)
			return DIE_DIE_DIE_DIE;
		else if (gridElements[x][y] == null)
			return DEFAULT_ACTIONS;
		else
			return gridElements[x][y].supportedActions();
	}

	public Set<Action> getSupportedActions(Vector pos) {
		return getSupportedActions((int) Math.round(pos.x), (int) Math.round(pos.y));
	}

	public void setColorForOneFrame(Vector p, Color color) {
		if (p.x >= 0 && p.y >= 0 && p.x < w && p.y < h)
			colorGrid.setColorForOneFrame((int) Math.round(p.x), (int) Math.round(p.y), color);
	}

	public Color getVisibleColor(IntVector pos, LookingDirection dir) {
		IntVector[] lookingChecks = dir.getAllLookVectors();
		for (int i = 0; i < lookingChecks.length; i++) {
			GameGridElement elem = getElement(pos.add(lookingChecks[i]));
			if (elem != null && elem.isOpaque()) {
				Color c = elem.getColor();
				double d = Math.sqrt(i + 1);
				return new Color((int) (c.getRed() / d), (int) (c.getGreen() / d),
						(int) (c.getBlue() / d));
			}
		}
		return EMPTY_COLOR;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
}
