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

/***
 * See standford algorthm part2 HW2. problem 1
 * */
public class HammingClusters
{
	//N is number of vertex in the "graph", W is # of bits that describe a bit
	private static int N, W;
	private static UnionFind uf;
	//node 0 to N-1 stores here, each ele is the W-bit representation of that vertex
	private static int[] vertexes;
	//set of all possible values of vertex's W-bit representation
	private static HashSet<Integer> bitDecSet;
	//mapping between a vertex's number(index from 0 to N-1) and its W-bit decimal value
	private static HashMap<Integer, Integer> map;
	//stores all nodes who have same W-bit as vertex from a specific index(0 to N-1)
	private static HashMap<Integer, ArrayList> identicals;
	
	public static void main(String[] args)
	{
		readInVertices("/Users/leizhang/coursera/stanford/algorithm2/src/class2/biggraph.txt");
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
		    vertexes = new int[N];
		    map = new HashMap<Integer, Integer>();
		    bitDecSet = new HashSet<Integer>();
		    identicals = new HashMap<Integer, ArrayList>();
		    
		    for (int i = 0; i < N; i++)
		    {
		    		line = br.readLine();
		    		int wbitValue = bitStr2Int(line);
		    		
		    		if (!map.containsKey(wbitValue))
		    			map.put(wbitValue, index);
		    		else
		    		{
		    			//we found a repeated W-bit, which has 
		    			//0 distance to a previous node's W-bit
		    			int oldIndex = map.get(wbitValue);
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
		    		bitDecSet.add(wbitValue);
		    		vertexes[i] = wbitValue;
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
		
		System.out.println(uf.clusters() + " " + bitDecSet.size());
		
		
		//connect all 0 distance verex
		Set<Integer> set = identicals.keySet();
		for (int i : set)
		{
			ArrayList<Integer> twins = identicals.get(i);
			for (int v : twins)
				uf.union(i, v);
		}
		
		//System.out.println("after connecting 0 distances, " + uf.clusters());
		
		for (int i = 0; i < N; i++)
		{
			int vertex = vertexes[i];
			
			Set<Integer> neighbors = hammingSet1(vertex);
			for (int neighbor : neighbors)
			{
				//if that neighbor is not an meaningful node, skip
				if (!bitDecSet.contains(neighbor)) continue;
				
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
				if (!bitDecSet.contains(neighbor)) continue;
				
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
