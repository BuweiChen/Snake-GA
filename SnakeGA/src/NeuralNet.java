import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
public class NeuralNet {
	private NeuralNetworkLayer inputLayer;
	private ArrayList<NeuralNetworkLayer> hiddenLayers;
	private NeuralNetworkLayer outputLayer;
	private String id;
	public NeuralNet(String id, NeuralNetworkLayer inputLayer, ArrayList<NeuralNetworkLayer> hiddenLayers, NeuralNetworkLayer outputLayer) 
	{
		this.id = id;
		this.inputLayer = inputLayer;
		this.hiddenLayers = hiddenLayers;
		this.outputLayer = outputLayer;
		this.initiateNeuralNet();
	}
	
	public NeuralNetworkLayer getInputLayer()
	{
		return inputLayer;
	}
	
	public ArrayList<NeuralNetworkLayer> getHiddenLayers()
	{
		return hiddenLayers;
	}
	
	public NeuralNetworkLayer getOutputLayer()
	{
		return outputLayer;
	}
	
	public Neuron returnBiggestOutput()
	{
		double biggest = outputLayer.getNeurons().get(0).calculateOutput();
		int index = 0;
		for (int i = 1; i < outputLayer.getNeurons().size(); i++)
		{
			if (outputLayer.getNeurons().get(i).calculateOutput() > biggest)
			{
				biggest = outputLayer.getNeurons().get(i).calculateOutput();
				index = i;
			}
		}
		return outputLayer.getNeurons().get(index);
	}
	
	public int returnBiggestOutputIndex()
	{
		double biggest = outputLayer.getNeurons().get(0).calculateOutput();
		int index = 0;
		for (int i = 1; i < outputLayer.getNeurons().size(); i++)
		{
			if (outputLayer.getNeurons().get(i).calculateOutput() > biggest)
			{
				biggest = outputLayer.getNeurons().get(i).calculateOutput();
				index = i;
			}
		}
		return index;
	}
	
	public ArrayList<Double> toArrayList()
	{
		ArrayList<Double> temp = new ArrayList<Double>();
		for (int i = 0; i < inputLayer.getNeurons().size(); i++)
		{
			for (int j = 0; j < inputLayer.getNeurons().get(i).getOutputConnections().size(); j++)
			{
				temp.add(inputLayer.getNeurons().get(i).getOutputConnections().get(j).getWeight());
			}
		}
		
		for (int l = 0; l < hiddenLayers.size(); l++)
		{
			for (int i = 0; i < hiddenLayers.get(l).getNeurons().size(); i++)
			{
				for (int j = 0; j < hiddenLayers.get(l).getNeurons().get(i).getOutputConnections().size(); j++)
				{
					temp.add(hiddenLayers.get(l).getNeurons().get(i).getOutputConnections().get(j).getWeight());
				}
			}
		}
		
		return temp;
	}
	
	public void convertFromArrayList(ArrayList<Double> weights)
	{
		int weightsIndex = 0;
		
		for (int i = 0; i < inputLayer.getNeurons().size(); i++)
		{
			for (int j = 0; j < inputLayer.getNeurons().get(i).getOutputConnections().size(); j++)
			{
				inputLayer.getNeurons().get(i).getOutputConnections().get(j).setWeight(weights.get(weightsIndex));
				weightsIndex++;
			}
		}
		
		for (int l = 0; l < hiddenLayers.size(); l++)
		{
			for (int i = 0; i < hiddenLayers.get(l).getNeurons().size(); i++)
			{
				for (int j = 0; j < hiddenLayers.get(l).getNeurons().get(i).getOutputConnections().size(); j++)
				{
					hiddenLayers.get(l).getNeurons().get(i).getOutputConnections().get(j).setWeight(weights.get(weightsIndex));
					weightsIndex++;
				}
			}
		}
	}
	
