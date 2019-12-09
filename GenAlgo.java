import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.*;

public class GenAlgo {
	public static void main(String[] args) {
		if (!PentominoBuilder.init()) {
			System.exit(0);
		}

		PentominoBuilder.init();
		RandomEngine.init();
		//PentominoBuilder.print();

		Vec[] generation = new Vec[1000];
		try {
			generation = Log.read("generation-final-5.log");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		try {
			Log.init(6);	
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		int[] fitness = new int[generation.length];
		for (int i = 0; i < generation.length; i++) {
			generation[i].normalize();
			fitness[i] = 0;
			System.out.println("function #" + i);
			for (int j = 0; j < 100; j++) {
				String logOutput = "game #" + Integer.toString(j);
				String goingBack = "";
				for (int k = 0; k < logOutput.length(); k++)
					goingBack += "\b";
				
				System.out.print(logOutput);

				fitness[i] += playGame(generation[i]);	

				System.out.print(goingBack);
			}
		}
		
		ArrayList<Integer> toBeShuffled = new ArrayList<>();
		for (int i = 0; i < generation.length; i++)
			toBeShuffled.add(i);

		toBeShuffled.sort(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				if (fitness[o1] < fitness[o2])
					return -1;
				else if (fitness[o1] == fitness[o2])
					return 0;
				else
					return 1;
			}
		});

		Vec[] _generation = new Vec[generation.length];
		int[] _fitness = new int[generation.length];

		for (int i = 0; i < generation.length; i++) {
			_generation[i] = new Vec(new double[]{generation[i].v[0], generation[i].v[1], generation[i].v[2], generation[i].v[3]});
			_fitness[i] = new Integer(fitness[i]);
		}

		for (int i = 0; i < generation.length; i++) {
			generation[i] = _generation[toBeShuffled.get(i)];
			fitness[i] = _fitness[toBeShuffled.get(i)];
		}

		try {
			Log.writeFitnessFunctionData(generation, fitness);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		Vec[] offset = new Vec[generation.length * 3 / 10];

		ArrayList<Vec[]> offsetToBePrinted = new ArrayList<>();
		ArrayList<Vec[]> offsetToBePrintedMutated = new ArrayList<>();

		for (int i = 0; i < generation.length * 3 / 10; i++) {
			Collections.shuffle(toBeShuffled);

			int id1 = -1, id2 = -1;
			for (int j = 0; j < generation.length / 10; j++) {
				int currentID = toBeShuffled.get(j);
				if (id1 == -1) {
					id1 = currentID;
					continue;
				}
				if (id2 == -1) {
					id2 = currentID;
					continue;
				}
				if (fitness[currentID] > fitness[id1]) {
					id2 = id1;
					id1 = currentID;
					continue;
				}
				if (fitness[currentID] > fitness[id2]) {
					id2 = currentID;
					continue;
				}
			}

			Vec p1 = Vec.multiply(generation[id1], fitness[id1]);
			Vec p2 = Vec.multiply(generation[id2], fitness[id2]);
			Vec son = Vec.add(p1, p2);
			son.normalize();
			offset[i] = son;

			Vec old_son = son;
			boolean result = mutate(offset[i]);

			if (result) 
				offsetToBePrintedMutated.add(new Vec[]{generation[id1], generation[id2], old_son, son});
			else
				offsetToBePrinted.add(new Vec[]{generation[id1], generation[id2], son});
		}

		try {
			Log.writeOffset(offsetToBePrinted);
			Log.writeMutatedOffset(offsetToBePrintedMutated);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		Vec[] replaced = new Vec[generation.length * 3 / 10];
		for (int i = 0; i < generation.length * 3 / 10; i++) {
			replaced[i] = generation[i];
			generation[i] = offset[i];
		}
		try {
			Log.writeReplacements(replaced, offset);
			Log.writeResultGeneration(generation);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
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

	//public static UI ui;

	public static int playGame(Vec p) {
		Field.init();
		/*try {
			ui = new UI(Field.getHeight(), Field.getWidth(), Field.getCellSize());
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		ui.setState(Field.getUsed());
		*/
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
			//ui.setState(Field.getUsed());

			/*try {
			    Thread.sleep(500);
			}
			catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}*/

			Field.updateScore();
			obj = new Pentomino();
			positions = PositionSearch.search(obj);
		}

		//ui.closeWindow();
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
