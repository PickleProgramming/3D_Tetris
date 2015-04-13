package tetris;

import java.util.ArrayList;

import org.lwjgl.util.Color;

//Object holding data for tetrominos
public class Tetromino
{
	// position variables
	private float x, y, z;
	// Type of black {I, S, Z, T, J, L, O}
	private char type;
	// Array holding individual blocks of tetrs
	private ArrayList<Block> blocks;

	public Tetromino(float x, float y, float z)
	{
		// Set properties
		this.x = x;
		this.y = y;
		this.z = z;

		this.blocks = new ArrayList<Block>();

		this.randomizeType();
		this.build();
	}

	// Careful with this constructor, if you don't give it coord before update
	// is
	// called the game will crash
	public Tetromino()
	{
		this.blocks = new ArrayList<Block>();

		this.randomizeType();
		this.build();
	}

	// Randomly generate letter to assign tetr
	public void randomizeType()
	{
		int rand = (int) (7 * Math.random());

		switch (rand)
		{
		case 0:
			type = 'I';
			break;
		case 1:
			type = 'J';
			break;
		case 2:
			type = 'L';
			break;
		case 3:
			type = 'O';
			break;
		case 4:
			type = 'S';
			break;
		case 5:
			type = 'T';
			break;
		case 6:
			type = 'Z';
			break;
		}

	}

	// Direct object to method that will construct block array
	public void build()
	{
		switch (this.type)
		{
		case 'I':
			this.buildI();
			break;
		case 'J':
			this.buildJ();
			break;
		case 'L':
			this.buildL();
			break;
		case 'O':
			this.buildO();
			break;
		case 'S':
			this.buildS();
			break;
		case 'T':
			this.buildT();
			break;
		case 'Z':
			this.buildZ();
			break;
		}
		update();
	}

	// Series of methods to construct block array based on type
	public void buildI()
	{
		Color lightBlue = new Color(61, 155, 255);

		this.blocks.add(new Block(0, 0, 0, lightBlue));
		this.blocks.add(new Block(0, 1, 0, lightBlue));
		this.blocks.add(new Block(0, 2, 0, lightBlue));
		this.blocks.add(new Block(0, 3, 0, lightBlue));
	}

	public void buildJ()
	{
		Color blue = new Color(0, 0, 255);

		this.blocks.add(new Block(-1, 0, 0, blue));
		this.blocks.add(new Block(0, 0, 0, blue));
		this.blocks.add(new Block(0, 1, 0, blue));
		this.blocks.add(new Block(0, 2, 0, blue));
	}

	public void buildL()
	{
		Color orange = new Color(240, 126, 71);

		this.blocks.add(new Block(1, 0, 0, orange));
		this.blocks.add(new Block(0, 0, 0, orange));
		this.blocks.add(new Block(0, 1, 0, orange));
		this.blocks.add(new Block(0, 2, 0, orange));
	}

	public void buildO()
	{
		Color yellow = new Color(255, 255, 0);

		this.blocks.add(new Block(0, 0, 0, yellow));
		this.blocks.add(new Block(1, 0, 0, yellow));
		this.blocks.add(new Block(0, 1, 0, yellow));
		this.blocks.add(new Block(1, 1, 0, yellow));
	}

	public void buildS()
	{
		Color green = new Color(0, 255, 0);

		this.blocks.add(new Block(0, 0, 0, green));
		this.blocks.add(new Block(1, 0, 0, green));
		this.blocks.add(new Block(0, 1, 0, green));
		this.blocks.add(new Block(-1, 1, 0, green));
	}

	public void buildT()
	{
		Color purple = new Color(115, 0, 255);

		this.blocks.add(new Block(0, 0, 0, purple));
		this.blocks.add(new Block(1, 0, 0, purple));
		this.blocks.add(new Block(1, 1, 0, purple));
		this.blocks.add(new Block(2, 0, 0, purple));
	}

	public void buildZ()
	{
		Color red = new Color(255, 0, 0);

		this.blocks.add(new Block(0, 0, 0, red));
		this.blocks.add(new Block(1, 0, 0, red));
		this.blocks.add(new Block(0, -1, 0, red));
		this.blocks.add(new Block(-1, -1, 0, red));
	}

	// Updates every block
	public void update()
	{
		for (Block elem : blocks)
			elem.update(x, y, z);
	}

