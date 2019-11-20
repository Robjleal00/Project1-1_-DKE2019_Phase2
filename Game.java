public class Game {
	public static UI ui;

	public static void main(String[] args) {
		ui = new UI(Field.getHeight(), Field.getWidth(), Field.getCellSize());
		Field.init();

		Pentomino currentObj = new Pentomino(true);
		while (true) {
			try {
			    Thread.sleep(200);
			}
			catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}

			boolean result = currentObj.moveDown();
			if (result)
				ui.setState(Field.getUsed());
			else {
				if (!currentObj.allInside()) 
					break;
				else {
					Field.updateScore();
					currentObj = new Pentomino(true);
				}
			}
		}

		System.out.println("Game over");
		System.out.println("Your score is " + Field.getScore());

		try {
		    Thread.sleep(1000);
		}
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		ui.closeWindow();
	}
}