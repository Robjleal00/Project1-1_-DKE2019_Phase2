import java.io.*;
import java.util.*;
 
public class Rank {
   
    //Instances
   
    private ArrayList <String> gameTags = new ArrayList<String>();
    private ArrayList <Integer> scores = new ArrayList<Integer>();
   
    //Constructor
    
    /**
     * Initialization of rank
     * @param _gameTags set of gametags
     * @param _scores set of scores  
     */   
    public Rank (ArrayList<String> _gameTags, ArrayList<Integer> _scores) {
        gameTags = _gameTags;
        scores = _scores;
    }
   
    /**
     * Updating the file  
     */
    public void FileWriter() throws FileNotFoundException{
       
        File file = new File("Score.txt");
        PrintWriter out = new PrintWriter(file);
       
        if(gameTags.size() != scores.size()) {
            System.out.println("ERROR");
        }
       
        for(int i = 0; i < gameTags.size(); i++) {
            out.print(gameTags.get(i) + "%" + scores.get(i) + System.lineSeparator());
        }
        out.close();
    }

    /**
     * printing everything  
     */
    public void print() {
        for (int i = 0; i < gameTags.size(); i++) {
            System.out.print(gameTags.get(i) + " ");
            System.out.println(scores.get(i));
        }
        System.out.println();
    }
   
    /**
     * Adding the new user
     * @param newTag gametag of the new user  
     */
    public void addUser(String newTag) throws FileNotFoundException {
       
            if(!(gameTags.contains(newTag))) {
                gameTags.add(newTag);
                scores.add(0);
            }
            else {
                System.out.println("Error");      
            }
           
            FileWriter();
    }
   
    /**
     * updating the score
     * @param gameTag 
     * @param newScore   
     */
    public void addScore(String gameTag, int newScore) throws FileNotFoundException {
       
        if (gameTags.contains(gameTag)) {
            int x = gameTags.indexOf(gameTag);
            if(scores.get(x) < newScore) {
                scores.set(x, newScore);
            }
        }
        else {
            System.out.println("The GameTag is not present");
        }  
       
        FileWriter();
    }
   
    /**
     * retrieving the score
     * @param gameTag  
     */
    public int getScore(String gameTag) {
        for (int i = 0; i < gameTags.size(); i++) {
            if (gameTag.equals(gameTags.get(i)))
                return scores.get(i);
        }
        return -1;
    }
}
