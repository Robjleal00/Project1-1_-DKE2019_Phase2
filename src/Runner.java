import java.io.FileNotFoundException;
import java.util.Scanner;

public class Runner {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("************************");
		System.out.println("******CONSOLE TEST******");
		System.out.println("************************");
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter the height of the rectangle: ");
		int height = in.nextInt();
		System.out.println("Enter the width of the rectangle: ");
		int width = in.nextInt();
		System.out.println("Enter \"yes\" for animated version and \"no\" for the faster answer: ");
		String animation = in.next();
		
		System.out.println("Enter the number of pentominoes: ");
		int inputSize = in.nextInt();
		System.out.println("Enter the all the pentominoes, separated with one empty space (e.g. X F Y): ");
		String[] input = new String[inputSize];
		for (int i = 0; i < inputSize; i++) {
			input[i] = in.next();
		}
		char[] pentominoes = new char[inputSize];
		for (int i = 0; i < inputSize; i++) {
			pentominoes[i] = (input[i].toCharArray())[0];
		}
	
		in.close();
		System.out.println("Searching for any possible solution...");
		long start = System.nanoTime();
		Search search = new Search(width, height, animation.equalsIgnoreCase("yes"), pentominoes);	
		long finish = System.nanoTime();
		System.out.println("Execution time is " + ((double)(finish - start) / 1e9) + " s");
	}
}