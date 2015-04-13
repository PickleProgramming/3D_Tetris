package engine;

import static org.lwjgl.opengl.GL11.*;

public class TwoShapes
{

	public static void drawRect(float x, float y, float w, float h)
	{
		glPushMatrix();
		{
			glColor3f(0, 1, 0);
			glBegin(GL_QUADS);
			{
				glVertex2f(x, y);
				glVertex2f(x + w, y);
				glVertex2f(x + w, y + h);
				glVertex2f(x, y + h);
			}
			glEnd();
		}
		glPopMatrix();
	}

}
