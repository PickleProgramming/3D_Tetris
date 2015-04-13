package stages;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

import engine.Main;
import engine.TwoShapes;
import engine.Utils;

public class Menu extends Stage
{

	public Menu()
	{
		init();
	}

	public void init()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glDisable(GL_DEPTH_TEST);
	}

	public void update()
	{
		pollInput();
	}

	public void pollInput()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Main.kill();
		if (Utils.isKeyPressed(Keyboard.KEY_S))
		{
			System.out.println("Change Stage");
			Main.changeStage(Main.GAME);
		}
		if (Utils.isKeyPressed(Keyboard.KEY_K))
			Main.changeStage(Main.BLANK);
	}

	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		TwoShapes.drawRect(50, 50, 50, 50);

	}

	public void cleanUp()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
	}

}
