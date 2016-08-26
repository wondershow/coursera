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
import java.util.Set;

import class4.Edge;

public class TSP
{
	// number of cities
	private static int N;
	private static float[] x, y;
	//double
	HashMap<HashSet<Integer>, Float> A1, A2;
	private static float[][] distance;
	
	private  static final String
	TEST_CASE_PATH = "/Users/leizhang/coursera/stanford/algorithm2/src/class5/tsp.txt";

	public static void main(String[] args)
	{
		//double[] set = {1f,2f,3f,4f,5f,6f};
	    //processSubsets(set, 3);
		readInFiles(TEST_CASE_PATH);
		TSP();
		debughelper();
	}
	
	private static void readInFiles(String file)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String firstline, line;
			firstline  = br.readLine().trim();
			N = Integer.parseInt(firstline);
			x = new float[N];
			y = new float[N];
			distance = new float[N][N];
			
			//int lineNumber = 1;
			int i = 0;
			while ( (line = br.readLine()) != null)
			{
				String[] tmp = line.split(" ");
				x[i] = Float.parseFloat(tmp[0]);
				y[i++] = Float.parseFloat(tmp[1]);
			}
			populateDistance();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	private static void setSetMap(int s, int j, float d, HashMap<Integer, Float[]> A)
	{
		Float[] dd = null;
		if (!A.containsKey(s))  {
			dd = new Float[N+1];
			for (int i = 1; i <= N; i++)
				dd[i] = Float.POSITIVE_INFINITY;
		}
		else
			dd = A.get(s);
		dd[j] = d;
		String tmps = helper(s);
		//System.out.println("A ["+ tmps + "," + j + "] = " + d );
		A.put(s, dd);
	}
	
	private static float getSetMap(int s, int j, HashMap<Integer, Float[]> A) 
	{
		Float[] dd = A.get(s);
		if (dd == null) return Float.POSITIVE_INFINITY;
		return dd[j];
	}
	
	
	private static void TSP(){
		HashMap<Integer, Float[]> A1 = new HashMap<Integer, Float[]>();
		HashMap<Integer, Float[]> A2 = new HashMap<Integer, Float[]>();
		
		setSetMap(1, 1, 0.0f,  A1);
		for (int i = 2; i <= N; i++)
			setSetMap(setBit(0, i), i, Float.POSITIVE_INFINITY, A1);
		
		for (int s = 2; s <= N; s++) 
			for (int l = 1; l <= N; l++)
				setSetMap(setBit(0, s), l, Float.POSITIVE_INFINITY, A1);
		
		for (int m = 2; m <= N; m++)
		{
			System.out.println("m = " + m);
			long startTime = System.nanoTime();
			HashMap<Integer, Float[]> A_new, A_old;
			A_new = ( A1.size() == 0 )? A1 : A2;
			A_old = ( A1.size() == 0 )? A2 : A1;
			Set<Integer> oldSet = A_old.keySet();
			Set<Integer> newSet = getLargerSubSets(oldSet);
			System.out.println("newSet size " + newSet.size());
			for (int s : newSet)
			{
//				for (int j = 2; j <= N; j++) 
//				{
//					Double min = Double.POSITIVE_INFINITY;
//					for (int k = j+1; k <= N; k++) 
//					{
//						// if j is not included in the newer set
//						if (!isBitSet(s, j)) continue; 
//						
//						int smallerS = resetBit(s, j);
//						double dist2K =  getSetMap(smallerS, k, A_old);
//						if (dist2K == Double.POSITIVE_INFINITY) continue;
//						dist2K += distance(k, j);
//						if (dist2K < min) min = dist2K;
//					}
//					setSetMap(s, j, min, A_new);
//				}
				if (!isBitSet(s, 1)) continue;
				for (int j = 1; j <= N; j++)
				{
					if (!isBitSet(s, j)) continue;
					Float min = Float.POSITIVE_INFINITY;
					for (int k = 1; k <= N; k++)
					{
						if (!isBitSet(s, k) || k == j) continue; 
						int smallerS = resetBit(s, j);
						float dist2K =  getSetMap(smallerS, k, A_old);
						if (dist2K == Double.POSITIVE_INFINITY) continue;
						dist2K += distance[k-1][j-1];
						if (dist2K < min) min = dist2K;
					}
					setSetMap(s, j, min, A_new);
				}
			}
			A_old.clear();
			long endTime = System.nanoTime();
			System.out.println("time for size m = " + m + " is " + (endTime - startTime)/1000000000 + "s");
		}
		
		HashMap<Integer, Float[]> A = A1.size() == 0 ? A2:A1;
		Float res = Float.POSITIVE_INFINITY;
					
//		System.out.println("size of last hashmap(should be 1 here) :" + A.size());
		Set<Integer> lastSetS = A.keySet();
//		for (int i : lastSetS )
//			System.out.println(i);
		int lastS = 0;
		for (int i = 1; i <= N; i++)
			lastS = setBit(lastS, i);
//		System.out.println("lastS = " + lastS);
		Float[] dd = A.get(lastS);
		if (dd == null) System.out.println("there is an error here !:");
		System.out.println(dd.length);
		for (int i = 2; i <= N; i++)
			if (dd[i] + (float)distance(1, i) < res ) res =  dd[i] + (float)distance(1, i);
		System.out.println("TSP result is " + res);
	}
	
	private static double distance(int k, int j)
	{
		double dx = x[k - 1] - x[j - 1];
		double dy = y[k - 1] - y[j - 1];
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	/*
	 * Given a set of sets of size m, which are all subsets of an universal set of size m
	 * generate all size m + 1 subsets of same universal set.
	 * Note that the set in the result contains element 1.
	 * */
	private static Set<Integer> getLargerSubSets(Set<Integer> set) {
		System.out.println("in ");
		//System.out.println("old size :" + set.size());
		HashSet<Integer> existing = new HashSet<Integer>();
		Set<Integer> res = new HashSet<Integer>();
		for (Integer s : set) {
			//System.out.println("old " + s);
			if (!isBitSet(s, 1)) continue;
			for (int bit = 2; bit <= N; bit++)
			{
				if (isBitSet(s, bit)) continue;
				int i = setBit(s, bit);
				if (existing.contains(i)) continue;
				existing.add(i);
				//System.out.println("new:" + i);
				res.add(i);
			}
		}
		System.out.println("out ");
		//System.out.println("New size " + res.size());
		return res;
	}
	
	
	private static int setBit (int n, int bit) {
		return n | (1 << (bit-1));
	}
	
	private static int resetBit (int n, int bit) {
		return n & ~(1 << (bit-1));
	}
	
	private static boolean isBitSet(int n, int bit) {
		return ((n>>(bit - 1)) & 1) == 1  ? true: false;
	}
	
	private static String helper(int n) {
		String res = "[";
		for (int i = 1; i <= N; i++)
			if (isBitSet(n, i)) res += i + ",";
		res += "]";
		return res;
	}
	
	private static void debughelper() {
		for (int i = 1; i <= N; i++)
			for (int j = 1 + i; j <= N;j ++)
				System.out.println(  i + " : " + j + ":" + distance(i,j));
	}
	
	private static void populateDistance() {
		for (int i = 1; i <= N; i++)
			for (int j = 1; j <= N;j ++)
			distance[i-1][j-1] = (float)distance(i,j);
	}
}
