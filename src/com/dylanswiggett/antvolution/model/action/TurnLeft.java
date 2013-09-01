package com.dylanswiggett.antvolution.model.action;

import com.dylanswiggett.antvolution.model.Ant;

public class TurnLeft implements Behavior{

	public void act(Ant ant) {
		ant.turn(-1);
	}
}
