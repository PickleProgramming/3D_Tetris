package tetris;

import org.lwjgl.util.Color;

import engine.ThreeShapes;

public class Block
{
	// position variables
	private float x, y, z;
	private float xRel, yRel, zRel;
	// Color of block
	private Color col;

	//Default constructor is used for making a fictional block
	//That can be used as a place holder block object
	public Block()
	{
		x = -1;
		y = -1;
		z = -1;
	}
	
	public Block(float xRel, float yRel, float zRel, Color col)
	{
		this.xRel = xRel;
		this.yRel = yRel;
		this.zRel = zRel;
		this.col = col;
	}

	public void update(float x, float y, float z)
	{
		this.x = x + xRel;
		this.y = y + yRel;
		this.z = z + zRel;
	}

	// Paints block to screen
	public void render()
	{
		ThreeShapes.drawFancyCube(x, y, z, col);
	}

	public Color getCol()
	{
		return col;
	}

	public void setCol(Color col)
	{
		this.col = col;
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

	public float getXRel()
	{
		return xRel;
	}

	public void setXRel(float xRel)
	{
		this.xRel = xRel;
	}

	public float getYRel()
	{
		return yRel;
	}

	public void setYRel(float yRel)
	{
		this.yRel = yRel;
	}

	public float getZRel()
	{
		return zRel;
	}

	public void setZRel(float zRel)
	{
		this.zRel = zRel;
	}

	@Override
	public String toString()
	{
		return "Block [x=" + x + ", y=" + y + ", z=" + z + ", xRel=" + xRel
				+ ", yRel=" + yRel + ", zRel=" + zRel + ", col=" + col.toString() + "]";
	}

	
	
}
