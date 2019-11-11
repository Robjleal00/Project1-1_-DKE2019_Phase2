import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class PentominoBuilder {

    //All basic pentominoes that will be rotated and inverted
    private static int[][][] basicDatabase = {
            {
                // pentomino representation X
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                // pentomino representation I
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                // pentomino representation Z
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                // pentomino representation T
                    {0, 0, 0, 0, 0},    
                    {0, 1, 0, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                // pentomino representation U
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                // pentomino representation V
                    {0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 1, 0, 0, 0},
                    {0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                // pentomino representation W
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 1, 1, 0, 0},
                    {0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                // pentomino representation Y
                    {0, 0, 0, 0, 0},    
                    {0, 0, 1, 0, 0},
                    {0, 1, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0}
            },
            {
                // pentomino representation L
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1},
                    {0, 1, 1, 1, 1},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                // pentomino representation P
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                // pentomino representation N
                    {0, 0, 0, 0, 0},
                    {0, 1, 1, 0, 0},
                    {0, 0, 1, 1, 1},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                // pentomino representation F
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 1, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            }
    };
    
    private static ArrayList<int[][][]> advancedDatabase = new ArrayList<>();
    
    public static void Builder() {
        for (int PentID = 0; PentID < 12; PentID++) {
            int[][][] current = new int[8][5][5];
            for (int i = 0; i < 4; i++) {
                rotate(PentID);
                current[i] = getBasicPent(PentID);
            }
            reflect(PentID);
            
            for (int i = 0; i < 4; i++) {
                rotate(PentID);
                current[4 + i] = getBasicPent(PentID);
            }
            reflect(PentID);
            advancedDatabase.add(removeDuplicates(current));
        }
    }
    
    private static int[][][] removeDuplicates(int[][][] current) {
        ArrayList<int[][]> answer = new ArrayList<>();
        for (int i = 0; i < current.length; i++) {
            boolean ok = true;
            for (int j = 0; j < i; j++) { 
                if (same2DMatrixes(current[i], current[j])) {
                    ok = false;
                }
            }
            if (ok) {
                answer.add(current[i]);
            }
        }
        int[][][] result = answer.toArray(new int[answer.size()][5][5]);
        return result;
    }
    
    public static boolean same2DMatrixes(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void rotate(int PentID) {
        int[][] rotated = new int[5][5];
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                rotated[j][5 - 1 - i] = basicDatabase[PentID][i][j];
            }
        }
        basicDatabase[PentID] = rotated;
    }
    
    public static void reflect(int PentID) {
        int[][] reflected = new int[5][5];
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++)
                reflected[5 - 1 - i][j] = basicDatabase[PentID][i][j];
        }
        basicDatabase[PentID] = reflected;
    }
    
    public static int[][] getBasicPent(int PentID) {
        return basicDatabase[PentID];
    }
    
    public static int[][][] getPent(int PentID) {
        return advancedDatabase.get(PentID);
    }
}