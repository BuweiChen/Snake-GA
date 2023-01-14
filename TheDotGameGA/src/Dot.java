import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Dot {
	PVector pos;
	PVector vel;
	PVector acc;
	Brain brain;
	int width = 800;
	int height = 800;
	Goal goal = new Goal();
	Map map = new Map(1);
	
	
	boolean dead = false;
	boolean reachedGoal = false;
	boolean isBest = false;
	
	double fitness = 0;
	
	public Dot()
	{
		brain = new Brain(1000);
		
		pos = new PVector(400, 700);
		vel = new PVector(0, 0);
		acc = new PVector(0, 0);
	}
	
	void draw(Graphics2D g)
	{
		if (isBest)
		{
			Ellipse2D.Double e = new Ellipse2D.Double(pos.x - 4, pos.y - 4, 8, 8);
			g.setColor(Color.GREEN);
			g.fill(e);
		}
		else
		{
			Ellipse2D.Double e = new Ellipse2D.Double(pos.x - 2, pos.y - 2, 4, 4);
			g.setColor(Color.black);
			g.fill(e);
		}
	}
	
	void move()
	{
		if (brain.directions.length > brain.step) {//if there are still directions left then set the acceleration as the next PVector in the direcitons array
		      acc = brain.directions[brain.step];
		      brain.step++;
		    } else {//if at the end of the directions array then the dot is dead
		      dead = true;
		    }

		    //apply the acceleration and move the dot
		    vel.add(acc);
		    vel.limit(5);//not too fast
		    pos.add(vel);
	}
	void update() 
	{
		if (!dead && !reachedGoal) 
		{
			move();
	      Ellipse2D.Double e = new Ellipse2D.Double(pos.x - 2, pos.y - 2, 4, 4);
	      if (pos.x< 2|| pos.y<2 || pos.x>width-2 || pos.y>height -2) 
	      {
	    	//if near the edges of the window then kill it 
	        dead = true;
	      } else if (pos.dist(goal.pos) <= 5) 
	      {
	    	  //if reached goal

	        reachedGoal = true;
	      }
	      else if (map.intersectMap(e))
	      {
	    	  dead = true;
	      }
	      
	    }
	}
	
	void calculateFitness() 
	{
	    if (reachedGoal) 
	    {
	      //if the dot reached the goal then the fitness is based on the amount of steps it took to get there
	      fitness = 1.0/16.0 + 10000.0/(float)(brain.step * brain.step);
	    } 
	    else 
	    {
	      //if the dot didn't reach the goal then the fitness is based on how close it is to the goal
	      double distanceToGoal = pos.dist(goal.pos);
	      fitness = 1.0/(distanceToGoal * distanceToGoal);
	    }
	}
	
	Dot gimmeBaby() 
	{
	    Dot baby = new Dot();
	    baby.brain = brain.clone();//babies have the same brain as their parents
	    return baby;
	}
	
	Dot crossover(Dot parent)
	{
		Dot baby = new Dot();
		double cutPoint = Math.random()* (parent.brain.directions.length - 2) + 1;
		for (int i = 0; i < this.brain.directions.length; i++)
		{
			if (i < cutPoint)
			{
				baby.brain.directions[i] = this.brain.directions[i];
			}
			else
				baby.brain.directions[i] = parent.brain.directions[i];
		}
		return baby;
	}
	
}
