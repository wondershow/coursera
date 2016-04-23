package ch5;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.Stack;

public class NFA
{
	private char[] re;
	private Digraph G;
	private int M;
	
	public NFA(String regexp) 
	{
		re = regexp.toCharArray();
		M = re.length;
		G = new Digraph(M + 1);
		Stack<Integer> ops = new Stack<Integer>();
		
		for (int i = 0; i <M; i++)
		{
			int lp = i;
			
			if (re[i] == '(' || re[i] == '|')
				ops.push(i);
			if (re[i] == ')') 
			{
				int or = ops.pop();
				if (re[or] == '|')
				{
					lp = ops.pop();
					G.addEdge(lp, or + 1);
					G.addEdge(or, i);
				}
				else lp = or;
			}
			if (i < M - 1 && re[i+1] == '*')
			{
				G.addEdge(lp, i+1);
				G.addEdge(i+1, lp);
			}
			if (re[i] == '(' || re[i] == '*' || re[i] == ')'  )
				G.addEdge(i, i+1);
			
		}
	}
	
	
	public boolean recognize(String str) 
	{
		Bag<Integer> pc = new Bag<Integer>();
		DirectedDFS dfs = new DirectedDFS(G, 0);
		
		for (int i = 0; i < re.length; i++)
			if (dfs.marked(i)) pc.add(i);
		
		for (int i = 1; i < re.length; i++)
		{
			Bag<Integer> states = new Bag<Integer>();
			for (int v : pc)
				if (v < M)
					if (re[v] == str.charAt(i) || re[v] =='.')
						states.add(v + 1);
			pc = new Bag<Integer>();
			dfs = new DirectedDFS(G, states);
			for (int j = 0; j < re.length; j++)
				if (dfs.marked(j)) pc.add(i);
		}
		
		for (int i : pc)
			if (i == M) return true;
		return false;
	}
}
