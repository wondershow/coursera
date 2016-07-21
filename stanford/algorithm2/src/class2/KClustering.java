package class2;

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
	//private static int[] parent, rank;
	//private static int[][] weights;
	private static ArrayList<Edge> edges;
	//private static Edge[] edgeArray();
	//M is edge #, N is node #
	private static int N;
	private static HashSet<String> MST;
	private static UnionFind uf;
	
	public static void main(String[] args)
	{
		readInEdges("/Users/leizhang/coursera/stanford/algorithm2/src/class2/cluster1.txt");
		kruskal();
	}
	
	public static void readInEdges(String file)
	{
		//int N;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String firstline, line;
		    firstline  = br.readLine();
		    N = Integer.parseInt(firstline);
		    uf = new UnionFind(N);
//		    weights = new int[N+1][N+1];
		    //edges = new Edge[N]
//		    weights[0][0] = 1;
		    edges = new ArrayList<Edge>();
		    //Arrays.fill(val, weights); 
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
		
		Collections.sort(edges, new EdgeComparator());
	}
	
	private static void kruskal()
	{
		
		int components = N;
		int index = 0;
		
		int res = Integer.MAX_VALUE;
		while (components > 4)
		{
			Edge ed = edges.get(index);
			System.out.println(ed.weight());
			
			int u = ed.vertex();
			int v = ed.other(u);
			//System.out.println( tmp[0] + " , " + tmp[1] + " : " + heap.firstEntry().getKey() );
			//components--;
			
			if (uf.find(u) != uf.find(v)) {
				uf.union(u, v);
				//System.out.println("union:" + u +  ":" + v + "," + ed.weight());
				components--;
			} 
			//else System.out.println("dictching:"+ u +  ":" + v + "," + ed.weight());
			index++;
		}
		
		
		int maxSpacing = Integer.MIN_VALUE;
		
		HashMap<String, Integer> distances = new HashMap<String, Integer>();
		
		for (Edge e: edges){
			int u = e.vertex();
			int v = e.other(u);
			
			//u, v belong to same set
			if (uf.find(u) == uf.find(v)) continue;
			
			
			String key = uf.find(u) > uf.find(v) ? 
					uf.find(u) + ":" + uf.find(v) : uf.find(v) + ":" + uf.find(u);
		
			if (e.weight() < res)
				res = e.weight();
//			System.out.println("here we are: " +  e.weight());
//			if (!distances.containsKey(key)) {
//				distances.put(key, e.weight());
//				continue;
//			}
//			
//			int oldMin = distances.get(key);
//			if (e.weight() < res)
//				distances.put(key, e.weight());
		}
		
		
//		for (int d : distances.values())
//			if (d > res) res = d;
		
		System.out.println(res);
		//System.out.println()
	}
	
}
