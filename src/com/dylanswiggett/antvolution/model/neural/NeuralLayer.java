package com.dylanswiggett.antvolution.model.neural;

public class NeuralLayer {
	private Neuron[] neurons;
	
	public NeuralLayer(Neuron[] neurons) {
		this.neurons = neurons;
	}
	
	public double[] score(double[] inputs) {
		double[] outputs = new double[neurons.length];
		for (int i = 0; i < neurons.length; i++)
			outputs[i] = neurons[i].score(inputs);
		
		return outputs;
	}
	
	public void setNeuron(int position, Neuron n) {
		neurons[position] = n;
	}
	
	public void reset() {
		for (Neuron n : neurons)
			n.reset();
	}
	
	public boolean mutate(double chance) {
		boolean isMutated = false;
		for (Neuron n : neurons)
			isMutated = isMutated || n.mutate(chance);
		return isMutated;
	}
	
	public NeuralLayer duplicate() {
		Neuron[] neurons = new Neuron[this.neurons.length];
		for (int i = 0; i < neurons.length; i++)
			neurons[i] = this.neurons[i].duplicate();
		return new NeuralLayer(neurons);
	}
}
