import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Goal {
	PVector pos;
	
	public Goal()
	{
		pos = new PVector(400, 7.5);
	}
	
	void drawGoal(Graphics2D g)
	{
		Ellipse2D.Double e = new Ellipse2D.Double(pos.x - 5, pos.y - 5, 10, 10);
		g.setColor(Color.RED);
		g.fill(e);
	}

}
