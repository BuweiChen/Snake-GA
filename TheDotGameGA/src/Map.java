import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Map {
	Obstacle[] map;
	int maxSize;
	int nextBlank;
	public Map(int i)
	{
		map = new Obstacle[i];
		maxSize = i;
		nextBlank = 0;
		Obstacle o2 = new Obstacle(0, 300, 500, 20);
		addObstacle(o2);
	}
	
	public Map() {}
	
	public void drawMap(Graphics2D g)
	{
		for (int i = 0; i < map.length; i++)
		{
			map[i].draw(g);
		}
	}
	
	public boolean intersectMap(Rectangle2D.Double r)
	{
		for (int i = 0; i < map.length; i++)
		{
			if(map[i].intersect(r))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean intersectMap(Ellipse2D.Double e)
	{
		for (int i = 0; i < maxSize; i++)
		{
			if(map[i].intersect(e))
			{
				return true;
			}
		}
		return false;
	}
	public void addObstacle(Obstacle o)
	{
		if (nextBlank < maxSize)
		{
			map[nextBlank] = o;
			nextBlank++;
		}
	}
}
