import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Obstacle {
	double x;
	double y;
	double length;
	double width;
	
	public Obstacle(double x, double y, double l, double w)
	{
		this.x = x; 
		this.y = y; 
		length = l; 
		width = w;
	}
	
	public Obstacle() {}

	
	public void draw(Graphics2D g)
	{
		Rectangle2D.Double rec = new Rectangle2D.Double(x, y, length, width);
		g.setColor(Color.cyan);
		g.fill(rec);
	}
	
	public boolean intersect(Rectangle2D.Double r)
	{
		Rectangle2D.Double rec = new Rectangle2D.Double(x, y, length, width);
		if (rec.intersects(r))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean intersect(Ellipse2D.Double e)
	{
		Rectangle2D.Double rec = new Rectangle2D.Double(x, y, length, width);
		if (e.intersects(rec))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
