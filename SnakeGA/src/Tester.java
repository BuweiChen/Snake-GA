import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tester{

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("best fitness of gens.txt"));
		PrintWriter writer = new PrintWriter(new FileOutputStream("root2 best fitness of gens.txt", false));
		ArrayList<Double> arr = new ArrayList<Double>();
		while(in.hasNextLine())
		{
			arr.add(Double.parseDouble(in.nextLine()));
		}
		
		for(int i = 0; i < arr.size(); i++)
		{
			writer.println(Math.sqrt(arr.get(i)));
		}
		writer.close();
		in.close();
		
		in = new Scanner(new File("best fitness of gens.txt"));
		writer = new PrintWriter(new FileOutputStream("root3 best fitness of gens.txt", false));
		arr = new ArrayList<Double>();
		while(in.hasNextLine())
		{
			arr.add(Double.parseDouble(in.nextLine()));
		}
		
		for(int i = 0; i < arr.size(); i++)
		{
			writer.println(Math.cbrt(arr.get(i)));
		}
		writer.close();
		in.close();
	}

}
