import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StuInfo extends JFrame {

Container cont;

StuInfo() throws IOException {
	
	
	setIconImage(new ImageIcon(ClassLoader.getSystemResource("logo.png")).getImage());
    setLayout(null);
    cont = getContentPane();
    cont.setLayout(null);
    cont.setBounds(0, 0, 700, 600);
    setSize(600, 500);
    setVisible(true);

 }


 }