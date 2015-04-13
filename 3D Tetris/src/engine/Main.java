package engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import stages.Game;
import stages.Menu;
import stages.Stage;
import stages.TestingZone;

public class Main
{
	// Sentry variable for entire program
	private static boolean running = true;
	// Declaring stage that will be "active", as in being updated and
	// rendered
	private static Stage active;
	// Target frame rate
	private int frameRate = 60;

	// Constants defining stages
	// initial menu for selecting mode or quitting
	public static final int START_MENU = 0;
	// menu to change game properties such as window size
	public static final int OPTIONS = 1;
	// The game itself
	public static final int GAME = 2;
	// Blank screen with no function
	public static final int BLANK = 3;
	// Stage for testing purposes
	public static final int TESTING = 4;

	// Instantiate main object, run it, then clean it up when it's finished
	public static void main(String args[])
	{
		Main m = new Main();
		m.run();
		m.cleanUp();
	}

	// Run init on instantiation
	public Main()
	{
		init();
	}

	// things to run when game first starts
	public void init()
	{
		// try to make the display, set the init background
		try
		{
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setInitialBackground(1, 1, 1);
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		// Instantiate a new game
		active = new Game();
	}

	// Main loop for game
	public void run()
	{
		while (running && !Display.isCloseRequested())
		{
			// Update the active stage
			active.update();
			// Render the active stage
			active.render();
			// Update OpenGL's display
			Display.update();

			// Sync framerate
			Display.sync(frameRate);
		}
	}

	// Cleanup objects
	public void cleanUp()
	{
		Display.destroy();
		System.exit(0);
	}

	// Change stage to corresponding stage constant
	public static void changeStage(int stage)
	{
		// Cleanup current stage
		active.cleanUp();
		// Unset stage
		active = null;
		// set to new stage
		switch (stage)
		{
		case START_MENU:
			active = new Menu();
			break;
		case OPTIONS:
			break;
		case GAME:
			active = new Game();
			break;
		case BLANK:
			active = new Menu();
			break;
		case TESTING:
			active = new TestingZone();
			break;
		}
		// update display
		Display.update();
	}

	// Kill the game
	public static void kill()
	{
		running = false;
	}

}
