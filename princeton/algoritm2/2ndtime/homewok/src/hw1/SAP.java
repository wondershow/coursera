import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DepthFirstOrder;

import java.util.HashSet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private Digraph G;
	private Iterable<Integer> postOrder;
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph g) {
		this.G = g.reverse().reverse();
		DepthFirstOrder dfs = new DepthFirstOrder(G);
		postOrder = dfs.post();
		//for (int w : postOrder)
			//System.out.println(w + " ");
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(G, w);
		
		boolean vScanned = false, wScanned = false;
		
		int min = Integer.MAX_VALUE;
		
		for (int u : postOrder) {
			if (!vPaths.hasPathTo(u) || !wPaths.hasPathTo(u)) continue;
			int vDist = vPaths.distTo(u);
			int wDist = wPaths.distTo(u);
			if ( (vDist + wDist) < min) 
				min = vDist + wDist;
			if (u == v) vScanned = true;
			if (u == w) wScanned = true;
			if (vScanned && wScanned) break;
		}
		
		if (min == Integer.MAX_VALUE) min = -1;
		return min;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(G, w);
		
		boolean vScanned = false, wScanned = false;
		
		int min = Integer.MAX_VALUE;
		int res = -1;
		for (int u : postOrder) {
			if (!vPaths.hasPathTo(u) || !wPaths.hasPathTo(u)) continue;
			int vDist = vPaths.distTo(u);
			int wDist = wPaths.distTo(u);
			if ( (vDist + wDist) < min) {
				min = vDist + wDist;
				res = u;
			}
			if (u == v) vScanned = true;
			if (u == w) wScanned = true;
			if (vScanned && wScanned) break;
		}
		return res;
	}

	
	private int iterateSize(Iterable<Integer> i) {
		int res = 0;
		for (int w : i)
			res++;
		return res;
	}
	
	private HashSet<Integer> iterate2Set(Iterable<Integer> i) {
		HashSet<Integer> res = new HashSet<Integer>();
		for (int w : i)
			res.add(w);
		return res;
	}
	
	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(G, w);
		
		int vSize = iterateSize(v);
		int wSize = iterateSize(w);
		
		HashSet<Integer> vSet = iterate2Set(v);
		HashSet<Integer> wSet = iterate2Set(w);
		
		int vScanned = 0, wScanned = 0;
		
		
		int min = Integer.MAX_VALUE;
		for (int u : postOrder) {
			
			if (!vPaths.hasPathTo(u) || !wPaths.hasPathTo(u)) continue;
			
			int vDist = vPaths.distTo(u);
			int wDist = wPaths.distTo(u);
			
			if ( (vDist + wDist) < min)
				min = vDist + wDist;
			
			if (vSet.contains(u)) vScanned++;
			if (wSet.contains(u)) wScanned++;
			if (vSize == vScanned && wSize == wScanned) break;
		}
		
		if (min == Integer.MAX_VALUE) min = -1;
		return min;
		
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(G, w);
		
		int vSize = iterateSize(v);
		int wSize = iterateSize(w);
		
		HashSet<Integer> vSet = iterate2Set(v);
		HashSet<Integer> wSet = iterate2Set(w);
		
		int vScanned = 0, wScanned = 0;
		
		int min = Integer.MAX_VALUE;
		int res = -1;
		for (int u : postOrder) {
			
			if (!vPaths.hasPathTo(u) || !wPaths.hasPathTo(u)) continue;
			
			int vDist = vPaths.distTo(u);
			int wDist = wPaths.distTo(u);
			
			if ( (vDist + wDist) < min) {
				min = vDist + wDist;
				res = u;
			}
			
			if (vSet.contains(u)) vScanned++;
			if (wSet.contains(u)) wScanned++;
			if (vSize == vScanned && wSize == wScanned) break;
		}
		
		return res;
	}

	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    //System.out.println("111");
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
	}
}

