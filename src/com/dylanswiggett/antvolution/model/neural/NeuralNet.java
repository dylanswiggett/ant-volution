package com.dylanswiggett.antvolution.model.neural;


public class NeuralNet {
//	public static void main(String[] args) {
//		for (int i = 0; i < 1; i++) {
//			NeuralNet randNet = NeuralNet.getRandomInstance(1, new int[] { 5, 4, 3, 2, 4 });
//			for (double d = -5; d <= 5; d += .1) {
//				System.out.println("D = " + d + ": output: "
//						+ Arrays.toString(randNet.score(new double[] { d })));
//				randNet.mutate(.1);
//			}
//		}
//	}

	private NeuralLayer[] layers;

	public NeuralNet(NeuralLayer[] layers) {
		this.layers = layers;
	}

	public double[] score(double[] inputs) {
		for (int i = 0; i < layers.length; i++) {
			layers[i].reset();
			inputs = layers[i].score(inputs);
		}
		return inputs;
	}

	public static NeuralNet getRandomInstance(int numInputs, int[] layerSizes) {
		NeuralNet net = new NeuralNet(new NeuralLayer[layerSizes.length]);
		for (int i = 0; i < net.layers.length; i++) {
			Neuron[] layer = new Neuron[layerSizes[i]];
			for (int j = 0; j < layer.length; j++) {
				int numWeights = (i == 0 ? numInputs : layerSizes[i - 1]);
				double[] weights = new double[numWeights];
				for (int k = 0; k < numWeights; k++) {
					weights[k] = 2.0 * Math.random() - 1; // [-1.0..1.0]
				}
				layer[j] = new Neuron(weights);
				layer[j].setIncrement(Math.random() - .5);
			}
			net.layers[i] = new NeuralLayer(layer);
		}
		return net;
	}

	public boolean mutate(double chance) {
		boolean isMutated = false;
		for (NeuralLayer layer : layers)
			isMutated = isMutated || layer.mutate(chance);
		return isMutated;
	}
	
	public NeuralNet duplicate() {
		NeuralLayer[] layers = new NeuralLayer[this.layers.length];
		for (int i = 0; i < layers.length; i++)
			layers[i] = this.layers[i].duplicate();
		return new NeuralNet(layers);
	}
}
