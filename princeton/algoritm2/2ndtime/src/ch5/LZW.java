package ch5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.TST;

public class LZW
{
	private static final int R = 256;
	private static final int L = 4096;
	private static final int W = 12;
	
	
	public static void main(String[] args)
	{
		
	}

	public static void compress()
	{
		String input = BinaryStdIn.readString();
		//char[] input = s.toCharArray();
		TST<Integer> st = new TST<Integer>();
		
		for (int i = 0; i < R; i++)
			st.put(""+i,  i);
		
		int code = R + 1;
		
		while (input.length() >0)
		{
			String s = st.longestPrefixOf(input);
			BinaryStdOut.write(st.get(s), W);
			int t = s.length();
			if (t < input.length() && code < L)
				st.put(input.substring(0, t+1), code++);
			input = input.substring(t);
		}
		BinaryStdOut.write(R);
		BinaryStdOut.close();
	}
	
	public static void expand() 
	{
		String[] st = new String[L];
		
		int i;
		
		for (i = 0; i < R; i++)
			st[i] = " " + (char) i;
		st[i++] = " "; //codeword for EOF
		
		int codeWord = BinaryStdIn.readInt(W);
		String val = st[codeWord];
		
		while (true) 
		{
			BinaryStdOut.write(val);
			codeWord = BinaryStdIn.readInt(W);
			if (codeWord == R) break;
			String s = st[codeWord];
			if (i == codeWord)
				s = val + val.charAt(0);
			if (i < L)
				st[i++] = val + s.charAt(0);
			val = s;
		}
		
		BinaryStdOut.close();
	}
}
