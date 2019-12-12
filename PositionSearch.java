import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.*;

public class PositionSearch {
	public static enum Move {
		DOWN,
		RIGHT,
		LEFT,
		ROTATE_LEFT,
		ROTATE_RIGHT
	}

	public class Parent{
		public int[] prev;
		public Move move;

		public Parent() {
			prev = new int[]{-1, -1, -1};
			move = Move.DOWN;
		} 

		public Parent(int[] _prev, Move _move) {
			prev = _prev;
			move = _move;
		}
	}

	public static boolean[][][] used;
	public static Parent[][][] parent;
	public static int pentID;
	public static ArrayList<int[]> answer;

	public static void init() {
		used = new boolean[Field.getWidth() + 5][Field.getHeight() + 5][4];
		parent = new Parent[Field.getWidth() + 5][Field.getHeight() + 5][4];
		answer = new ArrayList<>();
	}

	public static void buildBestPath(Pentomino obj, int[] position, UI ui) {
		ArrayList<Move> path = new ArrayList<>();
		int[] finalPos = new int[]{-1, -1, -1};
		while (!Arrays.equals(position, finalPos)) {
			path.add(parent[position[0]][position[1]][position[2]].move);
			position = parent[position[0]][position[1]][position[2]].prev;
		}

		for (int i = path.size() - 1; i >= 0; i--) {	
			switch(path.get(i)) {
				case LEFT:         obj.moveLeft();     break;
				case RIGHT:        obj.moveRight();    break;
				case DOWN:         obj.moveDown();     break;
				case ROTATE_LEFT:  obj.rotateLeft();   break;
				case ROTATE_RIGHT: obj.rotateRight();  break;
			}
			ui.setState(Field.getUsed());

			try {
			    Thread.sleep(50);
			}
			catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}

	public static ArrayList<int[]> search(Pentomino obj) {
		pentID = obj.getPentID();
		int n = Field.getWidth() + 5, m = Field.getHeight() + 5;
		
		for (int i = 0; i < n; i++) 
		for (int j = 0; j < m; j++) 
		for (int k = 0; k < 4; k++) {
			used[i][j][k] = false;
			parent[i][j][k] = new PositionSearch().new Parent();
		}

		dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{-1, -1, -1}, Move.DOWN);
		return answer;
	} 

	public static void dfs(int i, int j, int t, int[] previous, Move move) {
		if (used[i][j][t])
			return;
		used[i][j][t] = true;
		parent[i][j][t].prev = previous;
		parent[i][j][t].move = move;

		Pentomino obj = new Pentomino(i - 5, j - 5, pentID, t);
		Field.addPentomino(obj);
		boolean result = obj.moveDown();
		Field.deletePentomino(obj);
		if (result) {
			dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{i, j, t}, Move.DOWN);
		}
		else if (obj.allInside())
			answer.add(new int[]{i, j, t});			

		obj = new Pentomino(i - 5, j - 5, pentID, t);
		Field.addPentomino(obj);
		result = obj.moveLeft();
		Field.deletePentomino(obj);
		if (result) {
			dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{i, j, t}, Move.LEFT);
		}

		obj = new Pentomino(i - 5, j - 5, pentID, t);
		Field.addPentomino(obj);
		result = obj.moveRight();
		Field.deletePentomino(obj);
		if (result) {
			dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{i, j, t}, Move.RIGHT);
		}			

		obj = new Pentomino(i - 5, j - 5, pentID, t);
		Field.addPentomino(obj);
		result = obj.rotateLeft();
		Field.deletePentomino(obj);
		if (result) {
			dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{i, j, t}, Move.ROTATE_LEFT);
		}

		obj = new Pentomino(i - 5, j - 5, pentID, t);
		Field.addPentomino(obj);
		result = obj.rotateRight();
		Field.deletePentomino(obj);
		if (result) {
			dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{i, j, t}, Move.ROTATE_RIGHT);
		}
	}
}