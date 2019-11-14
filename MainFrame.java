import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;

public class MainFrame {

	private JFrame frame;
	private JPanel gamePanel;
	private JTextField textField;
	private JPanel menuPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//frame.setIconImage(logo); TODO set an icon image 
		
		menuPanel = new JPanel();
		menuPanel.setBounds(0, Util.realY(0), frame.getWidth(), (frame.getHeight()));
		menuPanel.setLayout(null);
		
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
		createUI(gamePanel);
		
	}
	
	private void createUI(JPanel p) throws IOException {
		JButton btnHome = new JButton("Home");
		btnHome.setBounds(114, 378, 117, 29);
		btnHome.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				menuPanel.setVisible(true);
				gamePanel.setVisible(false);
				frame.requestFocus();
				
			}
			
		});
		p.add(btnHome);
		
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
		
		JButton btnScores = new JButton("Scores");
		btnScores.setBounds(114, 430, 117, 29);
		p.add(btnScores);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(114, 455, 117, 29);
		btnQuit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
			
		});
		p.add(btnQuit);
		
		JLabel background = new JLabel(new ImageIcon(Util.scaledImage("background.jpg", (frame.getHeight()))));
		background.setBounds(0, 0, frame.getWidth(), frame.getHeight());//Update every time. 0,0,frame.getWidth(), frame.getHeight()
		p.add(background);
	}
}
