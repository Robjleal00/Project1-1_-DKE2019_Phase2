import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.lang.*;

public class Vec {
	/**
	 * public static void main(String args[]) {
		Vec a = new Vec(new double[]{1, 2});
		Vec b = new Vec(new double[]{1, 2});
		Vec c = Vec.add(a, b);

		System.out.println(a.v[0] + " " + a.v[1]);
		System.out.println(b.v[0] + " " + b.v[1]);
		System.out.println(c.v[0] + " " + c.v[1]);
	}
	 */

	public double[] v;
	public int length = 0;

	public Vec() {

	}
	/**
	 * Create a vector 
	 * @param len
	 */
	public Vec(int len) {
		v = new double[len];
		length = len;
	}

	/**
	 * Create a vector 
	 * @param _v an array of doubles
	 */
	public Vec(double[] _v) {
		v = _v;
		length = v.length;
	}
	
	/**
	 * sum up two vectors 
	 * @param l a vector
	 * @param r a vector
	 * @return a new vector 
	 */
	public static Vec add(Vec l, Vec r) {
		Vec res = new Vec(l.length);	
		for (int i = 0; i < l.length; i++)
			res.v[i] = l.v[i] + r.v[i];
		return res;
	}
	
	/**
	 * substract two vectors
	 * @param l a vector
	 * @param r a vector
	 * @return a new vector 
	 */
	public static Vec substract(Vec l, Vec r) {
		Vec res = new Vec(l.length);
		for (int i = 0; i < l.length; i++)
			res.v[i] = l.v[i] - r.v[i];
		return res;
	}
	
	/**
	 * multiply two vectors
	 * @param l a vector
	 * @param r a vector 
	 * @return a new vector
	 */
	public static double multiply(Vec l, Vec r) {
		double res = 0;
		for (int i = 0; i < l.length; i++)
			res += l.v[i] * r.v[i];
		return res;
	}
	
	/**
	 * multiply a vector by a scalar
	 * @param l a vector
	 * @param r a vector 
	 * @return a new vector
	 */
	public static Vec multiply(Vec l, double r) {
		Vec res = new Vec(l.length);
		for (int i = 0; i < l.length; i++) 
			res.v[i] = l.v[i] * r;
		return res;
	}
	/**
	 * divide one vector by another one 
	 * @param l a vector 
	 * @param r a vector
	 * @return a new vector
	 */
	public static Vec divide(Vec l, double r) {
		Vec res = new Vec(l.length);
		for (int i = 0; i < l.length; i++) 
			res.v[i] = l.v[i] / r;
		return res;
	}
	
	/**
	 * return the size of the vector (its length)
	 * @return a double number representing the size
	 */
	public double size() {
		double res = 0;
		for (int i = 0; i < length; i++) 
			res += v[i] * v[i];
		return Math.sqrt(res);
	}

	/**
	 * Normalize the vectors
	 */
	public void normalize() {
		double len = size();
		for (int i = 0; i < length; i++) 
			v[i] /= len;
	}
	/**
	 * @return true of the two vectors are equal and false otherwise
	 */
	public boolean equals(Object otherObject) {
		if (otherObject == null)
			return false;
		if (otherObject.getClass() != getClass())
			return false;
		Vec r = (Vec)otherObject;
		return (length == r.length && v == r.v);
	}
}