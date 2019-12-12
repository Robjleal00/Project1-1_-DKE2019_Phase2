import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.*;

public class Game {
	public static UI ui;
	public static Pentomino currentObj;
	public static ScheduledExecutorService ses;
	
	public static class MyKeyListener implements KeyListener {
		public void keyTyped(KeyEvent event) {
	
		}
		public void keyPressed(KeyEvent event) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					switch (event.getKeyCode()) {
						case KeyEvent.VK_LEFT:  currentObj.moveLeft();    ui.setState(Field.getUsed()); break;
						case KeyEvent.VK_RIGHT: currentObj.moveRight();   ui.setState(Field.getUsed()); break;
						case KeyEvent.VK_DOWN:  currentObj.moveDown();    ui.setState(Field.getUsed()); break;
						case KeyEvent.VK_SPACE: currentObj.drop();        ui.setState(Field.getUsed()); break;
						case KeyEvent.VK_Q:     currentObj.rotateLeft();  ui.setState(Field.getUsed()); break;
						case KeyEvent.VK_E:     currentObj.rotateRight(); ui.setState(Field.getUsed()); break;
					}
				}		
			});
		}
		public void keyReleased(KeyEvent event) {

		} 
	}
	
	public static Runnable iteration = new Runnable() {
		public void run() {
			boolean result = currentObj.moveDown();
			if (result)
				ui.setState(Field.getUsed());
			else {
				if (!currentObj.allInside()) {
					System.out.println("Game over");
					System.out.println("Your score is " + Field.getScore());

					try {
					    Thread.sleep(5000);
					}
					catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
					ui.closeWindow();		

					ses.shutdown();
					return;
				}
				else {
					Field.updateScore();
					currentObj = new Pentomino(0);
				}
			}
		}
	};

	public static void startGame(JPanel gamePanel) {
		try {
			ui = new UI(Field.getHeight(), Field.getWidth(), Field.getCellSize(), gamePanel);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		Field.init();
		if (!PentominoBuilder.init()) {
			System.exit(0);
		}
		RandomEngine.init();

		currentObj = new Pentomino(0);
		KeyListener keyListener = new MyKeyListener();
		ui.add(keyListener);

		ses.scheduleWithFixedDelay(iteration, 0, 500, TimeUnit.MILLISECONDS);	
	}
}