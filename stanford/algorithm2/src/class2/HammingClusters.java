package class2;

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

public class HammingClusters
{
	//private static int[] vertice;
	//N is number of vertex, W is the how many bit to represent a vertex.
	private static int N, W;
	//private static ArrayList<Integer>[] buckets;
	private static UnionFind uf;
	
	//freq logs all those values which repeat 2 or more times
	private static int[] vertexSet, freq;
	private static HashSet<Integer> universe;
	
	//contains vertex number to int(index) mapping
	private static HashMap<Integer, Integer> map;
	
	//stores all identical nodes from integer(index) to a list of its identicals
	private static HashMap<Integer, ArrayList> identicals;
	
	public static void main(String[] args)
	{
		//readInVertices("/Users/leizhang/coursera/stanford/algorithm2/src/class2/biggraph.txt");
		//kruskal();
		//W = 24;
		
		//ArrayList<Integer>[] bags =  new ArrayList[W];
		//for (int i = 0; i < W; i++)
		//	bags[i] = new ArrayList<Integer>();
		
		
		//int r1 = bitStr2Int("0 1 1 0 0 1 1 0 0 1 0 1 1 1 1 1 1 0 1 0 1 1 0 1");
		//int r2 = bitStr2Int("0 1 0 0 0 1 0 0 0 1 0 1 1 1 1 1 1 0 1 0 0 1 0 1");
		
		//System.out.println(dist(r1,r2));
		readInVertices("/Users/leizhang/coursera/stanford/algorithm2/src/class2/biggraph.txt");
		//for (int i = 0; i < buckets.length; i++)
			//System.out.println(buckets[i].size());
		//Set s = hammingSet2(r1);
//		for (int i : hammingSet2(r1))
//			System.out.println(i);
//		System.out.println(s.size());
		
		Union();
		
		System.out.println(uf.clusters());
	}
	
	public static void readInVertices(String file)
	{	
		int index = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String firstline, line;
		    firstline  = br.readLine();
		    String[] tmp = firstline.split(" ");
		    N = Integer.parseInt(tmp[0]);
		    W = Integer.parseInt(tmp[1]);
		    uf = new UnionFind(N);
		    vertexSet = new int[N];
		    map = new HashMap<Integer, Integer>();
		    universe = new HashSet<Integer>();
		    freq = new int[N];
		    identicals = new HashMap<Integer, ArrayList>();
		    
		    Arrays.fill(freq, 1);
		    
		    for (int i = 0; i < N; i++)
		    {
		    		line = br.readLine();
		    		int vertex = bitStr2Int(line);
		    		int bitsOfZero = zeros(line);
		    		
		    		if (!map.containsKey(vertex))
		    			map.put(vertex, index);
		    		else
		    		{
		    			//we found a repeated nodes, which has 
		    			//0 distance to a previous node
		    			int oldIndex = map.get(vertex);
		    			ArrayList<Integer> repeatationList;
		    			if (identicals.containsKey(oldIndex)) {
		    				System.out.println("wow contains more than 2");
		    				repeatationList = identicals.get(oldIndex);
		    			}
		    			else
		    				repeatationList = new ArrayList<Integer>();
		    			repeatationList.add(index);
		    			identicals.put(oldIndex, repeatationList);
		    		}
		    		universe.add(vertex);
		    		vertexSet[i] = vertex;
		    		index++;
		    }
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static int bitStr2Int(String s) {
		String[] tmp = s.split(" ");
		
		int res = 0;
		for (int i = 0; i<W; i++)
		{
			if (tmp[i].equals("0"))
				res &= ~(1<< (W-1-i));
			else
				res |= (1<< (W-1-i));
		}
		return res;
	}
	
	//compute the hamming distance of two ints
	private static int dist(int a, int b)
	{
		int res = 0;
		for (int i = 0; i < W; i++)
		{
			int bitA = (a >> i) & 1;
			int bitB = (b >> i) & 1;
			if ( bitA != bitB)
				res++;
		}
		return res;
	}
	
	private static int zeros(String str)
	{
		String[] tmp = str.trim().split(" ");
		int res = 0;
		for (int i = 0; i < tmp.length; i++)
			if (tmp[i].equals("0")) res++;
		return res;
	}
	
	
	/**
	 * for a given int, returns all the ints that is of 1
	 * hamming distance from a
	 * */
	private static Set<Integer> hammingSet1(int a)
	{
		HashSet<Integer> res  = new HashSet<Integer>();
		
		for (int i = 0; i < W; i++)
			res.add(a ^ (1<<i));
		
		return res;
	}
	
	/**
	 * for a given int, returns all the ints that is of 2 
	 * hamming distance from a
	 * */
	private static Set<Integer> hammingSet2(int a)
	{
		HashSet<Integer> res  = new HashSet<Integer>();
		
		for (int i = 0; i < W; i++)
			for (int j = i+1; j< W; j++)
			res.add(  (a ^ (1<<i))^ (1<<j));
		
		return res;
	}
	
	/**
	 * try to union all vertex pair which are 1 or 2 hop away
	 * */
	private static void Union()
	{
		boolean[] visited = new boolean[N];
		
		System.out.println(uf.clusters() + " " + universe.size());
		//Iterator<Integer> it = universe.iterator();
		
		
		//connect all 0 distance verex
		Set<Integer> set = identicals.keySet();
		for (int i : set)
		{
			ArrayList<Integer> twins = identicals.get(i);
			//System.out.println
			for (int v : twins)
				uf.union(i, v);
		}
		
		System.out.println("after connecting 0 distances, " + uf.clusters());
		
		//iterate all the vertex
		for (int i = 0; i < N; i++)
		{
			int vertex = vertexSet[i];
			
			//int index = map.get(vertex);
			//if (visited[index]) continue;
			
			Set<Integer> neighbors = hammingSet1(vertex);
			for (int neighbor : neighbors)
			{
				//if that neighbor is not an meaningful node, skip
				if (!universe.contains(neighbor)) continue;
				
				//System.out.println("wow find a one hop neightbor");
				int nbindex = map.get(neighbor);
				uf.union(i, nbindex);
				if (identicals.containsKey(nbindex))
				{
					ArrayList<Integer> as = identicals.get(nbindex);
					for (int twin : as)
						uf.union(twin, i);
				}
			}
			
			neighbors = hammingSet2(vertex);
			for (int neighbor : neighbors)
			{
				if (!universe.contains(neighbor)) continue;
				
				//System.out.println("wow find a 2 hop neightbor");
				int nbindex = map.get(neighbor);
				uf.union(i, nbindex);
				if (identicals.containsKey(nbindex))
				{
					ArrayList<Integer> as = identicals.get(nbindex);
					for (int twin : as)
						uf.union(twin, i);
				}
			}
		}
	}
}
