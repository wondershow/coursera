package class1.hw;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Bag;

public class WordNet {
	private HashMap<String, Bag<Integer>> noun2IdMap;
	private HashMap<Integer, String> id2StringMap;
	private int size; // number of sysnets
	private Digraph wordDigraph;
	private HashMap<String, Integer> distanceCache;
	private HashMap<String, String> ancestorCache;
	private SAP sap;
	
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		checkNullParam(synsets);
		checkNullParam(hypernyms);
		
		noun2IdMap = new HashMap<String, Bag<Integer>>();
		id2StringMap = new HashMap<Integer,String>();
		
		distanceCache = new HashMap<String, Integer>();
		ancestorCache = new HashMap<String, String>();
		
		
		
		
		size = 0;
		
		In in;
		try {   
            in = new In(synsets);
            while (!in.isEmpty()) {
                String line = in.readLine();
                size++;
                String[] tmpArr = line.split(",");
                int id = Integer.parseInt(tmpArr[0]);
                
                String[] nouns = tmpArr[1].split(" ");
                Bag<Integer> idsOfNoun;
                Bag<String> nounsOfId;
                
                for (int i = 0; i < nouns.length; i++) {
                	if (noun2IdMap.containsKey(nouns[i]))
                		idsOfNoun = noun2IdMap.get(nouns[i]);
                	else
                		idsOfNoun = new Bag<Integer>();
                	idsOfNoun.add(id);
                	noun2IdMap.put(nouns[i], idsOfNoun);
                }
                
                id2StringMap.put(id, tmpArr[1]);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        //System.out.println("Finished importing synsets");	
        
        
        wordDigraph = new Digraph(size);
        
        try {
            in = new In(hypernyms);
            while (!in.isEmpty()) {
                String line = in.readLine();
                String[] tmpArr = line.split(",");
                int childId = Integer.parseInt(tmpArr[0]);
                for (int i = 1; i < tmpArr.length; i++) {
                	int parentId = Integer.parseInt(tmpArr[i]);
                	wordDigraph.addEdge(childId, parentId);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        //System.out.println("done");
        
        if (!isRootedDAG(wordDigraph)) throw new java.lang.IllegalArgumentException();
        
        sap = new SAP(wordDigraph);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		   return noun2IdMap.keySet();
	}
	
	// is the word a WordNet noun?
	public boolean isNoun(String word){
		checkNullParam(word);
		return noun2IdMap.containsKey(word);
	}
	
	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB){
		checkNullParam(nounA);
		checkNullParam(nounB);
		checkNoun(nounA);
		checkNoun(nounB);
		
		
		Bag<Integer> aIds = noun2IdMap.get(nounA);
		Bag<Integer> bIds = noun2IdMap.get(nounB);
		
		String hash = hashOfVertices(aIds, bIds);
		
		if (distanceCache.containsKey(hash))
			return distanceCache.get(hash).intValue();
		
		int res =sap.length(aIds, bIds);
		//int res = sap.ancestor(aIds, bIds);
		distanceCache.put(hash, res);
		return res;
	}
	
	/*
	private int mutualAncester(Iterable<Integer> aIds, Iterable<Integer> bIds) {
		int min = Integer.MAX_VALUE;
		int res = -1;
		
		aBfs.initialize(aIds);
		bBfs.initialize(bIds);
		
		for (int v : topOrder) {
			if (aBfs.distTo(v) >=0 && bBfs.distTo(v) >=0 && (aBfs.distTo(v) + bBfs.distTo(v)) < min )
			{	min = aBfs.distTo(v) + bBfs.distTo(v); res = v;  if (min == 0) break;}
		}
		return res;
	}*/

	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB){
		checkNullParam(nounA);
		checkNullParam(nounB);
		
		checkNoun(nounA);
		checkNoun(nounB);
		
		
		Bag<Integer> aIds = noun2IdMap.get(nounA);
		Bag<Integer> bIds = noun2IdMap.get(nounB);
		
		String hash = hashOfVertices(aIds, bIds);
		if (ancestorCache.containsKey(hash))
			return ancestorCache.get(hash);
		
		int v = sap.ancestor(aIds, bIds);
		String res = id2StringMap.get(v);
		ancestorCache.put(hash, res);
		
		return res;
	}
	
	// do unit testing of this class
	public static void main(String[] args){
		WordNet wn = new WordNet("/Users/leizhang/Documents/workspace/Princeton/src/wordnet/synsets15.txt",
								 "/Users/leizhang/Documents/workspace/Princeton/src/wordnet/hypernyms15Path.txt");
		
		String dist = wn.sap("b", "c");
		System.out.println("result:" + dist);
		
		int[] src = new int [20];
		for (int i = 0;i < 20; i++) src[i] = i;
		System.arraycopy(src, 5, src, 0, 10);
		for (int i = 0;i < 20; i++) System.out.println(src[i]);
	}
	
	private void checkNullParam(Object o) {
		if (o == null) throw new java.lang.NullPointerException();
	}
	
	private boolean isRootedDAG(Digraph G) {
		int zeroOut = 0;
		
		for (int v = 0; v < G.V(); v++)
			if (G.outdegree(v) == 0) zeroOut++;
		
		if (zeroOut != 1) return false;
		return true;
	}
	
	private void checkNoun(String s) {
		if (!noun2IdMap.containsKey(s))
			throw new java.lang.IllegalArgumentException();
	}
	
	private String hashOfVertices(int a, int b) {
		String str = "";
		if (a > b)
			str = a + "," + b;
		else
			str = b + "," + a;
		return str;
	}
	
	private String hashOfVertices(Bag<Integer> a, Bag<Integer> b) {
		
		String res = "";
		
		if (a.size() == 1 && b.size() == 1) {
			int c1 = a.iterator().next();
			int c2 = b.iterator().next();
			return c1 > c2 ? c1 + "," + c2 : c2 + ":" + c1;
		}
		
		
		Iterator<Integer> it = a.iterator();
		while (it.hasNext()) { res += it.next()+",";}
		res += "+";
		it = b.iterator();
		while (it.hasNext()) {  res += it.next()+",";}
		
		return res;
	}
}