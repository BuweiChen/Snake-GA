import java.util.Random;

public class Brain {
	PVector[] directions;
	int step = 0;
	
	Brain(int size)
	{
		directions = new PVector[size];
		randomize();
	}
	
	void randomize()
	{
		Random rn = new Random();
		for(int i = 0; i < directions.length; i++)
		{
			int xNeg = rn.nextInt(2);
			int yNeg = rn.nextInt(2);
			double x = rn.nextDouble();
			double y = Math.sqrt(1 - x * x);
			if (xNeg == 0)
			{
				x = -x;
			}
			if (yNeg == 0)
			{
				y = -y;
			}
			directions[i] = new PVector(x, y);
		}
	}
	
	protected Brain clone() {
	    Brain clone = new Brain(directions.length);
	    for (int i = 0; i < directions.length; i++) {
	      clone.directions[i] = directions[i].copy();
	    }

	    return clone;
	  }
	
	void mutate() 
	{
	    double mutationRate = 0.05;//chance that any vector in directions gets changed
	    Random rn = new Random();
	    for (int i =0; i< directions.length; i++) 
	    {
	      double rand = rn.nextDouble();
	      if (rand < mutationRate) 
	      {
	          //set this direction as a random direction 
	    	  int xNeg = rn.nextInt(2);
	    	  int yNeg = rn.nextInt(2);
	    	  double x = rn.nextDouble();
	    	  double y = Math.sqrt(1 - x * x);
	    	  if (xNeg == 0)
	    	  {
	    		  x = -x;
	    	  }
	    	  if (yNeg == 0)
	    	  {
	    		  y = -y;
	    	  }
	    	  directions[i] = new PVector(x, y);
	      }
	    }
	}

}
