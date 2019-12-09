import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.*;

public class Bot {
	public static void main(String[] args) {
		if (!PentominoBuilder.init()) {
			System.exit(0);
		}

		PentominoBuilder.init();
		RandomEngine.init();
		//PentominoBuilder.print();

		Vec obtainedVec = new Vec(new double[]{-0.3141280779824179, 0.9377892613492809, 0.0841010847339205, -0.12166289273077524}); 
		playGameAnimated(obtainedVec);

		System.out.println("Game over");
		System.out.println("Your score is " + Field.getScore());

		try {
		    Thread.sleep(5000);
		}
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

		ui.closeWindow();
	}

	public static UI ui;
	
	public static void playGame(Vec p) {
		Field.init();
		try {
			ui = new UI(Field.getHeight(), Field.getWidth(), Field.getCellSize());
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		ui.setState(Field.getUsed());
		
		Function f = new Function(p);

		Pentomino obj = new Pentomino();
		PositionSearch.init();
		ArrayList<int[]> positions = PositionSearch.search(obj);
		while (positions.size() != 0) {

			int id = getBestPosition(obj.getPentID(), positions, f);
			int[] position = positions.get(id);
			Pentomino current = new Pentomino(position[0] - 5, position[1] - 5, obj.getPentID(), position[2]);
			Field.addPentomino(current);
			ui.setState(Field.getUsed());

			try {
			    Thread.sleep(2000);
			}
			catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}

			Field.updateScore();
			ui.setState(Field.getUsed());

			try {
			    Thread.sleep(2000);
			}
			catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}

			PositionSearch.init();
			obj = new Pentomino();
			positions = PositionSearch.search(obj);
		}
	}

	public static void playGameAnimated(Vec p) {
		Field.init();
		try {
			ui = new UI(Field.getHeight(), Field.getWidth(), Field.getCellSize());
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		ui.setState(Field.getUsed());
		
		Function f = new Function(p);

		Pentomino obj = new Pentomino();
		PositionSearch.init();
		ArrayList<int[]> positions = PositionSearch.search(obj);
		while (positions.size() != 0) {

			int id = getBestPosition(obj.getPentID(), positions, f);
			PositionSearch.buildBestPath(obj, positions.get(id), ui);

			PositionSearch.init();
			obj = new Pentomino();
			positions = PositionSearch.search(obj);
		}
	}

	public static int getBestPosition(int pentID, ArrayList<int[]> positions, Function f) {
		double mx = 0;
		int id = 0; 
		for (int i = 0; i < positions.size(); i++) {
			int[] currentPosition = positions.get(i);
			Pentomino current = new Pentomino(currentPosition[0] - 5, currentPosition[1] - 5, pentID, currentPosition[2]);

			Field.addPentomino(current);
			double value = f.calc();
			Field.deletePentomino(current);

			if (i == 0 || mx < value) {
				mx = value;
				id = i;
			}
		}
		return id;
	}
}
