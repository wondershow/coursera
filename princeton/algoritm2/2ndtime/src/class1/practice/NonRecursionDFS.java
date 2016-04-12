package class1.practice;
import java.util.Iterator;
import java.util.Stack;

import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

public class NonRecursionDFS {
	
	private boolean[] marked;
	
	
	public NonRecursionDFS(Graph G, int s) {
		marked = new boolean[G.V()];
		System.out.println();
		nonRecurDFS(G,s);
		System.out.println();
	}
	
	private void nonRecurDFS(Graph G, int s) {
		Stack<Integer> stack = new Stack<Integer>();
		Iterator<Integer>[] its = (Iterator<Integer>[]) new Iterator[G.V()];
		
		for (int i = 0; i < its.length; i++)
			its[i] = G.adj(i).iterator();
		
		stack.push(s);
		
		marked[s] = true;
		
		while(!stack.empty()) {
			int cur = stack.peek();
			
			if (its[cur].hasNext()){ 
				int w = its[cur].next();
				if (!marked[w]) {
					System.out.print(w + " ");
					marked[w] = true; 
					stack.push(w); 
				}
			} else {
				stack.pop(); 
			}
		}
	}
	
	public static void main(String[] args) {
		Graph p = new Graph(new In("/Users/leizhang/Documents/workspace/Princeton/src/ch4/TinyCG.txt"));
		
		DepthFirstPaths dfo = new DepthFirstPaths(p, 0);
		
		p = new Graph(new In("/Users/leizhang/Documents/workspace/Princeton/src/ch4/TinyCG.txt"));
		NonRecursionDFS s = new NonRecursionDFS(p,0);
		
		NonrecursiveDFS m = new NonrecursiveDFS(p,0);
		
	}
}
