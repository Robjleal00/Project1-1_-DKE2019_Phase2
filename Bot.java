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

		playGame(obtainedVec);
	}
	
	public static boolean mutate(Vec v) {
		double prob = RandomEngine.getDouble();
		if (prob >= 0.05)
			return false;

		double val = RandomEngine.getDouble() * 2 / 5;
		val -= 0.2;
		if (val == 0)
			val = 0.2;
		int id = RandomEngine.getInt(0, v.length);
		v.v[id] += val;
		v.normalize();
		return true;
	}

	public static UI ui;

	public static int playGame(Vec p) {
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

			/*try {
				Log.printPositions(positions);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}*/
			PositionSearch.init();
			obj = getBestPosition(obj.getPentID(), positions, f);
			Field.addPentomino(obj);
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
			
			obj = new Pentomino();
			positions = PositionSearch.search(obj);
		}

		ui.closeWindow();
		return Field.getScore();
	}

	public static Pentomino getBestPosition(int pentID, ArrayList<int[]> positions, Function f) {
		double mx = 0;
		Pentomino answer = new Pentomino(); 
		for (int i = 0; i < positions.size(); i++) {
			int[] currentPosition = positions.get(i);
			Pentomino current = new Pentomino(currentPosition[0] - 5, currentPosition[1] - 5, pentID, currentPosition[2]);

			Field.addPentomino(current);
			double value = f.calc();
			Field.deletePentomino(current);

			if (i == 0 || mx < value) {
				mx = value;
				answer = current;
			}
		}
		return answer;
	}
}
