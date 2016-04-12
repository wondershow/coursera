
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.IndexMinPQ;

public class MoveToFront {
	private static IndexMinPQ<Integer> mapping;
	
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
    	encode1();
    }
    
    private static void encode2() {
    	init();
    	StringBuilder sb = new StringBuilder("");
    	Set<Character> set = new HashSet<Character>();
    	while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            System.out.println(c);
            BinaryStdOut.write((char) mapping.keyOf((int)c).intValue());
            //System.out.println(c);
            mapping.changeKey((int)c, mapping.minKey()-1);
            sb.append(c);
            set.add(c);
    	}
    	
    	String fullStr = sb.toString();
    	
    	for (int i = 0; i< fullStr.length(); i++) {
    		
    		
    	}
    	
    	
    	int i = set.size();
    	System.out.println("size = " + i);
    	while (i > 0) {
    		BinaryStdOut.write((char)mapping.delMin());
    		i--;
    	}
    	BinaryStdOut.flush();
    }
    
    private static void encode1() {
    	char[] mapping = new char[256];
    	//System.out.println();
    	for (int i = 0; i < mapping.length; i++) {
    		mapping[i] = (char)i;
    		//System.out.print(mapping[i]);
    	}
    	//System.out.println();
    	while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int pos = -1;
            for(int i = 0; i < mapping.length; i++)
            	if (mapping[i] == c) {pos = i; break;}
            //System.out.println("pos = " + pos);
            BinaryStdOut.write((byte)pos);
            if (pos > 0) {
            	for(int i = pos; i >= 1; i-- )
            		mapping[i] = mapping[i -1];
            	mapping[0] = c;
            }
    	}
    	BinaryStdOut.flush();
    }
    
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	//init();
    	decode1();
    }
    
    
    private static void decode1() {
    	char[] mapping = new char[256];
    	System.out.println();
    	for (int i = 0; i < mapping.length; i++) {
    		mapping[i] = (char)i;
    		//System.out.print(mapping[i]);
    	}
    	while (!BinaryStdIn.isEmpty()) {
            int pos = BinaryStdIn.readByte();
            //for(int i = 0; i < mapping.length; i++)
            	//if (mapping[i] == c) {pos = i; break;}
            //System.out.println("pos = " + pos);
            BinaryStdOut.write((char) (mapping[pos] & 0xff));
            char c = mapping[pos];
            if (pos > 0) {
            	for(int i = pos; i >= 1; i-- )
            		mapping[i] = mapping[i -1];
            	mapping[0] = c;
            }
    	}
    	BinaryStdOut.flush();
    	System.out.println();
    	System.out.println();
    }

    private static void init() {
    	mapping = new IndexMinPQ<Integer>(256);
    	for (int i = 0; i < 256; i++) 
    		mapping.insert(i, i);
    	/*
    	Iterator<Integer> it = mapping.iterator();
    	while (it.hasNext())
    		System.out.print((char)it.next().intValue());*/
    	/*
    	System.out.println("1:" + (char)mapping.minKey().intValue()+"1");
    	mapping.changeKey(67, mapping.minKey() - 1);
    	mapping.changeKey(68, mapping.minKey() - 1);
    	mapping.changeKey(69, mapping.minKey() - 1);
    	mapping.changeKey(70, mapping.minKey() - 1);*/
    	//System.out.println("2:" + (char)mapping.delMin()+"2");
    	/*
    	while (!mapping.isEmpty())
    		System.out.print((char)mapping.delMin());*/
    	//System.out.println((char)(67));
    	//System.out.println(mapping.minKey());
    	//System.out.println(mapping.size());
    	
    } 
    
    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
    	
    	if (args[0].equals("-")) encode();
    	if (args[0].equals("+")) decode();
    	int i = 0;
    	//while (true)
    		//System.out.println(args[i++]);
    	
    	//System.out.println(BinaryStdIn.readChar());
    	//BinaryStdIn.readString();
    	
    	while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(c);
        }
    	
    	BinaryStdOut.flush();
    	
    	//String test = BinaryStdIn.readString("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/homewok/src/hw5/src/burrows/abra.txt");
    }
}
