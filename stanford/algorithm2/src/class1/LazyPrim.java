package class1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class LazyPrim
{
	private static boolean marked[];
	private static ArrayList<Edge> MST;
	private static PriorityQueue<Edge> heap;
	private static LinkedList<Edge> edges[];
	
	private static LinkedList<Edge>[] readInGraph(String file) {
		LinkedList<Edge>[] res = null;
		int N, E;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String firstline, line, tmpArr[];
		    firstline  = br.readLine();
		    tmpArr = firstline.split(" ");
		    N = Integer.parseInt(tmpArr[0]);
		    E = Integer.parseInt(tmpArr[1]);
		    res = new LinkedList[N+1];
		    
		    for (int i = 1; i <= N; i++)
		    		res[i] = new LinkedList<Edge>();
		    //Arrays.fill(res, new LinkedList<Edge>());
		    
		    //int len = 0;
		    //weights = new int[N];
		    //lengths = new int[N];
		    while ((line = br.readLine()) != null) {
		    		// process the line.
		    		if(line.trim().equals("")) continue;
		    		String[] tmp = line.split(" ");
		    		//weights[len] = Integer.parseInt(tmp[0]);
		    		//lengths[len++] = Integer.parseInt(tmp[1]);
		    		int u = Integer.parseInt(tmp[0]);
		    		int v = Integer.parseInt(tmp[1]);
		    		int weight = Integer.parseInt(tmp[2]);
		    		res[u].add(new Edge(u, v, weight));
		    		res[v].add(new Edge(v, u, weight));
		    }
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return res;
	}
	
	private static void initialize(int N) {
		marked = new boolean[N+1];
		MST = new ArrayList<Edge>();
		heap = new PriorityQueue<Edge>(new EdgeComparator());
	}
	
	public static void main(String[] args)
	{
		edges =  readInGraph("/Users/leizhang/coursera/stanford/algorithm2/src/class1/graph.txt");
		int N = edges.length - 1;
		initialize(N);
		
		visit(1);
		
		while (heap.size() != 0)
		{
			Edge e = heap.poll();
			int u = e.vertex();
			int v = e.other(u);
			if (marked[u] && marked[v]) continue;
			MST.add(e);
			if (!marked[u]) visit(u);
			if (!marked[v]) visit(v);
		}
		
		int res = 0;
		
		for (Edge e: MST)
			res += e.weight();
		System.out.println(res);//return res;
	}
	
	
	private static void visit(int v)
	{
		marked[v] = true;
		for (Edge e : edges[v])
		{
			int u = e.other(v);
			if (!marked[u])  heap.add(e);
		}
	}
	
	private static class EdgeComparator implements Comparator<Edge> {
		public int compare(Edge o1, Edge o2)
		{
			if (o1.weight() < o2.weight())
				return -1;
			else if (o1.weight() > o2.weight())
				return 1;
			return 0;
		}
	}
}
