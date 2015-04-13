package stages;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Color;

import tetris.Block;
import tetris.Tetromino;
import engine.Camera;
import engine.Main;
import engine.ThreeShapes;
import engine.Timer;
import engine.Utils;

//TODO: Bugs: Triangles seem to have opposite lighting from other shapes; Loss of control sometimes when changing views; Clear plane clears too early after first clear;

//TODO: Better Colors, Game Over, Clear plane, Score, GUI Score, GUI Time, Menus, Key Acceleration in Utils
//TODO?: Drawing Text to OpenGL, Scoreboard

public class Game extends Stage
{
	private Camera cam;
	// magnitude of movement by camera
	private float delta;
	// List that holds all tetrs
	private Tetromino active;
	// Array list holding all locked block
	private ArrayList<Block> grid;
	// variables that specify the dimensions of the arena
	private float width, height, length;
	// Variable that determines if the game will update
	private boolean paused;
	private static Timer time;
	private static long speed;
	// Variable used to toggle testing conditions
	private boolean dev;
	// Variable for which location the camera is at
	private int view;

	public void init()
	{
		delta = .1f;

		// IMPORTANT: width and length MUST by even!
		// (I think height could be odd, but I wouldn't
		// recommend it.)
		width = 10;
		height = 20;
		length = 10;

		// this assignment MUST be a multiple of 4
		view = 4;

		speed = 1000;

		paused = false;
		dev = false;

		// Instantiate camera for game at certain point
		cam = new Camera();
		time = new Timer(1000000);
		grid = new ArrayList<Block>();
		spawn();
	}

	public void update()
	{
		pollInput();

		if (time.isTicked(speed) && !paused)
		{
			tick();
			for (int i = 0; i < height; i++)
			{
				if (checkPlane(i))
					clearPlane(i);
			}
		}
	}

	// Code to run when a tick has passed.
	private void tick()
	{
		fall();
	}

	// Collects user input
	public void pollInput()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Main.kill();

		if (Utils.isKeyPressed(Keyboard.KEY_F3))
		{
			System.out.println("X: " + cam.getX() + " Y: " + cam.getY()
					+ " Z: " + cam.getZ());
			System.out.println("RX: " + cam.getRotX() + " RY: " + cam.getRotY()
					+ " RZ: " + cam.getRotZ());
		}
		if (Utils.isKeyPressed(Keyboard.KEY_F5))
		{
			dev = !dev;
			System.out.println("Dev: " + dev);
		}
		if (Utils.isKeyPressed(Keyboard.KEY_SPACE))
		{
			paused = !paused;
			System.out.println("Paused: " + paused);
		}
		if (Utils.isKeyPressed(Keyboard.KEY_E))
		{
			view++;
			cam.switchView(Math.abs(view) % 4);
		}
		if (Utils.isKeyPressed(Keyboard.KEY_Q))
		{
			view--;
			cam.switchView(Math.abs(view) % 4);
			System.out.println("View: " + Math.abs(view) % 4);
		}
		if (!dev)
		{
			gameControls();
		} else
		{
			devControls();
		}
		
