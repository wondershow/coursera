package class5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import class4.Edge;

public class TSP
{
	// number of cities
	private static int N;
	private static double[] x, y;
	//double
	HashMap<HashSet<Integer>, Double> A1, A2;
	
	
	
	private  static final String
	TEST_CASE_PATH = "/Users/leizhang/coursera/stanford/algorithm2/src/class5/tsp.txt";

	public static void main(String[] args)
	{
		double[] set = {1f,2f,3f,4f,5f,6f};
	    processSubsets(set, 3);
		//readInFiles(TEST_CASE_PATH);
	}
	
	private static void readInFiles(String file)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String firstline, line;
			firstline  = br.readLine().trim();
			N = Integer.parseInt(firstline);
			x = new double[N];
			y = new double[N];
			//int lineNumber = 1;
			int i = 0;
			while ( (line = br.readLine()) != null)
			{
				String[] tmp = line.split(" ");
				x[i] = Double.parseDouble(tmp[0]);
				x[i] = Double.parseDouble(tmp[1]);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void TSP(){
		for (int m = 2; m <= N; m++)
		{
			HashMap<HashSet<Integer>, Double> A_new, A_old;
			
			
			
		}
	}
	
	
	private static double distance(double x1, double y1, double x2, double y2)
	{
		double dx = x1 - y2;
		double dy = y1 - y2;
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	private static void processSubsets(double[] set, int k) {
		HashSet<Integer> subset = new HashSet<Integer>();
	    processLargerSubsets(set, subset, 0, 0);
	}
	
	private static void processSubset(HashSet<Integer> subset, 
									  HashMap<HashSet<Integer>, Double> A_new,
									  HashMap<HashSet<Integer>, Double> A_old)
	{
		Iterator<Integer> it = subset.iterator();
		while (it.hasNext())
		{
			int j = it.next();
			if (j == 0) continue;
			
		}
		
	}

	private static void processLargerSubsets(double[] set, HashSet<Integer> subset, int subsetSize, int nextIndex) {
	    if (subsetSize == subset.size()) {
	    		processSubset(subset);
	    } else {
	        for (int j = nextIndex; j < set.length; j++) {
	            subset.add(j);
	            processLargerSubsets(set, subset, subsetSize + 1, j + 1);
	        }
	    }
	}
	
	private static void process(double[] subset) {
	    System.out.println(Arrays.toString(subset));
	}
	
	
}