	public void convertFromArray(double[] weights)
	{
		int weightsIndex = 0;
		
		for (int i = 0; i < inputLayer.getNeurons().size(); i++)
		{
			for (int j = 0; j < inputLayer.getNeurons().get(i).getOutputConnections().size(); j++)
			{
				inputLayer.getNeurons().get(i).getOutputConnections().get(j).setWeight(weights[weightsIndex]);
				weightsIndex++;
			}
		}
		
		for (int l = 0; l < hiddenLayers.size(); l++)
		{
			for (int i = 0; i < hiddenLayers.get(l).getNeurons().size(); i++)
			{
				for (int j = 0; j < hiddenLayers.get(l).getNeurons().get(i).getOutputConnections().size(); j++)
				{
					hiddenLayers.get(l).getNeurons().get(i).getOutputConnections().get(j).setWeight(weights[weightsIndex]);
					weightsIndex++;
				}
			}
		}
	}
	
	public void convertFromArray(String[] weights)
	{
		int weightsIndex = 0;
		
		for (int i = 0; i < inputLayer.getNeurons().size(); i++)
		{
			for (int j = 0; j < inputLayer.getNeurons().get(i).getOutputConnections().size(); j++)
			{
				inputLayer.getNeurons().get(i).getOutputConnections().get(j).setWeight(Double.parseDouble(weights[weightsIndex]));
				weightsIndex++;
			}
		}
		
		for (int l = 0; l < hiddenLayers.size(); l++)
		{
			for (int i = 0; i < hiddenLayers.get(l).getNeurons().size(); i++)
			{
				for (int j = 0; j < hiddenLayers.get(l).getNeurons().get(i).getOutputConnections().size(); j++)
				{
					hiddenLayers.get(l).getNeurons().get(i).getOutputConnections().get(j).setWeight(Double.parseDouble(weights[weightsIndex]));
					weightsIndex++;
				}
			}
		}
	}
	
	public void initiateNeuralNet()
	{
		for (int i = 0; i < inputLayer.getNeurons().size(); i++)
		{
			for (int j = 0; j < hiddenLayers.get(0).getNeurons().size(); j++)
			{
				Connection temp = new Connection(inputLayer.getNeurons().get(i), hiddenLayers.get(0).getNeurons().get(j));
					inputLayer.getNeurons().get(i).addOutputConnection(temp);
					hiddenLayers.get(0).getNeurons().get(j).addInputConnection(temp);
			}
		}
		for (int l = 0; l < hiddenLayers.size() - 1; l++)
		{
			for (int i = 0; i < hiddenLayers.get(l).getNeurons().size(); i++)
			{
				for (int j = 0; j < hiddenLayers.get(l + 1).getNeurons().size(); j++)
				{
					Connection temp = new Connection(hiddenLayers.get(l).getNeurons().get(i), hiddenLayers.get(l + 1).getNeurons().get(j));
						hiddenLayers.get(l).getNeurons().get(i).addOutputConnection(temp);
						hiddenLayers.get(l + 1).getNeurons().get(j).addInputConnection(temp);
				}
			}
		}
		for (int i = 0; i < hiddenLayers.get(hiddenLayers.size() - 1).getNeurons().size(); i++)
		{
			for (int j = 0; j < outputLayer.getNeurons().size(); j++)
			{
				Connection temp = new Connection(hiddenLayers.get(hiddenLayers.size() - 1).getNeurons().get(i), outputLayer.getNeurons().get(j));
					hiddenLayers.get(hiddenLayers.size() - 1).getNeurons().get(i).addOutputConnection(temp);
					outputLayer.getNeurons().get(j).addInputConnection(temp);
			}
		}
	}
	
