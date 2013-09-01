package com.dylanswiggett.antvolution.model.action;

import com.dylanswiggett.antvolution.model.Ant;

public class TurnRight implements Behavior{

	public void act(Ant ant) {
		ant.turn(1);
	}
}
