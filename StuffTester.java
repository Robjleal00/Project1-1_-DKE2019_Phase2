import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.*;

public class StuffTester {
	public static void main(String[] args) {
		Vec[] generation = new Vec[10];
		int[] fitness = new int[10];

		for (int i = 0; i < generation.length; i++) 
			generation[i] = new Vec(new double[]{i, i, i, i});
		for (int i = 0; i < generation.length; i++)
			fitness[i] = -i;

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
		int[] _fitness = new int[fitness.length];

		for (int i = 0; i < generation.length; i++) {
			_generation[i] = new Vec(new double[]{generation[i].v[0], generation[i].v[1], generation[i].v[2], generation[i].v[3]});
			_fitness[i] = new Integer(fitness[i]);
		}

		for (int i = 0; i < generation.length; i++) {
			System.out.println(toBeShuffled.get(i));
			for (int j = 0; j < 4; j++)
				System.out.print(generation[i].v[j] + " ");
			System.out.println(fitness[i]);

			generation[i] = _generation[toBeShuffled.get(i)];
			fitness[i] = _fitness[toBeShuffled.get(i)];

			for (int j = 0; j < 4; j++)
				System.out.print(generation[i].v[j] + " ");
			System.out.println(fitness[i]);
		}
	}
}