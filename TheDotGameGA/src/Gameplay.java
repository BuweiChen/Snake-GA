import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	
	private Timer timer;
	private int delay = 10;
	private Population dots = new Population(1000);
	private Goal goal = new Goal();
	private Map map = new Map(1);	
	public Gameplay()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{
		map.drawMap((Graphics2D)g);
		dots.draw((Graphics2D)g);
		goal.drawGoal((Graphics2D)g);
		dots.showGenNumAndBestStep((Graphics2D)g);
		Font myFont = new Font("myFont", Font.BOLD, 15);
		g.setFont(myFont);
		g.setColor(Color.BLACK);
		g.drawString("Delay: " + timer.getDelay(), 650, 150);
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (dots.allDotsDead())
		{
			dots.calculateFitness();
			dots.naturalSelection();
			dots.mutateTheBabies();
		}
		else
		{
			dots.update();
			repaint();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if (timer.getDelay() > 1)
			{
				timer.setDelay(timer.getDelay() - 1);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			timer.setDelay(timer.getDelay() + 1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	

}
