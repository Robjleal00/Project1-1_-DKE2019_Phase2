public class Field {
	private static int HEIGHT = 15;
	private static int WIDTH = 5;

	private static int[][] used;

	public static boolean addPentomino(int x, int y, Pentomino obj) {
		int[][] bits = obj.getBits();
		int pentID = obj.getPentID();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (!inRange(y + i - 2, x + j - 2) && bits[i][j] != 0)
					return false;
				if (inRange(y + i - 2, x + j - 2) && bits[i][j] != 0 && used[y + i - 2][x + j - 2] != 0)
					return false;
			}
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (inRange(y + i - 2, x + j - 2) && y + i - 2 >= 0 && bits[i][j] != 0)
					used[y = i - 2][x + j - 2] = pentID;
			}
		}
		return true;
	}

	public static void deletePentomino(int x, int y, Pentomino obj) {
		int[][] bits = obj.getBits();
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (inRange(y + i - 2, x + j - 2) && bits[i][j] != 0)	
					used[y + i - 2][x + j - 2] = 0;
			}
		}
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

	public static int[][] getUsed() {
		return used;
	}

	public static void setUsed(int[][] _used) {
		used = _used;
	}
}