import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PentominoBuilder {

    //All basic pentominoes that will be rotated and inverted
    private static ArrayList<int[][][]> database;
    
    public static boolean init() {
        database = new ArrayList<>();

        File input = new File("data.tet");

        try {
            Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
                int n = scanner.nextInt(), l = scanner.nextInt();
                int[][][] current = new int[n][l][l];
                for (int i = 0; i < n; i++) 
                    for (int j = 0; j < l; j++) 
                        for (int t = 0; t < l; t++) 
                            current[i][j][t] = scanner.nextInt();
                database.add(current);
            } 
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void print() {
        System.out.println(database.size());

        for (int i = 0; i < database.size(); i++) {
            System.out.println("Pentomino #" + (i + 1));
            int[][][] current = database.get(i);
            for (int j = 0; j < current.length; j++) {
                for (int t = 0; t < current[j].length; t++) {
                    for (int k = 0; k < current[j][t].length; k++) {
                        System.out.print(current[j][t][k]);
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
    }
    
    public static int getNumberOfRotations(int pentID) {
        return (database.get(pentID)).length;
    }

    public static int[][][] getPent(int pentID) {
        return database.get(pentID);
    }

    public static int[][] getPent(int pentID, int _id) {
        return (database.get(pentID))[_id];
    }
}