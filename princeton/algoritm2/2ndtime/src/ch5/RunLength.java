package ch5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class RunLength
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public static void compress()
	{
		char cnt = 0;
		boolean b, old = false;
		while (!BinaryStdIn.isEmpty())
		{
			b = BinaryStdIn.readBoolean();
			if (b != old)
			{
				BinaryStdOut.write(cnt, 8);
				cnt = 0;
				old = b;
			}
			else 
			{
				if (cnt == 255)
				{
					BinaryStdOut.write(cnt, 8);
					cnt = 0;
					BinaryStdOut.write(cnt, 8);
				}
			}
			cnt++;
		}
		BinaryStdOut.write(cnt);
		BinaryStdOut.close();
	}
	
	public static void expand()
	{
		boolean b = false;
		while (!BinaryStdIn.isEmpty())
		{
			char cnt = BinaryStdIn.readChar();
			for (int i = 0; i < cnt; i++)
				BinaryStdOut.write(b);
			b = !b;
		}
		BinaryStdOut.close();
	}
}
