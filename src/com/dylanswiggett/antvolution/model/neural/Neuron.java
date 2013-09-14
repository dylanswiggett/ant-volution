package com.dylanswiggett.antvolution.model.neural;

import java.util.Random;

public class Neuron {
	private static final Random r = new Random();
	private static final double MUTATE_AMOUNT = .2;
	
	private double[] weights;
	
	private double increment;

	private Double score;

	public Neuron(double[] weights) {
		setWeights(weights);
		increment = 0;
	}

	public double score(double[] inputs) {
		if (inputs.length != inputs.length)
			throw new IllegalArgumentException("Different number of inputs and weights.");
		
		if (score == null) {
			score = 0.0;
			for (int i = 0; i < inputs.length; i++)
				score += inputs[i] * weights[i];
		}
		
		// TODO: Add threshold functions
		return score;
	}
	
	public void setIncrement(double increment){
		this.increment = increment;
	}
	
	public void setWeights(double[] weights){
		this.weights = weights;
	}
	
	public void reset() {
		score = null;
	}
	
	public boolean mutate(double chance){
		if (Math.random() < chance){
			int weightNum = r.nextInt(weights.length);
			if (Math.random() > .5)
				weights[weightNum] += MUTATE_AMOUNT;
			else
				weights[weightNum] -= MUTATE_AMOUNT;
			return true;
		} else
			return false;
	}
	
	public Neuron duplicate() {
		double[] weights = new double[this.weights.length];
		for (int i = 0; i < weights.length; i++)
			weights[i] = this.weights[i];
		return new Neuron(weights);
	}
}
