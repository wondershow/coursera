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
	private static int[] parent, rank;
	//private static int[][] weights;
	private static ArrayList<Edge> edges;
	//private static Edge[] edgeArray();
	//M is edge #, N is node #
	private static int N;
	private static HashSet<String> MST;
	
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
		//int n = N;
		parent = new int[N+1];
		rank = new int[N+1];
		
		for (int i = 1; i <= N; i++)
		{
			parent[i] = i;
			rank[i] = 0;
		}
		
		
		
		int components = N;
		int index = 0;
		while (components > 4)
		{
			Edge ed = edges.get(index);
			System.out.println(ed.weight());
			
			int u = ed.vertex();
			int v = ed.other(u);
			//System.out.println( tmp[0] + " , " + tmp[1] + " : " + heap.firstEntry().getKey() );
			//components--;
			if (u == 384 || u == 414 || u == 462 || v == 384 || v == 414 || v == 462)
			{
				//if (ed.weight() < 1641)
					System.out.println("weight = " + ed.weight());
			}
			if (find(u) != find(v)) {
				union(u, v);
				
				//System.out.println("union:" + u +  ":" + v + "," + ed.weight());
				components--;
			} 
			//else System.out.println("dictching:"+ u +  ":" + v + "," + ed.weight());
			index++;
		}
		
		//for (int i = 1; i<=N; i++)
		for (int j = 1; j <=N; j++)
		{
			//int edgeNumber = xyToEdgeNumber(i,j);
			int root = find(j);
			//if (root != 296)
			//System.out.println( "(" + j + " ) : " +  root );
		}
		
		int maxSpacing = Integer.MIN_VALUE;
		
		HashMap<String, Integer> distances = new HashMap<String, Integer>();
		
		
		for (Edge e: edges){
			int u = e.vertex();
			int v = e.other(u);
			
			//u, v belong to same set
			if (find(u) == find(v)) continue;
			
			
			String key = find(u) > find(v) ? 
						 find(u) + ":" + find(v) : find(v) + ":" + find(u);
			if (!distances.containsKey(key)) {
				distances.put(key, e.weight());
				continue;
			}
			int oldMin = distances.get(key);
			if (e.weight() < oldMin)
				distances.put(key, e.weight());
		}
		
		int res = Integer.MIN_VALUE;
		for (int d : distances.values())
			if (d > res) res = d;
		
		System.out.println(res);
		//System.out.println()
	}
	
	//convert 2 dimension to linear number
	private static int xyToEdgeNumber(int x, int y)
	{
		return (x-1)*N + (y-1);
	}
	
	private static int[] edgeNumber2XY(int n)
	{
		int[] res = new int[2];
		res[1] = (n + 1) % N;
		res[0] = n / N + 1;
		return res;
	}
	
	private static int find(int node)
	{
		while (node != parent[node])
			node = parent[node];
		return node;
	}
	
	private static void union(int i, int j)
	{
		int root1 = find(i), root2 = find(j);
		int change, root;
		
		if (rank[root1] <= rank[root2]) {
			change = i; // we update i and its subtree
			parent[root1] = root2;
			root = root2;
			if (rank[root1] == rank[root2])
				rank[root2]++;
		} else {
			change = j;
			parent[root2] = root1;
			rank[root1]++;
			root = root1;
		}
		while (change != root) 
		{
			int p = parent[change];
			parent[change] = root;
			change = p;
		}
	}
}
