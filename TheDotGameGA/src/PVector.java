
public class PVector {
	double x;
	double y;
	double limit = 1000000000;
	
	public PVector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void add(PVector v)
	{
		x += v.x;
		y += v.y;
		limit(limit);
	}
	
	public void limit(double max)
	{
		limit = max;
		if (x > y)
		{
			if (Math.abs(x) > limit)
			{
				double ratio = x / limit;
				x /= ratio;
				y /= ratio;
			}
		}
		else
		{
			if (Math.abs(y) > limit)
			{
				double ratio = y / limit;
				x /= ratio;
				y /= ratio;
			}
		}
	}
	
	public double dist(PVector p)
	{
		return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
	}

	public PVector copy() 
	{
		return this;
	}
	
	

}
