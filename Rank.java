import java.io.*;
import java.util.*;
 
public class Rank {
   
    //Instances
   
    private ArrayList <String> gameTags = new ArrayList<String>();
    private ArrayList <Integer> scores = new ArrayList<Integer>();
   
    //Constructor
   
    public Rank (ArrayList<String> _gameTags, ArrayList<Integer> _scores) {
        gameTags = _gameTags;
        scores = _scores;
    }
   
    //Writing file (doesn't need to be called every time because after each methods it is called)
    /*
     *
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

    public void print() {
        for (int i = 0; i < gameTags.size(); i++) {
            System.out.print(gameTags.get(i) + " ");
            System.out.println(scores.get(i));
        }
        System.out.println();
    }
   
    //Add new gameTag if is not present in the file other wise prints an error
   
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
   
    //Add Scores to players if it is higher that the one that they score before
   
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
   
    //Get Score from the GameTag returns the score of certain gameTag if the tag exist
   
    public int getScore(String gameTag) {
        System.out.println(gameTags.size());
        for (int i = 0; i < gameTags.size(); i++) {
            System.out.println(gameTags.get(i));
            if (gameTag.equals(gameTags.get(i)))
                return scores.get(i);
        }
        return -1;
    }
}