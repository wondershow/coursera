package programming.java.ch2;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Brownian
{
	public static void curve (double x0, double y0, 
							 double x1, double y1,
							 double var, double s)
	{
		if (x1 - x0 < .01) {
			StdDraw.line(x0,y0,x1,y1);
			return;
		}
		
		double xm = (x0 + x1)/2;
		double ym = (y0 + y1)/2;
		
		double delta = StdRandom.gaussian(0, Math.sqrt(var));
		
		//curve(x0, y0, xm, ym + delta, var/s, )
		
	}
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		System.out.println(ex234(6));
	}
	
	public static void ex233(int n)
	{
		if (n <= 0) return;
		StdOut.println(n);
		ex233(n-2);
		ex233(n-3);
		StdOut.println(n);
	}
	
	public static String ex234(int n)
	{
		if (n <= 0) return "";
		return ex234(n-3) + n + ex234(n-2) + n;
	}
}
