import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JPanel;
public class Snake extends JPanel {
	
	int posx;
	int posy;
	int xspeed;
	int yspeed;
	ArrayList<SnakeBody> snakeBody;
	boolean isDead;
	Food food;
	boolean isGrowing;
	Random rn;
	int[] tailDirection;
	double fitness;
	int stepsLeft;
	int steps;
	int apples;
	float[] sight;
	NeuralNet brain;
	double mutationRate;
	boolean replay;
	boolean perfectReplay = false;
	ArrayList<int[]> foodList;
	
	public Snake()
	{
		posx = Settings.GAMEWINDOW[0] + 5*Settings.GAMESIZE;
		posy = Settings.GAMEWINDOW[1] + 5*Settings.GAMESIZE;
		xspeed = 0;
		yspeed = 0;
		snakeBody = new ArrayList<SnakeBody>();
		isDead = false;
		food = new Food();
		rn = new Random();
		foodList = new ArrayList<int[]>();
		food.update(getNewFoodCoordinates(rn));
		isGrowing = false;
		tailDirection = new int[] {0, 0};
		fitness = 0;
		stepsLeft = 100;
		NeuralNetworkLayer inputLayer = new NeuralNetworkLayer("input_layer", 32, "ReLU");
		ArrayList<NeuralNetworkLayer> hiddenLayers = new ArrayList<NeuralNetworkLayer>();
		NeuralNetworkLayer hiddenLayer_1 = new NeuralNetworkLayer("hidden_layer_1", 20, "ReLU");
		NeuralNetworkLayer hiddenLayer_2 = new NeuralNetworkLayer("hidden_layer_2", 12, "ReLU");
		hiddenLayers.add(hiddenLayer_1);
		hiddenLayers.add(hiddenLayer_2);
		NeuralNetworkLayer outputLayer = new NeuralNetworkLayer("output_layer", 4, "Sigmoid");
		brain = new NeuralNet("brain", inputLayer, hiddenLayers, outputLayer);
		mutationRate = 0.05;
		replay = false;
		sight = new float[0];
	}
	
	public Snake(double[] weights)
	{
		posx = Settings.GAMEWINDOW[0] + 5*Settings.GAMESIZE;
		posy = Settings.GAMEWINDOW[1] + 5*Settings.GAMESIZE;
		xspeed = 0;
		yspeed = 0;
		snakeBody = new ArrayList<SnakeBody>();
		isDead = false;
		food = new Food();
		rn = new Random();
		foodList = new ArrayList<int[]>();
		food.update(getNewFoodCoordinates(rn));
		isGrowing = false;
		tailDirection = new int[] {0, 0};
		fitness = 0;
		stepsLeft = 100;
		NeuralNetworkLayer inputLayer = new NeuralNetworkLayer("input_layer", 32, "ReLU");
		ArrayList<NeuralNetworkLayer> hiddenLayers = new ArrayList<NeuralNetworkLayer>();
		NeuralNetworkLayer hiddenLayer_1 = new NeuralNetworkLayer("hidden_layer_1", 20, "ReLU");
		NeuralNetworkLayer hiddenLayer_2 = new NeuralNetworkLayer("hidden_layer_2", 12, "ReLU");
		hiddenLayers.add(hiddenLayer_1);
		hiddenLayers.add(hiddenLayer_2);
		NeuralNetworkLayer outputLayer = new NeuralNetworkLayer("output_layer", 4, "Sigmoid");
		brain = new NeuralNet("brain", inputLayer, hiddenLayers, outputLayer);
		brain.convertFromArray(weights);
		mutationRate = 0.05;
		replay = false;
		sight = new float[0];
	}
	
