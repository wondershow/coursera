package class1.hw;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAPTestCase {

	@Test
	public void testLengthAncestor() {
		Digraph graph;
		SAP sap;
		
		/*  *
	    *
	    *
	    **/
		graph = new Digraph(new In("./src/wordnet/digraph1.txt"));
	    sap = new SAP(graph);
	    assertEquals(0, sap.length(3, 3));
	    assertEquals(3, sap.ancestor(3, 3));
	    
	    
	    graph = new Digraph(new In("./src/wordnet/digraph5.txt"));
	    sap = new SAP(graph);
	    assertEquals(0, sap.length(10, 10));
	    assertEquals(10, sap.ancestor(10, 10));
	    
	    
	  
	    
	    
	    
	    graph = new Digraph(new In("./src/wordnet/digraph2.txt"));
	    sap = new SAP(graph);
	    assertEquals(4, sap.length(2, 0));
	    assertEquals(0, sap.ancestor(2, 0));
	    
	    
	    graph = new Digraph(new In("./src/wordnet/digraph3.txt"));
	    sap = new SAP(graph);
	    for (int i = 0; i < 100; i++) {
	    	System.out.println("i = " + i);
	    	assertEquals(3, sap.length(10, 7));
	    }
	    	//assertEquals(8, sap.ancestor(7, 10));
	    
	    
	    
	    
	    graph = new Digraph(new In("./src/wordnet/digraph4.txt"));
	    sap = new SAP(graph);
	    assertEquals(1, sap.length(0, 8));
	    assertEquals(8, sap.ancestor(0, 8));
	    
	    
	    
	    graph = new Digraph(new In("./src/wordnet/digraph6.txt"));
	    sap = new SAP(graph);
	    assertEquals(2, sap.length(3, 5));
	    assertEquals(4, sap.ancestor(3, 5));
	    
	    
	    graph = new Digraph(new In("./src/wordnet/digraph9.txt"));
	    sap = new SAP(graph);
	    assertEquals(2, sap.length(1, 6));
	    assertEquals(3, sap.ancestor(1, 6));
	    
	}
	
}
