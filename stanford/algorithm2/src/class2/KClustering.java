package class2;

/***
 * See standford algorthm part2 HW2. problem 1
 * */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import class1.Edge;
import class1.EdgeComparator;

public class KClustering
{
	private static ArrayList<Edge> edges;
	private static UnionFind uf;
	private static int N;
	
	// number of clusters we need
	private static final int K = 4;
	
	public static void main(String[] args)
	{
		readInEdges("/Users/leizhang/coursera/stanford/algorithm2/src/class2/cluster1.txt");
		kruskal();
	}
	
	public static void readInEdges(String file)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String firstline, line;
		    firstline  = br.readLine();
		    N = Integer.parseInt(firstline);
		    uf = new UnionFind(N);
		    edges = new ArrayList<Edge>();
		    
		    line = br.readLine();
		    while ( line != null) {
		    		// process the line.
		    		if(line.trim().equals("")) continue;
		    		String[] tmp = line.split(" ");
		    		
		    		int from = Integer.parseInt(tmp[0]);
		    		int to = Integer.parseInt(tmp[1]);
		    		int w = Integer.parseInt(tmp[2]);
		    		edges.add(new Edge(from, to, w));
		    		line = br.readLine();
		    }
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//sort the edges according to its weight
		Collections.sort(edges, new EdgeComparator());
	}
	
	/**
	 * Use kruskal's way to connect points to form culusters. 
	 * */
	private static void kruskal()
	{
		int components = N;
		int index = 0;
		
		
		while (components > K)
		{
			Edge ed = edges.get(index);
			
			int u = ed.vertex();
			int v = ed.other(u);
			
			if (uf.find(u) != uf.find(v)) {
				uf.union(u, v);
				components--;
			} 
			index++;
		}
		
		int res = Integer.MAX_VALUE;
		for (Edge e: edges){
			int u = e.vertex();
			int v = e.other(u);
			
			//u, v belong to same set
			if (uf.find(u) == uf.find(v)) continue;
		
			if (e.weight() < res)
				res = e.weight();
		}
		System.out.println(res);
	}
	
}
