package com.dylanswiggett.antvolution.render;

import java.awt.Color;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.dylanswiggett.antvolution.Controller;
import com.dylanswiggett.antvolution.model.Ant;
import com.dylanswiggett.antvolution.model.Model;
import com.dylanswiggett.antvolution.util.IntVector;
import com.dylanswiggett.antvolution.util.Vector;

public class View implements Runnable {
	private static final String TITLE = "Ant-volution dev";

	private Model model;
	private Controller controller;

	private int windowWidth, windowHeight;

	public View(int width, int height) {
		windowWidth = width;
		windowHeight = height;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void run() {
		/*
		 * Create a display
		 */

		try {
			Display.setDisplayMode(new DisplayMode(windowWidth, windowHeight));
			Display.setTitle(TITLE);
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Error: Failed to create display");
			e.printStackTrace();
			System.exit(0);
		}

		/*
		 * Initialize LWJGL and OpenGL
		 */
		{
			GL11.glViewport(0, 0, windowWidth, windowHeight);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
			// GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
			GL11.glClearColor(1.0f, 1.0f, 1.0f, 1f);
		}

		try {
			while (!(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_Q))) {
				// Don't eat everything!!!
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/*
				 * Draw Code
				 */

				setCamera(false);

				setCamera(true);

				// for (IntVector v : model.dir.getAllLookVectors()){
				// model.world.getColorGrid().setColorForOneFrame(100 + v.x, 100
				// + v.y, Color.BLACK);
				// }

				synchronized (model.ants) {
					for (Ant a : model.ants) {
						model.world.setColorForOneFrame(a.getPosition(), Color.RED);
					}
				}
//				
//				for (IntVector v : model.dir.getAllLookVectors()){
//					model.world.setColorForOneFrame(v.toVector().add(new Vector(100, 100)), Color.BLUE);
//				}

				synchronized (model.getDrawableObjects()) {
					for (Drawable drawable : model.getDrawableObjects())
						drawable.draw();
				}

				/*
				 * End draw code
				 */

				Display.update();
			}
		} catch (Exception e) {
			System.err.println("Uncaught exception in View main loop: " + e.getMessage()
					+ "\nCleaning up and exiting.");
			e.printStackTrace();
		}

		/*
		 * Clean up
		 */

		model.endGame();
		controller.endGame();
		Display.destroy();
	}

	/**
	 * Initialize the camera for the next frame of OpenGL. Positions the center
	 * of the camera at controller.getCameraPosition().
	 */
	private void setCamera(boolean threeD) {
		Vector viewTranslation = controller.getCameraPosition();

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		if (threeD) {
			float whRatio = (float) windowWidth / (float) windowHeight;
			GLU.gluPerspective(controller.getFOV(), whRatio, 1, 100000);
			GLU.gluLookAt((float) viewTranslation.x, (float) viewTranslation.y,
					(float) controller.getCameraDistance(), (float) viewTranslation.x,
					(float) viewTranslation.y, 0, 0, 1, 0);
		} else {
			GL11.glLoadIdentity();
			GL11.glOrtho(0.0f, (float) windowWidth, (float) windowHeight, 0.0f, 0.0f, 1.0f);
		}

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
}
