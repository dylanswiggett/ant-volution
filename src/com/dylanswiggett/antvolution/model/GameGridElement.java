package com.dylanswiggett.antvolution.model;

import java.awt.Color;
import java.util.Set;

import com.dylanswiggett.antvolution.model.action.Action;

public interface GameGridElement {
	public Color getColor();
	public boolean isOpaque();
	public Set<Action> supportedActions();
}
