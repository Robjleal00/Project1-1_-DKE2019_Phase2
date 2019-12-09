import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.*;

public class Function {
	Vec p;

	public Function(Vec _p) {
		p = _p;
	}

	public double calc() {
		double heightSum = 0;
		double bumpiness = 0;
		int previous = -1;
		for (int i = 0; i < Field.getWidth(); i++) {
			for (int j = 0; j < Field.getHeight(); j++) {
				if (Field.getCell(j, i) != -1) {
					int currentHeight = Field.getHeight() - j;
					heightSum += currentHeight;
					if (previous != -1) 
						bumpiness += Math.abs(previous - currentHeight);
					previous = currentHeight;
					break;
				}
			}
			if (previous == -1)
				previous = 0;
		}

		double completeLines = 0;
		for (int i = 0; i < Field.getHeight(); i++) {
			boolean ok = true;
			for (int j = 0; j < Field.getWidth(); j++) 
				if (Field.getCell(i, j) == -1)
					ok = false;
			if (ok)
				completeLines += 1;
		}

		double holes = 0;
		for (int i = 0; i < Field.getWidth(); i++) {
			boolean found = false;
			for (int j = 0; j < Field.getHeight(); j++) {
				if (found && Field.getCell(j, i) == -1)
					holes++;
				if (Field.getCell(j, i) != -1)
					found = true;
			}
		}

		//System.out.println("heightSum: " + heightSum);
		//System.out.println("completeLines: " + completeLines);
		//System.out.println("holes " + holes);
		//System.out.println("bumpiness " + bumpiness);

		Vec x = new Vec(new double[]{heightSum, completeLines, holes, bumpiness});
		return Vec.multiply(p, x);
	}
}