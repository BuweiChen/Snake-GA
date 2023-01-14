import java.util.ArrayList;
public class Neuron {
	private String id;
	private ArrayList<Connection> inputConnections;
	private ArrayList<Connection> outputConnections;
	private String activationFunction;
	private boolean isInput;
	private double inputValue;
	
	public Neuron(String activationFunction) 
	{
		inputConnections = new ArrayList <Connection> ();
		outputConnections = new ArrayList <Connection> ();
		this.setActivationFunction(activationFunction);
		isInput = false;
		inputValue = 0;
	}
	
	public Neuron(String activationFunction, String id) 
	{
		inputConnections = new ArrayList <Connection> ();
		outputConnections = new ArrayList <Connection> ();
		this.setActivationFunction(activationFunction);
		this.id = id;
		isInput = false;
		inputValue = 0;
	}
	
	public ArrayList<Connection> getInputConnections()
	{
		return inputConnections;
	}
	
	public ArrayList<Connection> getOutputConnections()
	{
		return outputConnections;
	}
	
	public void addInputConnection(Connection connection)
	{
		inputConnections.add(connection);
	}
	
	public void addOutputConnection(Connection connection)
	{
		outputConnections.add(connection);
	}
	
	public void setActivationFunction(String activationFunction)
	{
		this.activationFunction = activationFunction;
	}
	
	public double calculateOutput()
	{
		if(isInput)
			return inputValue;
		double totalInput = 0;
		
		for (int i = 0; i < inputConnections.size(); i++)
		{
			totalInput += inputConnections.get(i).getWeightedInput();
		}
		
		if (activationFunction.equals("Sigmoid"))
			return Settings.Sigmoid(totalInput);
		else
			return Settings.ReLU(totalInput);
	}
	
	public void setInput(double input)
	{
		isInput = true;
		inputValue = input;
	}
	
	public double getInput()
	{
		return inputValue;
	}
}
