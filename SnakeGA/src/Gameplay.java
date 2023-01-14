import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private Timer timer;
	private int delay = 0;
	Population population;
	
	public Gameplay() throws FileNotFoundException, UnsupportedEncodingException
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(delay, this);
		population = new Population(1000);
		population.loadPopulation("saved population.txt");
		timer.start();
	}
	
	public void paint(Graphics g)
	{
		Rectangle snakeBoarder = new Rectangle(Settings.GAMEWINDOW[0], Settings.GAMEWINDOW[1], Settings.BOUND, Settings.BOUND);
		g.setColor(Color.BLACK);
		((Graphics2D)g).draw(snakeBoarder);	
		population.draw((Graphics2D)g);
		population.bestSnake.food.draw((Graphics2D)g);
		population.bestSnake.brain.draw(population.bestSnake.sight, (Graphics2D)g);
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (!population.isDone())
		{
			population.update();
			repaint();
		}
		else
		{
			/*
			System.out.println((population.snakes[1].brain.getInputLayer().getNeurons().get(0).getOutputConnections().get(0).getWeight()));
			*/
			population.update();
			try {
				population.naturalSelection();
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			System.out.print("Gen: " + population.gen + "; ");
			System.out.print("Best fitness: " + population.bestFitness + "; ");
			System.out.print("Current gen best score: " + population.currentGenBestScore + "; ");
			System.out.print("Current gen best fitness: " + population.currentGenBestFitness + "; ");
			System.out.println("Total fitness : " + population.fitnessSum);
			repaint();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		/*
		if (Settings.isHumanPlayer)
		{
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				if (snake.yspeed == 0)
				{
					snake.xspeed = 0;
					snake.yspeed = 10;
				}
				repaint();
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP)
			{
			
				if (snake.yspeed == 0)
				{
					snake.xspeed = 0;
				snake.yspeed = -10;
				}
				repaint();
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if (snake.xspeed == 0)
				{
					snake.xspeed = -10;
					snake.yspeed = 0;
				}
				repaint();
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if (snake.xspeed == 0)
				{
					snake.xspeed = 10;
					snake.yspeed = 0;
				}
				repaint();
			}
		}
		*/
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