		//TODO: Testing block
		if(Utils.isKeyPressed(Keyboard.KEY_F9))
		{
			Main.changeStage(Main.TESTING);
		}
	}

	// Polls user input in dev mode
	private void devControls()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			cam.move(-delta, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			cam.move(delta, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			cam.move(delta, 1);
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			cam.move(-delta, 1);
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			cam.rotate(0, delta * 8, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			cam.rotate(0, -delta * 8, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			cam.rotate(-delta * 8, 0, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			cam.rotate(delta * 8, 0, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			cam.descend(delta);
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			cam.ascend(delta);
	}

	// Rotate the active tetromino based upon yor view
	// If you are facing in the positive z direction,
	// a positve rz will rotate about the z axis +90 degrees
	// and a positive ry will rotate about y axis +90 degrees
	private void rotateByView(float ry, float rz)
	{
		int dir = Math.abs(view) % 4;
		switch (dir)
		{
		// Facing -Z
		case 0:
			active.rotate(-rz, ry, 0);
			if (!checkCollision(0, 0, 0))
			{
				active.rotate(rz, -ry, 0);
			}
			break;
		// Facing -X
		case 1:
			active.rotate(0, ry, -rz);
			if (!checkCollision(0, 0, 0))
			{
				active.rotate(0, -ry, -rz);
			}
			break;
		// Facing +Z
		case 2:
			active.rotate(rz, -ry, 0);
			if (!checkCollision(0, 0, 0))
			{
				active.rotate(-rz, ry, 0);
			}
			break;
		// Facing +X
		case 3:
			active.rotate(0, ry, rz);
			if (!checkCollision(0, 0, 0))
			{
				active.rotate(0, -ry, -rz);
			}
			break;
		}

		active.update();
	}

	// Polls user input in regular mode
private void gameControls()
	{
		if (Utils.isKeyPressed(Keyboard.KEY_W))
			moveByView(0, -1);
		if (Utils.isKeyPressed(Keyboard.KEY_A))
			moveByView(-1, 0);
		if (Utils.isKeyPressed(Keyboard.KEY_S))
			moveByView(0, 1);
		if (Utils.isKeyPressed(Keyboard.KEY_D))
			moveByView(1, 0);
		if (Utils.isKeyPressed(Keyboard.KEY_UP))
			rotateByView(0, -1);
		if (Utils.isKeyPressed(Keyboard.KEY_RIGHT))
			rotateByView(1, 0);
		if (Utils.isKeyPressed(Keyboard.KEY_DOWN))
			rotateByView(0, 1);
		if (Utils.isKeyPressed(Keyboard.KEY_LEFT))
			rotateByView(-1, 0);
		if (Utils.isKeyPressed(Keyboard.KEY_F))
			drop();

	}

	// Moves the active tetromino based upon your view
	// positive x will move right, positive z will move toward
	private void moveByView(float x, float z)
	{
		int dir = Math.abs(view) % 4;
		switch (dir)
		{
		// Facing -Z
		case 0:
			if (!checkCollision(1 * x, 0, 1 * z))
				return;
			active.move(1 * x, 0, 1 * z);
			break;
		// Facing -X
		case 1:
			if (!checkCollision(1 * z, 0, -1 * x))
				return;
			active.move(1 * z, 0, -1 * x);
			break;
		// Facing +Z
		case 2:
			if (!checkCollision(-1 * x, 0, -1 * z))
				return;
			active.move(-1 * x, 0, -1 * z);
			break;
		// Facing +X
		case 3:
			if (!checkCollision(-1 * z, 0, 1 * x))
				return;
			active.move(-1 * z, 0, 1 * x);
			break;
		}
	}

	// Returns true if the tetromino, when moved by the x, y, z, values, will
	// intersect with a block, or go outside of the arena
	// Checks for collision for tetr of argument in the direction of argument
	private boolean checkCollision(float x, float y, float z)
	{
		// Cross check every block in the arena with every block in the
		// tetromino passed in the argument, to see if moving the tetromino
		// will cause a collision

		for (Block elem : grid)
		{
			for (Block eleme : active.getBlocks())
			{
				if (elem.getX() == eleme.getX() + x
						&& elem.getY() == eleme.getY() + y
						&& elem.getZ() == eleme.getZ() + z)
				{
					return false;
				}
			}

		}

		// Check every block in tetr to see if any will exit the bounds of the
		// arena
		Block left = active.getMostBlock(-1, 0, 0);
		if ((left.getX() + x) < 0)
			return false;

		Block right = active.getMostBlock(1, 0, 0);
		if ((right.getX() + x) > width - 1)
			return false;

		Block lowest = active.getMostBlock(0, -1, 0);
		if ((lowest.getY() + y) < 0)
			return false;

		Block highest = active.getMostBlock(0, 1, 0);
		if ((highest.getY() + y) > height - 1)
			return false;

		Block farthest = active.getMostBlock(0, 0, 1);
		if ((farthest.getZ() + z) > length - 1)
			return false;

		Block closest = active.getMostBlock(0, 0, -1);
		if ((closest.getZ() + z) < 0)
			return false;

		return true;
	}

	// Generates new tetromino at top of arena
	// Deactivates active tetr, drops new tetromino
	private void spawn()
	{
		active = new Tetromino();
		active.setPosByBlock(width / 2, height - 1, length / 2,
				active.getMostBlock(0, 1, 0));
		if(checkLoss())
		{
			active = null;
			System.out.println("You Lose!");
			Main.changeStage(Main.BLANK);
		}
	}

	// teleports the active tetromino to its first intersection in the y-axis
	private void drop()
	{
		Block gridIntersect = new Block();
		Block activeIntersect = active.getMostBlock(0, -1, 0);

		for (Block elem : active.getBlocks())
		{
			for (Block eleme : grid)
			{
				if (getTopBlock(elem.getX(), elem.getZ()).getY() > gridIntersect
						.getY())
				{
					activeIntersect = elem;
					gridIntersect = eleme;
				}
			}
		}

		active.setPosByBlock(activeIntersect.getX(), gridIntersect.getY() + 1,
				activeIntersect.getZ(), activeIntersect);

		lock();
		spawn();
	}

	// Makes the active tetromino move -1 in the y-direction
	// Active tetr checks for collision below it, but otherwise falls
	private void fall()
	{
		if (checkCollision(0, -1, 0))
		{
			active.move(0, -1, 0);
			return;
		}
		lock();
		spawn();
	}

	// Returns the highest block in the column denoted by the passed
	// coordinates
	// Returns y value + 1 of the highest block in the column x = x, z = z
	private Block getTopBlock(float x, float z)
	{
		Block highest = new Block();

		for (Block elem : grid)
		{
			if (elem.getX() == x && elem.getZ() == z)
			{
				if (elem.getY() > highest.getY())
				{
					highest = elem;
				}
			}
		}

		return highest;
	}

	// Draws a flat black rectangle where the tetromino will drop
	// Draws shadow of tetromino
	public void drawShadow()
	{
		for (Block elem : active.getBlocks())
		{
			ThreeShapes.fillRect(elem.getX(),
					getTopBlock(elem.getX(), elem.getZ()).getY() + 1,
					elem.getZ(), 1, 1, 90, 0, 0, new Color(0,0,0));
		}
	}

	// Adds all the blocks in the active tetromino to the grid
	// locks active tetromino to grid and adds its block to the grid
private void lock()
	{
		for (Block elem : active.getBlocks())
			grid.add(elem);
	}

	// Checks to see if the parameter designated plane is complete
	// return true if plane is full, and flase otherwise
	private boolean checkPlane(float y)
	{
		float volume = 0;

		for (Block elem : grid)
		{
			if (elem.getY() == y)
				volume++;
		}

		// System.out.println("\tFrom Check Plane: \n\t\t Y: " + y);

		if(y == 0)
			System.out.println("Found " + volume + " blocks on plane y= " + y);
		
		if (volume == (width * length))
			return true;
		return false;

	}

	// clears the parameter designated plane
	private void clearPlane(float y)
	{
		for (int i = 0; i < grid.size(); i++)
		{
			if (grid.get(i).getY() == y)
				grid.remove(i);
			if (grid.get(i).getY() > y)
				grid.get(i).setY(grid.get(i).getY() - 1);
		}
	}

	//Draws time to right of arena
	private void drawTime(int view)
	{
		int milliseconds = (int) (time.getTime()) % 1000;
		int seconds = (int) (time.getTime() / 1000) % 60;
		int minutes = (int) ((time.getTime() / (1000*60)) % 60);
		String timeString = minutes + ":" + seconds + ":" + milliseconds;
		
		switch(view)
		{
		case 0:
			ThreeShapes.drawDigitalNum(width, 0, 0, timeString, -45, 0, 0);
			break;
		case 1:
			ThreeShapes.drawDigitalNum(0, 0, 0, timeString, -45, 90, 0);
			break;
		case 2:
			ThreeShapes.drawDigitalNum(0, 0, length, timeString, -45, 180, 0);
			break;
		case 3:
			ThreeShapes.drawDigitalNum(width, 0, length, timeString, -45, -90, 0);
			break;
		}
	}
	
	// Render game window
	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		cam.useView();
		drawArena();
		drawShadow();
		drawTime(view % 4);

		active.render();

		if (dev)
			ThreeShapes.drawCompass(5, 20, 5);

		for (Block elem : grid)
		{
			elem.render();
		}
	}

	// Draws a wireFrame prism to represent the working area of the game
	// Draws working area for tetr in the form of a rectangular prism
	private void drawArena()
	{
		ThreeShapes.drawWirePrism(0, 0, 0, width, height, length);
	}

	//Returns true is the player has lost
	//	the player has lost when a tetromino cannot drop
	private boolean checkLoss()
	{
		if(!checkCollision(0,0,0))
			return true;
		return false;
	}
	
	// Cleans up extraneous variables
	public void cleanUp()
	{

	}

	// getter
	// Getter
	public static Timer getTimer()
	{
		return time;
	}

}
