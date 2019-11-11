import javafx.util.Pair;

public class Pentomino {
	private int[][] bits;
	private int pentID = 0;

	private int x, y;

	public Pentomino(int _x, int _y, boolean randomized) {
		if (randomized) 
			PentominoBuilder.randomize(pentID);
		pentID = RandomEngine.getInt(0, 12);

		bits = PentominoBuilder.getBasicPent(pentID);		 

		x = _x;
		y = _y;
	}

	public Pentomino(int _x, int _y, int _pentID, boolean randomized) {
		if (randomized) 
			PentominoBuilder.randomize(pentID);
		pentID = _pentID;

		bits = PentominoBuilder.getBasicPent(pentID);	

		x = _x;
		y = _y;
	}

	public Pentomino(int _pentID, boolean randomized) {
		if (randomized)
			PentominoBuilder.randomize(pentID);
		pentID = RandomEngine.getInt(0, 12);

		bits = PentominoBuilder.getBasicPent(pentID);

		setRandomCoord(pentID);
	}

	public Pentomino (boolean randomized) {
		if (randomized) 
			PentominoBuilder.randomize(pentID);
		pentID = RandomEngine.getInt(0, 12);

		bits = PentominoBuilder.getBasicPent(pentID);

		setRandomCoord(pentID);
	}

	public int[][] getBits() {
		return bits;
	}

	public void setBits(int[][] _bits) {
		bits = _bits;
	}

	public int getPentID() {
		return pentID;	
	}

	public void setPentID(int _pentID) {
		pentID = _pentID;
	}

	public int getX() {
		return x;
	}

	public void setX(int _x) {
		x = _x;
	}

	public int getY() {
		return y;
	}

	public void setY(int _y) {
		y = _y;
	}

	public boolean allInside() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (bits[i][j] != 0 && 0 <= y + i - 2 && y + i - 2 < Field.getHeight() && 0 <= x + j - 2 && x + j - 2 < Field.getWidth()) 
					continue;
				if (bits[i][j] != 0)
					return false;
			}
		}
		return true;
	}

	public void setRandomCoord(int pentID) {
		Pair<Integer, Integer> yParam = getHeightParam();
		Pair<Integer, Integer> xParam = getWidthParam();

		int xLeft = (2 - xParam.getKey()) + 0;
		int xRight = (2 - xParam.getValue()) + Field.getWidth();

		x = RandomEngine.getInt(xLeft, xRight);

		y = -1 - (yParam.getValue() - 2);  
	}

	public Pair<Integer, Integer> getHeightParam() {
		int mn = 4, mx = 0;
		for (int i = 0; i < 5; i++) {
			boolean found = false;
			for (int j = 0; j < 5; j++) 
				if (bits[i][j] != 0)
					found = true;
			
			if (found) {
				mn = Math.min(mn, i);
				mx = Math.max(mx, i);
			}
		}
		return new Pair<>(mn, mx);
	}

	public Pair<Integer, Integer> getWidthParam() {
		int mn = 4, mx = 0;
		for (int j = 0; j < 5; j++) {
			boolean found = false;
			for (int i = 0; i < 5; i++) 
				if (bits[i][j] != 0)
					found = true;
			
			if (found) {
				mn = Math.min(mn, j);
				mx = Math.max(mx, j);
			}
		}
		return new Pair<>(mn, mx);
	} 

	public boolean moveDown() {
		Field.deletePentomino(x, y, this);

		y++;
		if (!Field.addPentomino(x, y, this)) {
			y--;
			Field.addPentomino(x, y, this);
			return false;
		}
		return true;
	}

	public boolean moveLeft() {
		Field.deletePentomino(x, y, this);

		x--;
		if (!Field.addPentomino(x, y, this)) {
			x++;
			Field.addPentomino(x, y, this);
			return false;
		}
		return true;
	}

	public boolean moveRight() {
		Field.deletePentomino(x, y, this);

		x++;
		if (!Field.addPentomino(x, y, this)) {
			x--;
			Field.addPentomino(x, y, this);
			return false;
		}
		return true;
	}
}