package com.dylanswiggett.antvolution.model.action;

import com.dylanswiggett.antvolution.model.Ant;

public enum Action {
	WALK(new WalkForward()),
	LEFT(new TurnLeft()),
	RIGHT(new TurnRight()),
	DIE(new Kill());
	
	private Behavior result;
	
	private Action(Behavior result){
		this.result = result;
	}
	
	public void act(Ant ant){
		result.act(ant);
	}
}
