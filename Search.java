import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Search {
	
    public int NUMBER_OF_ROWS = 6;                  
    public int NUMBER_OF_COLS = 5;                    
    public final int DEFAULT_CELL_SIZE = 50;                
    public final int PENT_SIZE = 5;
    public final int LOC_CENT_X = 2;
    public final int LOC_CENT_Y = 2;
    public char[] INPUT = {'T', 'W', 'Z', 'L', 'I', 'Y'};    // sample INPUT
    public boolean ANIMATED = true;
    
    public PentominoBuilder database = new PentominoBuilder();
    public UI ui;
    
    public boolean[][] used;
    
    public int[][] field;
    public int[] mask;
    
    // Helper function which starts the brute force algorithm
    public Search(int height, int width, boolean ani, char[] inp) {
        NUMBER_OF_ROWS = height;
        NUMBER_OF_COLS = width;
        ANIMATED = ani;
        
        if (NUMBER_OF_ROWS * NUMBER_OF_COLS != inp.length * 5) {
        	System.out.println("No solution");
        	System.out.println("Finished");
        	return;
        }
        
        used = new boolean[NUMBER_OF_ROWS][NUMBER_OF_COLS];
    	
        if (ANIMATED)
        	ui = new UI(NUMBER_OF_ROWS, NUMBER_OF_COLS, DEFAULT_CELL_SIZE);
        
    	field = new int[height][width];
    	for(int i = 0; i < NUMBER_OF_ROWS; i++) {
            for(int j = 0; j < NUMBER_OF_COLS; j++) {
                // -1 in the state matrix corresponds to empty square
                // Any positive number identifies the ID of the pentomino
                field[i][j] = -1;
            }
        }
    	mask = new int[inp.length];
        for (int i = 0; i < inp.length; i++) {
        	mask[i] = 1;
        }
    	
    	INPUT = inp;
    	
        boolean result = backtracking();
       
        if (!result) {
        	System.out.println("No solution");
        } else {
        	if (!ANIMATED)
        		ui = new UI(NUMBER_OF_ROWS, NUMBER_OF_COLS, DEFAULT_CELL_SIZE);
     
        	ui.setState(field);
        	System.out.println("Solution found");
        }
        System.out.println("Finished");
    }
   
    public int characterToID(char character) {
        int pentID = -1;
        if (character == 'X') {
            pentID = 0;
        } else if (character == 'I') {
            pentID = 1;
        } else if (character == 'Z') {
            pentID = 2;
        } else if (character == 'T') {
            pentID = 3;
        } else if (character == 'U') {
            pentID = 4;
        } else if (character == 'V') {
            pentID = 5;
        } else if (character == 'W') {
            pentID = 6;
        } else if (character == 'Y') {
            pentID = 7;
        } else if (character == 'L') {
            pentID = 8;
        } else if (character == 'P') {
            pentID = 9;
        } else if (character == 'N') {
            pentID = 10;
        } else if (character == 'F') {
            pentID = 11;
        }
        return pentID;
    }
   
    public void goBack(int pentID) {
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLS; j++) {
                if (field[i][j] == pentID) {
                    field[i][j] = -1;
                }
            }
        }
    }
    
    public boolean tryToPush(int[][] locField, int pentID, int glCentX, int glCentY) {
    	for (int locY = 0; locY < PENT_SIZE; locY++) {
    		for (int locX = 0; locX < PENT_SIZE; locX++) {
    			if (locField[locY][locX] == 0)	
    				continue;
    			
    			int globalY = (locY - LOC_CENT_Y) + glCentY;
    			int globalX = (locX - LOC_CENT_X) + glCentX;
    			
    			if (!(0 <= globalY && globalY < NUMBER_OF_ROWS))
    				return false;
    			if (!(0 <= globalX && globalX < NUMBER_OF_COLS))
    				return false;
    			if (field[globalY][globalX] != -1)
    				return false;
    			
    			field[globalY][globalX] = pentID;
    		}
    	}
    	return true;
    }
    
    public int dfs(int i, int j) {
    	used[i][j] = true;
    	int sum = 1;
    	if (i != 0)
    		if (!used[i - 1][j])
    			sum += dfs(i - 1, j);
    	if (i != NUMBER_OF_ROWS - 1)
    		if (!used[i + 1][j])
    			sum += dfs(i + 1, j);
    	if (j != 0)
    		if (!used[i][j - 1])
    			sum += dfs(i, j - 1);
    	if (j != NUMBER_OF_COLS - 1)
    		if (!used[i][j + 1])
    			sum += dfs(i, j + 1);
    	return sum;
    }
    
    public boolean isValid() {
    	for (int i = 0; i < NUMBER_OF_ROWS; i++) {
    		for (int j = 0; j < NUMBER_OF_COLS; j++) {
    			used[i][j] = !(field[i][j] == -1);
    		}
    	}
    	
    	for (int i = 0; i < NUMBER_OF_ROWS; i++) {
    		for (int j = 0; j < NUMBER_OF_COLS; j++) {
    			if (!used[i][j]) {
    				int sum = dfs(i, j);
    				if (sum % 5 != 0)
    					return false;
    			}
    		}
    	}
    	return true;
    }
    
    public boolean backtracking() {   
    	//System.out.println(x + "  " + y);
        
    	if (!isValid()) {
        	return false;
        }
    	
    	if (ANIMATED) {
	    	ui.setState(field);
	        try {
			    Thread.sleep(1);
			}
			catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
    	}
    	
        boolean ok = true;
    	for (int i = 0; i < INPUT.length; i++)
    		if (mask[i] == 1)
    			ok = false;
    	if (ok)
    		return true;
    	
    	int currentY = -1, currentX = -1;
    	for (int i = 0; i < NUMBER_OF_ROWS && currentY == -1; i++) {
    		for (int j = 0; j < NUMBER_OF_COLS && currentX == -1; j++) { 
    			if (field[i][j] == -1) {
    				currentY = i;
    				currentX = j;
    			}
    		}
    	}
    	
    	for (int x = currentX; x < Math.min(NUMBER_OF_COLS, currentX + 5); x++) {
    		for (int y = currentY; y < Math.min(NUMBER_OF_ROWS, currentY + 5); y++) {
    			if (field[y][x] == -1) {
    	        	for (int i = 0; i < INPUT.length; i++) {
    		            if (mask[i] == 0) {
    		                continue;
    		            }
    		           
    		            int pentID = characterToID(INPUT[i]);
    		            
    		            int[][][] current = database.getPent(pentID);
    		            for (int j = 0; j < current.length; j++) {
    		            	boolean result = tryToPush(current[j], pentID, x, y);
    		            	if (result) {
    		            		mask[i] = 0;
    		            		boolean successfully = backtracking();
    		            		if (successfully) {
    		            			return true;
    		            		}
    		            	}
    		            	goBack(pentID);
    		            	mask[i] = 1;
    		            }
    		        }
    	    	}
    		}
    	}
    	
        return false;
    }
}