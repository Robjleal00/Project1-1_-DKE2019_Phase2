public class Pentomino {
	private int[][] bits;
	private int pentID = 0;

	private int x, y;

	public Pentomino(int _x, int _y) {
		x = _x;
		y = _y;

		pentID = RandomEngine.getInt(0, 12);
		int numberOfRotations = RandomEngine.getInt(0, 4);
		int numberOfReflections = RandomEngine.getInt(0, 2);

		for (int i = 0; i < numberOfRotations; i++)
			PentominoBuilder.rotate(pentID);
		for (int i = 0; i < numberOfReflections; i++) 
			PentominoBuilder.reflect(pentID);

		bits = PentominoBuilder.getBasicPent(pentID);		 
	}

	public Pentomino(int _x, int _y, int _pentID, boolean randomized) {
		x = _x;
		y = _y;
		pentID = _pentID;

		if (randomized) {
			int numberOfRotations = RandomEngine.getInt(0, 4);
			int numberOfReflections = RandomEngine.getInt(0, 2);

			for (int i = 0; i < numberOfRotations; i++)
				PentominoBuilder.rotate(pentID);
			for (int i = 0; i < numberOfReflections; i++) 
				PentominoBuilder.reflect(pentID);
		}
		bits = PentominoBuilder.getBasicPent(pentID);	
	}

	public Pentomino(int _pentID, boolean randomized) {
		x = RandomEngine.getInt(2, Field.getWidth() - 2);
		y = -3;

		pentID = _pentID;
		if (randomized) {
			int numberOfRotations = RandomEngine.getInt(0, 4);
			int numberOfReflections = RandomEngine.getInt(0, 2);

			for (int i = 0; i < numberOfRotations; i++)
				PentominoBuilder.rotate(pentID);
			for (int i = 0; i < numberOfReflections; i++) 
				PentominoBuilder.reflect(pentID);
		}
		bits = PentominoBuilder.getBasicPent(pentID);
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
}