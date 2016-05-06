package class5.hw;

import java.util.Arrays;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler
{
	private static int R = 256;
	
	private static class CharWithIndex
	{
		public char c;
		public int index;
		
		public CharWithIndex(char ch, int i)
		{
			c = ch;
			index = i;
		}
	}
	
	
	// apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode()
    {
    	String s = BinaryStdIn.readString();
    	int N = s.length();
    	
    	CircularSuffixArray csa = new CircularSuffixArray(s);
    	
    	int first = -1;
    	for (int i = 0; i < N; i++)
    		if (csa.index(i) == 0) { first = i; break; }
    	
    	BinaryStdOut.write(first, 32);
    	
    	for (int i = 0; i < N; i++)
    	{
    		int shift = csa.index(i);
    		BinaryStdOut.write( s.charAt( (N-1 + shift) % N), 8);
    	}
    	
    	BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    
    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode()
    {
    	int first = BinaryStdIn.readInt(32);
    	
    	//System.out.println("first = " + first);
    	
    	String s = BinaryStdIn.readString();
    	int N = s.length();
    	CharWithIndex[] cws = new CharWithIndex[N];
    	
    	for (int i = 0; i < N; i++) 
    		cws[i] = new CharWithIndex(s.charAt(i), i);
    	
    	sortArr(cws);
    	
    	int[] next = new int[N];
    	
    	for (int i = 0; i < N; i++)
    		next[i] = cws[i].index;
    		//System.out.println(c + " : " +  "j = " + j + ", t[j] = " + t[j]);
    	
    	/*
    	for (int i =0; i < N; i++)
    		System.out.println(i + " : " + next[i]);*/
    	
    	int index = first;
    	int len = 1;
    	while (true) 
    	{
    		char c = cws[index].c;
    		BinaryStdOut.write(c, 8);
    		index = next[index];
    		if (len == N ) break;
    		len++;
    	}
    	
    	BinaryStdOut.flush();
        BinaryStdOut.close();
    }
    
    private static void sortArr(CharWithIndex[] nodes)
    {
    	
    	int N = nodes.length;
    	CharWithIndex[] aux = new CharWithIndex[N];
    	
    	int[] count = new int[R+1];
    	for (int i = 0; i < N; i++)
    		count[nodes[i].c + 1]++;
    	for (int i = 1; i <= R; i++)
    		count[i] += count[i-1];
    	for (int i = 0; i <N; i++)
    	{
    		char c = nodes[i].c;
    		aux[count[(int)c]++] = nodes[i];
    	}
    	
    	for (int i = 0; i < N; i++)
    	{
    		nodes[i] = aux[i];
    	}
    	/*
    	for (int i = 0; i < N; i++)
    		System.out.println(res[i]);*/
    	
    }

    public static void main(String[] args)
    {
    	if (args.length < 1) throw new java.lang.NullPointerException();
		if (args[0].equals("-"))
			encode();
		else
		if (args[0].equals("+"))
			decode();
		else
			throw new java.lang.IllegalArgumentException();
	}
    	
}
