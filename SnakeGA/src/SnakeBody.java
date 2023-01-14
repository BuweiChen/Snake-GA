
public class SnakeBody {
	int posx;
	int posy;
	
	public SnakeBody(int x, int y)
	{
		posx = x;
		posy = y;
	}
	
	public int getX()
	{
		return posx;
	}
	
	public int getY()
	{
		return posy;
	}
	
	public void move(int newxpos, int newypos)
	{
		posx = newxpos;
		posy = newypos;
	}
	
}
