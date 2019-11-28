import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

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


public class StuInfo extends JFrame {
	public Container cont;

	StuInfo() throws IOException {
		setIconImage(new ImageIcon(ClassLoader.getSystemResource("res/logo.png")).getImage());
	    setLayout(null);
	    cont = getContentPane();
	    cont.setLayout(null);
	    cont.setBounds(0, 0, 700, 600);
	    setSize(600, 500);
	    setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StuInfo window = new StuInfo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}