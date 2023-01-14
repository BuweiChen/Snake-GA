import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Population {
	Snake[] snakes;
	Snake bestSnake;
	   
	int bestSnakeScore = 0;
	int gen = 0;
   
	double bestFitness = 0;
	double currentGenBestFitness = 0;
	double fitnessSum = 0;
	int currentGenBestScore = 0;
	boolean isReplay = false;
	
	public Population(int size) throws FileNotFoundException, UnsupportedEncodingException 
	{
		snakes = new Snake[size]; 
		for(int i = 0; i < snakes.length; i++) 
		{
			snakes[i] = new Snake(); 
		}
		bestSnake = snakes[0].clone();
		bestSnake.replay = true;
	}
	
	public boolean isDone() 
	{
		for(int i = 0; i < snakes.length; i++) 
		{
			if(!snakes[i].isDead)
				return false;
		}
		if(!bestSnake.isDead) 
		{
			return false; 
		}
		return true;
	}
	
	public void update() 
	{
			bestSnake.update();
	      
		for(int i = 0; i < snakes.length; i++) 
		{
				snakes[i].update();
		}
	}
	
	public void draw(Graphics2D g)
	{
		bestSnake.draw(g);
	}
	
	public void setBestSnake() throws FileNotFoundException
	{
		System.out.println("setting best snake...");
		double max = 0;
		int maxIndex = 0;
		for(int i = 0; i < snakes.length; i++) 
		{
			if(snakes[i].fitness > max) 
			{
				max = snakes[i].fitness;
				maxIndex = i;
			}
		}
		currentGenBestScore = snakes[maxIndex].apples;
		if (max >= bestFitness)
			this.saveBestSnake(maxIndex);
		if(max > bestFitness) 
		{
			bestFitness = max;
			bestSnake = snakes[maxIndex].clone();
			bestSnakeScore = snakes[maxIndex].apples;
			
		} 
		else 
		{
			bestSnake = bestSnake.clone(); 
		}
		//this.saveBestSnake();
		bestSnake.replay = true;
		currentGenBestFitness = max;
	}
	
	public Snake selectParent() 
	{
		double rand = Math.random() * fitnessSum;
		double tempSum = 0;
		for(int i = 0; i < snakes.length; i++) 
		{
			tempSum += snakes[i].fitness;
			if(tempSum > rand)
				return snakes[i];
		}
		//should never get to this point
		return snakes[0];
	}
	
	public void mutate() 
	{
		for(int i = 0; i < snakes.length; i++) 
		{
			snakes[i].mutate(); 
		}
	}
	
	public void calculateFitness() 
	{
		for(int i = 1; i < snakes.length; i++) 
		{
			snakes[i].calculateFitness(); 
		}
	}
	
	public void naturalSelection() throws FileNotFoundException {
		Snake[] newSnakes = new Snake[snakes.length];
		
		setBestSnake();
		calculateFitnessSum();
		newSnakes[0] = bestSnake.clone();
		for(int i = 1; i < snakes.length; i++) 
		{
			Snake child = selectParent().crossover(selectParent());
			child.mutate();
			newSnakes[i] = child;
		}
		snakes = newSnakes.clone();
		gen++;
	}
	
	public void calculateFitnessSum() 
	{
		fitnessSum = 0;
		for(int i = 0; i < snakes.length; i++) 
		{
			fitnessSum += snakes[i].fitness; 
		}
	}
	
	public void saveBestSnake() throws FileNotFoundException
	{
		System.out.println("new best snake found!");
		PrintWriter writer = new PrintWriter(new FileOutputStream("best snake example.txt", false));
		writer.println("gen: " + gen);
		writer.println("score: " + this.currentGenBestScore);
		ArrayList<Double> weights = bestSnake.brain.toArrayList();
		ArrayList<int[]> foodList = bestSnake.foodList;
		if (weights.size() > 0)
		{
			for (int i = 0; i < weights.size() - 1; i++)
			{
				writer.print(weights.get(i) + ",");
			}
			writer.print(weights.get(weights.size()-1));
			writer.println("");
			for (int i = 0; i < foodList.size() - 1; i++)
			{
				for (int j = 0; j < 2; j++)
					writer.print(foodList.get(i)[j] + ",");
			}
			writer.print(foodList.get(foodList.size()-1)[0] + ",");
			writer.print(foodList.get(foodList.size()-1)[1]);
			writer.close();
		}
		System.out.println("new best snake saved!");
	}
	
	public void saveBestSnake(int index) throws FileNotFoundException
	{
		System.out.println("new best snake found!");
		PrintWriter writer = new PrintWriter(new FileOutputStream("best snake example.txt", false));
		writer.println("gen: " + gen);
		writer.println("score: " + this.currentGenBestScore);
		ArrayList<Double> weights = snakes[index].brain.toArrayList();
		ArrayList<int[]> foodList = snakes[index].foodList;
		if (weights.size() > 0)
		{
			for (int i = 0; i < weights.size() - 1; i++)
			{
				writer.print(weights.get(i) + ",");
			}
			writer.print(weights.get(weights.size()-1));
			writer.println("");
			for (int i = 0; i < foodList.size() - 1; i++)
			{
				for (int j = 0; j < 2; j++)
					writer.print(foodList.get(i)[j] + ",");
			}
			writer.print(foodList.get(foodList.size()-1)[0] + ",");
			writer.print(foodList.get(foodList.size()-1)[1]);
			writer.close();
		}
		System.out.println("new best snake saved!");
	}
	
	public void savePopulation() throws FileNotFoundException
	{
		System.out.println("saving...");
		PrintWriter writer = new PrintWriter(new FileOutputStream("saved population.txt", false));
		writer.println((gen));
		writer.println("score: " + bestSnakeScore);
		for (int j = 0; j < this.snakes.length; j++)
		{
			ArrayList<Double> weights = snakes[j].brain.toArrayList();
			if (weights.size() > 0)
			{
				for (int i = 0; i < weights.size() - 1; i++)
				{
					writer.print(weights.get(i) + ",");
				}
				writer.print(weights.get(weights.size()-1));
				writer.println("");
			}
		}
		writer.close();
		System.out.println("saved gen " + (gen) + " successfully!");
	}
	
	public void loadPopulation(String file) throws FileNotFoundException
	{
		File loadedSnakes = new File(file);
		Scanner in = new Scanner(loadedSnakes);
		gen = Integer.parseInt(in.nextLine());
		in.nextLine();
		for (int i = 0; i < snakes.length; i++)
		{
			snakes[i].brain.convertFromArray(in.nextLine().split(","));
		}
		in.close();
		System.out.println("loaded gen " + (gen) + " successfully!");
	}
	
	public void saveForGraph() throws FileNotFoundException
	{
		PrintWriter writer = new PrintWriter(new FileOutputStream("generations.txt", true));
		writer.println((gen));
		writer.close();
		writer = new PrintWriter(new FileOutputStream("fitness sums.txt", true));
		writer.println(this.fitnessSum);
		writer.close();
		writer = new PrintWriter(new FileOutputStream("best scores.txt", true));
		writer.println(this.bestSnakeScore);
		writer.close();
		writer = new PrintWriter(new FileOutputStream("best fitness of gens.txt", true));
		writer.println(this.currentGenBestFitness);
		writer.close();
	}
	
	public void clearSavedDataForGraph() throws FileNotFoundException
	{
		PrintWriter writer = new PrintWriter(new FileOutputStream("generations.txt", false));
		writer.print("");
		writer.close();
		writer = new PrintWriter(new FileOutputStream("fitness sums.txt", false));
		writer.print("");
		writer.close();
		writer = new PrintWriter(new FileOutputStream("best scores.txt", false));
		writer.print("");
		writer.close();
		writer = new PrintWriter(new FileOutputStream("best fitness of gens.txt", false));
		writer.print("");
		writer.close();
	}
}
