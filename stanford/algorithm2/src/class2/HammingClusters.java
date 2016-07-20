package class2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HammingClusters
{
	private static int[] vertice;
	
	//N is number of vertex, W is the how many bit to represent a vertex.
	private static int N, W;
	
	public static void main(String[] args)
	{
		//readInVertices("/Users/leizhang/coursera/stanford/algorithm2/src/class2/biggraph.txt");
		//kruskal();
		W = 24;
		int r1 = bitStr2Int("0 1 1 0 0 1 1 0 0 1 0 1 1 1 1 1 1 0 1 0 1 1 0 1");
		int r2 = bitStr2Int("0 1 0 0 0 1 0 0 0 1 0 1 1 1 1 1 1 0 1 0 0 1 0 1");
		
		System.out.println(dist(r1,r2));
	}
	
	public static void readInVertices(String file)
	{	
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String firstline, line;
		    firstline  = br.readLine();
		    String[] tmp = firstline.split(" ");
		    N = Integer.parseInt(tmp[0]);
		    W = Integer.parseInt(tmp[1]);
		    vertice = new int[N+1];
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
		for (int i = 0; i>=0; i--)
		{
			if (tmp[i].equals("0"))
				res &= ~(1<<i);
			else
				res |= (1<<i);
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
			if ( bitA == bitB)
				res++;
		}
		return res;
	}
}
