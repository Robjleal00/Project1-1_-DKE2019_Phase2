import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;



public class Test {
	public static void main(String[]args) throws FileNotFoundException {
		File file = new File("scores.txt");
		PrintWriter out = new PrintWriter(file);
		Scanner in = new Scanner(file);
		String input = "Inquation"; // This line is replaced by the input of the user.
		
		while(in.hasNextLine()) {
			if() {
				
			}
		}
	}
	
	public static String nameRetriever(String line) {
		//Expected format <tag>:<score>
		for() {
			//you retrieve the name 
		}
	}
	public static String scoreRetriever(String line) {
		//Expected format <tag>:<score>
		for() {
			//you retrieve the score
		}
		
		//if you get nothing you throw an error 
	}
	
}
