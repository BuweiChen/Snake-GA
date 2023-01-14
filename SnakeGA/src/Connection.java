
public class Connection {
	private Neuron fromNeuron;
	private Neuron toNeuron;
	private double weight;
	private String id;
	
	public Connection(Neuron fromNeuron, Neuron toNeuron) {
		this.fromNeuron = fromNeuron;
		this.toNeuron = toNeuron;
		this.weight = Math.random();
		if (Math.random() > 0.5) 
			this.weight = - weight;
	}
	
	public Connection(Neuron fromNeuron, Neuron toNeuron, double weight) {
		this(fromNeuron, toNeuron);
		this.weight = weight;
		if (Math.random() > 0.5) 
			this.weight = - weight;
	}
	
	public Connection(Neuron fromNeuron, Neuron toNeuron, String id) {
		this.fromNeuron = fromNeuron;
		this.toNeuron = toNeuron;
		this.weight = Math.random();
		if (Math.random() > 0.5) 
			this.weight = - weight;
		this.id = id;
		}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public double getInput() {
		return fromNeuron.calculateOutput();
	}
	
	public double getWeightedInput() {
		return fromNeuron.calculateOutput() * weight;
	}
	
	public Neuron getFromNeuron() {
		return fromNeuron;
	}
	
	public Neuron getToNeuron() {
		return toNeuron;
	}
}
