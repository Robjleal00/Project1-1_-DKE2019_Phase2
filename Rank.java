import java.io.*;
import java.util.*;
 
public class Rank {
   
    //Instances
   
    private ArrayList <String> gameTags = new ArrayList<String>();
    private ArrayList <Integer> Scores = new ArrayList<Integer>();
   
    //Constructor
   
    public Rank(ArrayList<String>gameTags,ArrayList<Integer>Scores) {
       for(String s : gameTags) {
    	   System.out.println(s);
       }
       for(int s : Scores) {
    	   System.out.println(s);
       }
        this.gameTags = gameTags;
        this.Scores = Scores;
    }
    
    
   
    //Writing file (doesn't need to be called every time because after each methods it is called)
    /*
     *
     */
    public void FileWriter() throws FileNotFoundException{
       
        File file = new File("Score.txt");
        PrintWriter out = new PrintWriter(file);
       
        if(gameTags.size() != Scores.size()) {
            System.out.println("ERROR");
        }
       
        for(int i = 0; i < this.gameTags.size(); i++) {
            //out.print(this.gameTags.get(i) + "<" + this.Scores.get(i) + ">" + System.lineSeparator());
        	out.print(this.gameTags.get(i) + "%" + this.Scores.get(i) + System.lineSeparator());
        }
        out.close();
       
    }
   
    //Add new gameTag if is not present in the file other wise prints an error
   
    public void addUser(String newTag) throws FileNotFoundException {
       
            if(!(this.gameTags.contains(newTag))) {
                this.gameTags.add(newTag);
                this.Scores.add(0);
               
            }
            else {
                System.out.println("Error");      
            }
           
            FileWriter();
           
    }
   
    //Add Scores to players if it is higher that the one that they score before
   
    public void addScore(String gameTag,int newScore) throws FileNotFoundException {
       
        if(this.gameTags.contains(gameTag)) {
            int x = this.gameTags.indexOf(gameTag);
            if(this.Scores.get(x) < newScore) {
            this.Scores.set(x, newScore);
            }
        }
        else {
            System.out.println("The GameTag is not present");
           
        }  
       
        FileWriter();
       
    }
    
    public List<String> getGameTags(){
    	return gameTags;
    }
   
    //Get Score from the GameTag returns the score of certain gameTag if the tag exist
   
    public int getScore(String gameTag) {
       
        int out = 0;
       
        if(this.gameTags.contains(gameTag)) {
            int x = this.gameTags.indexOf(gameTag);
            out = this.Scores.get(x);
        }
        else {
            System.out.println("The GameTag is not present");
        }
        return out;
       
    }
   
    public void getHighScore() {
         System.out.println(Collections.max(Scores));  
    }
}