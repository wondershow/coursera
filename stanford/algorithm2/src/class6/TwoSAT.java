package class6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.KosarajuSharirSCC;

public class TwoSAT
{
	private static int E, V = 0;
	private static int[][] clasues;
	private static HashSet<Integer> existing;
	
	private  static final String
	TEST_CASE_PATH = "/Users/leizhang/coursera/stanford/algorithm2/src/class6/2sat6.txt";
	//demo.unsatisfied  
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		readInFiles(TEST_CASE_PATH);
		System.out.println(isSATable());
	}
	
	private static void readInFiles(String file)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String firstline, line;
			firstline  = br.readLine().trim();
			E = Integer.parseInt(firstline);
			clasues = new int[E][2];
			existing = new HashSet<Integer>();
			int i = 0;
			while ( (line = br.readLine()) != null)
			{
				String[] tmp = line.split(" ");
				clasues[i][0] = Integer.parseInt(tmp[0]);
				clasues[i][1] = Integer.parseInt(tmp[1]);
				if (Math.abs(clasues[i][0]) > V) V = Math.abs(clasues[i][0]);
				if (Math.abs(clasues[i][1]) > V) V = Math.abs(clasues[i][1]);
				existing.add(clasues[i][0]);
				existing.add(clasues[i][1]);
				i++;
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static boolean isSATable() {
		Digraph g = new Digraph(V * 2 + 2);
		System.out.println("V = " + V + ", E = " + E);
		for (int i = 0; i < clasues.length; i++)
		{
			int[] clause = clasues[i];
			g.addEdge(-clause[0] + V, clause[1] + V);
			g.addEdge(-clause[1] + V, clause[0] + V);
		}
		//g.printGraph();
		KosarajuSharirSCC scc = new KosarajuSharirSCC(g);
		
		for (int i = 0; i <= V ; i++) {
			if (existing.contains(i)) {
				//System.out.println("here" + i);
				if (scc.stronglyConnected(i + V, -i + V))
					return false;
			}
		}
		
		return true;
	}
	
	
	static class Graph {
		int N; // number of vertex
		private Set<Integer>[] Edges;
		
		Graph(int n) {
			N = n;
			Edges = new HashSet[N];
			for (int i = 0; i < N; i++)
				Edges[i] = new HashSet<Integer>();
			//System.out.println("N = " + N);
		}
		
		public void addEdge(int from, int to) {
			//System.out.println("adding edge : from = " + from + ", to = " + to);
			Edges[from].add(to);
		}
		
		public Graph reverse() {
			Graph res = new Graph(N);
			for (int i = 0; i < N; i++)
				for (int v : Edges[i])
					res.addEdge(v, i);
			return res;
		}
		
		public int V() {
			return N;
		}
		
		public Iterable<Integer> adj(int n) {
			return Edges[n];
		}
		
		private void printGraph() {
			for (int i = 0; i < Edges.length; i++)
			{
				for (int k : Edges[i])
					System.out.print( i + "-> " + k + ";");
				System.out.println();
			}
			
		}
	}
	
	static class DFSOrder {
		private boolean[] marked;
		private Stack<Integer> toplogical;
		
		public DFSOrder(Graph g) {
			marked = new boolean[g.V()];
			toplogical = new Stack<Integer>();
			
			for (int i = 0; i < g.V(); i++) {
				if (!marked[i]) dfs(g, i);
			}
		}
		
		private void dfs(Graph g, int v){
			marked[v] = true;
			for (int u : g.adj(v))
				if (!marked[u]) dfs(g, u);
			toplogical.push(v);
		}
		
		public Iterable<Integer> topological() {
//			String res = "";
//			for (int s : toplogical)
//				res += " , " + s;
//			System.out.println(res);
			return toplogical;
		}
	}
	
	static class SCC {
		private boolean[] marked;
		private int[] id;
		private int count;
		
		public SCC(Graph g) {
			marked = new boolean[g.V()];
			id = new int[g.V()];
			Graph h = g.reverse();
			DFSOrder order = new DFSOrder(h);
			//h.printGraph();
			for (int s : order.topological())
				if (!marked[s])
				{ dfs(g, s); count++;}
			
//			for (int i = 0; i < id.length; i++)
//			{
//				System.out.println(i + ":" + id[i]);
//			}
		}
		
		private void dfs(Graph g, int v) {
			marked[v] = true;
			id[v] = count;
			for (int w : g.adj(v))
				if (!marked[w]) dfs(g, w);
		}
		
		public boolean strongConnected(int w, int v) {
			//System.out.println("w = " + w + ", v = " + v);
			return id[w] == id[v];
		}
	}
}
