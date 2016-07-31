package class4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class JohnsonAPSS
{
	private static int M,N;
	private static ArrayList<Edge>[] edgesOut, edgesIn;
	private static int[][] D;
	private  static final String
	TEST_CASE_PATH = "/Users/leizhang/coursera/stanford/algorithm2/src/class4/g3.txt";
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		readInFiles(TEST_CASE_PATH);
		johnson();
	}
	
	private static void readInFiles(String file)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String firstline, line;
			firstline  = br.readLine();
			String[] tmp = firstline.split(" ");
			N = Integer.parseInt(tmp[0]);
			M = Integer.parseInt(tmp[1]);
			D = new int[N+1][N+1];
			edgesOut = new ArrayList[N+1];
			edgesIn = new ArrayList[N+1];
			for (int i = 1; i <= N; i++)
			{
				edgesOut[i] = new ArrayList<Edge>();
				edgesIn[i] = new ArrayList<Edge>();
			}
			
			//int lineNumber = 1;
			while ( (line = br.readLine()) != null)
			{
				tmp = line.split(" ");
				int source = Integer.parseInt(tmp[0]);
				int destination = Integer.parseInt(tmp[1]);
				int weight = Integer.parseInt(tmp[2]);
				Edge e = new Edge(source, destination, weight);
				edgesIn[destination].add(e);
				edgesOut[source].add(e);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void johnson() {
		//add a new vertex with number 0
		ArrayList<Edge> list = new ArrayList<Edge>();
		for (int i = 1; i <= N; i++) {
			Edge e = new Edge(0,i,0);
			list.add(e);
			edgesIn[i].add(e);
		}
		edgesOut[0] = list;
		edgesIn[0] = new ArrayList<Edge>();
		
		int[] P = belmanford();
		
		
		if (P == null)
		{
			//negative cycle detected
			System.out.println("NULL");
			return;
		}
		
		for (int i = 1; i <= N; i++)
			for (Edge e : edgesIn[i])
			{
				//offSet[e.from][e.to] = ;
				e.reweigh(P[e.from] - P[e.to]);
			}
		
		
		for (int i = 1; i <= N; i++)
			dijsktra(i);
		
		int min = Integer.MAX_VALUE;
		for (int i = 1; i <= N; i++)
			for (int j = 1; j <= N; j++)
			{
				int realDistance = D[i][j] - P[i] + P[j]; 
				if (min > realDistance) min = realDistance;
			}
		
		System.out.println(min); 
	}
	
	private static void dijsktra(int s) {
		
		for (int i = 1; i<=N; i++)
			D[s][i] = Integer.MAX_VALUE;

		
		D[s][s] = 0;
		boolean[] visited = new boolean[N+1];
		Arrays.fill(visited, false);
		IndexMinPQ<Integer> heap = new IndexMinPQ<Integer>(N+1);
		
		visited[s] = true;
		heap.insert(s, D[s][s]);
		
		while (!heap.isEmpty())
		{
			int v = heap.delMin();
			for (Edge e : edgesOut[v])
			{
				int u = e.to;
				if (D[s][u] > D[s][v] + e.johnsonWeight())
				{
					D[s][u] = D[s][v] + e.johnsonWeight();
//					if (e.johnsonWeight()<0)
//						System.out.println(e.johnsonWeight());
					if (heap.contains(u))
						heap.decreaseKey(u, D[s][u]);
					else
						heap.insert(u, D[s][u]);
				}
			}
			//System.out.println(heap.size());
		}
	}
	
	private static int[] belmanford() {
		
		int[][] A = new int[N+1][N+1];
		A[0][0] = 0;
		int[] res = new int[N + 1];
		for (int i = 1; i <= N; i++)
			A[0][i] = Integer.MAX_VALUE;
		
		for (int i = 1; i<= N; i++)
			for (int v = 0; v<=N; v++)
		{
			A[i][v] = A[i-1][v];
			for (Edge e: edgesIn[v])
			{
				int w = e.weight();
				int u = e.from;
				
				//skip this edge if u is not reachable from source
				if (A[i-1][u] == Integer.MAX_VALUE)
					continue;
				if (A[i][v] > A[i-1][u] + w)
					A[i][v] = A[i-1][u] + w;
			}
		}
		
		//run Bellman ford one more time to check
		//if there is a negative cycle
		for (int v = 0; v<=N; v++)
		{
			int minLen = A[N][v];
			for (Edge e: edgesIn[v])
			{
				int w = e.johnsonWeight();
				int u = e.from;
				if (minLen > A[N][u] + w)
					throw new NullPointerException("There is negative cycle");
			}
		}
		
		for (int i = 1; i <= N; i++)
		{
			res[i] = A[N][i];
//			if (res[i] < 0)
//				throw new NullPointerException("asdfasdf");
		}
		return res;
	}
}
