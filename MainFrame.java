import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.List;

public class MainFrame {
	public static String currentPlayer = "";
	public static int current_player_highest_score = 0;
	/**
	 * Read a file and return a representation of the lines
	 * @param filename
	 * @return String Return a representation of the lines
	 * @throws FileNotFoundException
	 */
	public static String[] readFile(String filename) throws FileNotFoundException {
        
        List<String> lines = new ArrayList<String>();
        try {
        	// Load the text file.
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null && !line.trim().equals("")) {
                lines.add(line);
            }
        } catch (IOException e) {
            // We fucked up.
            e.printStackTrace();
        }
        return lines.toArray(new String[lines.size()]);
    }
	/**
	 * Read a file retrieve the score for each person 
	 * @param fileData
	 * @return Rank the current rank 
	 */
    public static Rank initialiseRanks(String[] fileData) {
        ArrayList<String> tags = new ArrayList<String>();
        ArrayList<Integer> scores = new ArrayList<Integer>();
        for (String line : fileData) {
            String[] pieces = line.split("%");
            if (pieces.length != 2) {
                // There is a problem with this line, so ignore it.
                continue;
            }
            String gamertag = pieces[0];
            String score = pieces[1];
            int scoreCast;
            try {
                scoreCast = Integer.parseInt(score);
            } catch(ClassCastException e) {
                // Score is not a number, ignore this line.
                continue;
            }
            tags.add(gamertag);
            scores.add(scoreCast);
        }
        Rank rank = new Rank(tags, scores);
        return rank;
    }
	private JFrame frame;
	private JPanel gamePanel;
	private JTextField textField;
	private JPanel menuPanel;
	private JPanel scorePanel;
	private Rank rank;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			/**
			 * run the frame and catch eventual exceptions
			 */
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public MainFrame() throws IOException {
		rank = initialiseRanks(readFile("Score.txt"));//previously data.txt
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		//Setting up the different components
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setIconImage(new ImageIcon(ClassLoader.getSystemResource("res/logo.png")).getImage());
		
		//Creating a panel
		menuPanel = new JPanel();
		menuPanel.setBounds(0, Util.realY(0), frame.getWidth(), (frame.getHeight()));
		menuPanel.setLayout(null);
		
		//Creating the Score panel
		scorePanel = new JPanel();
		scorePanel.setBounds(0, Util.realY(0), frame.getWidth(), (frame.getHeight()));
		menuPanel.setLayout(null);
		
		//Creating a text field
		textField = new JTextField();
		textField.setBounds(203, 330, 130, 26);
		menuPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblGamertag = new JLabel("Gamertag:");
		lblGamertag.setBounds(114, 335, 77, 16);
		lblGamertag.setForeground(Color.WHITE);
		menuPanel.add(lblGamertag);
		
		frame.getContentPane().add(menuPanel);
		
		gamePanel = new JPanel();
		gamePanel.setBounds(0, Util.realY(0), frame.getWidth(), (frame.getHeight()));
		gamePanel.setLayout(null);
		
		frame.getContentPane().add(gamePanel);
		
		menuPanel.setVisible(true);
		gamePanel.setVisible(false);
		
		createUI(menuPanel);
		//createUI(gamePanel);
	}
	/**
	 * create the GUI components 
	 * @param p
	 * @throws IOException
	 */
	private void createUI(JPanel p) throws IOException {
		//Create the home button
		JButton btnHome = new JButton("Home");
		btnHome.setBounds(114, 378, 117, 29);
		btnHome.addActionListener(new ActionListener() {
			//Set an actionListener whenever we press the button it's gonna 
			//hide the irrelevant panels and requestFocus
			public void actionPerformed(ActionEvent e) {
				
				menuPanel.setVisible(true);
				gamePanel.setVisible(false);
				frame.requestFocus();
			}
		});
		p.add(btnHome);
		
		//Add the Game button
		//Whenever we press the button it hides the other panels and displays the game panel
		JButton btnGame = new JButton("Game");
		btnGame.setBounds(114, 403, 117, 29);
		btnGame.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				menuPanel.setVisible(false);
				gamePanel.setVisible(true);
				frame.requestFocus();
			}
		});
		p.add(btnGame);
		
		//Add the AI button
				//Whenever we press the button it hides the other panels and displays the AI panel
				JButton btnAI = new JButton("AI");
				btnAI.setBounds(114, 428, 117, 29);//28+15= 
				btnAI.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						
						//Implements the AI frame and display it here TODO
					}
				});
				p.add(btnAI);
		//Create the Score button
		JButton btnScores = new JButton("Scores");
		btnScores.setBounds(114, 453, 117, 29);
		btnScores.addActionListener(new ActionListener() {
			/**
			 * Whenever we click the button it displays a pop up window displaying the different tags and scores 
			 *from our data base
			 */
			public void actionPerformed(ActionEvent e) {
				//StringBuilder sb = new StringBuilder();
				String dataCopy = "";
				try {
					dataCopy = DataBaseUtil.getAllData();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				//for(String gameTag : rank.getGameTags()) {
					//int score = rank.getScore(gameTag);
					//sb.append(gameTag + " : " + score + System.lineSeparator());
				//}
				JOptionPane.showMessageDialog(frame, dataCopy, "Scores", JOptionPane.PLAIN_MESSAGE);
	
			}
		});
		p.add(btnScores);
		
		//Create the button quit 
		//Whenever we press it, it quits the window and closes the program
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(114, 478, 117, 29);
		btnQuit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		p.add(btnQuit);
		
		JButton btnLog = new JButton("Login");
		btnLog.setBounds(335, 330, 117, 29);
		btnLog.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
		
				String gamerTag = textField.getText();
				
				StringBuilder sb = new StringBuilder();
				JOptionPane.showMessageDialog(frame, "Welcome " + gamerTag + "!",""  , JOptionPane.PLAIN_MESSAGE);
				
				if(!(DataBaseUtil.checkFor(gamerTag))) {
					//Add the gamertag to the database and initialise the value of score to 0
					DataBaseUtil.add(gamerTag);
					//set the current player to <player>
					currentPlayer = gamerTag;
					current_player_highest_score = 0;
					
				}else {
					//set the current player to <player>
					currentPlayer = gamerTag;
					try {
						current_player_highest_score = DataBaseUtil.getScore(gamerTag);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	
			}
		});
		p.add(btnLog);
		
		JLabel background = new JLabel(new ImageIcon(Util.scaledImage("res/background.jpg", (frame.getHeight()))));
		background.setBounds(0, 0, frame.getWidth(), frame.getHeight());//Update every time. 0,0,frame.getWidth(), frame.getHeight()
		p.add(background);
	}
}