	public Snake(boolean perfectReplay) throws FileNotFoundException
	{
		this.perfectReplay = true;
		posx = Settings.GAMEWINDOW[0] + 5*Settings.GAMESIZE;
		posy = Settings.GAMEWINDOW[1] + 5*Settings.GAMESIZE;
		xspeed = 0;
		yspeed = 0;
		snakeBody = new ArrayList<SnakeBody>();
		isDead = false;
		food = new Food();
		rn = new Random();
		isGrowing = false;
		tailDirection = new int[] {0, 0};
		fitness = 0;
		stepsLeft = 100;
		NeuralNetworkLayer inputLayer = new NeuralNetworkLayer("input_layer", 32, "ReLU");
		ArrayList<NeuralNetworkLayer> hiddenLayers = new ArrayList<NeuralNetworkLayer>();
		NeuralNetworkLayer hiddenLayer_1 = new NeuralNetworkLayer("hidden_layer_1", 20, "ReLU");
		NeuralNetworkLayer hiddenLayer_2 = new NeuralNetworkLayer("hidden_layer_2", 12, "ReLU");
		hiddenLayers.add(hiddenLayer_1);
		hiddenLayers.add(hiddenLayer_2);
		NeuralNetworkLayer outputLayer = new NeuralNetworkLayer("output_layer", 4, "Sigmoid");
		brain = new NeuralNet("brain", inputLayer, hiddenLayers, outputLayer);
		mutationRate = 0.05;
		replay = false;
		sight = new float[0];
		foodList = new ArrayList<int[]>();
		this.loadBestSnake();
		food.update(getNewFoodCoordinates(rn));
	}
	
	public void draw(Graphics2D g)
	{
		for (int i = 0; i < snakeBody.size(); i++)
		{
			Rectangle bodyPart = new Rectangle(snakeBody.get(i).getX(), snakeBody.get(i).getY(), Settings.GAMESIZE, Settings.GAMESIZE);
			g.setColor(Color.green);
			g.fill(bodyPart);
		}
		Rectangle body = new Rectangle(posx, posy, Settings.GAMESIZE, Settings.GAMESIZE);
		if (!isDead)
			g.setColor(Color.green);
		else
			g.setColor(Color.red);
		g.fill(body);
	}
	
	public void move()
	{
		think();
		int lastX = posx;
		int lastY = posy;
		posx = posx + xspeed;
		posy = posy + yspeed;
		if (snakeBody.size() > 0)
		{
			for (int i = snakeBody.size() - 1; i > 0; i--)
			{
				snakeBody.get(i).move(snakeBody.get(i - 1).getX(), snakeBody.get(i - 1).getY());
			}
			snakeBody.get(0).posx = lastX;
			snakeBody.get(0).posy = lastY;
		}
		updateTailDirection();
		steps++;
		stepsLeft--;

	}
	
	public void update()
	{
		if (!isDead)
		{
			if (isGrowing == true) 
			{
				snakeBody.add(0, new SnakeBody(posx, posy));
				posx += xspeed; posy += yspeed;	
				isGrowing = false;
			}
			else
			{
				move();
			}
			
		}
		else
		{
			this.updateFitness();
		}
		for (int i = 0; i < snakeBody.size(); i++)
		{
			if (posx == snakeBody.get(i).getX() && posy == snakeBody.get(i).getY())
			{
				isDead = true;
			}
		}
		if (posx >= Settings.BOUND + Settings.GAMEWINDOW[0] || posx < 0 + Settings.GAMEWINDOW[0] || posy >= Settings.BOUND + Settings.GAMEWINDOW[1] || posy < 0 + Settings.GAMEWINDOW[1])
		{
			isDead = true;
		}
		if (posx == food.posx && posy == food.posy)
		{
			isGrowing = true;
			apples++;
			stepsLeft += 50;
			food.update(getNewFoodCoordinates(rn));
		}
		if (stepsLeft <= 0)
		{
			isDead = true;
		}
	}
	
	public int[] getNewFoodCoordinates(Random rn)
	{
		if (perfectReplay)
		{
			return foodList.get(apples);
		}
		
		int rX;
		int rY;
		boolean isIllegal = false;
		do 
		{
			rX = rn.nextInt(Settings.BOUND / Settings.GAMESIZE) * Settings.GAMESIZE + Settings.GAMEWINDOW[0];
			rY = rn.nextInt(Settings.BOUND / Settings.GAMESIZE) * Settings.GAMESIZE + Settings.GAMEWINDOW[1];
			isIllegal = false;
			for (int i = 0; i < snakeBody.size(); i++)
			{
				if (rX == snakeBody.get(i).getX() && rY == snakeBody.get(i).getY())
				{
					isIllegal = true;
				}
			}
			if (rX == posx && rY == posy)
			{
				isIllegal = true;
			}
		}
		while (isIllegal == true);
		foodList.add(new int[] {rX, rY});
		return new int[] {rX, rY};
	}
	
