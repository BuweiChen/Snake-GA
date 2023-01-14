import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Food {
	int posx;
	int posy;
	
	public Food()
	{
		posx = 0;
		posy = 0;
	}
	
	public Food(int x, int y)
	{
		posx = x;
		posy = y;
	}
	
	public void draw(Graphics2D g)
	{
		Rectangle food = new Rectangle(posx, posy, Settings.GAMESIZE, Settings.GAMESIZE);
		g.setColor(Color.red);
		g.fill(food);
	}
	/**
	public void update()
	{
		Random rn = new Random();
		posx = rn.nextInt(gameplay.length/10) * 10;
		posy = rn.nextInt(gameplay.width/10) * 10;
		for (int i = 0; i < gameplay.snake.snakeBody.size(); i++)
		{
			if (posx == gameplay.snake.snakeBody.get(i).getX() && posy == gameplay.snake.snakeBody.get(i).getY())
			{
				update();
			}
			if (posx == gameplay.snake.posx && posy == gameplay.snake.posy)
			{
				update();
			}
		}
		
	}
	**/
	
	public void update(int x, int y)
	{
		posx = x;
		posy = y;
	}
	
	public void update(int[] xy)
	{
		posx = xy[0];
		posy = xy[1];
	}

}
