import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Log {
	public static String filename, resultFilename;
	/**
	 * Make new file
	 * @param the population size
	 * @throws IOException when the file does not exist.
	 */
	public static void init(int n) throws IOException {
		filename = "generation-" + Integer.toString(n) + ".log";
		PrintWriter log = new PrintWriter(filename, "UTF-8");
		log.close();

		resultFilename = "generation-final-" + Integer.toString(n) + ".log";
		PrintWriter result = new PrintWriter(resultFilename, "UTF-8");
		result.close();
	}
	/**
	 * Create a vector 
	 * @param name the file name
	 * @return a vector (Just like in Linear Algebra :D !)
	 * @throws IOException
	 */
	public static Vec[] read(String name) throws IOException {
		File input = new File(name);
        Scanner scanner = new Scanner(input);
        ArrayList<Vec> list = new ArrayList<>();
        for (int j = 0; j < 1000; j++) {
        	String trash = scanner.next();
        	trash = scanner.next();
	        Vec current = new Vec(4);	
	        for (int i = 0; i < 4; i++)
	        	current.v[i] = scanner.nextDouble();
	        list.add(current);
	        scanner.nextLine();
	    }
	    Vec[] result = new Vec[list.size()];
	    for (int i = 0; i < list.size(); i++)
	    	result[i] = list.get(i);
	    return result;
	}
	
	/**
	 * Read population and fitnesses
	 * @param generation A given population
	 * @param fitness the fitnesses for this given population
	 * @throws IOException
	 */
	public static void readGenerationAndFitness(Vec[] generation, int[] fitness) throws IOException {
		File input = new File(filename);
		Scanner scanner = new Scanner(input);

		for (int j = 0; j < generation.length; j++) {
			String trash = scanner.next();
			trash = scanner.next();

			Vec current = new Vec(4);
			for (int i = 0; i < 4; i++) 
				current.v[i] = scanner.nextDouble();
			fitness[j] = scanner.nextInt();
			scanner.nextLine();
			generation[j] = current; 
		}
	}
	
	/**
	 * Give information about the current state of our population
	 * @param population the number of individuals in the population
	 * @throws IOException
	 */
	public static void generateLog(int population) throws IOException {
		PrintWriter init = new PrintWriter("generation-final-0.log", "UTF-8");

		for (int i = 0; i < population; i++) {
			Vec current = new Vec(4);
			for (int j = 0; j < 4; j++) {
				current.v[j] = RandomEngine.getDouble();
				current.v[j] -= 0.5;
				if (current.v[j] == 0) 
					current.v[j] = 0.5;
			}
			current.normalize();

			init.printf("%-4s: ", Integer.toString(i));
			for (int j = 0; j < 4; j++)
				init.printf("%-50s ", Double.toString(current.v[j]));
			init.println();	
		}
		init.close();
	}
	
	/**
	 * Print position for each individual in the population.
	 * @param positions an arrayList of integers array
	 * @throws IOException
	 */
	public static void printPositions(ArrayList<int[]> positions) throws IOException {
		FileWriter fw = new FileWriter(filename, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter log = new PrintWriter(bw);

		for (int i = 0; i < positions.size(); i++) {
			int[] current = positions.get(i);
			for (int x : current) 
				log.println(x + " ");
			log.println();
		}
		log.println();

		log.close();
		bw.close();
		fw.close();
	}
	
	/**
	 * Write down the result in a file 
	 * @param generation the population of individuals (vectors)
	 * @throws IOException
	 */
	public static void writeResultGeneration(Vec[] generation) throws IOException {
		FileWriter fw = new FileWriter(resultFilename, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter log = new PrintWriter(bw);

		for (int i = 0; i < generation.length; i++) {
			log.printf("%-4s: ", Integer.toString(i));
			for (int j = 0; j < 4; j++) 
				log.printf("%-50s ", Double.toString(generation[i].v[j]));
			log.println();
		}

		log.close();
		bw.close();
		fw.close();
	}

	/**
	 * Write in a file the fitness data
	 * @param generation a generation of vectors(individuals)
	 * @param fitness an array with their respective fitness
	 * @throws IOException
	 */
	public static void writeFitnessFunctionData(Vec[] generation, int[] fitness) throws IOException {
		FileWriter fw = new FileWriter(filename, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter log = new PrintWriter(bw);

		for (int i = 0; i < generation.length; i++) {
			log.printf("%-4s: ", Integer.toString(i));
			for (int j = 0; j < 4; j++) 
				log.printf("%-10s ", Double.toString(generation[i].v[j]));
			log.println(fitness[i]);
		}
		log.println(); 

		log.close();
		bw.close();
		fw.close();
	}
	
	/**
	 * Write in a text file the offset(set of children) resulting of the parents
	 * @param v a list containing vectors (individuals)
	 * @throws IOException
	 */
	public static void writeOffset(ArrayList<Vec[]> v) throws IOException {
		FileWriter fw = new FileWriter(filename, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter log = new PrintWriter(bw);

		for (int i = 0; i < v.size(); i++) {
			log.printf("%-4s:%n", Integer.toString(i));
			log.printf("parent#1: ");
			for (int j = 0; j < 4; j++)
				log.printf("%-50s ", Double.toString((v.get(i))[0].v[j]));
			log.println();

			log.printf("parent#2: ");
			for (int j = 0; j < 4; j++)
				log.printf("%-50s ", Double.toString((v.get(i))[1].v[j]));
			log.println();

			log.printf("son     : ");
			for (int j = 0; j < 4; j++)
				log.printf("%-50s ", Double.toString((v.get(i))[2].v[j]));
			log.println();
		}
		log.println();

		log.close();
		bw.close();
		fw.close();
	}
	
	/**
	 * Write the mutation offset information inside a text file.
	 * @param v an arrayList containing array of vectors.
	 * @throws IOException
	 */
	public static void writeMutatedOffset(ArrayList<Vec[]> v) throws IOException {
		FileWriter fw = new FileWriter(filename, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter log = new PrintWriter(bw);

		for (int i = 0; i < v.size(); i++) {
			log.printf("%-4s:%n", Integer.toString(i));
			log.printf("parent#1: ");
			for (int j = 0; j < 4; j++)
				log.printf("%-50s ", Double.toString((v.get(i))[0].v[j]));
			log.println();

			log.printf("parent#2: ");
			for (int j = 0; j < 4; j++)
				log.printf("%-50s ", Double.toString((v.get(i))[1].v[j]));
			log.println();

			log.printf("son:      ");
			for (int j = 0; j < 4; j++)
				log.printf("%-50s ", Double.toString((v.get(i))[2].v[j]));
			log.println();

			log.printf("mutated:  ");
			for (int j = 0; j < 4; j++)
				log.printf("%-50s ", Double.toString((v.get(i))[3].v[j]));
			log.println();
		}
		log.println();

		log.close();
		bw.close();
		fw.close();
	}
	/**
	 * Write inside a text file the replacements for two vectors
	 * @param a an array of vectors
	 * @param b an array of vectors
	 * @throws IOException
	 */
	public static void writeReplacements(Vec[] a, Vec[] b) throws IOException {
		FileWriter fw = new FileWriter(filename, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter log = new PrintWriter(bw);

		for (int i = 0; i < a.length; i++) {
			log.printf("%-4s:%n", Integer.toString(i));
			log.printf("old vec:       ");
			for (int j = 0; j < 4; j++)
				log.printf("%-50s ", Double.toString(a[i].v[j]));
			log.println();

			log.printf("replaced with: ");
			for (int j = 0; j < 4; j++)
				log.printf("%-50s ", Double.toString(b[i].v[j]));
			log.println();
		}
		log.println();

		log.close();
		bw.close();
		fw.close();
	}
}