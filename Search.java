import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Search {
    public char[] INPUT = {'T', 'W', 'Z', 'L', 'I', 'Y'};    // sample INPUT
    public boolean ANIMATED = true;
    
    public UI ui;
    
    public int maxScore = 0;

    public Search(int height, int width, boolean ani, char[] inp) {
        ANIMATED = ani;
        
        if (ANIMATED)
        	ui = new UI(Field.getHeight(), Field.getWidth(), Field.getCellSize());
        
    	INPUT = inp;
    	
    	Field.init();
    	PentominoBuilder.init();

        backtracking(0);
       
    	if (!ANIMATED)
        	ui = new UI(Field.getHeight(), Field.getWidth(), Field.getCellSize());
     
    	ui.setState(Field.getUsed());
    	System.out.println("Maximum score found is " + maxScore);
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
    
  	public void backtracking(int inpID) {   
    	//System.out.println(x + "  " + y);
        
  		if (ANIMATED) {
	    	ui.setState(Field.getUsed());
	        try {
			    Thread.sleep(100);
			}
			catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
    	}

    	maxScore = Math.max(maxScore, Field.getScore());

    	if (inpID == INPUT.length)
    		return;

    	int pentID = characterToID(INPUT[inpID]);
    	
    	for (int y = 0; y < Field.getHeight(); y++) {
    		for (int x = 0; x < Field.getWidth(); x++) {
    			boolean base_exists = false;

    			for (int y1 = y + 1; y1 <= y + 3 && !base_exists; y1++) {
    				for (int x1 = x - 2; x1 <= x + 2 && !base_exists; x1++) {
    					if (x1 < 0 || x1 >= Field.getWidth()) 
    						base_exists = true;
    					else if (y1 >= Field.getHeight())
    						base_exists = true;
    					else if (Field.getCell(y, x) != -1) 
    						base_exists = true;
    				}
    			}

    			if (!base_exists)
    				continue; 
				for (int i = 0; i < PentominoBuilder.getNumberOfPentRotations(pentID); i++) {
					Pentomino obj = new Pentomino(x, y, pentID, i);
					boolean result = Field.addPentomino(obj);
					Field.updateScore();

					if (result)
						backtracking(inpID + 1);

					Field.deletePentomino(obj);
				}
    		}
    	}
    }
}