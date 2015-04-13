package engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

public class Camera
{

	// Declare basic variables
	// Position of camera
	private float x, y, z;
	// Rotation matrix
	private float rx = 0, ry = 0, rz = 0;

	// Lighting Buffers
	private FloatBuffer matSpecular;
	private FloatBuffer lightPosition;
	private FloatBuffer whiteLight;
	private FloatBuffer lModelAmbient;

	// final coordinates and rotations used for camera rotation
	private final float[] RIGHT =
	{ -4.5f, -21, -21, 32, 0, 0 };
	private final float[] BACK =
	{ -20, -21, -5, 32, -90, 0 };
	private final float[] LEFT =
	{ -5.1f, -21, 10, 32, -180, 0 };
	private final float[] FRONT =
	{ 11f, -21, -5f, 32, -270, 0 };

	public Camera()
	{
		init();
		
		switchView(0);
	}
	
	// Camera constructor sets position and runs init method
	public Camera(float x, float y, float z)
	{
		init();

		this.x = x;
		this.y = y;
		this.z = z;
	}

	// Camera constructor sets position, rotation matrix, and runs init method
	public Camera(float x, float y, float z, float rx, float ry, float rz)
	{
		init();

		this.x = x;
		this.y = y;
		this.z = z;
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
	}

	// Should be run whenever a camera is instantiated
	public void init()
	{
		initGL();
	}

	// Basic OpenGL Initialization
	public void initGL()
	{
		// Sets Background to white
		glClearColor(1, 1, 1, 0);
		// Clear Depth Buffer
		glClearDepth(1);
		// enable depth test
		glEnable(GL_DEPTH_TEST);

		// Initializes lighting variables for open gl
		initGLLighting();

		// Set open gl matrix mode
		glMatrixMode(GL_PROJECTION);
		// Load identity matrix
		glLoadIdentity();
		// set perspective
		gluPerspective(70,
				(float) Display.getWidth() / (float) Display.getHeight(), 0.3f,
				1000);
		glMatrixMode(GL_MODELVIEW);

		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	}

	// OpenGL Lighting Initialization
	public void initGLLighting()
	{
		initLightArrays();
		glShadeModel(GL_SMOOTH);
		glMaterial(GL_FRONT, GL_SPECULAR, matSpecular); // sets specular
														// material color
		glMaterialf(GL_FRONT, GL_SHININESS, 1.0f); // sets shininess

		glLight(GL_LIGHT0, GL_POSITION, lightPosition); // sets light position
		glLight(GL_LIGHT0, GL_SPECULAR, whiteLight); // sets specular light to
														// white
		glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight); // sets diffuse light to
													// white
		glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient); // global ambient
																// light

		glEnable(GL_LIGHTING); // enables lighting
		glEnable(GL_LIGHT0); // enables light0

		glEnable(GL_COLOR_MATERIAL); // enables opengl to use glColor3f to
										// define material color
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
	}

	// Creates arrays in opengl for material, position, and two properties of
	// the light
	private void initLightArrays()
	{
		matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();

		whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

		lModelAmbient = BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();
	}

	// Updates camera with instance variables
	public void useView()
	{
		glLoadIdentity();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
		glRotatef(rx, 1, 0, 0);
		glRotatef(ry, 0, 1, 0);
		glRotatef(rz, 0, 0, 1);
		glTranslatef(x, y, z);

	}

	// Rotates the camera the specified degress about each axis
	public void rotate(float rotX, float rotY, float rotZ)
	{
		glTranslatef(x, y, z);
		this.rx += rotX;
		this.ry += rotY;
		this.rz += rotZ;
	}

	// Moves the camera depending on where it is facing
	public void move(float delta, float dir)
	{
		this.z += delta
				* (float) (Math.sin(Math.toRadians((double) (ry + 90 * dir))));
		this.x += delta
				* (float) (Math.cos(Math.toRadians((double) (ry + 90 * dir))));
	}

	// Move the camera positively on the y axis
	public void ascend(float delta)
	{

		this.y -= delta;
	}

	// Move the camera negatively on the y axis
	public void descend(float delta)
	{
		this.y += delta;
	}

	// Magically teleport the camera to another location in the world
	public void teleport(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// Set the cameras three rot variables
	public void setView(float rx, float ry, float rz)
	{
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
	}

	// ToString and getters and setterss
	public String coordToString()
	{
		String ret = "";

		ret += "X: " + x;
		ret += " Y: " + y;
		ret += " Z: " + z;

		return ret;
	}

	// Changes the cameras position and rotation to a preset view
	public void switchView(int view)
	{
		switch (view)
		{
		case 0:
			x = RIGHT[0];
			y = RIGHT[1];
			z = RIGHT[2];
			rx = RIGHT[3];
			ry = RIGHT[4];
			rz = RIGHT[5];
			break;
		case 1:
			x = BACK[0];
			y = BACK[1];
			z = BACK[2];
			rx = BACK[3];
			ry = BACK[4];
			rz = BACK[5];
			break;
		case 2:
			x = LEFT[0];
			y = LEFT[1];
			z = LEFT[2];
			rx = LEFT[3];
			ry = LEFT[4];
			rz = LEFT[5];
			break;
		case 3:
			x = FRONT[0];
			y = FRONT[1];
			z = FRONT[2];
			rx = FRONT[3];
			ry = FRONT[4];
			rz = FRONT[5];
			break;
		}
	}

	public FloatBuffer getLightPosition()
	{
		return lightPosition;
	}

	public void setLightPosition(float lx, float ly, float lz)
	{
		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(lx).put(ly).put(lz).put(0.0f).flip();
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getZ()
	{
		return z;
	}

	public void setZ(float z)
	{
		this.z = z;
	}

	public float getRotX()
	{
		return rx;
	}

	public void setRotX(float rotX)
	{
		this.rx = rotX;
	}

	public float getRotY()
	{
		return ry;
	}

	public void setRotY(float rotY)
	{
		this.ry = rotY;
	}

	public float getRotZ()
	{
		return rz;
	}

	public void setRotZ(float rotZ)
	{
		this.rz = rotZ;
	}

}
