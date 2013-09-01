package com.dylanswiggett.antvolution.model;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import com.dylanswiggett.antvolution.model.action.Action;

public class Wall implements GameGridElement{
	private static final Color color = Color.WHITE;
	private static final Set<Action> actions = new HashSet<Action>();
	
	static {
		actions.add(Action.DIE);
	}

	public Color getColor() {
		return color;
	}

	@Override
	public boolean isOpaque() {
		return true;
	}

	public Set<Action> supportedActions() {
		return actions;
	}

}
