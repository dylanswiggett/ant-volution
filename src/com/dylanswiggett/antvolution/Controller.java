package com.dylanswiggett.antvolution;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.dylanswiggett.antvolution.model.Model;
import com.dylanswiggett.antvolution.util.Vector;


public class Controller implements Runnable {
	private boolean gameRunning = true;
	private Model model;

	private Vector cameraPosition;
	private double cameraDistance;
	
	private float FOV;

	public Controller() {
		cameraPosition = new Vector(0, 0);
		cameraDistance = 100;
		FOV = 45.0f;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	

	public void run() {
		while (!Keyboard.isCreated());
		
		while (gameRunning) {
			/*
			 * Camera Controls
			 */
			cameraDistance += Mouse.getDWheel() * cameraDistance / 1000;
			if (cameraDistance < 20)
				cameraDistance = 20;
			if (cameraDistance > 50000)
				cameraDistance = 50000;
			
			double cameraSpeed = cameraDistance / 10000000.0;
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				cameraPosition.addInPlace(new Vector(-cameraSpeed, 0));
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				cameraPosition.addInPlace(new Vector(cameraSpeed, 0));
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				cameraPosition.addInPlace(new Vector(0, -cameraSpeed));
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				cameraPosition.addInPlace(new Vector(0, cameraSpeed));
			
			if (Mouse.isButtonDown(0)){
				
			}
		}

		/*
		 * Clean up
		 */
	}

	public void endGame() {
		gameRunning = false;
	}

	public Vector getCameraPosition() {
		return cameraPosition;
	}

	public double getCameraDistance() {
		return cameraDistance;
	}
	
	public float getFOV() {
		return FOV;
	}
}
