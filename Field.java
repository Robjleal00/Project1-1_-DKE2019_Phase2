public class Field {
	private static final int HEIGHT = 20;
	private static final int WIDTH = 5;
	private static final int DEFUALT_CELL_SIZE = 20;
	private static UI ui;

	private static int[][] used;

	private static int score = 0;

	public static void init(UI _ui) {
		ui = _ui;
		used = new int[HEIGHT][WIDTH];
		for (int i = 0; i < HEIGHT; i++) 
			for (int j = 0; j < WIDTH; j++) 
				used[i][j] = -1;
	}

	public static boolean addPentomino(Pentomino obj) {
		int[][] bits = obj.getBits();
		int pentID = obj.getPentID();

		int x = obj.getX();
		int y = obj.getY();

		for (int i = 0; i < obj.getLength(); i++) {
			for (int j = 0; j < obj.getLength(); j++) {
				if (!inRange(y + i, x + j) && bits[i][j] != 0)
					return false;
				if (inRange(y + i, x + j) && y + i >= 0 && bits[i][j] != 0 && used[y + i][x + j] != -1)
					return false;
			}
		}

		for (int i = 0; i < obj.getLength(); i++) {
			for (int j = 0; j < obj.getLength(); j++) {
				if (inRange(y + i, x + j) && y + i >= 0 && bits[i][j] != 0)
					used[y + i][x + j] = pentID;
			}
		}

		return true;
	}

	public static void deletePentomino(Pentomino obj) {
		int[][] bits = obj.getBits();

		int x = obj.getX();
		int y = obj.getY();
		
		for (int i = 0; i < obj.getLength(); i++) {
			for (int j = 0; j < obj.getLength(); j++) {
				if (inRange(y + i, x + j) && y + i >= 0 && used[y + i][x + j] == obj.getPentID() && bits[i][j] != 0)	
					used[y + i][x + j] = -1;
			}
		}
	}

	public static void updateScore() {
		int current = 0, id = HEIGHT - 1;
		for (int i = HEIGHT - 1; i >= 0; i--) {
			boolean line = true;
			for (int j = 0; j < WIDTH; j++)
				if (used[i][j] == -1)
					line = false;

			if (!line) {
				if (current == 4)
					score += 800;
				else
					score += 100 * current;
				current = 0;
				for (int j = 0; j < WIDTH; j++)
					used[id][j] = used[i][j]; 
				id--;
			}
			else
				current++;
		}	
		if (current == 4)
			score += 800;
		else
			score += 100 * current;
	}

	public static int getScore() {
		return score;
	}

	public static boolean inRange(int y, int x) {
		return (0 <= x && x < WIDTH && y < HEIGHT);
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getCellSize() {
		return DEFUALT_CELL_SIZE;
	}

	public static int[][] getUsed() {
		return used;
	}

	public static void setUsed(int[][] _used) {
		used = _used;
	}

	public static int getCell(int _row, int _col) {
		return used[_row][_col];
	}
}