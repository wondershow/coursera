package class5.hw;

import java.util.Arrays;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler
{
	private static int R = 256;
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
    	
    	int[] t  = new int[N];
    	for (int i = 0; i < N; i++) t[i] = s.charAt(i);
    	int[] next = new int[N];
    	Arrays.fill(next, -1);
    	
    	
    	int[] firstChars = sortArr(t, N, s);
    	
    	for (int i = 0; i < N; i++)
    	{
    		char c = (char)firstChars[i];
    		int j = 0;
    		for (j = 0; j < N; j++)
    		{
    			if (t[j] == c) 
    			{
    				t[j] = -1;
    				next[i] = j; 
    				break; 
    			}
    		}
    		//System.out.println(c + " : " +  "j = " + j + ", t[j] = " + t[j]);
    	}
    	
    	/*
    	for (int i =0; i < N; i++)
    		System.out.println(i + " : " + next[i]);*/
    	
    	int index = first;
    	int len = 1;
    	while (true) 
    	{
    		char c = (char)firstChars[index];
    		BinaryStdOut.write(c, 8);
    		index = next[index];
    		if (len == N ) break;
    		len++;
    	}
    	
    	//for (int i = 0; i < N; i++)
    		//System.out.println(next[i]);
    	BinaryStdOut.flush();
        BinaryStdOut.close();
    }
    
    private static int[] sortArr(int[] t, int N, String s)
    {
    	int[] res = new int[N];
    	
    	int[] count = new int[R+1];
    	
    	for (int i = 0; i < N; i++)
    		count[s.charAt(i) + 1]++;
    	for (int i = 1; i <= R; i++)
    		count[i] += count[i-1];
    	for (int i = 0; i <N; i++)
    	{
    		char c = (char)t[i];
    		res[count[(int)c]++] = c;
    	}
    	/*
    	for (int i = 0; i < N; i++)
    		System.out.println(res[i]);*/
    	
    	return res;
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
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
