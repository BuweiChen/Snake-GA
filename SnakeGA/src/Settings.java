
public class Settings {
	public static final int GAMESIZE = 10;
	public static final int BOUND = 100;
	public static final int[] GAMEWINDOW = new int[] {800, 10};	
	public static double ReLU(double x)
	{
		if (x > 0)
			return x;
		else
			return 0;
	}
	
	public static double Sigmoid(double x)
	{
		return 1/(1 + Math.pow(Math.E, -x));
	}
	
	public static double rewardFuntion(int steps, int apples)
	{
		return steps + (Math.pow(2, apples) + Math.pow(apples, 2.1) * 500) - (Math.pow(apples, 1.2) * Math.pow(0.25 * steps, 1.3));
	}
}
