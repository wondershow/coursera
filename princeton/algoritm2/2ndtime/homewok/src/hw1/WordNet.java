import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {
	private HashMap<Integer, String> id2NounMap;
	private HashMap<String, Bag<Integer>> noun2IdMap;
	private SAP sap;
	//public SAP sap;
	private Digraph graph;
	private int netSize;
	
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		nullChecker(synsets);
		nullChecker(hypernyms);
		
		id2NounMap = new HashMap<Integer, String>();
		noun2IdMap = new HashMap<String, Bag<Integer>>();
		netSize = 0;
		constructSysnet(synsets);
		
		//System.out.println("netSize = " + netSize);
		graph = new Digraph(netSize);
		constructGraph(hypernyms);
		
		if (!isRootedDAG())
			throw new java.lang.IllegalArgumentException();
		
		sap = new SAP(graph);
	}
	
	
	private boolean isRootedDAG() {
		int numberOf0Degree = 0;
		for (int v = 0; v < graph.V(); v++) {
			int degree = 0;
			for (int w : graph.adj(v))
				degree++;
			if (degree == 0) numberOf0Degree++;
		}
		if (numberOf0Degree != 1) return false;
		DirectedCycle dc = new DirectedCycle(graph);
		return !dc.hasCycle();
	}
	
	private void constructGraph(String file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] fields = line.split(",");
		    	int from = Integer.parseInt(fields[0]);
		    	for (int i = 1; i< fields.length; i++)
		    		graph.addEdge(from, Integer.parseInt(fields[i]));
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void constructSysnet(String file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] fields = line.split(",");
		    	int vertex = Integer.parseInt(fields[0]);
		    	String[] words = fields[1].trim().split(" ");
		    	netSize++;
		    	//System.out.println(netSize + ":" + line);
		    	for (int i = 0; i < words.length; i++) {
		    		id2NounMap.put(vertex, fields[1]);
		    		putWord(words[i], vertex);
		    	}
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void putWord(String word, int vertex) {
		Bag<Integer> ints;
		if (noun2IdMap.containsKey(word)) 
			ints = noun2IdMap.get(word);
		else
			ints = new Bag<Integer>();
		ints.add(vertex);
		noun2IdMap.put(word, ints);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return noun2IdMap.keySet();
	}

	// is the word a WordNet noun?
 	public boolean isNoun(String word) {
 		nullChecker(word);
 		return noun2IdMap.containsKey(word);
 	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		nullChecker(nounA);
		nullChecker(nounB);
		if (!noun2IdMap.containsKey(nounA) || !noun2IdMap.containsKey(nounB))
			throw new java.lang.IllegalArgumentException();
		return sap.length(noun2IdMap.get(nounA), noun2IdMap.get(nounB));
 	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		nullChecker(nounA);
		nullChecker(nounB);
		if (!noun2IdMap.containsKey(nounA) || !noun2IdMap.containsKey(nounB))
			throw new java.lang.IllegalArgumentException();
		int ancester = sap.ancestor(noun2IdMap.get(nounA), noun2IdMap.get(nounB));
		return id2NounMap.get(ancester);
 	}

	// do unit testing of this class
	public static void main(String[] args) {
		WordNet wordnet = new WordNet("wordnet/synsets11.txt", "wordnet/random.txt");
		System.out.println(wordnet.sap.length(8,9));
 	}
	
	//check if a parameter is null
	private void nullChecker(String p) {
		if (p == null) throw new java.lang.NullPointerException();
	}
}
