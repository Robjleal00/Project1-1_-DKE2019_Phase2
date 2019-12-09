import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.*;

public class PositionSearch {
	public static boolean[][][] used;
	public static int[][][][] parent;
	public static int pentID;
	public static ArrayList<int[]> answer;

	public static void init() {
		used = new boolean[Field.getWidth() + 5][Field.getHeight() + 5][4];
		parent = new int[Field.getWidth() + 5][Field.getHeight() + 5][4][3];
		answer = new ArrayList<>();
	}

	public static ArrayList<int[]> search(Pentomino obj) {
		pentID = obj.getPentID();
		int n = Field.getWidth() + 5, m = Field.getHeight() + 5;
		
		for (int i = 0; i < n; i++) 
		for (int j = 0; j < m; j++) 
		for (int k = 0; k < 4; k++) {
			used[i][j][k] = false;
			parent[i][j][k] = new int[]{-1, -1, -1};
		}

		dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{-1, -1, -1});
		return answer;
	} 

	public static void dfs(int i, int j, int t, int[] previous) {
		if (used[i][j][t])
			return;
		used[i][j][t] = true;
		parent[i][j][t] = previous;

		Pentomino obj = new Pentomino(i - 5, j - 5, pentID, t);
		Field.addPentomino(obj);
		boolean result = obj.moveLeft();
		Field.deletePentomino(obj);
		if (result) {
			dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{i, j, t});
		}

		obj = new Pentomino(i - 5, j - 5, pentID, t);
		Field.addPentomino(obj);
		result = obj.moveRight();
		Field.deletePentomino(obj);
		if (result) {
			dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{i, j, t});
		}			

		obj = new Pentomino(i - 5, j - 5, pentID, t);
		Field.addPentomino(obj);
		result = obj.moveDown();
		Field.deletePentomino(obj);
		if (result) {
			dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{i, j, t});
		}
		else if (obj.allInside())
			answer.add(new int[]{i, j, t});			

		obj = new Pentomino(i - 5, j - 5, pentID, t);
		Field.addPentomino(obj);
		result = obj.rotateLeft();
		Field.deletePentomino(obj);
		if (result) {
			dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{i, j, t});
		}

		obj = new Pentomino(i - 5, j - 5, pentID, t);
		Field.addPentomino(obj);
		result = obj.rotateRight();
		Field.deletePentomino(obj);
		if (result) {
			dfs(obj.getX() + 5, obj.getY() + 5, obj.getRotation(), new int[]{i, j, t});
		}
	}
}