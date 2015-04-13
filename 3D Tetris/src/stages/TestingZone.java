package stages;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.input.Keyboard;

import engine.Camera;
import engine.Main;
import engine.ThreeShapes;
import engine.Timer;
import engine.Utils;

public class TestingZone extends Stage
{

	private Camera cam;
	private float delta;
	private long speed;
	private Timer time;
	private int test;

	public void init()
	{
		delta = .1f;

		cam = new Camera(-5, -17, -7, 16, 1, 0);
		
		time = new Timer(1000000);
		
		speed = 100;
		test = 0;
	}

	public void update()
	{
		super.update();
		
		if(time.isTicked(speed))
			test++;
			
	}
	
	public void pollInput()
	{
		if (Utils.isKeyPressed(Keyboard.KEY_ESCAPE))
			Main.kill();

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

		if (Utils.isKeyPressed(Keyboard.KEY_F3))
		{
			System.out.println("X: " + cam.getX() + " Y: " + cam.getY()
					+ " Z: " + cam.getZ());
			System.out.println("RX: " + cam.getRotX() + " RY: " + cam.getRotY()
					+ " RZ: " + cam.getRotZ());
		}
	}

	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		cam.useView();
		
		ThreeShapes.drawDigitalNum(5, 16, 5, "" + test, 0, 0, 0);
	}

	public void cleanUp()
	{

	}

}
