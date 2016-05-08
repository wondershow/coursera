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
    	
    	String s = BinaryStdIn.readString();
    	int N = s.length();
    	int[] next = new int[s.length()];
    	char[] firstColumn = new char[s.length()];
    	int[] count = new int[R + 1];
    	
    	for (int i = 0; i < N; i++) count[s.charAt(i) + 1]++;
    	for (int i = 0; i <	R; i++) count[i + 1] += count[i];
    	for (int i = 0; i < N; i++)
    	{
    		char c = s.charAt(i);
    		int index = count[c]++;
    		firstColumn[index] = c;
    		next[index] = i;
    	}
    	
    	for (int i = 0 ; i< N; i++)
    	{
    		BinaryStdOut.write(firstColumn[first]);
    		first = next[first];
    	}
    	
    	BinaryStdOut.flush();
        BinaryStdOut.close();
    }
    
    private static int[] sortArr(CharWithIndex[] nodes)
    {
    	int N = nodes.length;
    	int[] index = new int[N];
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
    		index[i] = aux[i].index;
    	}
    	return index;
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
