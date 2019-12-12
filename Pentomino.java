import javafx.util.Pair;

public class Pentomino {
	private int[][] bits;
	private int pentID = 0;
	private int rotation = 0;
	private int length = 0;

	private int x, y;
	
	/**
	 * Create a Pentominoe
	 */
	public Pentomino() {
		pentID = RandomEngine.getInt(0, 12);

		bits = PentominoBuilder.getPent(pentID, rotation);
		length = bits.length;

		x = Field.getWidth() / 2 - length / 2;
		y = -length;
	}
	
	/**
	 * Create a pentominoe
	 * @param _pentID the pentominoe's ID
	 */
	public Pentomino(int _pentID) {
		pentID = _pentID;

		bits = PentominoBuilder.getPent(pentID, rotation);
		length = bits.length;

		x = Field.getWidth() / 2 - length / 2;
		y = -length;	
	}
	/**
	 * Create a pentominoe 
	 * @param _x its x position
	 * @param _y its y position
	 */
	public Pentomino(int _x, int _y) {
		pentID = RandomEngine.getInt(0, 12);
		
		bits = PentominoBuilder.getPent(pentID, rotation);		 
		length = bits.length;

		x = _x;
		y = _y;
	}
	/**
	 * Create a pentominoe
	 * @param _x its x position
	 * @param _y its y position
	 * @param _pentID the pentominoe's ID
	 */
	public Pentomino(int _x, int _y, int _pentID) {
		pentID = _pentID;

		bits = PentominoBuilder.getPent(pentID, rotation);	
		length = bits.length;

		x = _x;
		y = _y;
	}
	
	/**
	 * Create a pentominoe
	 * @param _x its x position
	 * @param _y its y position
	 * @param _pentID the pentominoe's ID
	 * @param _id the ID
	 */
	public Pentomino(int _x, int _y, int _pentID, int _id) {
		pentID = _pentID;
		rotation = _id;

		bits = PentominoBuilder.getPent(pentID, rotation);
		length = bits.length;

		x = _x;
		y = _y;
	}
	
	/**
	 * return the number of bits
	 * @return bits the bits number
	 */
	public int[][] getBits() {
		return bits;
	}
	
	/**
	 * set the number of bits
	 * @param _bits an array of integer
	 */
	public void setBits(int[][] _bits) {
		bits = _bits;
		length = bits.length;
	}	
	
	/**
	 * return the length of the pentominoe 
	 * @return an integer representing the pento length
	 */
	public int getLength() {
		return length;
	}
	/**
	 * return the number of rotations
	 * @return an integer representing the number of rotations
	 */
	public int getRotation() {
		return rotation;
	}
	/**
	 * Return the pento ID
	 * @return an integer representing its ID
	 */
	public int getPentID() {
		return pentID;	
	}
	/**
	 * Return the x position
	 * @return an integer representing the pento position
	 */
	public int getX() {
		return x;
	}
	/**
	 * set a new x for the pentominoe 
	 * @param _x an integer representing the new x coordinates
	 */
	public void setX(int _x) {
		x = _x;
	}

	/**
	 * get the y coordinate for the pentominoe
	 * @return an integer representing the y coordinate of the pentominoe
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * set a new y for the pentominoe 
	 * @param _y an integer representing the new y coordinates
	 */
	public void setY(int _y) {
		y = _y;
	}
	
	/**
	 * Check if all pento pieces are inside 
	 * @return true if it is the case and false otherwise
	 */
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
	/**
	 * Try to rotate it to the left
	 * @return true if possible and false otherwise
	 */
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
	/**
	 * Try to move it to the left
	 * @param dx horizontal shift
	 * @param dy vertical shift
	 * @return true if possible and false otherwise
	 */
	public boolean tryLeft(int dx, int dy) {
		x += dx;
		y += dy;

		rotation--;
		rotation += PentominoBuilder.getNumberOfRotations(pentID);
		rotation %= PentominoBuilder.getNumberOfRotations(pentID);
		bits = PentominoBuilder.getPent(pentID, rotation);

		if (!Field.addPentomino(this)) {
			x -= dx;
			y -= dy;

			rotation++;
			rotation %= PentominoBuilder.getNumberOfRotations(pentID);
			bits = PentominoBuilder.getPent(pentID, rotation);			
			return false;
		}
		return true;
	}
	
	/**
	 * Try to rotate it to the right
	 * @return true if possible and false otherwise
	 */
	public boolean rotateRight() {
		Field.deletePentomino(this);

		if (tryRight(0, 0))
			return true;
		if (tryRight(1, 0))
			return true;
		if (tryRight(2, 0))
			return true;
		if (tryRight(-1, 0))
			return true;
		if (tryRight(-2, 0))
			return true;
		if (tryRight(0, 1))
			return true;

		Field.addPentomino(this);
		return false;	
	}
	/**
	 * Try to move it to the right
	 * @param dx horizontal shift
	 * @param dy vertical shift
	 * @return true if possible and false otherwise
	 */
	public boolean tryRight(int dx, int dy) {
		x += dx;
		y += dy;

		rotation++;
		rotation %= PentominoBuilder.getNumberOfRotations(pentID);
		bits = PentominoBuilder.getPent(pentID, rotation);

		if (!Field.addPentomino(this)) {
			x -= dx;
			y -= dy;

			rotation--;
			rotation += PentominoBuilder.getNumberOfRotations(pentID);
			rotation %= PentominoBuilder.getNumberOfRotations(pentID);
			bits = PentominoBuilder.getPent(pentID, rotation);			
			return false;
		}
		return true;
	}
	
	/**
	 * Try to move it down
	 * @return true if we can a false otherwise
	 */
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
	
	/**
	 * Try to move it left
	 * @return true if we can and false otherwise
	 */
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
	
	/**
	 * Try to move it right 
	 * @return true if we can and false otherwise
	 */
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
	/**
	 * drop the pentomino down to the bottom
	 */
	public void drop() {
		while (moveDown());
	}
}