package engine;

//TODO: Getting ticks is completely and very strangely broken

public class Timer
{
	// determines size of number returned by timer methods
	// Ex. if resolution is 10000 timer will return miliseconds
	private long resolution;
	// last time timer ticked
	private long lastTime = 0;
	// time of the machine when timer
	// was instantiated
	private long initTime;

	public Timer(long resolution)
	{
		this.resolution = resolution;
		initTime = System.nanoTime();
		mark();
	}

	// set tick point
	public void mark()
	{
		lastTime = getTime();
	}

	// return if ticked since last call
	public boolean isTicked(long timePassed)
	{
		if (getTime() - lastTime >= timePassed)
		{
			mark();
			return true;
		}
		return false;
	}

	// return time since game started
	public long getTime()
	{
		return (System.nanoTime() - initTime) / resolution;
	}

	// return current time in game in string form
	public String toString()
	{
		return "" + ((System.nanoTime() - initTime) / resolution);
	}

}
