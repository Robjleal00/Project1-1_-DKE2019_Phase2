import javafx.util.Pair;

public class Pentomino {
	private int[][] bits;
	private int pentID = 0;
	private int rotation = 0;
	private int length = 0;

	private int x, y;

	public Pentomino() {
		pentID = RandomEngine.getInt(0, 12);

		bits = PentominoBuilder.getPent(pentID, rotation);
		length = bits.length;

		x = Field.getWidth() / 2 - length / 2;
		y = -length;
	}

	public Pentomino(int _pentID) {
		pentID = _pentID;

		bits = PentominoBuilder.getPent(pentID, rotation);
		length = bits.length;

		x = Field.getWidth() / 2 - length / 2;
		y = -length;	
	}

	public Pentomino(int _x, int _y) {
		pentID = RandomEngine.getInt(0, 12);
		
		bits = PentominoBuilder.getPent(pentID, rotation);		 
		length = bits.length;

		x = _x;
		y = _y;
	}

	public Pentomino(int _x, int _y, int _pentID) {
		pentID = _pentID;

		bits = PentominoBuilder.getPent(pentID, rotation);	
		length = bits.length;

		x = _x;
		y = _y;
	}

	public Pentomino(int _x, int _y, int _pentID, int _id) {
		pentID = _pentID;
		rotation = _id;

		bits = PentominoBuilder.getPent(pentID, rotation);
		length = bits.length;

		x = _x;
		y = _y;
	}

	public int[][] getBits() {
		return bits;
	}

	public void setBits(int[][] _bits) {
		bits = _bits;
		length = bits.length;
	}

	public int getLength() {
		return length;
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
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (bits[i][j] != 0 && 0 <= y + i && y + i < Field.getHeight() && 0 <= x + j && x + j < Field.getWidth()) 
					continue;
				if (bits[i][j] != 0)
					return false;
			}
		}
		return true;
	}

	public boolean rotateLeft() {
		Field.deletePentomino(this);

		if (tryLeft(0, 0))
			return true;
		if (tryLeft(-1, 0))
			return true;
		if (tryLeft(-2, 0))
			return true;
		if (tryLeft(1, 0))
			return true;
		if (tryLeft(2, 0))
			return true;
		if (tryLeft(0, 1))
			return true;

		Field.addPentomino(this);
		return false;
	}

	public boolean tryLeft(int dx, int dy) {
		x += dx;
		y += dy;

		rotation--;
		rotation += PentominoBuilder.getNumberOfRotations(pentID);
		rotation %= PentominoBuilder.getNumberOfRotations(pentID);
		bits = PentominoBuilder.getPent(pentID, rotation);

		if (!Field.addPentomino(this)) {
			rotation++;
			rotation %= PentominoBuilder.getNumberOfRotations(pentID);
			bits = PentominoBuilder.getPent(pentID, rotation);			
			return false;
		}
		return true;
	}

	public boolean rotateRight() {
		Field.deletePentomino(this);

		if (tryLeft(0, 0))
			return true;
		if (tryLeft(1, 0))
			return true;
		if (tryLeft(2, 0))
			return true;
		if (tryLeft(-1, 0))
			return true;
		if (tryLeft(-2, 0))
			return true;
		if (tryLeft(0, 1))
			return true;

		Field.addPentomino(this);
		return false;	
	}

	public boolean tryRight(int dx, int dy) {
		x += dx;
		y += dy;

		rotation++;
		rotation %= PentominoBuilder.getNumberOfRotations(pentID);
		bits = PentominoBuilder.getPent(pentID, rotation);

		if (!Field.addPentomino(this)) {
			rotation--;
			rotation += PentominoBuilder.getNumberOfRotations(pentID);
			rotation %= PentominoBuilder.getNumberOfRotations(pentID);
			bits = PentominoBuilder.getPent(pentID, rotation);			
			return false;
		}
		return true;
	}

	public boolean moveDown() {
		Field.deletePentomino(this);

		y++;
		if (!Field.addPentomino(this)) {
			y--;
			Field.addPentomino(this);
			return false;
		}
		return true;
	}

	public boolean moveLeft() {
		Field.deletePentomino(this);

		x--;
		if (!Field.addPentomino(this)) {
			x++;
			Field.addPentomino(this);
			return false;
		}
		return true;
	}

	public boolean moveRight() {
		Field.deletePentomino(this);

		x++;
		if (!Field.addPentomino(this)) {
			x--;
			Field.addPentomino(this);
			return false;
		}
		return true;
	}

	public void drop() {
		while (moveDown());
	}
}