import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DepthFirstOrder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private Digraph G;
	private Iterable<Integer> postOrder;
	private MyBFS bfs;
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph g) {
		this.G = g.reverse().reverse();
		DepthFirstOrder dfs = new DepthFirstOrder(G);
		//System.out.println("size is " + G.V());
		postOrder = dfs.post();
		bfs = new MyBFS(g);
		//for (int w : postOrder)
		//	System.out.println(w + " , ");
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		
		boolean vScanned = false, wScanned = false;
		
		int min = Integer.MAX_VALUE;
		
		for (int u : postOrder) {
			if (!bfs.hasPathTo(v,u) || !bfs.hasPathTo(w,u)) continue;
			int vDist = bfs.distTo(v , u);
			int wDist = bfs.distTo(w , u);
			if ( (vDist + wDist) < min) 
				min = vDist + wDist;
			/*
			if (u == v) vScanned = true;
			if (u == w) wScanned = true;
			if (vScanned && wScanned) break;*/
		}
		
		if (min == Integer.MAX_VALUE) min = -1;
		/*
		if (G.V() == 10 && G.E() == 40 && v ==8 && w == 9 && min == 3) {
			System.out.println (G.toString());
		}*/
		return min;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		
		boolean vScanned = false, wScanned = false;
		
		int min = Integer.MAX_VALUE;
		int res = -1;
		for (int u : postOrder) {
			if (!bfs.hasPathTo(v,u) || !bfs.hasPathTo(w,u)) continue;
			int vDist = bfs.distTo(v , u);
			int wDist = bfs.distTo(w , u);
			if ( (vDist + wDist) < min) {
				min = vDist + wDist;
				res = u;
			}
			/*
			if (u == v) vScanned = true;
			if (u == w) wScanned = true;
			if (vScanned && wScanned) break;*/
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
		
		int vSize = iterateSize(v);
		int wSize = iterateSize(w);
		
		HashSet<Integer> vSet = iterate2Set(v);
		HashSet<Integer> wSet = iterate2Set(w);
		
		int vScanned = 0, wScanned = 0;
		
		
		int min = Integer.MAX_VALUE;
		for (int u : postOrder) {
			
			if (!bfs.hasPathTo(v,u) || !bfs.hasPathTo(w,u)) continue;
			int vDist = bfs.distTo(v , u);
			int wDist = bfs.distTo(w , u);
			
			if ( (vDist + wDist) < min)
				min = vDist + wDist;
			/*
			if (vSet.contains(u)) vScanned++;
			if (wSet.contains(u)) wScanned++;
			if (vSize == vScanned && wSize == wScanned) break;*/
		}
		
		if (min == Integer.MAX_VALUE) min = -1;
		return min;
		
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		
		int vSize = iterateSize(v);
		int wSize = iterateSize(w);
		
		HashSet<Integer> vSet = iterate2Set(v);
		HashSet<Integer> wSet = iterate2Set(w);
		
		int vScanned = 0, wScanned = 0;
		
		int min = Integer.MAX_VALUE;
		int res = -1;
		for (int u : postOrder) {
			
			if (!bfs.hasPathTo(v,u) || !bfs.hasPathTo(w,u)) continue;
			int vDist = bfs.distTo(v , u);
			int wDist = bfs.distTo(w , u);
			
			if ( (vDist + wDist) < min) {
				min = vDist + wDist;
				res = u;
			}
			/*
			if (vSet.contains(u)) vScanned++;
			if (wSet.contains(u)) wScanned++;
			if (vSize == vScanned && wSize == wScanned) break;*/
		}
		
		return res;
	}

	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In("wordnet/random.txt");
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


class MyBFS {

	private Digraph G;
	private HashMap<Integer, int[]> vertexDistToMap;
	private HashMap<Integer, boolean[]> vertexMarkMap;
	private TreeSet<Integer> vertexBFSDone;
	
    /**
     * Computes the shortest path from <tt>s</tt> and every other vertex in graph <tt>G</tt>.
     * @param G the digraph
     * @param s the source vertex
     */
    public MyBFS(Digraph G) {
        this.G = G;
        vertexDistToMap = new HashMap<Integer, int[]>();
        vertexMarkMap = new HashMap<Integer, boolean[]>();
        vertexBFSDone = new TreeSet<Integer>();
    }

    // BFS from single source
    private void bfs(int s) {
        Queue<Integer> q = new Queue<Integer>();
        
        boolean[] marked = getMarkedArray(s);
        int[] distTo = getDistArray(s);
        
        
        
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
        
        vertexDistToMap.put(s, distTo);
        vertexMarkMap.put(s, marked);
        vertexBFSDone.add(s);
    }
    
    private int[] getDistArray(int v) {
    	int[] res;
    	if (!vertexDistToMap.containsKey(v)) {
    		res = new int[G.V()];
    		for (int i=0; i<res.length;i++)
    			res[i] = Integer.MAX_VALUE;
    	}
    	else
    		res = vertexDistToMap.get(v);
    	return res;
    }
    	
    private boolean[] getMarkedArray(int v) {
    	boolean[] res;
    	if (!vertexMarkMap.containsKey(v))
    		res = new boolean[G.V()];
    	else
    		res = vertexMarkMap.get(v);
    	return res;
    
    }

    /**
     * Is there a directed path from the source <tt>s</tt> (or sources) to vertex <tt>v</tt>?
     * @param v the vertex
     * @return <tt>true</tt> if there is a directed path, <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int s, int v) {
    	if (!vertexBFSDone.contains(s))
    		bfs(s);
    	boolean[] marked = getMarkedArray(s);
        return marked[v];
    }
    
    
    public boolean hasPathTo(Iterable<Integer> sources, int v) {
    	for(int s : sources)
	    	if (!vertexBFSDone.contains(s))
	    		bfs(s);
    	for (int s : sources) {
    		boolean[] marked = getMarkedArray(s);
    		if (marked[v]) return true;
    	}
        return false;
    }
    
    
    
    
    public int distTo(Iterable<Integer> sources, int v) {
    	int res = Integer.MAX_VALUE;
    	for(int s : sources)
	    	if (!vertexBFSDone.contains(s))
	    		bfs(s);
    	for (int s : sources) {
    		int[] marked = getDistArray(s);
    		if (marked[v] < res) res = marked[v];
    	}
    	if (res == Integer.MAX_VALUE) res = -1;
    	return res;
    }

    /**
     * Returns the number of edges in a shortest path from the source <tt>s</tt>
     * (or sources) to vertex <tt>v</tt>?
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(int s, int v) {
    	if (!vertexBFSDone.contains(s))
    		bfs(s);
    	int[] distTo = getDistArray(s);
    	if (distTo[v] < 0) return -1;
        return distTo[v];
    }
}

