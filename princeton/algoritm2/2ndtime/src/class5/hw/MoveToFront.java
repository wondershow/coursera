package class5.hw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront
{
	private static final int R = 256;
	public static void main(String[] args)
	{
		if (args.length < 1) throw new java.lang.NullPointerException();
		if (args.length > 2 && args[1] != null && args[1].equals("<") && args[2] != null ) {
			
		    try {
		        System.setIn(new FileInputStream(args[2]));
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }
		}
		
		if (args[0].equals("-"))
			encode();
		else
		if (args[0].equals("+"))
			decode();
		else
			throw new java.lang.IllegalArgumentException();
	}
	
	public static void encode() 
	{
		//System.out.println("here1");
		char[] mapping = new char[256]; 
		
		int i = 0;
		for ( i = 0; i < mapping.length; i++)
			mapping[i] = (char)i;
		
		while (!BinaryStdIn.isEmpty())
		{
			char c = BinaryStdIn.readChar(8);
			
			//System.out.println(c);
			i = 0;
			while (mapping[i] != c ) i++;
			BinaryStdOut.write(i, 8);
			while (i >= 1) { mapping[i] = mapping[i-1]; i--; }
			mapping[0] = c;
		}
		BinaryStdOut.flush();
        BinaryStdOut.close();
	}
	
	
	public static void decode()
	{
		//System.out.println("there2");
		char[] mapping = new char[256]; 
		int i = 0;
		for ( i = 0; i < mapping.length; i++)
			mapping[i] = (char)i;
		
		while (!BinaryStdIn.isEmpty())
		{
			int c = BinaryStdIn.readInt(8);
			BinaryStdOut.write(mapping [c]);
			char tmp = mapping [c];
			i = c;
			while (i>0) { mapping[i] = mapping[i-1]; i--;}
			mapping[i] = tmp;
		}
		BinaryStdOut.flush();
        BinaryStdOut.close();
	}
}