	// Returns most block in a given direction
	// Ex. getMostBlock(0, -1, 0) will return the
	// lowest block. Breaks if more than one non-zero
	// argument is given
	public Block getMostBlock(float x, float y, float z)
	{
		if (x > 0)
			return getRightMostBlock();
		if (x < 0)
			return getLeftMostBlock();
		if (y > 0)
			return getHighestBlock();
		if (y < 0)
			return getLowestBlock();
		if (z > 0)
			return getFarthestBlock();
		if (z < 0)
			return getClosestBlock();
		return null;
	}

	// returns block with least y value
	private Block getLowestBlock()
	{
		Block lowest = blocks.get(0);

		for (Block elem : blocks)
		{
			if (elem.getY() < lowest.getY())
				lowest = elem;
		}

		return lowest;
	}

	// returns block with greatest y value
	private Block getHighestBlock()
	{
		Block highest = blocks.get(0);

		for (Block elem : blocks)
		{
			if (elem.getY() > highest.getY())
				highest = elem;
		}

		return highest;
	}

	// returns block with lowest x value
	private Block getLeftMostBlock()
	{
		Block leftMost = blocks.get(0);

		for (Block elem : blocks)
		{
			if (elem.getX() < leftMost.getX())
				leftMost = elem;
		}

		return leftMost;
	}

	// returns block with greatest x value
	private Block getRightMostBlock()
	{
		Block rightMost = blocks.get(0);

		for (Block elem : blocks)
		{
			if (elem.getX() > rightMost.getX())
				rightMost = elem;
		}

		return rightMost;
	}

	// returns block with lowest z value
	private Block getClosestBlock()
	{
		Block closest = blocks.get(0);

		for (Block elem : blocks)
		{
			if (elem.getZ() < closest.getZ())
				closest = elem;
		}

		return closest;
	}

	// returns block with highest z value
	private Block getFarthestBlock()
	{
		Block farthest = blocks.get(0);

		for (Block elem : blocks)
		{
			if (elem.getZ() > farthest.getZ())
				farthest = elem;
		}

		return farthest;
	}

	// Method that will paint tetr on screen
	public void render()
	{
		for (Block elem : this.blocks)
			elem.render();
	}

	// moves tetromino the number passed on the corresponding axes
	public void move(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;

		update();
	}

	// teleports tetromino to passed coord
	public void teleport(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;

		update();
	}

	// Sets the position of the tetronimo by changing the specified block to the
	// specified position
	public void setPosByBlock(float x, float y, float z, Block b)
	{
		this.x = x - b.getXRel();
		this.y = y - b.getYRel();
		this.z = z - b.getZRel();

		update();
	}

	// Rotates the object 90 degress about the x axis if rx == 1,
	// -90 degress about the z axis if rz == -1, etc.
	// breaks program if more than one parameters are not 0
	// or if anything but -1 or 1 is passed into any parameter
	public void rotate(float rx, float ry, float rz)
	{
		if (rx != 0)
		{
			for (Block elem : blocks)
			{
				float y = elem.getYRel();
				float z = elem.getZRel();

				elem.setYRel(z * rx);
				elem.setZRel(y * -rx);

			}
		}

		if (ry != 0)
		{
			for (Block elem : blocks)
			{
				float x = elem.getXRel();
				float z = elem.getZRel();

				elem.setXRel(z * -ry);
				elem.setZRel(x * ry);
			}
		}

		if (rz != 0)
		{
			for (Block elem : blocks)
			{
				float x = elem.getXRel();
				float y = elem.getYRel();

				elem.setXRel(y * -rz);
				elem.setYRel(x * rz);
			}
		}

		update();

	}

	// Getters and setters
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

	public char getType()
	{
		return type;
	}

	public void setType(char type)
	{
		this.type = type;
	}

	public ArrayList<Block> getBlocks()
	{
		return blocks;
	}

	public void setBlocks(ArrayList<Block> blocks)
	{
		this.blocks = blocks;
	}

	public boolean equals(Tetromino t)
	{
		if (this.x != t.getX())
			return false;
		if (this.y != t.getY())
			return false;
		if (this.z != t.getZ())
			return false;
		if (this.type != t.getType())
			return false;
		return true;
	}
}
