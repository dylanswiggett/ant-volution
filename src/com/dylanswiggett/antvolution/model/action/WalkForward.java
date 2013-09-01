package com.dylanswiggett.antvolution.model.action;

import com.dylanswiggett.antvolution.model.Ant;

public class WalkForward implements Behavior{
	
	public void act(Ant ant) {
		ant.walkForward();
	}

}