	public void updateTailDirection()
	{
		if (snakeBody.size() < 1)
		{
			tailDirection = new int[] {xspeed / 10, yspeed / 10};
		}
		else if(snakeBody.size() < 2)
		{
			tailDirection = new int[] {(posx - snakeBody.get(0).posx) / 10, (posy - snakeBody.get(0).posy) / 10};
		}
		else
		{
			tailDirection = new int[] {(snakeBody.get(snakeBody.size()-2).posx - snakeBody.get(snakeBody.size()-1).posx) / 10, (snakeBody.get(snakeBody.size()-2).posy - snakeBody.get(snakeBody.size()-1).posy) / 10};
		}
	}
	
	public int[] getTailDirection()
	{
		return tailDirection;
	}
	
	public int[] getHeadDirection()
	{
		return new int[] {xspeed / 10, yspeed / 10};
	}
	
	public double calculateFitness()
	{
		return Settings.rewardFuntion(steps, apples);
	}
	
	public void updateFitness()
	{
		fitness = calculateFitness();
	}
	
	public void look()
	{
		sight = new float[24];
	    float[] temp = lookInDirection(new PVector(-Settings.GAMESIZE,0));
	    sight[0] = temp[0];
	    sight[1] = temp[1];
	    sight[2] = temp[2];
	    temp = lookInDirection(new PVector(-Settings.GAMESIZE,-Settings.GAMESIZE));
	    sight[3] = temp[0];
	    sight[4] = temp[1];
	    sight[5] = temp[2];
	    temp = lookInDirection(new PVector(0,-Settings.GAMESIZE));
	    sight[6] = temp[0];
	    sight[7] = temp[1];
	    sight[8] = temp[2];
	    temp = lookInDirection(new PVector(Settings.GAMESIZE,-Settings.GAMESIZE));
	    sight[9] = temp[0];
	    sight[10] = temp[1];
	    sight[11] = temp[2];
	    temp = lookInDirection(new PVector(Settings.GAMESIZE,0));
	    sight[12] = temp[0];
	    sight[13] = temp[1];
	    sight[14] = temp[2];
	    temp = lookInDirection(new PVector(Settings.GAMESIZE,Settings.GAMESIZE));
	    sight[15] = temp[0];
	    sight[16] = temp[1];
	    sight[17] = temp[2];
	    temp = lookInDirection(new PVector(0,Settings.GAMESIZE));
	    sight[18] = temp[0];
	    sight[19] = temp[1];
	    sight[20] = temp[2];
	    temp = lookInDirection(new PVector(-Settings.GAMESIZE,Settings.GAMESIZE));
	    sight[21] = temp[0];
	    sight[22] = temp[1];
	    sight[23] = temp[2];
	}
	
	public float[] lookInDirection(PVector direction)
	{
		float vision[] = new float[3];
		PVector pos = new PVector(posx, posy);
		float distance = 0;
		boolean bodyFound = false;
		boolean foodFound = false;
		pos.add(direction);
		distance++;
		while (!wallCollide(pos.x, pos.y))
		{
			if (!foodFound && foodCollide(pos.x, pos.y))
			{
				foodFound = true;
				vision[0] = 1 / distance;
			}
			
			if (!bodyFound && bodyCollide(pos.x, pos.y))
			{
				bodyFound = true;
				vision[1] = 1 / distance;
			}
			
			pos.add(direction);
			distance++;
		}
		vision[2] = 1 / distance;
		return vision;
	}
	
	public boolean wallCollide(double x, double y)
	{
		return x < Settings.GAMEWINDOW[0] || x > Settings.BOUND + Settings.GAMEWINDOW[0] || y < Settings.GAMEWINDOW[1] || y > Settings.BOUND + Settings.GAMEWINDOW[1];
	}
	
	public boolean foodCollide(double x, double y)
	{
		return food.posx == x && food.posy == y;
	}
	
	public boolean bodyCollide(double x, double y)
	{
		for (int i = 0; i < snakeBody.size(); i++)
		{
			if (snakeBody.get(i).posx == x && snakeBody.get(i).posy == y)
			{
				return true;
			}
		}
		return false;
	}
	
