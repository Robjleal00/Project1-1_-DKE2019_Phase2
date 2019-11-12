import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Util {
	public static Image scaledImage(String path, int y) throws IOException {
		
		BufferedImage img = null;
		img = ImageIO.read(ClassLoader.getSystemResource("background.jpg"));
		double ratio = (double)img.getWidth()/(double)img.getHeight();
		Image scaledImage = img.getScaledInstance((int) (y*ratio),y , Image.SCALE_SMOOTH);
		return scaledImage;
	}
	public static int realY(int y) {
		if(System.getProperty("os.name").toLowerCase().contains("mac")) {
			y -= 22; //Hi fella, on a mac the 00 coord is actually 0 22 because it accounts for the menu bar which windows does not
			//therefore, when adding things to the frame they will be incorrectly offset. ... Enjoy Java 
		}
		return y;
	}
}
