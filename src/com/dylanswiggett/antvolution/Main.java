package com.dylanswiggett.antvolution;

import com.dylanswiggett.antvolution.model.Model;
import com.dylanswiggett.antvolution.model.vision.LookingDirection;
import com.dylanswiggett.antvolution.render.View;

public class Main {
	
	// Pre-set values for now.
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 800;
	
	public static void main(String[] args){
		View view = new View(WIDTH, HEIGHT);
		Controller controller = new Controller();
		Model model = new Model();
		
		view.setModel(model);
		view.setController(controller);
		controller.setModel(model);
		
		new Thread(model).start();
		new Thread(controller).start();
		new Thread(view).start();
	}
}