	public void draw(float[] vision, Graphics2D g)
	{
		/*
		float space = 5;
		float nSize = (h - (space*(this.inputLayer.getNeurons().size()-2))) / this.inputLayer.getNeurons().size();
		float nSpace = (w - ((this.inputLayer.getNeurons().size() * this.hiddenLayers.get(0).getNeurons().size() + this.hiddenLayers.get(0).getNeurons().size() * this.hiddenLayers.get(1).getNeurons().size() + this.hiddenLayers.get(1).getNeurons().size() * this.outputLayer.getNeurons().size()) *nSize)) / (this.inputLayer.getNeurons().size() * this.hiddenLayers.get(0).getNeurons().size() + this.hiddenLayers.get(0).getNeurons().size() * this.hiddenLayers.get(1).getNeurons().size() + this.hiddenLayers.get(1).getNeurons().size() * this.outputLayer.getNeurons().size());
		*/
		int i = 0;
		int i1 = 0;
		int i2 = 0;
		if (vision.length > 0)
		{
			
			ArrayList<Double> weights = this.toArrayList();
			int w = 0;
			for (int m = 0; m < this.inputLayer.getNeurons().size(); m++)
			{
				for (int n = 0; n < this.hiddenLayers.get(0).getNeurons().size(); n++)
				{
					Line2D.Double weight = new Line2D.Double(20, 12.5 + 20 * m, 125, 12.5 + 20 * (n + 6));
					if (weights.get(w) >= 0)
					{
						g.setColor(Color.red);
					}
					else
						g.setColor(Color.blue);
					g.draw(weight);
					w++;
				}
			}
			
			for (int m = 0; m < this.hiddenLayers.get(0).getNeurons().size(); m++)
			{
				for (int n = 0; n < this.hiddenLayers.get(1).getNeurons().size(); n++)
				{
					Line2D.Double weight = new Line2D.Double(140, 12.5 + 20 * (m + 6), 245, 12.5 + 20 * (n + 10));
					if (weights.get(w) >= 0)
					{
						g.setColor(Color.red);
					}
					else
						g.setColor(Color.blue);
					g.draw(weight);
					w++;
				}
			}
			
			for (int m = 0; m < this.hiddenLayers.get(1).getNeurons().size(); m++)
			{
				for (int n = 0; n < this.outputLayer.getNeurons().size(); n++)
				{
					Line2D.Double weight = new Line2D.Double(260, 12.5 + 20 * (m + 10), 365, 12.5 + 20 * (n + 14));
					if (weights.get(w) >= 0)
					{
						g.setColor(Color.red);
					}
					else
						g.setColor(Color.blue);
					g.draw(weight);
					w++;
				}
			}
						
			
			for (; i < this.inputLayer.getNeurons().size() - 8; i++)
			{
				if (vision[i] != 0)
				{
					g.setColor(new Color(0, (int) (vision[i] * 100 + 155), 0));
				}
				else
				{
					g.setColor(Color.black);
				}
				Ellipse2D.Double node = new Ellipse2D.Double(5, 5 + 20 * i, 15, 15);
				g.fill(node);
				g.setColor(Color.black);
				g.draw(node);
			}
			
			for (; i < this.inputLayer.getNeurons().size(); i++)
			{
				if (inputLayer.getNeurons().get(i).getInput() != 0)
				{
					g.setColor(new Color(0, 255, 0));
				}
				else
					g.setColor(Color.black);
				Ellipse2D.Double node = new Ellipse2D.Double(5, 5 + 20 * i, 15, 15);
				g.fill(node);
				g.setColor(Color.black);
				g.draw(node);
			}
			
			for (; i1 < this.hiddenLayers.get(0).getNeurons().size(); i1++)
			{
				if (this.hiddenLayers.get(0).getNeurons().get(i1).calculateOutput() != 0)
				{
					g.setColor(new Color(0, 255, 0));
				}
				else
					g.setColor(Color.black);
				Ellipse2D.Double node = new Ellipse2D.Double(125, 5 + 20 * (i1 + 6), 15, 15);
				g.fill(node);
				g.setColor(Color.black);
				g.draw(node);
			}
			
			for (; i2 < this.hiddenLayers.get(1).getNeurons().size(); i2++)
			{
				if (this.hiddenLayers.get(0).getNeurons().get(i2).calculateOutput() != 0)
				{
					g.setColor(new Color(0, 255, 0));
				}
				else
					g.setColor(Color.black);
				Ellipse2D.Double node = new Ellipse2D.Double(245, 5 + 20 * (i2 + 10), 15, 15);
				g.fill(node);
				g.setColor(Color.black);
				g.draw(node);
			}
			
			int decision = returnBiggestOutputIndex();
			for (int i3 = 0; i3 < 4; i3++)
			{
				if (i3 == decision)
					g.setColor(new Color(0, 255, 0));
				else
					g.setColor(Color.black);
				Ellipse2D.Double node = new Ellipse2D.Double(365, 5 + 20 * (i3 + 14), 15, 15);
				g.fill(node);
				g.setColor(Color.black);
				g.draw(node);
			}
			
			
			
		}
		
		
	}
}