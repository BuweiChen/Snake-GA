import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;

public class Population {
	Dot[] dots;
	
	double fitnessSum;
	int gen = 1;
	
	int bestDot = 0;
	
	int minStep = 1000;
	
	Population(int size) 
	{
	    dots = new Dot[size];
	    for (int i = 0; i< size; i++) 
	    {
	      dots[i] = new Dot();
	    }
	}
	
	void draw(Graphics2D g)
	{
		for(int i = 1; i< dots.length; i++)
		{
			dots[i].draw(g);
		}
		dots[0].draw(g);
	}
	
	void update() 
	{
	    for (int i = 0; i< dots.length; i++) 
	    {
	      if (dots[i].brain.step > minStep) 
	      {
	    	//if the dot has already taken more steps than the best dot has taken to reach the goal
	        dots[i].dead = true;//then it dead
	      } 
	      else 
	      {
	        dots[i].update();
	      }
	    }
	}
	
	void calculateFitness() 
	{
	    for (int i = 0; i< dots.length; i++) 
	    {
	      dots[i].calculateFitness();
	    }
	}
	
	boolean allDotsDead() 
	{
	    for (int i = 0; i< dots.length; i++) 
	    {
	      if (!dots[i].dead && !dots[i].reachedGoal) 
	      { 
	        return false;
	      }
	    }

	    return true;
	}

	public void naturalSelection() 
	{
		Dot[] newDots = new Dot[dots.length];//next gen
	    setBestDot();
	    calculateFitnessSum();

	    //the champion lives on 
	    newDots[0] = dots[bestDot].gimmeBaby();
	    newDots[0].isBest = true;
	    for (int i = 1; i< newDots.length; i++) 
	    {
	      //select parent based on fitness
	      Dot parent = selectParent();

	      //get baby from them
	      newDots[i] = parent.crossover(selectParent());
	    }

	    dots = newDots.clone();
	    gen ++;
	}

	public void mutateTheBabies() 
	{
		for (int i = 1; i< dots.length; i++) 
		{
			dots[i].brain.mutate();
		}
	}
	
	void calculateFitnessSum() 
	{
	    fitnessSum = 0;
	    for (int i = 0; i< dots.length; i++) 
	    {
	      fitnessSum += dots[i].fitness;
	    }
	}
	
	Dot selectParent() 
	{
	    
		Random rn = new Random();
		double rand = rn.nextDouble() * fitnessSum;


	    double runningSum = 0;

	    for (int i = 0; i< dots.length; i++) 
	    {
	      runningSum+= dots[i].fitness;
	      if (runningSum > rand) 
	      {
	        return dots[i];
	      }
	    }

	    //should never get to this point

	    return null;
	}
	
	void setBestDot() 
	{
	    double max = 0;
	    int maxIndex = 0;
	    for (int i = 0; i< dots.length; i++) 
	    {
	      if (dots[i].fitness > max) 
	      {
	        max = dots[i].fitness;
	        maxIndex = i;
	      }
	    }

	    bestDot = maxIndex;

	    //if this dot reached the goal then reset the minimum number of steps it takes to get to the goal
	    if (dots[bestDot].reachedGoal) 
	    {
	      minStep = dots[bestDot].brain.step;
	    }
	}
	
	void showGenNumAndBestStep(Graphics2D g)
	{
		Font myFont = new Font("myFont", Font.BOLD, 15);
		g.setFont(myFont);
		g.setColor(Color.BLACK);
		g.drawString("Generation: " + gen, 650, 90);
		g.drawString("Best Step: " + minStep, 650, 120);
	}

}
