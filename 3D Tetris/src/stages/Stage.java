package stages;

public abstract class Stage
{

	public Stage()
	{
		init();
	}
	
	public abstract void init();
	
	public void update()
	{
		pollInput();
	}
	
	public abstract void pollInput();
	
	public abstract void render();
	
	public abstract void cleanUp();
	
}
