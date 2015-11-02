package hw1;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Queue;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import edu.princeton.cs.algs4.Bag;

/**
 * WordNet implemented for coursera hw1
 * http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html
 * **/
public class WordNet {
		//mapping from a noun to its id in the diagram
		private TreeMap<String, Integer> noun2IdMap;
		
		//mapping from an id in digraph to the set of nouns it represents in the digraph
		private TreeMap<Integer, Bag> id2SynetMap;
		private Digraph graph;
		
		//most distant ancestor of a node
		private int[] roots;
		
		//# of vertices in this digraph
		private final int size;
		
		// constructor takes the name of the two input files
		public WordNet(String synsets, String hypernyms) {
			nullChecker(synsets);
			nullChecker(hypernyms);
			noun2IdMap = new TreeMap<String, Integer>();
			id2SynetMap = new TreeMap<Integer, Bag>();
			//graph = new Digraph();
			constructSynsets(synsets);
			size = id2SynetMap.size();
			roots = new int[size];
			for (int i=0; i<roots.length; i++) 
				roots[i] = i;
			graph = new Digraph(size);
			constructDigraph(hypernyms);
			if (!isRootedDAG()) throw new java.lang.IllegalArgumentException();
		}
		
		//read a file and populate noun2IdMap and id2SynetMap
		private void constructSynsets(String synsets) {
			try {
				In in =  new In(synsets);
				while (!in.isEmpty()) {
					String s = in.readLine();
					String[] fields = s.split(",");
					int synId = Integer.parseInt(fields[0]);
					String[] nouns = fields[1].split(" ");
					Bag<String> bagOfNouns = new Bag<String>();
					for (int i = 0; i<nouns.length; i++) {
						bagOfNouns.add(nouns[i]);
						noun2IdMap.put(nouns[i], synId);
					}
					id2SynetMap.put(synId, bagOfNouns);
				}			
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//read a file and add edges to digraph
		private void constructDigraph(String hypernyms) {
			try {
				In in =  new In(hypernyms);
				while (!in.isEmpty()) {
					String s = in.readLine();
					String[] fields = s.split(",");
					int fromNode = Integer.parseInt(fields[0]);
					for (int i = 1; i < fields.length; i++) {
						graph.addEdge(fromNode, Integer.parseInt(fields[i]));
						//int parentid = roots[Integer.parseInt(fields[i])];					
						roots[Integer.parseInt(fields[i])] = fromNode;
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		//check if our contructed digraph is a rootedDAG
		private boolean isRootedDAG() {
			//set up root of each node
			for(int i = 0; i < roots.length; i++) {
				roots[i] = root(i);
			}
			
			int realroot = roots[0];
			for (int i = 0; i< roots.length; i++)
				if (roots[i] != realroot) return false;
			return true;
		}
		
		//get the root of  a vertex in a DAG
		private int root(int i) {
			while (i != roots[i])  {
				roots[i] = roots[roots[i]];
				i = roots[i];
			}
			return i;
		}
		
		
		//use bfs to get the root of a node
		private int rootByBfs(int i) {
			HashSet<Integer> visited = new HashSet<Integer>();
			Queue<Integer> q = new Queue<Integer>();
			q.enqueue(i);
			int last;
			visited.add(i);
			while (!q.isEmpty()) {
				last = q.dequeue().intValue();
				
			}
		} 
		
	   // returns all WordNet nouns
	   public Iterable<String> nouns() {
		   return noun2IdMap.keySet();
	   }
	
	   // is the word a WordNet noun?
	   public boolean isNoun(String word) {
		   nullChecker(word);
		   return noun2IdMap.keySet().contains(word);
	   }
	
	   // distance between nounA and nounB (defined below)
	   public int distance(String nounA, String nounB) {
		   nullChecker(nounA);
		   nullChecker(nounB);
		   if (!(isNoun(nounA) && isNoun(nounB))) throw new java.lang.IllegalArgumentException();
		   
	   }
	
	   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	   // in a shortest ancestral path (defined below)
	   public String sap(String nounA, String nounB) {
		   nullChecker(nounA);
		   nullChecker(nounB);
		   if (!(isNoun(nounA) && isNoun(nounB))) throw new java.lang.IllegalArgumentException();
		   
		   
	   }
	   
	   // do unit testing of this class
	   public static void main(String[] args) {
		   
		   
	   }
	   
	   //throw a nullpnt exception when parameter is null
	   private void nullChecker(String s) {
		   if (s == null) throw new java.lang.NullPointerException();
	   }
}
