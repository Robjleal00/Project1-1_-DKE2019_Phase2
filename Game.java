public class Game {
	public static final int DEFAULT_CELL = 50;
	public static UI ui;

	public static void main(String[] args) {
		ui = new UI(Field.getHeight(), Field.getWidth(), DEFAULT_CELL);
		Field.init();

		Pentomino currentObj = new Pentomino(true);
		while (true) {
			try {
			    Thread.sleep(100);
			}
			catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}

			boolean result = currentObj.moveDown();
			if (result)
				ui.setState(Field.getUsed());
			else {
				if (currentObj.getY() == -3) 
					break;
				else
					currentObj = new Pentomino(true);
			}
		}

		System.out.println("Game over");

		try {
		    Thread.sleep(1000);
		}
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		ui.closeWindow();
	}
}