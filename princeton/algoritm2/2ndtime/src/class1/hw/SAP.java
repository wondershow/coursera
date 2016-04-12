package class1.hw;


import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class SAP {
	private Digraph G;
	//private BiStructure queryCache;
	private String hashCache;
	private anceterDistance lastAnswer;
	
	private HashSet<Integer> dirtyBits;
	private int[] distanceToU;
	private int[] distanceToV;
	private boolean[] marked;
	private static final int INFINITY = Integer.MAX_VALUE;
	
	
	
	
	
	//private BFS4SAP sapBfs;
	private class anceterDistance{
		//public final int u,v;
		public final int distance;
		public final int ancestor;
		
		public anceterDistance(int d, int a) {
			//this.u = u;
			//this.v = v;
			this.distance = d;
			this.ancestor = a;
		}
	}
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph g) {
		checkNullParam(g);
		G = new Digraph(g);
		distanceToU = new int[G.V()];
		distanceToV = new int[G.V()];
		marked = new boolean[G.V()];
		
		Arrays.fill(distanceToU, INFINITY);
		Arrays.fill(distanceToV, INFINITY);
		
		dirtyBits = new HashSet<Integer>();
		hashCache = "";
	}
	
	private void reset() {
		Iterator<Integer> it = dirtyBits.iterator();
		while (it.hasNext()) {
			int w = it.next();
			distanceToU[w] = INFINITY;
			distanceToV[w] = INFINITY;
			marked[w] = false;
		}
		dirtyBits.clear();
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		checkNullParam(v);
		checkNullParam(w);
		checkVertex(v);
		checkVertex(w);
		
		//String hash = v > w ? v + "," + w : w + "," + v;
		
		
		
		
		lastAnswer = this.findSAP(Arrays.asList(v), Arrays.asList(w));
		
		return lastAnswer.distance;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		checkNullParam(v);
		checkNullParam(w);
		checkVertex(v);
		checkVertex(w);
		
		
		String hash = v > w ? v + "," + w : w + "," + v;
		
		lastAnswer = this.findSAP(Arrays.asList(v), Arrays.asList(w));
		
		hashCache = hash;
		return lastAnswer.ancestor;
	}
	
	
	

	   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	   public int length(Iterable<Integer> v, Iterable<Integer> w) {
		   checkNullParam(v);
		   checkNullParam(w);
		   for (int p : v) checkVertex(p);
		   for (int p : w) checkVertex(p);
		   
		   lastAnswer = this.findSAP(v, w);
		   return lastAnswer.distance;
	   }

	   // a common ancestor that participates in shortest ancestral path; -1 if no such path
	   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		   checkNullParam(v);
		   checkNullParam(w);
		   for (int p : v) checkVertex(p);
		   for (int p : w) checkVertex(p);
		   lastAnswer = this.findSAP(v, w);
		   return lastAnswer.ancestor;
	   }

	   // do unit testing of this class
	   public static void main(String[] args) {
		   Digraph p = new Digraph(new In("/Users/leizhang/Documents/workspace/Princeton/src/wordnet/digraph1.txt"));
		   SAP sap = new SAP(p);
		   
		   //for (int run = 0; run < 100; run++) {
			   //Random rn = new Random();
			   System.out.println(sap.length(1,2));
			   
			   System.out.println(sap.ancestor(2, 7));
		   //}
	   }
		
	   
	   /*
		private String hashOfVertices(Iterable<Integer> a, Iterable<Integer> b) {
			
			String res = "";
			
			TreeSet<Integer> ts = new TreeSet<Integer>();
			
			
			Iterator<Integer> it = a.iterator();
			while (it.hasNext()) { ts.add(it.next());}
			
			Integer[] arr = ts.toArray(new Integer[ts.size()]);
			
			
			for (int i = 0; i < arr.length; i++)
				res += arr[i] + ",";
			res += "+";
			ts = new TreeSet<Integer>();
			it = b.iterator();
			while (it.hasNext()) {  ts.add(it.next());}
			arr = (Integer[]) ts.toArray(new Integer[ts.size()]);
			for (int i = 0; i < arr.length; i++)
				res += arr[i] + ",";
			return res;
		} */
	   
	   private void checkNullParam(Object o) {
			if (o == null) throw new java.lang.NullPointerException();
	   }
	   
	   private void checkVertex(int v) {
		   if (v < 0 || v >= G.V())
			   throw new java.lang.IndexOutOfBoundsException();
	   }
	   
	   private anceterDistance findSAP(Iterable<Integer> u, Iterable<Integer> v) {
		   reset();
		   Queue<Integer> que = new LinkedList<Integer>();
		   //Iterator<Integer> it = u.iterator();
		   for (int w: u) {
			   distanceToU[w] = 0;
			   marked[w] = true;
			   que.add(w);
			   dirtyBits.add(w);
		   }
		  
		   for (int w: v) {
			  distanceToV[w] = 0;
			  marked[w] = true;
			  que.add(w);
			  dirtyBits.add(w);
		  }
		  
		  int min = INFINITY;
		  int ancestor = -1; 
		  
		  //check if u and v has mutual elements, return it as the correct ancestor
		  HashSet<Integer> h1 = new HashSet<Integer>();
		  for (int w: u) {
			  h1.add(w);
		  }
		  for (int w:v) {
			  if (h1.contains(w))
				  return new anceterDistance(0, w);
		  }
		  
		  while (!que.isEmpty()) {
			  int x = que.remove();
			  if (distanceToU[x] > min && distanceToV[x] > min) continue;
			  for (int w: G.adj(x)) {
				  //optimization 2
				  //if ((distanceToU[x] != INFINITY) && distanceToU[x] > min) continue;
				  //if ((distanceToV[x] != INFINITY) && distanceToV[x] > min) continue;
				  
				  if (!marked[w]) {
					  if (distanceToU[x] != INFINITY)  distanceToU[w] = distanceToU[x] + 1;
					  if (distanceToV[x] != INFINITY)  distanceToV[w] = distanceToV[x] + 1;
					  marked[w] = true;
					  dirtyBits.add(w);
					  que.add(w);
				  }
 
				  if (distanceToU[x] != INFINITY && distanceToU[w] == INFINITY) {
					  distanceToU[w] = distanceToU[x] + 1;
					  if (distanceToU[w] + distanceToV[w] < min) {
						  min = distanceToU[w] + distanceToV[w];
						  ancestor = w;
					  }
				  }
				  if  ( distanceToV[x] != INFINITY && distanceToV[w] == INFINITY)  { 
					  distanceToV[w] = distanceToV[x] + 1;
					  //we find a mutual ancestor
					  if (distanceToU[w] + distanceToV[w] < min) {
						  min = distanceToU[w] + distanceToV[w];
						  ancestor = w;
					  }
				  }
			  }
		  }
		  
		  if (min == INFINITY) {min = -1; ancestor = -1;}
		  return new anceterDistance(min, ancestor);
	   }
}