	public void think()
	{
		look();
		for (int i = 0; i < 24; i++)
		{
			brain.getInputLayer().getNeurons().get(i).setInput(sight[i]);
		}
		
		brain.getInputLayer().getNeurons().get(24).setInput(0);
		brain.getInputLayer().getNeurons().get(25).setInput(0);
		brain.getInputLayer().getNeurons().get(26).setInput(0);
		brain.getInputLayer().getNeurons().get(27).setInput(0);
		brain.getInputLayer().getNeurons().get(28).setInput(0);
		brain.getInputLayer().getNeurons().get(29).setInput(0);
		brain.getInputLayer().getNeurons().get(30).setInput(0);
		brain.getInputLayer().getNeurons().get(31).setInput(0);
		
		if (tailDirection[0] == 1)
			brain.getInputLayer().getNeurons().get(24).setInput(1);
		else if (tailDirection[0] == -1)
			brain.getInputLayer().getNeurons().get(25).setInput(1);
		else if (tailDirection[1] == 1)
			brain.getInputLayer().getNeurons().get(26).setInput(1);
		else if (tailDirection[1] == -1)
			brain.getInputLayer().getNeurons().get(27).setInput(1);
		
		if (this.getHeadDirection()[0] == 1)
			brain.getInputLayer().getNeurons().get(28).setInput(1);
		else if (this.getHeadDirection()[0] == -1)
			brain.getInputLayer().getNeurons().get(29).setInput(1);
		else if (this.getHeadDirection()[1] == 1)
			brain.getInputLayer().getNeurons().get(30).setInput(1);
		else if (this.getHeadDirection()[1] == -1)
			brain.getInputLayer().getNeurons().get(31).setInput(1);
		
		int decision = brain.returnBiggestOutputIndex();
		switch(decision)
		{
		case 0: moveUp(); break;
		case 1: moveDown(); break;
		case 2: moveLeft(); break;
		case 3: moveRight(); break;
		default: break;
		}
	}
	
	public void moveUp() 
	{
		if (yspeed == 0)
		{
			xspeed = 0;
			yspeed = -10;
		}
	}
	
	public void moveDown() 
	{
		if (yspeed == 0)
		{
			xspeed = 0;
			yspeed = 10;
		}
	}
	
	public void moveLeft() 
	{
		if (xspeed == 0)
		{
			xspeed = -10;
			yspeed = 0;
		}
	}
	
	public void moveRight() 
	{
		if (xspeed == 0)
		{
			xspeed = 10;
			yspeed = 0;
		}
	}
	
	public Snake clone()
	{
		Snake clone = new Snake();
		clone.brain.convertFromArrayList(brain.toArrayList());
		return clone;
	}
	
	public Snake crossover(Snake parent)
	{
		Snake child = new Snake();
		ArrayList<Double> p1Weights = brain.toArrayList();
		ArrayList<Double> p2Weights = brain.toArrayList();
		ArrayList<Double> cWeights = new ArrayList<Double>();
		
		double cutPoint = Math.random() * (p1Weights.size()-2) + 1;
		for (int i = 0; i < p1Weights.size(); i++)
		{
			if (i < cutPoint)
				cWeights.add(p1Weights.get(i));
			else
				cWeights.add(p2Weights.get(i));
		}
		child.brain.convertFromArrayList(cWeights);
		return child;
	}

	public void mutate()
	{
		ArrayList<Double> weights = brain.toArrayList();
		for (int i = 0; i < weights.size(); i++)
		{
			if (Math.random() < mutationRate)
			{
				if (Math.random() < 0.5)
					weights.set(i, weights.get(i) + Math.random() / 5);
				else
					weights.set(i, weights.get(i) - Math.random() / 5);
			}
			if (weights.get(i) > 1)
				weights.set(i, 1.0);
			
			if (weights.get(i) < -1)
				weights.set(i, -1.0);
		}
		brain.convertFromArrayList(weights);
	}
	
	public void loadBestSnake() throws FileNotFoundException
	{
		File bestSnake = new File("best snake.txt");
		Scanner in = new Scanner(bestSnake);
		System.out.println("loading best snake!");
		System.out.println(in.nextLine());
		System.out.println(in.nextLine());
		brain.convertFromArray(in.nextLine().split(","));
		String[] tempFoods = in.nextLine().split(",");
		for(int i = 0; i < tempFoods.length - 1; i+=2)
		{
			foodList.add(new int[] {Integer.parseInt(tempFoods[i]), Integer.parseInt(tempFoods[i+1])});
		}
		System.out.println("loaded successfully!");
		in.close();
	}
	
}
