package engine;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Color;

public class ThreeShapes
{

	// Constant height of border rectangles on fancy cubes
	// Used for determining a variety of geometric
	// Calculations for fancy cube
	private static final float C = .1f;
	private static final float K = C / (float) Math.sqrt(2);

	// Draws tetr block
	public static void drawFancyCube(float x, float y, float z, Color col)
	{
		drawFancyCube(x, y, z, 0, 0, 0, col);
	}

	// Draws tetr block, takes rotation arguments
	public static void drawFancyCube(float x, float y, float z, float rx,
			float ry, float rz, Color col)
	{
		float A = 1 - C;

		glPushMatrix();
		{
			glColor3f((float) (col.getRed()) / 255,
					(float) (col.getGreen()) / 255,
					(float) (col.getBlue()) / 255);
			glTranslatef(x, y, z);
			glRotatef(rx, 1, 0, 0);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rz, 0, 0, 1);

			// Faces:
			// Top
			fillRect(0, A + K, 0, A, A, 90, 0, 0);
			// Bottom
			fillRect(0, 0 - K, 0, A, A, 90, 0, 0);
			// Front
			fillRect(0, 0, 0 - K, A, A, 0, 0, 0);
			// Back:
			fillRect(0, 0, A + K, A, A, 0, 0, 0);
			// Right:
			fillRect(-K, 0, 0, A, A, 0, -90, 0);
			// Left:
			fillRect(A + K, 0, 0, A, A, 0, -90, 0);

			glColor3f(0, 0, 0);
			// Edges:
			// Front/Top
			fillRect(0, A, -K, A, C, 45, 0, 0);
			// Back/Top
			fillRect(0, A, A + K, A, C, -45, 0, 0);
			// Right/Top
			fillRect(A + K, A, 0, A, C, 45, -90, 0);
			// Left/Top
			fillRect(-K, A, 0, A, C, -45, -90, 0);
			// Front/Bottom
			fillRect(0, 0, -K, A, C, 135, 0, 0);
			// Back/Bottom
			fillRect(0, 0, A + K, A, C, -135, 0, 0);
			// Back/Right
			fillRect(A + K, 0, 0, A, C, 135, -90, 0);
			// Back/Left
			fillRect(-K, 0, 0, A, C, -135, -90, 0);
			// Front/Right
			fillRect(0, 0, -K, A, C, 0, 45, 90);
			// Front/Left
			fillRect(A, 0, -K, A, C, 0, 135, 90);
			// Back/Right
			fillRect(0, 0, A + K, A, C, 0, -45, 90);
			// Back/Left
			fillRect(A, 0, A + K, A, C, 0, -135, 90);

			// Corners:
			// Front/Top/Right
			fillTriangle(-K, A, 0, C, 35, 45, 0);
			// Front/Bottom/Right
			fillTriangle(-K, 0, 0, C, 145, 45, 0);
			// Front/Top/Left
			fillTriangle(A + K, A, 0, C, -35, 135, 0);
			// Front/Bottom/Left
			fillTriangle(A + K, 0, 0, C, -145, 135, 0);
			// Back/Top/Right
			fillTriangle(-K, A, A, C, -35, -45, 0);
			// Back/Top/Left
			fillTriangle(A + K, A, A, C, 35, -135, 0);
			// Back/Bottom/Right
			fillTriangle(-K, 0, A, C, -145, -45, 0);
			// Back/Bottom/Left
			fillTriangle(A + K, 0, A, C, 145, -135, 0);

		}
		glPopMatrix();

	}

	// Draws Wire Rectangle
	public static void drawRect(float x, float y, float z, float w, float l,
			float rx, float ry, float rz)
	{
		glPushMatrix();
		{
			glTranslatef(x, y, z);
			glRotatef(rx, 1, 0, 0);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rz, 0, 0, 1);

			glBegin(GL_LINE_STRIP);
			{
				// Left:
				glVertex3f(0, 0, 0);
				glVertex3f(0, 0, l);
				// Back:
				glVertex3f(0, 0, l);
				glVertex3f(w, 0, l);
				// Right:
				glVertex3f(w, 0, l);
				glVertex3f(w, 0, 0);
				// Front:
				glVertex3f(w, 0, 0);
				glVertex3f(0, 0, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}

	// Draws Wire Rectangle takes rotation variable
	public static void drawRect(float x, float y, float z, float w, float l)
	{
		drawRect(x, y, z, w, l, 0, 0, 0);
	}

	// Draws a wireframe rectangular prism
	public static void drawWirePrism(float x, float y, float z, float w,
			float h, float l)
	{
		drawWirePrism(x, y, z, w, h, l, 0, 0, 0);
	}

	// Draws a wireframe rectangular prism, takes rotation arguments
	public static void drawWirePrism(float x, float y, float z, float w,
			float h, float l, float rx, float ry, float rz)
	{
		glPushMatrix();
		{
			glRotatef(rx, 1, 0, 0);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rz, 0, 0, 1);

			glColor3f(0, 0, 0);
			// Top
			drawRect(x, y + h, z, w, l);
			// Right
			drawRect(x + w, y, z, h, l, 0, 0, 90);
			// Left
			drawRect(x, y, z, h, l, 0, 0, 90);
			// Bottom
			drawRect(x, y, z + l, w, l, 0, 90, 0);
		}
		glPopMatrix();

	}

	// Draws a rectangle
	public static void fillRect(float x, float y, float z, float w, float h)
	{
		fillRect(x, y, z, w, h, 0, 0, 0);
	}

	// Draws a rectangle, takes rotation argument
	public static void fillRect(float x, float y, float z, float w, float h,
			float rx, float ry, float rz)
	{
		glPushMatrix();
		{
			glTranslatef(x, y, z);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rz, 0, 0, 1);
			glRotatef(rx, 1, 0, 0);

			glBegin(GL_QUADS);
			{

				glNormal3f(0f, 0f, 1f);
				glVertex3f(0, h, 0);
				glVertex3f(w, h, 0);
				glVertex3f(w, 0, 0);
				glVertex3f(0, 0, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}

	// Draws a rectangle, takes color argument
	public static void fillRect(float x, float y, float z, float w, float h,
			Color col)
	{
		glColor3f((float) (col.getRed()) / 255, (float) (col.getGreen()) / 255,
				(float) (col.getBlue()) / 255);
		fillRect(x, y, z, w, h, col);
	}

	// Draws a rectangle, takes a color argument and a rotation argument
	public static void fillRect(float x, float y, float z, float w, float h,
			float rx, float ry, float rz, Color col)
	{
		glColor3f((float) (col.getRed()) / 255, (float) (col.getGreen()) / 255,
				(float) (col.getBlue()) / 255);
		fillRect(x, y, z, w, h, rx, ry, rz);
	}

	public static void fillRect(float x, float y, float z, float w, float h,
			float rx, float ry, float rz, Color col, boolean lighting)
	{
		glColor3f((float) (col.getRed()) / 255, (float) (col.getGreen()) / 255,
				(float) (col.getBlue()) / 255);

		glPushMatrix();
		{
			glTranslatef(x, y, z);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rz, 0, 0, 1);
			glRotatef(rx, 1, 0, 0);

			glBegin(GL_QUADS);
			{

				if (lighting)
					glNormal3f(0, 0, 1);
				glVertex3f(0, h, 0);
				glVertex3f(w, h, 0);
				glVertex3f(w, 0, 0);
				glVertex3f(0, 0, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}

	// Draws a triangle
	public static void fillTriangle(float x, float y, float z, float w)
	{
		fillTriangle(x, y, z, w, 0f, 0f, 0f);
	}

	public static void fillTriangle(float x, float y, float z, float w,
			float rx, float ry, float rz, Color col, boolean lighting)
	{
		glColor3f((float) (col.getRed()) / 255, (float) (col.getGreen()) / 255,
				(float) (col.getBlue()) / 255);

		float h = (float) ((Math.sqrt(3) / 2) * w);

		glPushMatrix();
		{
			glTranslatef(x, y, z);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rz, 0, 0, 1);
			glRotatef(rx, 1, 0, 0);

			glBegin(GL_TRIANGLES);
			{
				if (lighting)
					glNormal3f(0, 0, 1);
				glNormal3f(0, 0, 1);
				glVertex3f(0, 0, 0);
				glVertex3f(w, 0, 0);
				glVertex3f(w / 2, h, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}

	// Draws a triangle, takes color argument
	public static void fillTriangle(float x, float y, float z, float w,
			Color col)
	{
		glColor3f((float) (col.getRed()) / 255, (float) (col.getGreen()) / 255,
				(float) (col.getBlue()) / 255);
		fillTriangle(x, y, z, w);
	}

	// Draws a triangle, takes rotation arguments
	public static void fillTriangle(float x, float y, float z, float w,
			float rx, float ry, float rz)
	{
		float h = (float) ((Math.sqrt(3) / 2) * w);

		glPushMatrix();
		{
			glTranslatef(x, y, z);
			glRotatef(rz, 0, 0, 1);
			glRotatef(rx, 1, 0, 0);
			glRotatef(ry, 0, 1, 0);

			glBegin(GL_TRIANGLES);
			{
				glNormal3f(0, 0, 1);
				glVertex3f(0, 0, 0);
				glVertex3f(w, 0, 0);
				glVertex3f(w / 2, h, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}

	// Draws a triangle, takes color and rotation arguments
	public static void fillTriangle(float x, float y, float z, float w,
			float rx, float ry, float rz, Color col)
	{
		glColor3f((float) (col.getRed()) / 255, (float) (col.getGreen()) / 255,
				(float) (col.getBlue()) / 255);
		fillTriangle(x, y, z, w, rx, ry, rz);
	}

	// Draws an arrow
	public static void drawArrow(float x, float y, float z, float x1, float y1,
			float z1)
	{
		glPushMatrix();
		{
			glBegin(GL_LINES);
			{
				glVertex3f(x, y, z);
				glVertex3f(x1, y1, z1);

				glVertex3f(x1, y1, z1);
				glVertex3f(x1 - .1f, y1, z1 - .1f);

				glVertex3f(x1, y1, z1);
				glVertex3f(x1 + .1f, y1, z1 - .1f);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void drawCompass(float x, float y, float z)
	{
		drawArrow(x, y, z, x + 1, y, z);
		drawArrow(x, y, z, x, y + 1, z);
		drawArrow(x, y, z, x, y, z + 1);
	}

	public static void drawDigitalNum(float x, float y, float z, String num,
			float rx, float ry, float rz)
	{

		glDisable(GL_LIGHTING);
		
		glPushMatrix();
		{
			glTranslatef(x, y, z);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rx, 1, 0, 0);
			glRotatef(rz, 0, 0, 1);

			for (int i = 0; i < num.length(); i++)
			{
				drawDigitalDigit(1.50f * i, 0, 0, num.charAt(i) - 48, 0, 0, 0);
			}
		}
		glPopMatrix();
		
		glEnable(GL_LIGHTING);
	}

	public static void drawDigitalDigit(float x, float y, float z, int num,
			float rx, float ry, float rz)
	{
		glPushMatrix();
		{
			glTranslatef(x, y, z);

			glRotatef(rx, 1, 0, 0);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rz, 0, 0, 1);

			switch (num)
			{
			case 0:
				// Top Left
				drawDigitalSegment(0, 1.18f, 0);
				// Bottom Left
				drawDigitalSegment(0, 0, 0);
				// Top
				drawDigitalSegment(.14f, 2 * 1.16f, 0, 0, 0, -90);
				// Top Right
				drawDigitalSegment(1.18f, 1.18f, 0);
				// Bottom Right
				drawDigitalSegment(1.18f, 0, 0);
				// Bottom
				drawDigitalSegment(.14f, -.04f, 0, 0, 0, -90);
				break;
			case 1:
				// Top Left
				drawDigitalSegment(0, 1.18f, 0);
				// Bottom Left
				drawDigitalSegment(0, 0, 0);
				break;
			case 2:
				// Top
				drawDigitalSegment(.14f, 2 * 1.16f, 0, 0, 0, -90);
				// Top Right
				drawDigitalSegment(1.18f, 1.18f, 0);
				// Middle
				drawDigitalSegment(.14f, 1.14f, 0, 0, 0, -90);
				// Bottom Left
				drawDigitalSegment(0, 0, 0);
				// Bottom
				drawDigitalSegment(.14f, -.04f, 0, 0, 0, -90);
				break;
			case 3:
				// Top
				drawDigitalSegment(.14f, 2 * 1.16f, 0, 0, 0, -90);
				// Middle
				drawDigitalSegment(.14f, 1.14f, 0, 0, 0, -90);
				// Bottom
				drawDigitalSegment(.14f, -.04f, 0, 0, 0, -90);
				// Top Right
				drawDigitalSegment(1.18f, 1.18f, 0);
				// Bottom Right
				drawDigitalSegment(1.18f, 0, 0);
				break;
			case 4:
				// Top Left
				drawDigitalSegment(0, 1.18f, 0);
				// Middle
				drawDigitalSegment(.14f, 1.14f, 0, 0, 0, -90);
				// Top Right
				drawDigitalSegment(1.18f, 1.18f, 0);
				// Bottom Right
				drawDigitalSegment(1.18f, 0, 0);
				break;
			case 5:
				// Top
				drawDigitalSegment(.14f, 2 * 1.16f, 0, 0, 0, -90);
				// Top Left
				drawDigitalSegment(0, 1.18f, 0);
				// Middle
				drawDigitalSegment(.14f, 1.14f, 0, 0, 0, -90);
				// Bottom Right
				drawDigitalSegment(1.18f, 0, 0);
				// Bottom
				drawDigitalSegment(.14f, -.04f, 0, 0, 0, -90);
				break;
			case 6:
				// Top
				drawDigitalSegment(.14f, 2 * 1.16f, 0, 0, 0, -90);
				// Top Left
				drawDigitalSegment(0, 1.18f, 0);
				// Middle
				drawDigitalSegment(.14f, 1.14f, 0, 0, 0, -90);
				// Bottom Left
				drawDigitalSegment(0, 0, 0);
				// Bottom Right
				drawDigitalSegment(1.18f, 0, 0);
				// Bottom
				drawDigitalSegment(.14f, -.04f, 0, 0, 0, -90);
				break;
			case 7:
				// Top
				drawDigitalSegment(.14f, 2 * 1.16f, 0, 0, 0, -90);
				// Top Right
				drawDigitalSegment(1.18f, 1.18f, 0);
				// Bottom Right
				drawDigitalSegment(1.18f, 0, 0);
				break;
			case 8:
				// Top
				drawDigitalSegment(.14f, 2 * 1.16f, 0, 0, 0, -90);
				// Middle
				drawDigitalSegment(.14f, 1.14f, 0, 0, 0, -90);
				// Bottom
				drawDigitalSegment(.14f, -.04f, 0, 0, 0, -90);
				// Top Left
				drawDigitalSegment(0, 1.18f, 0);
				// Bottom Left
				drawDigitalSegment(0, 0, 0);
				// Top Right
				drawDigitalSegment(1.18f, 1.18f, 0);
				// Bottom Right
				drawDigitalSegment(1.18f, 0, 0);
				break;
			case 9:
				// Top
				drawDigitalSegment(.14f, 2 * 1.16f, 0, 0, 0, -90);
				// Middle
				drawDigitalSegment(.14f, 1.14f, 0, 0, 0, -90);
				// Top Right
				drawDigitalSegment(1.18f, 1.18f, 0);
				// Top Left
				drawDigitalSegment(0, 1.18f, 0);
				// Bottom Right
				drawDigitalSegment(1.18f, 0, 0);
				break;
			case 10:
				fillRect(.75f, 1.67f, 0, .1f, .1f);
				fillRect(.75f, .59f, 0, .1f, .1f);
				break;
			}
		}
		glPopMatrix();
	}

	public static void drawDigitalSegment(float x, float y, float z, float rx,
			float ry, float rz, Color col)
	{
		glPushMatrix();
		{
			glTranslatef(x, y, z);

			glRotatef(rx, 1, 0, 0);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rz, 0, 0, 1);

			fillTriangle(0, 1, 0, .1f, 0, 0, 0, col, false);
			fillRect(0, 0, 0, .1f, 1, 0, 0, 0, col, false);
			fillTriangle(0, 0, 0, .1f, 180, 0, 0, col, false);
		}
		glPopMatrix();
	}

	public static void drawDigitalSegment(float x, float y, float z, Color col)
	{
		drawDigitalSegment(x, y, z, 0, 0, 0, col);
	}

	public static void drawDigitalSegment(float x, float y, float z)
	{
		drawDigitalSegment(x, y, z, new Color(0, 0, 0));
	}

	public static void drawDigitalSegment(float x, float y, float z, float rx,
			float ry, float rz)
	{
		drawDigitalSegment(x, y, z, rx, ry, rz, new Color(0, 0, 0));
	}

	// getters
	public static float getK()
	{
		return K;
	}

	public static float getC()
	{
		return C;
	}

}