import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBaseUtil {
	
	public static void sort() throws FileNotFoundException {
		String copy = "";
		File file = new File("Score.txt");
		ArrayList<Gamer> gamers = new ArrayList<Gamer>();
		
		
		Scanner in = new Scanner(file);
		while(in.hasNextLine()) {
			String currentLine = in.nextLine();
			String[] pieces = currentLine.split("%");
			Gamer gamer = new Gamer(pieces[0], Integer.parseInt(pieces[1]));
			gamers.add(gamer);
			//gamers.sort(null);
		}
		in.close();
		
		//Sort from highest to lowest
		/**for(int i = 0; i< scores.size(); i++) {
			
		}
		 * int highest = Integer.MIN_VALUE;
		 */
		
	}
	/**
	 * Check if a given player already exists in the database
	 * @param gamerTag
	 * @return true if the player is in the database and false otherwise
	 */
	public static boolean checkFor(String gamerTag) {
		File file = new File("Score.txt");
		try {
			Scanner in = new Scanner(file);
			while(in.hasNextLine()) {
				String currentLine = in.nextLine();
				String[] pieces = currentLine.split("%");
				if(pieces[0].equals(gamerTag)) {
					return true;
				}
			}
			in.close();
			return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * update the the score for a given player
	 * @param gamerTag
	 * @param score
	 */
	public static void update(String gamerTag, int score) {
		File file = new File("Score.txt");
		
		try {
			Scanner in = new Scanner(file);
			while(in.hasNextLine()) {
				String currentLine = in.nextLine();
				String[] pieces = currentLine.split("%");
				if(pieces[0].equals(gamerTag)) {
					pieces[1] = String.valueOf(score);
					PrintWriter out = new PrintWriter(file);
					System.out.println(pieces[0] + "%" + pieces[1]);
				}
				in.close();
				System.out.println(pieces[0] + "%" + pieces[1]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getAllData() throws FileNotFoundException {
		String copy = "";
		File file = new File("Score.txt");
		Scanner in = new Scanner(file);
		while(in.hasNextLine()) {
			String currentLine = in.nextLine();
			String[] pieces = currentLine.split("%");
			copy += pieces[0] + ":" + pieces[1] + System.lineSeparator();
		}
		in.close();
		return copy;
	}
	/**
	 * add a new player at the end of the database
	 * @param gamerTag
	 */
	public static void add(String gamerTag) {
		//Copy file 
		File file = new File("Score.txt");
		String copy = "";
		try {
			Scanner in = new Scanner(file);
			
			while(in.hasNextLine()) {
				String currentLine = in.nextLine();
				copy += currentLine + System.lineSeparator();
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//add new player at the end
			copy += gamerTag + "%" + 0;
			PrintWriter out = new PrintWriter(file);
			out.print(copy);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Get the score for a given player
	 * @param tag
	 * @return
	 * @throws FileNotFoundException
	 */
	public static int getScore(String tag) throws FileNotFoundException {
		File file = new File("Score.txt");
		Scanner in = new Scanner(file);
		while(in.hasNextLine()) {
			String[] pieces = in.nextLine().split("%");
			if(pieces[0].equals("tag")) {
				return Integer.parseInt(pieces[1]);
			}
		}
		in.close();
		return 0;
	}
}