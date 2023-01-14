import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class SnakeTrainer {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		Population population = new Population(1000);
		population.loadPopulation("saved population.txt");
		while (population.bestSnakeScore < 100)
		{
			if (!population.isDone())
			{
				population.update();
			}
			else
			{
				population.update();
				try {
					population.naturalSelection();
					population.saveForGraph();
					if (population.gen % 100 == 0) {
						population.savePopulation();
					}
				} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				}
				System.out.print("Gen: " + population.gen + "; ");
				System.out.print("Best fitness: " + population.bestFitness + "; ");
				System.out.print("Current gen best score: " + population.currentGenBestScore + "; ");
				System.out.print("Current gen best fitness: " + population.currentGenBestFitness + "; ");
				System.out.println("Total fitness : " + population.fitnessSum);
			}
		}
	}
}
