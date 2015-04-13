package engine;

import org.lwjgl.input.Keyboard;

import stages.Game;

public class Utils
{
	static long press = Game.getTimer().getTime();
	static long interval = 150;

	public static boolean isKeyPressed(int key)
	{
		if ((Keyboard.isKeyDown(key) && (Game.getTimer().getTime() - press) >= interval))
		{
			press = Game.getTimer().getTime();
			return true;
		}
		return false;

	}
}
