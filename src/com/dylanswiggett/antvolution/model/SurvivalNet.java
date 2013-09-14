package com.dylanswiggett.antvolution.model;

import com.dylanswiggett.antvolution.model.neural.NeuralNet;

public class SurvivalNet implements Comparable<SurvivalNet>{
	public Long time;
	public NeuralNet net;
	
	public SurvivalNet(long time, NeuralNet net) {
		this.time = time;
		this.net = net;
	}

	@Override
	public int compareTo(SurvivalNet other) {
		return -time.compareTo(other.time);
	}
	
	
}
