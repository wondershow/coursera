package class1.practice;


import java.util.Iterator;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;


/***
 * To implement the DFSorder without recursion
 * Instead using explicit stacks to keep track of dfs calls.
 * */
public class NonRecursionDFSOrder {
	private Digraph G;
	private int W;
	private boolean[] marked;
	private Iterator<Integer>[] stateStack;
	private Queue<Integer> preQue,postQue;
	private Stack<Integer> postStack;
	
	
	public static void main(String[] args) {

	}
	
	public NonRecursionDFSOrder(Digraph g) {
		G = new Digraph(g);
		marked = new boolean[G.V()];
		stateStack = (Iterator<Integer> [])new Iterator[G.V()];
		for (int i = 0; i < G.V(); i++) {
			stateStack[i] = G.adj(i).iterator();
		}
		
		preQue = new Queue<Integer>();
		postQue = new Queue<Integer>();
		postStack = new Stack<Integer>();
		
		for (int i = 0; i < G.V(); i++) 
			if(!marked[i]) dfs(i);
	}
	
	
	private void dfs(int v) {
		Stack<Integer> tmpQue = new Stack<Integer>();
		tmpQue.push(v);
		marked[v] = true;
		preQue.enqueue(v);
		
		while (!tmpQue.isEmpty()) {
			int cur = tmpQue.peek();
			Iterator<Integer> it = stateStack[cur];
			if (it.hasNext()) {
				int neighbor = it.next();
				if (!marked[neighbor]) { // first visit
					marked[neighbor] = true;
					tmpQue.push(neighbor);
					preQue.enqueue(neighbor);
					//break; // handle one neignbor at one time
				}
			} else { // done with this vertex
				tmpQue.pop();
				postQue.enqueue(cur);
				postStack.push(cur);
			}
		}
	}
	
	
	public Iterable<Integer> preOrder() {
		return preQue;
	}
	
	public Iterable<Integer> postOrder() {
		return postQue;
	}

	public Iterable<Integer> reversPostOrder() {
		return postStack;
	}
}
