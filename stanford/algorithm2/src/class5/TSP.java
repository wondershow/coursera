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
	private static double[] x, y;
	//double
	HashMap<HashSet<Integer>, Double> A1, A2;
	private static double[][] distance;
	
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
			x = new double[N];
			y = new double[N];
			distance = new double[N][N];
			
			//int lineNumber = 1;
			int i = 0;
			while ( (line = br.readLine()) != null)
			{
				String[] tmp = line.split(" ");
				x[i] = Double.parseDouble(tmp[0]);
				y[i++] = Double.parseDouble(tmp[1]);
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
	
	
	private static void setSetMap(int s, int j, double d, HashMap<Integer, Double[]> A)
	{
		Double[] dd = null;
		if (!A.containsKey(s))  {
			dd = new Double[N+1];
			for (int i = 1; i <= N; i++)
				dd[i] = Double.POSITIVE_INFINITY;
		}
		else
			dd = A.get(s);
		dd[j] = d;
		//String tmps = helper(s);
		//System.out.println("A ["+ tmps + "," + j + "] = " + d );
		A.put(s, dd);
	}
	
	private static double getSetMap(int s, int j, HashMap<Integer, Double[]> A) 
	{
		Double[] dd = A.get(s);
		if (dd == null) return Double.POSITIVE_INFINITY;
		return dd[j];
	}
	
	
	private static void TSP(){
		ElementToHash A1 = new ElementToHash(N);
		ElementToHash A2 = new ElementToHash(N);
		
		
		A1.set(1, 1, 0.0d);
		for (int i = 2; i <= N; i++)
			A1.set(setBit(0, i), 1, Double.POSITIVE_INFINITY);
		
		
		ElementToHash A_new, A_old;
		
		for (int m = 2; m <= N; m++)
		{
			System.out.println("m = " + m);
			A_new = ( A1.isEmpty() )? A1 : A2;
			A_old = ( A1.isEmpty() )? A2 : A1;
			Set<Integer> oldSet = A_old.keys();
			Set<Integer> newSet = getLargerSubSets(oldSet);
			System.out.println("newSet size " + newSet.size());
			for (int s : newSet)
			{
				if (!isBitSet(s, 1)) continue;
				for (int j = 2; j <= N; j++)
				{
					if (!isBitSet(s, j)) continue;
					Double min = Double.POSITIVE_INFINITY;
					for (int k = 1; k <= N; k++)
					{
						if (!isBitSet(s, k) || k == j) continue; 
						int smallerS = resetBit(s, j);
						double dist2K =  A_old.get(smallerS, k);
						if (dist2K == Double.POSITIVE_INFINITY) continue;
						dist2K += distance[k-1][j-1];
						if (dist2K < min) min = dist2K;
					}
					A_new.set(s, j, min);
				}
			}
			A_old.clear();
			System.out.println("tst");
		}
		
		ElementToHash A = (A1.isEmpty())? A2:A1;
		Double res = Double.POSITIVE_INFINITY;
		Set<Integer> lastSetS = A.keys();
		System.out.println("size of last hashmap(should be 1 here) :" + lastSetS.size());
//		for (int i : lastSetS )
//			System.out.println(i);
		int lastS = 0;
		for (int i = 1; i <= N; i++)
			lastS = setBit(lastS, i);
//		System.out.println("lastS = " + lastS);
		for (int i = 2; i <= N; i++)
			if (A.get(lastS, i) + distance(1, i) < res ) res =  A.get(lastS, i) + distance(1, i);
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
		System.out.println("out");
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
			distance[i-1][j-1] = distance(i,j);
	}
	
	static class ElementToHash {
		int N;
		HashMap<Integer, Double>[] maps;
		HashSet<Integer> keySet;
		
		public ElementToHash(int n) {
			N = n;
			maps = new HashMap[N];
			for (int i = 0; i < N; i++){
				maps[i] = new HashMap<Integer, Double>();
			}
			keySet = new HashSet<Integer>();
		}
		
		public void clear() {
			for (int i = 0; i < N; i++)
				maps[i].clear();
			keySet.clear();
		}
		
		public boolean isEmpty() {
			for (int i = 0; i < N; i++)
				if (maps[i].size() > 0) return false;
			return true;
		}
		
		public void set(int hash, int vertex, double dist) {
			maps[vertex - 1].put(hash, dist);
			keySet.add(hash);
		}
		
		public double get(int hash, int vertex) {
			if (maps[vertex - 1].containsKey(hash))
				return maps[vertex - 1].get(hash) ;
			return Double.POSITIVE_INFINITY;
		}
		
		public Set<Integer> keys() {
			return keySet;
		}
	}
}
