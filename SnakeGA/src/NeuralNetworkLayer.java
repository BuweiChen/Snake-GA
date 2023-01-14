import java.util.ArrayList;
public class NeuralNetworkLayer {
	private ArrayList<Neuron> neurons;
	private String id;
	public NeuralNetworkLayer(String id)
	{
		neurons = new ArrayList<Neuron>();
		this.id = id;
	}
	
	public NeuralNetworkLayer(String id, int neuronCount, String activationFunction)
	{
		neurons = new ArrayList<Neuron>();
		this.id = id;
		for (int i = 0; i < neuronCount; i++)
		{
			neurons.add(new Neuron(activationFunction));
		}
	}
	
	public ArrayList<Neuron> getNeurons()
	{
		return neurons;
	}
}
